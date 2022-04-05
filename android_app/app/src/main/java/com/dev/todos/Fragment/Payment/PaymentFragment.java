package com.dev.todos.Fragment.Payment;

import android.app.ProgressDialog;
import android.os.Build;
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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Payment.StripeFragment;
import com.dev.todos.Model.GetPaymentMethod.GetPaymentMethodResponse;
import com.dev.todos.Model.TodosCommission.ComRequest;
import com.dev.todos.Model.TodosCommission.ComResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentPaymentBinding;

import java.nio.channels.ScatteringByteChannel;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentFragment extends Fragment {

    String productInorder, orderValue, shippinCost, travelleReward, taxAndFess, order_offer_id, orderId;
    ProgressDialog progressDialog;

    String strtotalPrice = "0";
    String strreward = "0";
    String strshippingCarge = "0";
    String strtaxAndFess = "0";
    String totalPrice = "0";
    String stringTotal = "";
    AlertDialog alertDialog;
    String Activty = "";
    String bankTransfer = "";
    String payPhone = "";
    String Stripe = "";
    FragmentPaymentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        progressDialog = UseMe.showProgressDialog(getActivity());
        bundle = getArguments();
        try {
            productInorder = bundle.getString(Keys.productInorder);
            orderValue = bundle.getString(Keys.total_order_price);
            shippinCost = bundle.getString(Keys.shipping_cost);
            travelleReward = bundle.getString(Keys.reward);
            taxAndFess = bundle.getString(Keys.taxes_fees);
            orderId = bundle.getString(Keys.order_id);
            order_offer_id = bundle.getString(Keys.offer_order_id);
            Activty = bundle.getString("Activity");

            //  double totalOrderValue = Double.parseDouble(orderValue.trim()) - Double.parseDouble(travelleReward.trim());

            binding.txtOrderValue.setText("$" + orderValue);

            strtotalPrice = "" + orderValue.trim();
            strreward = "" + travelleReward.trim();
            strshippingCarge = "" + shippinCost.trim();
            strtaxAndFess = "" + taxAndFess.trim();


            binding.txtProductsInOrder.setText(productInorder);
            binding.txtReward.setText("$" + travelleReward);
            binding.txtSheepingCost.setText("$" + shippinCost);
            orderId = bundle.getString(Keys.order_id);


            binding.stripeRB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getTotal("stripe", Stripe);
                }
            });

            binding.bankTransferRB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getTotal("bankTransfer", bankTransfer);
                }
            });


            binding.payPhoneRB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getTotal("payphone", payPhone);
                }
            });


        } catch (Exception e) {
        }

        getPaymentMethod();


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.btnPAy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                        // Do something for lollipop and above versions
                        if (binding.bankTransferRB.isChecked()) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(Keys.order_id, orderId);
                            bundle1.putString(Keys.offer_order_id, order_offer_id);
                            bundle1.putString("Total", binding.txtTotal.getText().toString());
                            bundle1.putString("Activity", Activty);
                            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getActivity().getSupportFragmentManager(), bundle1, R.id.frameLayoout, new BankTransferFragment());
                        } else if (binding.stripeRB.isChecked()) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(Keys.order_id, orderId);
                            bundle1.putString(Keys.offer_order_id, order_offer_id);
                            bundle1.putString("Total", binding.txtTotal.getText().toString());
                            bundle1.putString("Activity", Activty);

                            Toast.makeText(getActivity(),"order_id "+orderId,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"Total "+binding.txtTotal.getText().toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getActivity().getSupportFragmentManager(),
                                    bundle1, R.id.frameLayoout, new StripeFragment());
                        } else if (binding.payPhoneRB.isChecked()) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(Keys.order_id, orderId);
                            bundle1.putString("Total", binding.txtTotal.getText().toString());
                            bundle1.putString(Keys.offer_order_id, order_offer_id);
                            bundle1.putString("Activity", order_offer_id);

                            Toast.makeText(getActivity(),"order_id "+orderId,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"Total "+binding.txtTotal.getText().toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
//                        new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getActivity().getSupportFragmentManager(),
                                    bundle1, R.id.frameLayoout, new PayPhoneFragment());
                        }

                    } else {
                        // do something for phones running an SDK before lollipop
                        if (binding.bankTransferRB.isChecked()) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(Keys.order_id, orderId);
                            bundle1.putString(Keys.offer_order_id, order_offer_id);
                            bundle1.putString("Total", binding.txtTotal.getText().toString().trim().replace("$ ", ""));
                            bundle1.putString("Activity", Activty);
                            Toast.makeText(getActivity(),"order_idelse "+orderId,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"Total "+binding.txtTotal.getText().toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle1, R.id.frameLayoout, new BankTransferFragment());
                        } else if (binding.stripeRB.isChecked()) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(Keys.order_id, orderId);
                            bundle1.putString(Keys.offer_order_id, order_offer_id);
                            bundle1.putString("Total", binding.txtTotal.getText().toString().trim().replace("$ ", ""));
                            bundle1.putString("Activity", Activty);
                            Toast.makeText(getActivity(),"order_idelse "+orderId,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"Total "+binding.txtTotal.getText().toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                                    bundle1, R.id.frameLayoout, new StripeFragment());
                        } else if (binding.payPhoneRB.isChecked()) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(Keys.order_id, orderId);
                            bundle1.putString("Total", binding.txtTotal.getText().toString().trim().replace("$ ", ""));
                            bundle1.putString(Keys.offer_order_id, order_offer_id);
                            bundle1.putString("Activity", Activty);
                            Toast.makeText(getActivity(),"order_idelse "+orderId,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"Total "+binding.txtTotal.getText().toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"order_offer_id "+order_offer_id,Toast.LENGTH_LONG).show();
                            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                                    bundle1, R.id.frameLayoout, new PayPhoneFragment());
                        }
                    }

                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void getPaymentMethod() {

        Call<GetPaymentMethodResponse> getPaymentMethodResponseCall = ApiClient.getService().getpaymentmethod();
        getPaymentMethodResponseCall.enqueue(new Callback<GetPaymentMethodResponse>() {
            @Override
            public void onResponse(Call<GetPaymentMethodResponse> call, Response<GetPaymentMethodResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {

                        bankTransfer = response.body().getBankTransferFees();
                        Stripe = response.body().getStripeCreditCardFees();
                        payPhone = response.body().getPayphoneCreditCardFees();

                        if (response.body().getBankTransferStatus().equals("True")) {
                            binding.bankTransferRB.setChecked(true);
                            binding.bankTransferRB.setVisibility(View.VISIBLE);
                            getTotal("bankTransfer", bankTransfer);
                        } else {
                            binding.bankTransferRB.setVisibility(View.INVISIBLE);
                        }

                        if (response.body().getStripeAccountStatus().equals("True")) {
                            binding.stripeRB.setVisibility(View.VISIBLE);
                            binding.credit.setVisibility(View.VISIBLE);

                        } else {
                            binding.stripeRB.setVisibility(View.INVISIBLE);
                            binding.credit.setVisibility(View.INVISIBLE);
                        }

                        if (response.body().getPayPhoneAccountStatus().equals("True")) {
                            binding.payPhoneRB.setVisibility(View.VISIBLE);
                            binding.imgPayphone.setVisibility(View.VISIBLE);
                            binding.imgPayphone1.setVisibility(View.VISIBLE);
                        } else {
                            binding.payPhoneRB.setVisibility(View.INVISIBLE);
                            binding.imgPayphone.setVisibility(View.INVISIBLE);
                            binding.imgPayphone1.setVisibility(View.INVISIBLE);

                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<GetPaymentMethodResponse> call, Throwable t) {

            }
        });
    }

    public void getTotal(String transfer, String bankTransfer) {

        stringTotal = "";

        if (transfer.equals("bankTransfer")) {

            binding.taxesTxt.setText(getString(R.string.tando));
            Float taxes = Float.valueOf(taxAndFess) + Float.valueOf(bankTransfer);
            binding.txtTaxFees.setText("$" + new DecimalFormat("##.##",new DecimalFormatSymbols(Locale.ENGLISH)).format(taxes));

            Float total = Float.parseFloat(strreward)
                    + taxes
                    + Float.parseFloat(strtotalPrice)
                    + Float.parseFloat(strshippingCarge);
            Log.d("TAG", "getTotal: " + total + "--" + strreward + "--" + strtaxAndFess + "---" + strshippingCarge + "--" + taxes);
            setCommission(total);
        } else if (transfer.equals("payphone")) {
            binding.taxesTxt.setText(getString(R.string.taxes));
            Float taxes = Float.valueOf(taxAndFess);

            Float total = Float.parseFloat(strreward)
                    + taxes
                    + Float.parseFloat(strtotalPrice)
                    + Float.parseFloat(strshippingCarge);

            setCommission(total);
            Log.d("TAG", "getTotal: " + total + "--" + strreward + "--" + strtaxAndFess + "---" + strshippingCarge + "--" + taxes);


        } else if (transfer.equals("stripe")) {
            binding.taxesTxt.setText(getString(R.string.taxes));
            Float taxes = Float.valueOf(taxAndFess);
            binding.txtTaxFees.setText("$" + taxes);

            Float total = Float.parseFloat(strreward)
                    + taxes
                    + Float.parseFloat(strtotalPrice)
                    + Float.parseFloat(strshippingCarge);
            Log.d("TAG", "getTotal: " + total + "--" + strreward + "--" + strtaxAndFess + "---" + strshippingCarge + "--" + taxes);
            setCommission(total);
        }



        /*  Log.d("TAG", "getTotal: "+total);*/

        /*Float withCommision = total + total * per / 100;
        binding.txtTotal.setText(String.valueOf(withCommision));*/
    }

    public void setCommission(Float total) {

        progressDialog.show();
        ComRequest comRequest = new ComRequest();
        comRequest.setAmount(String.valueOf(strreward));

        Call<ComResponse> responseCall = ApiClient.getService().comission(comRequest);
        responseCall.enqueue(new Callback<ComResponse>() {
            @Override
            public void onResponse(Call<ComResponse> call, Response<ComResponse> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("1")) {
                            binding.txtTodosCommsion.setText("$ " + response.body().getIcurierCommission());
                            Float Total = Float.parseFloat(response.body().getIcurierCommission()) + total;
                            stringTotal = String.valueOf(Total);
                            binding.txtTotal.setText(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(Total));


                            if (binding.payPhoneRB.isChecked()) {
                                Float totalMain = (Float.parseFloat(stringTotal)) * Integer.parseInt(payPhone) / 100;
                                Float taxes = Float.valueOf(taxAndFess);
                                Float mainTaxes = taxes + totalMain;

                                Log.e("total value", total + "");
                                Log.e("total value1", taxes + "");
                                Log.e("total value2", stringTotal + "");
                                Log.e("total value3", payPhone + "");
                                Log.e("total value4", mainTaxes + "");
                                binding.txtTaxFees.setText("$" + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(mainTaxes));
                                Log.e("total value5", "$" + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(mainTaxes) + "");

                                float totalNew = Float.parseFloat(binding.txtOrderValue.getText().toString().replace("$", "").trim())
                                        +
                                        Float.parseFloat(binding.txtReward.getText().toString().replace("$", "").trim())
                                        +
                                        Float.parseFloat(binding.txtSheepingCost.getText().toString().replace("$", "").trim())
                                        + mainTaxes
//                                    Float.parseFloat(binding.txtTaxFees.getText().toString().replace("$","").trim())
                                        +
                                        Float.parseFloat(binding.txtTodosCommsion.getText().toString().replace("$", "").trim());
                                binding.txtTotal.setText(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(totalNew));
                                Log.d("TAG", "getTotal:::::" + Total);
                            } else if (binding.stripeRB.isChecked()) {
                                Float totalMain = (Float.parseFloat(stringTotal)) * Integer.parseInt(Stripe) / 100;
                                Float taxes = Float.valueOf(taxAndFess);
                                Float mainTaxes = taxes + totalMain;
                                binding.txtTaxFees.setText("$" + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(mainTaxes));

                                Float Total1 = Total + mainTaxes;


                                float totalNew = Float.parseFloat(binding.txtOrderValue.getText().toString().replace("$", "").trim())
                                        +
                                        Float.parseFloat(binding.txtReward.getText().toString().replace("$", "").trim())
                                        +
                                        Float.parseFloat(binding.txtSheepingCost.getText().toString().replace("$", "").trim())
                                        + mainTaxes
//                                        Float.parseFloat(binding.txtTaxFees.getText().toString().replace("$","").trim())
                                        +
                                        Float.parseFloat(binding.txtTodosCommsion.getText().toString().replace("$", "").trim());
                                binding.txtTotal.setText(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH)).format(totalNew));
                                Log.d("TAG", "getTotal:::::" + totalNew);
                            }

                        }
                    } else {
                        Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error in amount"+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ComResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

}