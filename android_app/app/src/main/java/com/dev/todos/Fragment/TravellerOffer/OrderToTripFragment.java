package com.dev.todos.Fragment.TravellerOffer;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Model.SingleorderList.OrderDetails;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.ImageSlider;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentOrderToTripBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderToTripFragment extends Fragment {
    ViewPager viewPager;
    ArrayList<String> imges = new ArrayList<>();
    String orderId,travelDate;
    Bundle bundle;
    KProgressHUD hud;
    List<ProductListItem> productList;
    FragmentOrderToTripBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_order_to_trip,container,false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getApicall();
        getSingleOrderList(UseMe.getJsonObject(jsonObject1));

        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager=view.findViewById(R.id.viewPager);
        imges=new ArrayList<>();
        // viewPager.setAdapter(new ImageSlider(getActivity()));


        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1=new Bundle();
                bundle1.putString(Keys.order_id,orderId);
                bundle1.putString(Keys.delivery_to,binding.txtDestinationCity.getText().toString());
                bundle1.putString(Keys.delivery_from,binding.txtOriginCity.getText().toString());
                bundle1.putString(Keys.travel_date,travelDate);
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                        bundle1,R.id.frameLayoout,new PurchaseMadeFragment());
            }
        });

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFragmentManager().popBackStackImmediate();

            }
        });

        view.findViewById(R.id.llOederToManage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new OrderToTripFragment());*/
            }
        });

        view.findViewById(R.id.llOrderDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new ClsOrdeDeliveredFrag());*/
            }
        });
        view.findViewById(R.id.llSatisfiedUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new ClsSatisfiedBuyerFrag());*/
            }
        });

    }

    public void getSingleOrderList(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<SingleOrderResponse> orderListResponceCall = ApiClient.getService().getSingleDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderResponse>() {
            @Override
            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    productList = new ArrayList<>();
                    productList = response.body().getOrderDetails().getProductList();
                    setDataByView(response.body().getOrderDetails());
                } else {
                    productList = new ArrayList<>();
                }
            }

            @Override
            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                hud.dismiss();


            }
        });

    }

    private void setDataByView(OrderDetails orderDetails) {
        try {

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date d= simpleDateFormat.parse(orderDetails.getProductList().get(0).getCreatedDate());
            simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String date=  simpleDateFormat.format(d);

         /*   SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
            Date d1= simpleDateFormat1.parse(Sharedpreferences.readFromPreferences(getActivity(), Keys.travel_date,""));
            simpleDateFormat1=new SimpleDateFormat("MMM dd,yyyy");
            String date1=  simpleDateFormat1.format(d1);*/

            binding.txtName.setText(orderDetails.getUserName());
            binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
            binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
            binding.txtDate.setText(Sharedpreferences.readFromPreferences(getActivity(), Keys.travel_date,""));
            binding.txtOrderOn.setText(getString(R.string.order_on_new)  + date);
            binding.txtProductName.setText(orderDetails.getProductList().get(0).getArticleName());
            binding.txtReward.setText("$ "+orderDetails.getTotalDeliveryReward());
            binding.txtProductName.setText(getString(R.string.top) +orderDetails.getTotalOrderPrice());
            BaseActivity.setProfile(getContext(),orderDetails.getUserImage(), binding.imgProfile);
            int noOFOrder = orderDetails.getProductList().size();
            binding.txtNoOrder.setText(getString(R.string.noofproduct) + noOFOrder);
            orderId = Sharedpreferences.readFromPreferences(getContext(),Keys.order_id,"");
            travelDate=date;

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

                binding.travellerProfileLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("travellerId",orderDetails.getUserId());
                        new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout, new TravellerProfileFragment());
                    }
                });

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



            // getOrderStatus();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getApicall() {




        OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
        orderStatusRequest.setOrderId(Sharedpreferences.readFromPreferences(getContext(),Keys.order_id,""));

        Call<OrderStatusResponse> orderStatusResponseCall = ApiClient.getService().getOrderStatus(orderStatusRequest);
        orderStatusResponseCall.enqueue(new Callback<OrderStatusResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {

                if(response.isSuccessful()){
                    if (response.body().getStatus().equals(Keys.status_succes)) {


                        try {
                            if (response.body().getOrderInfoStatus().equals("no_info")) {

                                binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));


                            } else if (response.body().getOrderInfoStatus().equals("purchase_made")) {
                                binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                                binding.dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv2.setTextColor(getResources().getColor(R.color.btn_color));


                            } else if (response.body().getOrderInfoStatus().equals("order_delivered")) {
                                binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                                binding.dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv2.setTextColor(getResources().getColor(R.color.btn_color));

                                binding.dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv3.setTextColor(getResources().getColor(R.color.btn_color));
                            } else if (response.body().getOrderInfoStatus().equals("satisfied_buyer")) {
                                binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                                binding.dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv2.setTextColor(getResources().getColor(R.color.btn_color));

                                binding.dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                                binding.tv3.setTextColor(getResources().getColor(R.color.btn_color));
                            }
                        }catch (Exception e){}

                    } else {
                        //Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
                hud.dismiss();
            }
        });


    }


}