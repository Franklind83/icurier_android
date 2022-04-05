package com.dev.todos.Fragment.TripAndOffers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Activity.MainActivity;
import com.dev.todos.Activity.TermsActivity;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.More.ProfileFragment;
import com.dev.todos.Model.GetProfileData.GetProfileRequest;
import com.dev.todos.Model.GetProfileData.GetProfileResponse;
import com.dev.todos.Model.Logout.LogoutRequest;
import com.dev.todos.Model.Logout.LogoutResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SingleOrderListResponce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentEditOfferBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditOfferFragment extends Fragment {

    FragmentEditOfferBinding binding;
    KProgressHUD hud;
    Snackbar snackBar;
    String OrderId="";
    String deliveryDate="";
    String changedDate="";
    String orderofferId = "";
    androidx.appcompat.app.AlertDialog alertDialog;
    SingleOrderListResponce.OrderDetalis orderDetalis;
    ArrayList<SingleOrderListResponce.OrderOfferList> order_offer_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_offer, container, false);

        return binding.getRoot();}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = getArguments();
        OrderId = bundle.getString(Keys.order_id);
        orderofferId = bundle.getString(Keys.offer_order_id);

        onClickListener();
        getProfiledata();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.order_id, OrderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSingleOrderList(UseMe.getJsonObject(jsonObject));

    }


    private void onClickListener() {
        binding.termTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsActivity.class);
                intent.putExtra("Activity","Terms");
                startActivity(intent);
            }
        });
        binding.EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new ProfileFragment());
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.logoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.logouttxt));
            builder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                dialog.dismiss();
                try {
                    logoutCall();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        });

        binding.makeofferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.checkbox.isChecked()) {

                    if(binding.edtReward.getText().toString().isEmpty()){
                        binding.edtReward.setError("Enter rewards");
                    }else{
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, ""));
                            jsonObject.put(Keys.order_id, OrderId);
                            jsonObject.put(Keys.offer_order_id, orderofferId);
                            jsonObject.put(Keys.reward, binding.edtReward.getText().toString());
                            jsonObject.put(Keys.delivery_date, deliveryDate);
                            jsonObject.put("terms_and_condition", "1");
                            jsonObject.put("shipping_cost",binding.shippingEt.getText().toString());
                            jsonObject.put("taxes_fees", binding.taxEt.getText().toString());
                        }catch (Exception e){}

                        makeApicall(UseMe.getJsonObject(jsonObject));
                    }

                }else {
                    Toast.makeText(getActivity(), getString(R.string.accept_term_and_condition), Toast.LENGTH_SHORT).show();
                }
            }
            });

    }

    private void makeApicall(JsonObject jsonObject) {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        Call<Responce> responseCall = ApiClient.getService().editoffer(jsonObject);
        responseCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                       setalertDialog(getString(R.string.offerupdate));
                    }else{
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });


    }

    private void getProfiledata() {
        hud=  KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        GetProfileRequest getProfileRequest = new GetProfileRequest();
        getProfileRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));

        Call<GetProfileResponse> getProfileResponseCall = ApiClient.getService().profile(getProfileRequest);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    if(response.body().getStatus().equals("1")){
                        binding.usernameTxt.setText(response.body().getUserDetails().getName());
                        binding.phoneTxt.setText(response.body().getUserDetails().getMobile());
                        binding.emailTxt.setText(response.body().getUserDetails().getEmail());

                    }
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }

    private void logoutCall() {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setDevice("android");
        logoutRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));


        Call<LogoutResponse> logoutResponseCall  = ApiClient.getService().logout(logoutRequest);
        logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();

                    if(response.body().getStatus().equals("1")){
//                        Log.e("lang15",Sharedpreferences.readFromPreferences(getContext(), AppConstant.APPLANGUAGE,""));
//                        String language = Sharedpreferences.readFromPreferences(getContext(),AppConstant.APPLANGUAGE,"en");
//                        Sharedpreferences.clearData(getActivity());
//                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.APPLANGUAGE,language);
//                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.lang_val,"true");
                        disconnectFromFacebook();

                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                hud.dismiss();
            }
        });

    }

    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }

    public void getSingleOrderList(JsonObject jsonObject) {

        Call<SingleOrderListResponce> orderListResponceCall = ApiClient.getService().getSingleOrderDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderListResponce>() {
            @Override
            public void onResponse(Call<SingleOrderListResponce> call, Response<SingleOrderListResponce> response) {

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    order_offer_list= response.body().getOrder_details().getOrder_offer_list();
                    orderDetalis = response.body().getOrder_details();
                    setDataByView();
                }
            }

            @Override
            public void onFailure(Call<SingleOrderListResponce> call, Throwable t) {
            }
        });

    }

    private void setDataByView() {

        for(int i=0;i< order_offer_list.size();i++){
            if(Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERID,"").equals(order_offer_list.get(i).getUser_id())){
                binding.edtReward.setText(order_offer_list.get(i).getReward());
                binding.shippingEt.setText(order_offer_list.get(i).getShipping_cost());
                binding.taxEt.setText(order_offer_list.get(i).getTaxes_fees());

                 deliveryDate = order_offer_list.get(i).getDelivery_date();

                Log.d("TAG", "setOnItemClickListener: "+deliveryDate);
                SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
                Date d = null;
                try {
                    d = dateFormatprev.parse(deliveryDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
                changedDate = dateFormat.format(d);

                binding.buyerTxt.setText(getString(R.string.hello)+" "+Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERNAME,"")+" "+getString(R.string.planatrip) +" "+
                        " "+order_offer_list.get(i).getDelivery_from()+" "+getString(R.string.totxt)+" "+order_offer_list.get(i).getDelivery_to()+" "+getString(R.string.fortxt) +" "+changedDate+" "+getString(R.string.acceptmyyoffer));

                break;
            }

        }



    }

    public void setalertDialog(String mes){
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
                getFragmentManager().popBackStackImmediate();

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}