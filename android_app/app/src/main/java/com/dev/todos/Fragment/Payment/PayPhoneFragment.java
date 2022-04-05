package com.dev.todos.Fragment.Payment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Model.ApiPayPhone.PaymentPayPhoneRequest;
import com.dev.todos.Model.ApiPayPhone.PaymentPayPhoneResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SaveAmountPayPhone.PayPhoneRequest;
import com.dev.todos.Model.SaveAmountPayPhone.PayPhoneResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentPayPhoneBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayPhoneFragment extends Fragment {

    FragmentPayPhoneBinding binding;
    String Amt="",Orderid="",Activity="",Offerid="";
    KProgressHUD hud;
    AlertDialog alertDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_phone, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        Orderid = bundle.getString(Keys.order_id);
        Activity = bundle.getString("Activity");
        Offerid = bundle.getString(Keys.offer_order_id);
        Amt = bundle.getString("Total");

        Log.d("TAG", "onViewCreated: "+Amt);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });


        getApiCall();


    }

    private void setApiCall(String message) {

        if (message.equals("0")) {
            setalertDialog("Unable to process your request. Please try after some time", "S");
        } else {
            try {
                hud = KProgressHUD.create(getActivity())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait...")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
            } catch (Exception e) {
            }

            PaymentPayPhoneRequest paymentPayPhoneRequest = new PaymentPayPhoneRequest();
            paymentPayPhoneRequest.setOrderId(Orderid);
            paymentPayPhoneRequest.setId(message);
            paymentPayPhoneRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID, ""));
            paymentPayPhoneRequest.setAmount(Amt);

            Call<PaymentPayPhoneResponse> paymentPayPhoneResponseCall = ApiClient.getService().payment(paymentPayPhoneRequest);
            paymentPayPhoneResponseCall.enqueue(new Callback<PaymentPayPhoneResponse>() {
                @Override
                public void onResponse(Call<PaymentPayPhoneResponse> call, Response<PaymentPayPhoneResponse> response) {
                    hud.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("1")) {
                            if (response.body().getTransactionStatus().equals("Canceled")) {
                                setalertDialog("Unable to process your request. Please try after some time", "S");
                            } else {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put(Keys.offer_order_id, Offerid);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                acceptOrder(UseMe.getJsonObject(jsonObject));
                                // setalertDialog(response.body().getMsg(),"S");
                            }
                        } else {
                            setalertDialog("Unable to process your request. Please try after some time", "S");
                        }
                    } else {
                        setalertDialog("Unable to process your request. Please try after some time", "S");
                    }
                }

                @Override
                public void onFailure(Call<PaymentPayPhoneResponse> call, Throwable t) {
                    hud.dismiss();
                }
            });

        }
    }

    private void getApiCall() {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        PayPhoneRequest payPhoneRequest = new PayPhoneRequest();
        payPhoneRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));
        payPhoneRequest.setOrderId(Orderid);
        payPhoneRequest.setAmountToPay(Amt);

        Call<PayPhoneResponse> payPhoneResponseCall = ApiClient.getService().savePayPhone(payPhoneRequest);
        payPhoneResponseCall.enqueue(new Callback<PayPhoneResponse>() {
            @SuppressLint("JavascriptInterface")
            @Override
            public void onResponse(Call<PayPhoneResponse> call, Response<PayPhoneResponse> response) {
                hud.dismiss();
                if(response.isSuccessful()){

                    if(response.body().getStatus().equals("1")){

                        Toast.makeText(getActivity(),"Url "+response.body().getPaymentCheckoutUrl(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"Userid "+Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""),Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"Order id "+Orderid,Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"Amount "+Amt,Toast.LENGTH_LONG).show();
                        binding.webView.setWebViewClient(new WebViewClient());
                        binding.webView.addJavascriptInterface(this, "android");
                        binding.webView.loadUrl(response.body().getPaymentCheckoutUrl());
                        WebSettings webSettings = binding.webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        binding.webView.setWebChromeClient(new WebChromeClient() {
                            @Override
                            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                                Log.d("WebView", consoleMessage.message());
                                boolean bar_ok = consoleMessage.message().matches("^[0-9]*$");
                                if(bar_ok) {
                                    setApiCall(consoleMessage.message());
                                }

                                return true;
                            }
                        });



                    }
                }
            }

            @Override
            public void onFailure(Call<PayPhoneResponse> call, Throwable t) {
                hud.dismiss();
            }
        });
    }


    public void setalertDialog(String mes, String s){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                if(s.equals("S")){
                    try {
                        UseMe.clearAllFragment(getFragmentManager());
                    }catch (Exception e){}

                    if(Activity.equals("FromTraveller")){
                        new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                                null, R.id.frameLayoout, new TravellerFragment());
                    }else{
                        new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                                null, R.id.frameLayoout, new MyOrderFragment());
                    }

                }else{
                    getFragmentManager().popBackStackImmediate();
                }


            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    public void acceptOrder(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<Responce> responceCall = ApiClient.getService().acceptOrder(jsonObject);


        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();


                String result = response.body().getMsg();
                if (response.body().getStatus().equals(Keys.status_succes)) {

                    setalertDialog(getString(R.string.offeraccepted),"S");



                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();

            }
        });
    }
}