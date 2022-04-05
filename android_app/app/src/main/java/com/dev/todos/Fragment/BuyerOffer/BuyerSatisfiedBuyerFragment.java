package com.dev.todos.Fragment.BuyerOffer;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;

import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Model.DeliveryStatus.DeliveryStatusRequest;
import com.dev.todos.Model.DeliveryStatus.DeliveryStatusResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SingleorderList.OrderDetails;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
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
import com.dev.todos.databinding.FragmentBuyerSatisfiedBuyerBinding;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyerSatisfiedBuyerFragment extends Fragment {


    Button btnFollowing;
    ProgressDialog progressDialog;
    List<ProductListItem> productList;
    ViewPager viewPager;

    String orderStatus = "";
    androidx.appcompat.app.AlertDialog alertDialog = null;
    String userId = "";
    String traveller_id = "";

    ArrayList<String> imges;
    String orderId,travelDate;
    OrderDetails orderDetails;


    KProgressHUD hud1,hud;
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

    FragmentBuyerSatisfiedBuyerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   return inflater.inflate(R.layout., container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buyer_satisfied_buyer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //   sliderLayout = view.findViewById(R.id.imageSlider);
        txtName = view.findViewById(R.id.txtName);
        txtOriginCity = view.findViewById(R.id.txtOriginCity);
        txtDestinationCity = view.findViewById(R.id.txtDestinationCity);
        profile = view.findViewById(R.id.imgProfile);
        txtDate = view.findViewById(R.id.txtDate);
        txtOrderOn = view.findViewById(R.id.txtOrderOn);
        tvReward = view.findViewById(R.id.txtReward);
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

        viewPager=view.findViewById(R.id.viewPager);
        imges=new ArrayList<>();

        getApiCall();

        view.findViewById(R.id.llOederToManage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new BuyerOrderToTripFragment());
            }
        });

        view.findViewById(R.id.llPurchaseMade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new BuyerPurchaseMadeFragment());
            }
        });

        view.findViewById(R.id.llOrderDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new BuyerOrderDeliverFragment());
            }
        });

        binding.travellerProfileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("travellerId",orderDetails.getUserId());
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout, new TravellerProfileFragment());
            }
        });


        String mydate = "";


        try {
            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
            Date d1= null;
            try {
                d1 = simpleDateFormat1.parse(Sharedpreferences.readFromPreferences(getContext(), Keys.travel_date,""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            simpleDateFormat1=new SimpleDateFormat("MMM dd, yyyy");
            mydate=  simpleDateFormat1.format(d1);
        } catch (Exception e) {

        }


/*

        txtName.setText(StaticTransitData.UserName);

        txtDate.setText(mydate);
        txtOrderOn.setText(getString(R.string.order_on_new) + mydate);
        tvReward.setText("$" + StaticTransitData.Reward);
        tvTotalPrice.setText(getString(R.string.top) + StaticTransitData.TotalPrice);
        UseMe.setImage(getContext(), StaticTransitData.ProfileImage, profile);
        tvNumberOfProducts.setText(getString(R.string.noofproduct) + StaticTransitData.NoOfProducts);
*/



        binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
        binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
        binding.txtDate.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,""));

        progressDialog = UseMe.showProgressDialog(getActivity());


        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });

        binding.travellerProfileLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("travellerId",orderDetails.getUserId());
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout, new TravellerProfileFragment());
            }
        });


        btnFollowing = view.findViewById(R.id.btnFollowing);


        if(Sharedpreferences.readFromPreferences(getActivity(),AppConstant.OFFERSTATUS,"").equals("satisfied_buyer")){
            binding.btnFollowing.setVisibility(View.GONE);
        }else{
            binding.btnFollowing.setVisibility(View.VISIBLE);
        }


        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addConfirmReceipt();

            }
        });

    }




    private void addConfirmReceipt() {

        progressDialog.show();

        DeliveryStatusRequest addSatisfiedRequest = new DeliveryStatusRequest();
        addSatisfiedRequest.setOrderId(Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));


        Call<DeliveryStatusResponse> responceCall = ApiClient.getService().deliverystatus(addSatisfiedRequest);
        responceCall.enqueue(new Callback<DeliveryStatusResponse>() {
            @Override
            public void onResponse(Call<DeliveryStatusResponse> call, Response<DeliveryStatusResponse> response) {
                progressDialog.dismiss();


                String result = response.body().getMsg();

                if (response.body().getStatus().equals(Keys.status_succes)) {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.feedback_dialog, null);
        final TextView tvDesc = view.findViewById(R.id.tvDesc);
        CircularImageView imgProfile = view.findViewById(R.id.imgProfile);
        final AppCompatRatingBar rating1 = view.findViewById(R.id.rating1);
        final EditText edFeedBack = view.findViewById(R.id.edFeedBack);
        Button btnSubmit = view.findViewById(R.id.btnSend);
        UseMe.setImage(getContext(),Sharedpreferences.readFromPreferences(getActivity(),Keys.profileImage,""), imgProfile);
        if(StaticTransitData.fromWhom.equals("fromtraveller")){
            tvDesc.setText(getString(R.string.helptraveller));
        }else{
            tvDesc.setText(getString(R.string.helpbuyer));
        }
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

                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<DeliveryStatusResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


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

        if(StaticTransitData.fromWhom.equals("fromtraveller")) {
            try {
                jsonObject.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(), Keys.order_id,""));
                jsonObject.put(Keys.date, StaticClass.getDate());
                jsonObject.put("rating_for", "t");
                jsonObject.put("user_id",traveller_id);
                jsonObject.put("rating", "" + rating);
                jsonObject.put("review", strFeedback);
                jsonObject.put("rating_from_user_id",userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                jsonObject.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(), Keys.order_id,""));
                jsonObject.put(Keys.date, StaticClass.getDate());
                jsonObject.put("rating_for", "b");
                jsonObject.put("user_id",userId);
                jsonObject.put("rating", "" + rating);
                jsonObject.put("review", strFeedback);
                jsonObject.put("rating_from_user_id",traveller_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




        Call<Responce> responceCall = ApiClient.getService().addRating(UseMe.getJsonObject(jsonObject));
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();

                String result = response.body().getMsg();

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    setalertDialog(getString(R.string.thnakyou));

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

  /*  private void getConfirmStatus() {

        OrderInfoRequest orderInfoRequest = new OrderInfoRequest();
        orderInfoRequest.setOrderId(Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));

        Call<OrderInfoResponse> responceCall = ApiClient.getService().orderinfo(orderInfoRequest);
        responceCall.enqueue(new Callback<OrderInfoResponse>() {
            @Override
            public void onResponse(Call<OrderInfoResponse> call, Response<OrderInfoResponse> response) {
                progressDialog.dismiss();


                String result = response.body().getMsg();

                if (response.body().getStatus().equals(Keys.status_succes)) {

                    orderStatus = response.body().getOrderStatus();
                    traveller_id = response.body().getTravellerId();

                    if (orderStatus.equals("pending")) {
                        btnFollowing.setVisibility(View.VISIBLE);
                        btnFollowing.setText("Confirm Receipt");
                    }

                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<OrderInfoResponse> call, Throwable t) {
                progressDialog.dismiss();
              //  UseMe.internateErrorDialog(getActivity());
            }
        });


    }*/

    @Override
    public void onStart() {

        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSingleOrderList(UseMe.getJsonObject(jsonObject1));

        super.onStart();
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
                        new MyOrderFragment());
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    public void getSingleOrderList(JsonObject jsonObject) {

        Call<SingleOrderResponse> orderListResponceCall = ApiClient.getService().getSingleDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderResponse>() {
            @Override
            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    orderDetails = new OrderDetails();
                    orderDetails = response.body().getOrderDetails();
                    setDataByView(response.body().getOrderDetails());
                }
            }

            @Override
            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {

            }
        });

    }

    private void setDataByView(OrderDetails orderDetails) {
        try {

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date d= simpleDateFormat.parse(orderDetails.getProductList().get(0).getCreatedDate());
            simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String date=  simpleDateFormat.format(d);

            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
            Date d1= simpleDateFormat1.parse(orderDetails.getProductList().get(0).getCreatedDate());
            simpleDateFormat1=new SimpleDateFormat("MMM dd, yyyy");
            final String date1=  simpleDateFormat1.format(d1);

            binding.txtName.setText(orderDetails.getUserName());
            binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
            binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
            binding.txtDate.setText(date1);
            binding.txtOrderOn.setText(getString(R.string.order_on_new) + date);
            binding.txtReward.setText("$ "+orderDetails.getTotalDeliveryReward());
            binding.tvTotalPrice.setText(getString(R.string.top) +getPriceTotal(Double.parseDouble(orderDetails.getTotalDeliveryReward()),orderDetails.getTotalOrderPrice()));
            BaseActivity.setProfile(getContext(),orderDetails.getUserImage(), binding.imgProfile);
            int noOFOrder = orderDetails.getProductList().size();

            binding.cardView.setVisibility(View.VISIBLE);
            binding.tvNumberOfProducts.setText(getString(R.string.noofproduct) + noOFOrder);
           // orderId = Sharedpreferences.readFromPreferences(getContext(),Keys.order_id,"");
           // travelDate=date;

            try {
                if(orderDetails.getProductList().size() == 1){
                    for (int i = 0; i < orderDetails.getProductList().get(0).getProductImageList().size(); i++) {
                        if (!orderDetails.getProductList().get(0).getProductImageList().get(i).equals("")){
                            imges.add(orderDetails.getProductList().get(0).getProductImageList().get(i));
                        }
                    }

                }else {
                    for(int j=0;j<orderDetails.getProductList().size();j++) {
                        for (int i = 0; i <orderDetails.getProductList().get(j).getProductImageList().size(); i++) {
                            if (!orderDetails.getProductList().get(j).getProductImageList().get(i).equals("")) {
                                imges.add(orderDetails.getProductList().get(j).getProductImageList().get(i));
                            }
                        }
                    }
                }
               /* ArrayList<String> strings = new ArrayList<>();
                strings = (ArrayList<String>) orderDetails.getProductList().get(0).getProductImageList();

                for (int i=0;i<strings.size();i++){
                    if (!strings.get(i).equals("")){
                        imges.add(strings.get(i));
                    }
                }*/
                viewPager.setAdapter(new ImageSlider(getActivity(),imges));
                binding.pagerIndicator.setPager(viewPager);
            }catch (Exception e){
                e.printStackTrace();
            }

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



            // getOrderStatus();

        }catch (Exception e){
            e.printStackTrace();
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
                        userId = response.body().getBuyerId();
                        traveller_id = response.body().getTravellerId();

                        Log.d("TAG", "onResponse: "+userId+"---"+traveller_id);
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                hud.dismiss();
            }
        });
    }

    private String getPriceTotal(double price, String total_order_price) {

        double mainprice =0;
        mainprice = price + Double.parseDouble(total_order_price);
        // price = total + Double.parseDouble(total_order_price);
        Log.d("TAG", "onBindViewHolder: 1"+price);

        return String.valueOf(mainprice);
    }
}