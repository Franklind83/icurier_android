package com.dev.todos.Fragment.BuyerOffer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.TravellerOffer.SatisfiedBuyerFragment;
import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Model.SingleorderList.OrderDetails;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.ImageSlider;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentBuyerOrderToTripBinding;
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


public class BuyerOrderToTripFragment extends Fragment {

    ViewPager viewPager;
    ArrayList<String> imges;
    String orderId,travelDate;
    Bundle bundle;
    KProgressHUD hud;
    OrderDetails orderDetails;
    List<ProductListItem> productList;

    FragmentBuyerOrderToTripBinding binding;
    CardView dot1, dot2, dot3, dot4;
    View view1, view2, view3, view4;
    TextView tv1, tv2, tv3, tv4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_buyer_order_to_trip,container,false);
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
        getSingleOrderList(UseMe.getJsonObject(jsonObject1));

        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        // viewPager.setAdapter(new ImageSlider(getActivity()));




        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getFragmentManager().popBackStackImmediate();

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



      /*  view.findViewById(R.id.llPurchaseMade).setOnClickListener(new View.OnClickListener() {
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
        view.findViewById(R.id.llSatisfiedUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new SatisfiedBuyerFragment());
            }
        });
*/
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
                    orderDetails = new OrderDetails();
                    orderDetails = response.body().getOrderDetails();
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

            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
            Date d1= simpleDateFormat1.parse(orderDetails.getProductList().get(0).getCreatedDate());
            simpleDateFormat1=new SimpleDateFormat("MMM dd, yyyy");
            final String date1=  simpleDateFormat1.format(d1);

            binding.txtName.setText(orderDetails.getUserName());
            binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
            binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
            binding.txtDate.setText(date1);
            binding.txtorderNo.setText(getString(R.string.ordersid)+orderDetails.getOrderId());
            binding.txtOrderOn.setText(getString(R.string.order_on_new) + date);
            binding.txtReward.setText("$ "+orderDetails.getTotalDeliveryReward());
            binding.txtProductName.setText(getString(R.string.top) +getPriceTotal(Double.parseDouble(orderDetails.getTotalDeliveryReward()),orderDetails.getTotalOrderPrice()));
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

    private String getPriceTotal(double price, String total_order_price) {

        double mainprice =0;
        mainprice = price + Double.parseDouble(total_order_price);
        // price = total + Double.parseDouble(total_order_price);
        Log.d("TAG", "onBindViewHolder: 1"+price);

        return String.valueOf(mainprice);
    }



}