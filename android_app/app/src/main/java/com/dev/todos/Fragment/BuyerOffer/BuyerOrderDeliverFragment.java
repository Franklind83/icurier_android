package com.dev.todos.Fragment.BuyerOffer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.TravellerOffer.OrderDetailsFragment;
import com.dev.todos.Fragment.TravellerOffer.OrderToTripFragment;
import com.dev.todos.Fragment.TravellerOffer.PurchaseMadeFragment;
import com.dev.todos.Fragment.TravellerOffer.SatisfiedBuyerFragment;
import com.dev.todos.Model.DeliveryInfo.DeliveryInfoRequest;
import com.dev.todos.Model.DeliveryInfo.DeliveryInfoResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.OrderDelivered;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentBuyerOrderDeliverBinding;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyerOrderDeliverFragment extends Fragment {

    FragmentBuyerOrderDeliverBinding binding;
    String orderId;
    ProgressDialog progressDialog;
    String purchseId;
    boolean isOrderDeliverd;
    Button btnFollowing;
    String deliveryId = "0", delivered_image = "";
    androidx.appcompat.app.AlertDialog alertDialog = null;
    String userId = "";
    EditText edtPersonName;
    EditText edtDocument;
    TextView txtSelectDate;
    ImageView imgSelectDate;
    ImageView imgSelectProduct1;
    ImageView imgProduct1;


    CardView dot1, dot2, dot3, dot4;
    View view1, view2, view3, view4;
    TextView tv1, tv2, tv3, tv4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buyer_order_deliver, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressDialog = UseMe.showProgressDialog(getActivity());
        userId = Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID, "");


        String mydate="";

        try {
            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
            Date d1= simpleDateFormat1.parse(Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,""));
            simpleDateFormat1=new SimpleDateFormat("MMM dd, yyyy");
             mydate=  simpleDateFormat1.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        dot1 = view.findViewById(R.id.dot1);
        dot2 = view.findViewById(R.id.dot2);
        dot3 = view.findViewById(R.id.dot3);
        dot4 = view.findViewById(R.id.dot4);

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);

        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);

        try {

            if (StaticTransitData.offerStatus.trim().equals("no_info")) {

                dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv1.setTextColor(getResources().getColor(R.color.btn_color));

            } else if (StaticTransitData.offerStatus.trim().equals("purchase_made")) {

                dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv1.setTextColor(getResources().getColor(R.color.btn_color));

                dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv2.setTextColor(getResources().getColor(R.color.btn_color));

            } else if (StaticTransitData.offerStatus.trim().equals("order_delivered")) {

                dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv1.setTextColor(getResources().getColor(R.color.btn_color));

                dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv2.setTextColor(getResources().getColor(R.color.btn_color));

                dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv3.setTextColor(getResources().getColor(R.color.btn_color));

            } else if (StaticTransitData.offerStatus.trim().equals("satisfied_buyer")) {

                dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv1.setTextColor(getResources().getColor(R.color.btn_color));

                dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv2.setTextColor(getResources().getColor(R.color.btn_color));

                dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv3.setTextColor(getResources().getColor(R.color.btn_color));

                dot4.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                view4.setBackgroundColor(getResources().getColor(R.color.btn_color));
                tv4.setTextColor(getResources().getColor(R.color.btn_color));

            }

        } catch (Exception e) {
        }

        binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getActivity(), Keys.delivery_from, ""));
        binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(), Keys.delivered_to, ""));
        binding.txtDate.setText(mydate);

        imgProduct1 = view.findViewById(R.id.imgProduct1);
        imgSelectProduct1 = view.findViewById(R.id.imgSelectProduct1);
        imgSelectDate = view.findViewById(R.id.imgSelectDate);
        txtSelectDate = view.findViewById(R.id.txtSelectDate);
        edtDocument = view.findViewById(R.id.edtDocument);
        edtPersonName = view.findViewById(R.id.edtPersonName);
        btnFollowing = view.findViewById(R.id.btnFollowing);


        binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new BuyerPurchaseMadeFragment());
            }
        });

        binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new BuyerOrderToTripFragment());
            }
        });

       /* binding.llSatisfiedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new BuyerSatisfiedBuyerFragment());
            }
        });
*/


        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });


        orderId = Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID, "");


        getDeliveryInfo();


    }


    public void getDeliveryInfo() {
        progressDialog.show();

        DeliveryInfoRequest deliveryInfoRequest = new DeliveryInfoRequest();
        deliveryInfoRequest.setOrderId(Sharedpreferences.readFromPreferences(getActivity(), Keys.order_id, ""));

        Call<DeliveryInfoResponse> deliveryInfoResponceCall = ApiClient.getService().getDelivery(deliveryInfoRequest);
        deliveryInfoResponceCall.enqueue(new Callback<DeliveryInfoResponse>() {
            @Override
            public void onResponse(Call<DeliveryInfoResponse> call, Response<DeliveryInfoResponse> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    String date="";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date d = simpleDateFormat.parse(response.body().getDeliveryData().getDeliveryDate());
                        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                         date = simpleDateFormat.format(d);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 //   deliveryId = response.body().getDeliveryData().getDeliveryId();

                    binding.maintxt.setText(getString(R.string.order_no)+response.body().getDeliveryData().getOrderId()+getString(R.string.hasbeen)+response.body().getDeliveryData().getDeliveredTo()+getString(R.string.docnumber)+response.body().getDeliveryData().getDeliveredPersonDocNo()+getString(R.string.on)+date+".");

                    try {
                        if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("no_info")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));


                        } else if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("purchase_made")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderToTripFragment());
                                }
                            });

                            binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerPurchaseMadeFragment());
                                }
                            });


                        } else if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("order_delivered")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv3.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderToTripFragment());
                                }
                            });

                            binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerPurchaseMadeFragment());
                                }
                            });

                            binding.llOrderDel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderDeliverFragment());
                                }
                            });


                        } else if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("satisfied_buyer")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv3.setTextColor(getResources().getColor(R.color.btn_color));

                            dot4.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view4.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv4.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderToTripFragment());
                                }
                            });

                            binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerPurchaseMadeFragment());
                                }
                            });

                            binding.llOrderDel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderDeliverFragment());
                                }
                            });
                            binding.llSatisfiedUser.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerSatisfiedBuyerFragment());
                                }
                            });

                        }
                    }catch (Exception e){}
                }

            }

            @Override
            public void onFailure(Call<DeliveryInfoResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }

}