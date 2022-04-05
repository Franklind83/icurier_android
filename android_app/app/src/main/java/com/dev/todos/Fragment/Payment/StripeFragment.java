package com.dev.todos.Fragment.Payment;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Model.Payment.PaymentRequest;
import com.dev.todos.Model.Payment.PaymentResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentStripeBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StripeFragment extends Fragment {

    FragmentStripeBinding binding;
    private KProgressHUD hud;
    ProgressDialog progressDialog;
    Card card;
    Stripe stripe;
    AlertDialog alertDialog, alertDialog1;
    String tok = "";
    String stringTotal = "";
    String Ativty = "";
    String order_offer_id, orderId;
    String testStripekey = "pk_test_pk98PdLUobIWtGhCeNKBoNqO";//sk_test_TExmyi0Ph8wSpBF3KY2dP3xy00ST8LxRya";
    String liveStripekey = "pk_live_khZjiG4BjgqgJAU2aMTqxW4K";//sk_live_NXQ5YMfSNPA5xksscIRjFzBg00LIjDGwMp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stripe, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = getArguments();

        try {
            orderId = bundle.getString(Keys.order_id);
            order_offer_id = bundle.getString(Keys.offer_order_id);
            stringTotal = bundle.getString("Total");
            Ativty = bundle.getString("Activity");
        } catch (Exception e) {
        }

        onclicklistener();
    }

    private void onclicklistener() {

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String card = binding.card1number.getText().toString() + binding.card2number.getText().toString() + binding.card3number.getText().toString();
                if (binding.nameExt.getText().toString().isEmpty()) {
                    binding.nameExt.setError("Enter name");
                } else if (binding.card1number.getText().toString().isEmpty()) {
                    binding.card1number.setError("Enter valid card number");
                } else if (binding.card2number.getText().toString().isEmpty()) {
                    binding.card2number.setError("Enter valid card number");
                } else if (binding.card3number.getText().toString().isEmpty()) {
                    binding.card3number.setError("Enter valid card number");
                } else if (card.length() != 16) {
                    binding.card3number.setError("Enter valid card number");
                } else if (binding.monthEtx.getText().toString().isEmpty()) {
                    binding.monthEtx.setError("Enter month");
                } else if (binding.yearEtx.getText().toString().isEmpty()) {
                    binding.yearEtx.setError("Enter year");
                } else if (binding.cvvNumberEtx.getText().toString().isEmpty()) {
                    binding.cvvNumberEtx.setError("Enter cvv");
                } else {
                    getData();
                }
            }
        });
    }

    private void getData() {

        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        String cardNumber = binding.card1number.getText().toString() + binding.card2number.getText().toString() + binding.card3number.getText().toString();
        card = new Card(cardNumber, Integer.valueOf(binding.monthEtx.getText().toString()), Integer.valueOf(binding.yearEtx.getText().toString()), binding.cvvNumberEtx.getText().toString().trim());
        card.validateNumber();
        card.validateCVC();

        stripe = new Stripe(getActivity(), liveStripekey);
//        stripe = new Stripe(getActivity(), testStripekey);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        try {
                            hud.dismiss();
                            // Send token to your server
                            tok = token.getId();

                            payment();
                        }
                        catch (Exception ex){
                            Toast.makeText(getActivity(), ex.getMessage()+" token issue",Toast.LENGTH_LONG).show();
                        }
                    }

                    public void onError(Exception error) {
                        Log.d("TAG", "onError: " + error);
                        hud.dismiss();
                        warningsetalertDialog(error.getLocalizedMessage());
                        Toast.makeText(getActivity(), error.getMessage()+" token issue1",Toast.LENGTH_LONG).show();

                        // openDialog(error.getLocalizedMessage(), "F");
                    }
                }
        );


    }

    public void payment() {
        try {
            hud = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setSource(tok);
            paymentRequest.setOrderId(orderId);
            paymentRequest.setAmount(stringTotal);
            Toast.makeText(getActivity(),"tok "+orderId,Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(),"tok "+stringTotal,Toast.LENGTH_LONG).show();

            paymentRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID, ""));



            Call<PaymentResponse> paymentResponseCall = ApiClient.getService().payment(paymentRequest);
            paymentResponseCall.enqueue(new Callback<PaymentResponse>() {
                @Override
                public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {

                    try {
                        hud.dismiss();
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getStatus().equals("1")) {
                                if (response.body().getTransactionStatus().equals("succeeded")) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put(Keys.offer_order_id, order_offer_id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    acceptOrder(UseMe.getJsonObject(jsonObject));
                                }
                            } else {
                                warningsetalertDialog(response.body().getMsg());
                            }
                        } else {
                            warningsetalertDialog("Some thing went wrong in pay");
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage() + "\nError in stripe payment", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<PaymentResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error in failture", Toast.LENGTH_LONG).show();
                    hud.dismiss();
                }
            });
        }catch (Exception ex)
        {
            Toast.makeText(getActivity(), ex.getMessage()+" payment issue",Toast.LENGTH_LONG).show();
        }
    }

    public void acceptOrder(JsonObject jsonObject) {
        hud = KProgressHUD.create(getActivity())
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

                    setalertDialog(getString(R.string.offeraccepted));


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

    public void setalertDialog(String mes) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                try {
                    UseMe.clearAllFragment(getFragmentManager());
                } catch (Exception e) {
                }

                if (Ativty.equals("FromTraveller")) {
                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                            null, R.id.frameLayoout, new TravellerFragment());
                } else {
                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                            null, R.id.frameLayoout, new MyOrderFragment());
                }

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    public void warningsetalertDialog(String mes) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });

        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.setCancelable(false);
        alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog1.show();
    }

}