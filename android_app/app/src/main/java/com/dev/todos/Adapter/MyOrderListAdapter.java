package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Fragment.BuyerOffer.BuyerOrderDeliverFragment;
import com.dev.todos.Fragment.BuyerOffer.BuyerOrderToTripFragment;
import com.dev.todos.Fragment.BuyerOffer.BuyerPurchaseMadeFragment;
import com.dev.todos.Fragment.BuyerOffer.BuyerSatisfiedBuyerFragment;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.OrderSummaryFragment;
import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Fragment.TripAndOffers.OffersFragment;
import com.dev.todos.Model.GetOrderList.OrderListItem;
import com.dev.todos.Model.GetOrderList.ProductListItem;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.ImageSlider;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.databinding.OrderItemNewBinding;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Fragment.Home.TravellerFragment.TravellerId;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.MyViewHolder> {
    Context context;
    String type;
    FragmentManager fragmentManager;
    List<com.dev.todos.Model.GetOrderList.OrderListItem> orderLists;
    ArrayList<String> sliderImage;
    String userstatus ="";
    OnClick_Event onClick_event;
    OnOrder_Event onOrder_event;
    KProgressHUD hud;
    List<ProductListItem> productLists;
    Boolean traveller_status = false;
    boolean isFromInTransit;
    double total = 0.0;


    public MyOrderListAdapter(Context context, String type, FragmentManager fragmentManager, List<com.dev.todos.Model.GetOrderList.OrderListItem> orderLists){
        this.orderLists=orderLists;
        this.fragmentManager=fragmentManager;
        this.context=context;
        this.type= type;

        sliderImage=new ArrayList<>();
        productLists=new ArrayList<>();

    }

    public interface OnClick_Event {
        void setOnItemClickListener(OrderListItem orderListItem, int postion);
    }

    public void setOnItemClickListener(OnClick_Event onClick_event) {
        this.onClick_event = onClick_event;
    }


    public void setOnOrderItemClickListener(OnOrder_Event onOrder_event) {
        this.onOrder_event = onOrder_event;
    }
    public interface OnOrder_Event {
        void setOnOrderItemClickListener(com.dev.todos.Model.GetOrderList.OrderListItem orderLists, int postion);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderItemNewBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.order_item_new,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Sharedpreferences.saveToPreferences(context,Keys.order_id,orderLists.get(position).getOrderId());



        if(TravellerId.equals("")){
            holder.binding.uncheckedImg.setVisibility(View.GONE);
        }else {
            if (type.equals(Keys.active)) {
                holder.binding.uncheckedImg.setVisibility(View.VISIBLE);
            } else {
                holder.binding.uncheckedImg.setVisibility(View.GONE);
            }
        }

        holder.binding.uncheckedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrder_event.setOnOrderItemClickListener(orderLists.get(position), position);
            }
        });

        try {



            if(type.equals("inactive") || type.equals("accepted") || type.equals("delivered")){
                holder.binding.txtOffers.setVisibility(View.GONE);
                holder.binding.cvEdt.setVisibility(View.GONE);
            }else{
                holder.binding.txtOffers.setVisibility(View.VISIBLE);
                holder.binding.cvEdt.setVisibility(View.VISIBLE);
            }
            BaseActivity.setProfile(context, orderLists.get(position).getUserImage(), holder.binding.imgProfile);
            holder.binding.txtDestinationCity.setText(orderLists.get(position).getProductList().get(0).getLocationTo());
            holder.binding.txtOriginCity.setText(orderLists.get(position).getProductList().get(0).getLocationFrom());
            holder.binding.txtName.setText(orderLists.get(position).getUserName());
            holder.binding.txtNoOfProducts.setText(context.getString(R.string.no_product) +
                    orderLists.get(position).getProductList().size());
            holder.binding.orderid.setText(context.getString(R.string.ordersid)+orderLists.get(position).getOrderId());
            SpannableString content = new SpannableString(orderLists.get(position).getOrderOfferList().size()+" "+context.getString(R.string.offer));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            holder.binding.txtOffers.setText(content);

            for(int i=0; i< orderLists.get(position).getProductList().size();i++){
                total = total + Double.parseDouble(orderLists.get(position).getProductList().get(i).getDeliveryReward());
                Log.d("TAG", "onBindViewHolder: "+total);
            }
            String price = getPriceTotal(Double.parseDouble(orderLists.get(position).getTotalDeliveryReward()),orderLists.get(position).getTotalOrderPrice());

            holder.binding.txtTotalPrice.setText(context.getString(R.string.top) + price);
            total = 0.0;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = simpleDateFormat.parse(orderLists.get(position).getProductList().get(0).getCreatedDate());
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date1 = simpleDateFormat.format(date);
                holder.binding.txtOrderOn.setText(context.getString(R.string.order_on_new)+date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.binding.txtReward.setText("$ "+orderLists.get(position).getTotalDeliveryReward());
            sliderImage=new ArrayList<>();

            if(orderLists.get(position).getProductList().size() == 1){
                for (int i = 0; i <orderLists.get(position).getProductList().get(0).getProductImageList().size() ; i++) {
                    if (!orderLists.get(position).getProductList().get(0).getProductImageList().get(i).equals("")){
                        sliderImage.add(orderLists.get(position).getProductList().get(0).getProductImageList().get(i));
                    }
                }

            }else {
                for(int j=0; j < orderLists.get(position).getProductList().size();j++) {
                    for (int i = 0; i < orderLists.get(position).getProductList().get(j).getProductImageList().size(); i++) {
                        if (!orderLists.get(position).getProductList().get(j).getProductImageList().get(i).equals("")) {
                            sliderImage.add(orderLists.get(position).getProductList().get(j).getProductImageList().get(i));
                        }
                    }
                }
            }


            // UseMe.setImage(context,orderLists.get(position).getProductList().get(0).getProduct_image_list().get(0),holder.binding.imgProduct);
            holder.binding.viewPager.setAdapter(new ImageSlider(context,sliderImage));
            holder.binding.pagerIndicator.setPager(holder.binding.viewPager);


            holder.binding.viewPager.setAdapter(new ImageSlider(context, sliderImage));

            holder.binding.travellerProfileLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("travellerId",orderLists.get(position).getUserId());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new TravellerProfileFragment());
                }
            });

            holder.binding.imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("travellerId",orderLists.get(position).getUserId());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new TravellerProfileFragment());
                }
            });

            holder.binding.cvEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("edited","edited");
                    if (type.equals(Keys.active)) {

                        String userid = Sharedpreferences.readFromPreferences(context, AppConstant.USERID,"");
                        if(orderLists.get(position).getUserId().equals(userid)){
                            userstatus = "true";
                            traveller_status = true;
                        }else{
                            userstatus = "false";
                            traveller_status = false;

                            if(orderLists.get(position).getBringProduct().equals("no")){
                                userstatus = "I Will Bring";
                            }else{
                                userstatus = "SEND MESSAGE";
                            }
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.order_id, orderLists.get(position).getOrderId());
                        bundle.putString(Keys.bringProduct, orderLists.get(position).getBringProduct());
                        bundle.putBoolean(Keys.isFromMyOrder, traveller_status);
                        bundle.putString(Keys.userstatus,userstatus);
                        bundle.putString(Keys.name,orderLists.get(position).getUserName());
                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
                        // bundle.putString(Keys.delivery_deadline,date);
                        try {
                            bundle.putString(Keys.image1, orderLists.get(position).getProductList().get(0).getProductImageList().get(0));
                            bundle.putString(Keys.image2, orderLists.get(position).getProductList().get(0).getProductImageList().get(1));
                            bundle.putString(Keys.image3, orderLists.get(position).getProductList().get(0).getProductImageList().get(2));
                            bundle.putString(Keys.image4, orderLists.get(position).getProductList().get(0).getProductImageList().get(3));
                        }catch (Exception e){}
                        bundle.putString(Keys.reward,orderLists.get(position).getTotalDeliveryReward());
                        Log.d("TAG", "onClick: "+holder.binding.txtTotalPrice.getText().toString());
                        bundle.putString(Keys.total_order_price,holder.binding.txtTotalPrice.getText().toString());
                        bundle.putInt(Keys.noOfOrder,orderLists.get(position).getProductList().size());
                        bundle.putString(Keys.order_id,orderLists.get(position).getOrderId());
                        bundle.putStringArrayList(Keys.imageList,orderLists.get(position).getProductList().get(0).getProductImageList());
                        bundle.putString(Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());

                        //  bundle.putString(Keys.travel_date,orderLists.get(position).getCreated_date());
                        // bundle.putString(Keys.travel_date,travelDate);
//                        sharePreferance.setValue(Keys.order_id,orderLists.get(position).getOrder_id());
//                       // sharePreferance.setValue(Keys.product_id,orderLists.get(position).getProduct_id());
//                        sharePreferance.setValue(Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocation_from());
//                        sharePreferance.setValue(Keys.delivered_to,orderLists.get(position).getProductList().get(0).getLocation_to());
                        //sharePreferance.setValue(Keys.travel_date,orderLists);

                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new OrderSummaryFragment());
                    } else if (type.equals("accepted")) {
                        Bundle bundle=new Bundle();
//                        bundle.putString(Keys.name,orderLists.get(position).getUser_name());
//                        bundle.putString(Keys.image,orderLists.get(position).getUser_image());
//                        bundle.putString(Keys.total_order_price,orderLists.get(position).getTotal_order_price());
//                        bundle.putInt(Keys.noOfOrder,orderLists.get(position).getProductList().size());
//                        bundle.putString(Keys.reward,orderLists.get(position).getTotal_delivery_reward());
//                        bundle.putBoolean(Keys.transit,true);
//                        bundle.putString(Keys.profileImage,orderLists.get(position).getUser_image());
                        bundle.putStringArrayList(Keys.imageList,sliderImage);
                        Sharedpreferences.saveToPreferences(context,Keys.delivered_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        Sharedpreferences.saveToPreferences(context,Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        Sharedpreferences.saveToPreferences(context,Keys.order_id,orderLists.get(position).getOrderId());
                        Sharedpreferences.saveToPreferences(context,Keys.nameOrderManage,orderLists.get(position).getUserName());
                        Sharedpreferences.saveToPreferences(context,Keys.imageOrderManage,orderLists.get(position).getUserImage());
                        Sharedpreferences.saveToPreferences(context,Keys.total_order_price,holder.binding.txtTotalPrice.getText().toString());
                        Sharedpreferences.saveToPreferences(context,Keys.reward,orderLists.get(position).getTotalDeliveryReward());
                        Sharedpreferences.saveToPreferences(context,Keys.noOfOrder,orderLists.get(position).getProductList().size());
                        Sharedpreferences.saveToPreferences(context,Keys.transit,true);

                      //  new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout, new ClsOrderToManageFrag());

                    }


                }
            });

            holder.binding.txtOffers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("OrderId",orderLists.get(position).getOrderId());
                    bundle.putString("Fragment","FromMyOrder");

                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                            new OffersFragment());

                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals(Keys.active)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.reward, orderLists.get(position).getTotalDeliveryReward());
                        bundle.putString(Keys.total_order_price, orderLists.get(position).getTotalOrderPrice());
                        bundle.putString(Keys.order_id, orderLists.get(position).getOrderId());
                        bundle.putString(Keys.productInorder, String.valueOf(orderLists.get(position).getProductList().size()));
                       /* new ChangeFragment(context).changeFragmentWithoutBackStack(fragmentManager, bundle, R.id.frameLayoout,
                                new ClsMyOfferFrag());*/
                    }else if(type.equals("delivered")){

                        Sharedpreferences.saveToPreferences(context,Keys.order_id,orderLists.get(position).getOrderId());
                        Sharedpreferences.saveToPreferences(context,Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        Sharedpreferences.saveToPreferences(context,Keys.delivered_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        Sharedpreferences.saveToPreferences(context,Keys.travel_date,orderLists.get(position).getProductList().get(0).getDeliveryDeadline());
                        Sharedpreferences.saveToPreferences(context,Keys.profileImage,orderLists.get(position).getUserImage());

                        getApicall();
                    }else if (type.equals("accepted")){
                        Sharedpreferences.saveToPreferences(context,Keys.order_id,orderLists.get(position).getOrderId());
                        Sharedpreferences.saveToPreferences(context,Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        Sharedpreferences.saveToPreferences(context,Keys.delivered_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        Sharedpreferences.saveToPreferences(context,Keys.travel_date,orderLists.get(position).getProductList().get(0).getDeliveryDeadline());
                        Sharedpreferences.saveToPreferences(context,Keys.profileImage,orderLists.get(position).getUserImage());

                        getApicall();
                    }
                }
            });
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



    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    private void getApicall() {

        OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
        orderStatusRequest.setOrderId(Sharedpreferences.readFromPreferences(context,Keys.order_id,""));

        Call<OrderStatusResponse> orderStatusResponseCall = ApiClient.getService().getOrderStatus(orderStatusRequest);
        orderStatusResponseCall.enqueue(new Callback<OrderStatusResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {

                if(response.isSuccessful()){
                    if (response.body().getStatus().equals(Keys.status_succes)) {

                        Sharedpreferences.saveToPreferences(context,AppConstant.OFFERSTATUS,response.body().getOrderInfoStatus());

                        if(type.equals("accepted")){

                            StaticTransitData.fromWhom = "fromtraveller";

                            if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("no_info")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerOrderToTripFragment());

                            } else if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("purchase_made")) {

                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerPurchaseMadeFragment());

                            } else if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("order_delivered")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerSatisfiedBuyerFragment());

                            } else if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("satisfied_buyer")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerSatisfiedBuyerFragment());
                            }
                        }else if(type.equals("delivered")){

                            if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("no_info")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerPurchaseMadeFragment());

                            } else if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("purchase_made")) {

                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerOrderDeliverFragment());

                            } else if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("order_delivered")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerSatisfiedBuyerFragment());

                            } else if (Sharedpreferences.readFromPreferences(context, AppConstant.OFFERSTATUS, "").equals("satisfied_buyer")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new BuyerSatisfiedBuyerFragment());
                            }
                        }

                    } else {
                        //Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {

            }
        });


    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        OrderItemNewBinding binding;


        public MyViewHolder(OrderItemNewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
