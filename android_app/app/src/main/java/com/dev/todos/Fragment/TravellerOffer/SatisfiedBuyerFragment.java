package com.dev.todos.Fragment.TravellerOffer;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.TripAndOffers.MyOfferFragment;
import com.dev.todos.Model.DeliveryStatus.DeliveryStatusRequest;
import com.dev.todos.Model.DeliveryStatus.DeliveryStatusResponse;
import com.dev.todos.Model.OrderInfomation.OrderInfoRequest;
import com.dev.todos.Model.OrderInfomation.OrderInfoResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.StatusInformation.StatusResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.ImageSlider;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentSatisfiedBuyerBinding;
import com.google.android.material.slider.Slider;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.internal.util.VolatileSizeArrayList;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SatisfiedBuyerFragment extends Fragment {

    FragmentSatisfiedBuyerBinding binding;
    Button btnFollowing;
    ProgressDialog progressDialog;

    String orderStatus = "";
    androidx.appcompat.app.AlertDialog alertDialog = null;
    String userId = "";
    String traveller_id = "";

    KProgressHUD hud;
    TextView txtName;
    TextView txtOriginCity;
    TextView txtDestinationCity;
    ImageView profile;
    TextView txtDate;
    TextView txtOrderOn;
    TextView tvReward;
    TextView tvTotalPrice;
    TextView tvNumberOfProducts;
    private Slider sliderLayout;

    CardView dot1, dot2, dot3, dot4;
    View view1, view2, view3, view4;
    TextView tv1, tv2, tv3, tv4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_satisfied_buyer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     //   sliderLayout = view.findViewById(R.id.imageSlider);
        txtName = view.findViewById(R.id.txtName);
        txtOriginCity = view.findViewById(R.id.txtOriginCity);
        txtDestinationCity = view.findViewById(R.id.txtDestinationCity);
        profile = view.findViewById(R.id.profileImage);
        txtDate = view.findViewById(R.id.txtDate);
        txtOrderOn = view.findViewById(R.id.txtOrderOn);
        tvReward = view.findViewById(R.id.tvReward);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        tvNumberOfProducts = view.findViewById(R.id.tvNumberOfProducts);



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


        String mydate = "";

      
        try {
             mydate = Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,"");

            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            Date d = simpleDateFormat2.parse(mydate);
            simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy");
            mydate = simpleDateFormat2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        txtName.setText(StaticTransitData.UserName);
        txtOriginCity.setText(StaticTransitData.TravelFrom);
        txtDestinationCity.setText(StaticTransitData.TravelTo);
        txtDate.setText(mydate);
        txtOrderOn.setText(getString(R.string.order_on_new)  + mydate);
        tvReward.setText("$" + StaticTransitData.Reward);
        tvTotalPrice.setText(getString(R.string.top) + StaticTransitData.TotalPrice);
        UseMe.setImage(getContext(), StaticTransitData.ProfileImage, profile);
        tvNumberOfProducts.setText(getString(R.string.noofproduct) + StaticTransitData.NoOfProducts);


        binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
        binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
        binding.txtDate.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,""));

        progressDialog = UseMe.showProgressDialog(getActivity());

        getApiCall();


        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        btnFollowing = view.findViewById(R.id.btnFollowing);




        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addConfirmReceipt();

            }
        });

        view.findViewById(R.id.llOederToManage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new OrderToTripFragment());
            }
        });

        view.findViewById(R.id.llOrderDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new OrderDetailsFragment());
            }
        });

        view.findViewById(R.id.llPurchaseMade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new PurchaseMadeFragment());
            }
        });

    }

    private void getApiCall() {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Call<StatusResponse> responseCall = ApiClient.getService().getstatus(UseMe.getJsonObject(jsonObject));
        responseCall.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                hud.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){

                        traveller_id = response.body().getTravellerId();
                        userId = response.body().getBuyerId();


                        binding.txt.setText(getString(R.string.buyersatis));
                        if(response.body().getOrderStatus().equals("delivered")) {
                            if (response.body().getRatingStatus().equals("notupdated")) {
                                dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                tv1.setTextColor(getResources().getColor(R.color.btn_color));

                                dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                tv2.setTextColor(getResources().getColor(R.color.btn_color));

                                dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                tv3.setTextColor(getResources().getColor(R.color.btn_color));


                                binding.btnFollowing.setVisibility(View.VISIBLE);
                            }else{
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

                                binding.btnFollowing.setVisibility(View.GONE);
                            }
                        }else{
                            try {
                                dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                tv1.setTextColor(getResources().getColor(R.color.btn_color));

                                dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                tv2.setTextColor(getResources().getColor(R.color.btn_color));

                                dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                tv3.setTextColor(getResources().getColor(R.color.btn_color));

                            }catch (Exception e){}

                            binding.txt.setText(getString(R.string.buyerdoesnot));
                            binding.btnFollowing.setVisibility(View.GONE);
                        }
                    }else{

                        try {
                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv3.setTextColor(getResources().getColor(R.color.btn_color));


                        }catch (Exception e){}
                        binding.txt.setText(getString(R.string.buyerdoesnot));
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                hud.dismiss();
            }
        });
    }


    private void addConfirmReceipt() {

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.feedback_dialog, null);
                    final TextView tvDesc = view.findViewById(R.id.tvDesc);
                    CircularImageView imgProfile = view.findViewById(R.id.imgProfile);
                    final AppCompatRatingBar rating1 = view.findViewById(R.id.rating1);
                    final EditText edFeedBack = view.findViewById(R.id.edFeedBack);
                    Button btnSubmit = view.findViewById(R.id.btnSend);
                    UseMe.setImage(getContext(), StaticTransitData.ProfileImage, imgProfile);

                    tvDesc.setText(getString(R.string.helpbuyer));

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();


                            float rating = rating1.getRating();
                            String strFeedback = edFeedBack.getText().toString();

                            if (rating == 0) {
                                Toast.makeText(getContext(), "Please Give Ratings", Toast.LENGTH_SHORT).show();
                            } else if (strFeedback.equals("")) {
                                Toast.makeText(getContext(), "Please Give Some Reviews", Toast.LENGTH_SHORT).show();

                            } else {
                                addFeedBack(rating, strFeedback);

                            }
                        }
                    });
                    builder.setView(view);
                    alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    alertDialog.show();


    }

    private void addFeedBack(float rating, String strFeedback) {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(), Keys.order_id,""));
            jsonObject.put(Keys.date, StaticClass.getDate());
            jsonObject.put("rating_for", "b");
            jsonObject.put("user_id", userId);
            jsonObject.put("rating", "" + rating);
            jsonObject.put("review", strFeedback);
            jsonObject.put("rating_from_user_id",traveller_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Call<Responce> responceCall = ApiClient.getService().addRating(UseMe.getJsonObject(jsonObject));
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();

                String result = response.body().getMsg();

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    setalertDialog(response.body().getMsg());

                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();
              //  UseMe.internateErrorDialog(getActivity());
            }
        });


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
                UseMe.clearAllFragment(getFragmentManager());


                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null, R.id.frameLayoout,
                        new MyOfferFragment());
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}