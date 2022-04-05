package com.dev.todos.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Message.MessageFragment;
import com.dev.todos.Fragment.TravellerOffer.OrderDetailsFragment;
import com.dev.todos.Fragment.TravellerOffer.PurchaseMadeFragment;
import com.dev.todos.Fragment.TravellerOffer.SatisfiedBuyerFragment;
import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Fragment.TripAndOffers.EditOfferFragment;
import com.dev.todos.Fragment.TripAndOffers.MakeOfferFragment;
import com.dev.todos.Model.MyOrderResponce;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.ImageSlider;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.databinding.OfferItemListBinding;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOfferAdapter extends RecyclerView.Adapter<MyOfferAdapter.MyViewHolder> {
    Context context;
    FragmentManager fragmentManager;
    ArrayList<String> sliderImage;
    ArrayList<MyOrderResponce.OfferStatusCount.TravelOderList> travelOderLists;
    boolean isAccepted;
    KProgressHUD hud;

    String type ="";

    StaticTransitData staticTransitData;

    public MyOfferAdapter(Context context, FragmentManager fragmentManager, ArrayList<MyOrderResponce.OfferStatusCount.TravelOderList> travelOderLists,
                          boolean isAccepted, String travelDate, String type){
        this.travelOderLists=travelOderLists;
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.isAccepted=isAccepted;
        this.type = type;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      //  View view= LayoutInflater.from(context).inflate(R.layout.offer_item_list,parent,false);
        OfferItemListBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.offer_item_list,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            if(type.equals("all")){
                holder.binding.cvEdt.setVisibility(View.GONE);
            }else if(type.equals("delivered")){
                holder.binding.cvEdt.setVisibility(View.GONE);
            }else if(type.equals("accepted")){
                holder.binding.cvEdt.setVisibility(View.GONE);
            }else{
                holder.binding.cvEdt.setVisibility(View.VISIBLE);
            }


            holder.binding.travellerProfileLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("travellerId",travelOderLists.get(position).getUser_id());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new TravellerProfileFragment());
                }
            });

            holder.binding.imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("travellerId",travelOderLists.get(position).getUser_id());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new TravellerProfileFragment());
                }
            });

            holder.binding.cvEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.order_id, travelOderLists.get(position).getOrder_id());
                    bundle.putString(Keys.offer_order_id,travelOderLists.get(position).getOffer_order_id());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                            new EditOfferFragment());
                }
            });


            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date d= simpleDateFormat.parse(travelOderLists.get(position).getProduct_list().get(0).getCreated_date());
            simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
          final String date=  simpleDateFormat.format(d);
            holder.binding.txtOrderOn.setText(context.getString(R.string.order_on)+": "+date );

            holder.binding.orderid.setText(context.getString(R.string.ordersid)+travelOderLists.get(position).getOrder_id());
            holder.binding.txtName.setText(travelOderLists.get(position).getUser_name());
            holder.binding.txtDestinationCity.setText(travelOderLists.get(position).getProduct_list().get(0).getLocation_to());
            holder.binding.txtOriginCity.setText(travelOderLists.get(position).getProduct_list().get(0).getLocation_from());
            holder.binding.txtNoOfProducts.setText(context.getString(R.string.no_product)+travelOderLists.get(position).getProduct_list().size());
            Double total = 0.0;
            total = Double.valueOf(travelOderLists.get(position).getTotal_order_price())+ Double.valueOf(travelOderLists.get(position).getTotal_delivery_reward());
            holder.binding.txtTotalPrice.setText(context.getString(R.string.top)+total);
//            UseMe.setImage(context,travelOderLists.get(position).getProduct_list().get(0).getProduct_image_list().get(0),
//                    holder.binding.img);
            BaseActivity.setProfile(context,travelOderLists.get(position).getUser_image(),
                    holder.binding.imgProfile);
            holder.binding.txtReward.setText("$"+travelOderLists.get(position).getTotal_delivery_reward());
            sliderImage=new ArrayList<>();

            if(travelOderLists.get(position).getProduct_list().size() == 1){
                for (int i = 0; i <travelOderLists.get(position).getProduct_list().get(0).getProduct_image_list().size() ; i++) {
                    if (!travelOderLists.get(position).getProduct_list().get(0).getProduct_image_list().get(i).equals("")){
                        sliderImage.add(travelOderLists.get(position).getProduct_list().get(0).getProduct_image_list().get(i));
                    }
                }

            }else {
                for(int j=0;j<travelOderLists.get(position).getProduct_list().size();j++) {
                    for (int i = 0; i < travelOderLists.get(position).getProduct_list().get(j).getProduct_image_list().size(); i++) {
                        if (!travelOderLists.get(position).getProduct_list().get(j).getProduct_image_list().get(i).equals("")) {
                            sliderImage.add(travelOderLists.get(position).getProduct_list().get(j).getProduct_image_list().get(i));
                        }
                    }
                }
            }

            holder.binding.viewPager.setAdapter(new ImageSlider(context,sliderImage));
            holder.binding.pagerIndicator.setPager(holder.viewPager);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (type) {
                        case "accepted": {
                            StaticTransitData.fromWhom = "fromtraveller";

                            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
                            Date d1= null;
                            try {
                              //  Log.d("TAG", "onClick: "+travelOderLists.get(position).getCreated_date());
                                d1 = simpleDateFormat1.parse(travelOderLists.get(position).getProduct_list().get(0).getCreated_date());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            simpleDateFormat1=new SimpleDateFormat("MMM dd,yyyy");
                            String date1=  simpleDateFormat1.format(d1);

                         //   Log.d("TAG", "onClick: "+travelOderLists.get(position).getProduct_list().get(0).getCreated_date());

                            Sharedpreferences.saveToPreferences(context, Keys.order_id, travelOderLists.get(position).getOrder_id());
                            Sharedpreferences.saveToPreferences(context, Keys.product_id, travelOderLists.get(position).getProduct_id());
                            Sharedpreferences.saveToPreferences(context, Keys.delivery_from, travelOderLists.get(position).getProduct_list().get(0).getLocation_from());
                            Sharedpreferences.saveToPreferences(context, Keys.delivered_to, travelOderLists.get(position).getProduct_list().get(0).getLocation_to());
                            Sharedpreferences.saveToPreferences(context, Keys.travel_date, date1);

                            getApicall(travelOderLists.get(position).getOrder_id());


                                break;
                        }
                        case "all": {
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.order_id, travelOderLists.get(position).getOrder_id());
                            StaticClass.fromTraveller = false;

                            new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                                    new MakeOfferFragment());
                            break;
                        }
                        case "pending": {
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.user2_id, travelOderLists.get(position).getUser_id());
                            bundle.putString(Keys.user1_id, Sharedpreferences.readFromPreferences(context, AppConstant.USERID, ""));
                            bundle.putString(Keys.order_id, travelOderLists.get(position).getOrder_id());
                            bundle.putString(Keys.name, travelOderLists.get(position).getUser_name());
                            bundle.putString(Keys.profileImage, travelOderLists.get(position).getUser_image());
                            bundle.putString("CustomerProfile", travelOderLists.get(position).getUser_image());
                            new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                                    new MessageFragment());
                            break;
                        }
                        case "delivered":
                            holder.binding.cvEdt.setVisibility(View.GONE);

                            StaticTransitData.fromWhom = "fromtraveller";

                            StaticTransitData.OrderOnDate =travelOderLists.get(position).getCreated_date();
                            StaticTransitData.UserName = travelOderLists.get(position).getUser_name();
                            StaticTransitData.Reward = travelOderLists.get(position).getTotal_delivery_reward();
                            StaticTransitData.TotalPrice = travelOderLists.get(position).getTotal_order_price();
                            StaticTransitData.NoOfProducts = ""+travelOderLists.get(position).getProduct_list().size();
                            StaticTransitData.DeliveryDate = date;
                            StaticTransitData.TravelFrom = travelOderLists.get(position).getLocation_from();
                            StaticTransitData.TravelTo = travelOderLists.get(position).getLocation_to();
                            StaticTransitData.ProfileImage = ""+travelOderLists.get(position).getUser_image();

                            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
                            Date d1= null;
                            try {
                                d1 = simpleDateFormat1.parse(travelOderLists.get(position).getProduct_list().get(0).getCreated_date());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            simpleDateFormat1=new SimpleDateFormat("MMM dd,yyyy");
                            String date1=  simpleDateFormat1.format(d1);

                            Sharedpreferences.saveToPreferences(context, Keys.order_id, travelOderLists.get(position).getOrder_id());
                            Sharedpreferences.saveToPreferences(context, Keys.product_id, travelOderLists.get(position).getProduct_id());
                            Sharedpreferences.saveToPreferences(context, Keys.delivery_from, travelOderLists.get(position).getProduct_list().get(0).getLocation_from());
                            Sharedpreferences.saveToPreferences(context, Keys.delivered_to, travelOderLists.get(position).getProduct_list().get(0).getLocation_to());
                            Sharedpreferences.saveToPreferences(context, Keys.travel_date,date1);

                            getApicall(travelOderLists.get(position).getOrder_id());


                            break;
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void getApicall(String order_id) {

        if(hud == null){
            hud=   KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }else{
            hud.dismiss();
        }

            OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
            orderStatusRequest.setOrderId(order_id);

            Call<OrderStatusResponse> orderStatusResponseCall = ApiClient.getService().getOrderStatus(orderStatusRequest);
            orderStatusResponseCall.enqueue(new Callback<OrderStatusResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {

                    if(response.isSuccessful()){
                        if (response.body().getStatus().equals(Keys.status_succes)) {
                            hud.dismiss();
                            StaticTransitData.offerStatus = ""+response.body().getOrderInfoStatus();

                            if (StaticTransitData.offerStatus.equals("no_info")){
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new PurchaseMadeFragment());
                            } else if (StaticTransitData.offerStatus.equals("purchase_made")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new OrderDetailsFragment());
                            }else if (StaticTransitData.offerStatus.equals("order_delivered")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new SatisfiedBuyerFragment());
                            }else if (StaticTransitData.offerStatus.equals("satisfied_buyer")) {
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, null, R.id.frameLayoout,
                                        new SatisfiedBuyerFragment());
                            }

                        } else {
                            //Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            hud.dismiss();
                        }

                    }
                }

                @Override
                public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
                    hud.dismiss();
                }
            });


    }

    @Override
    public int getItemCount() {
        return travelOderLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ViewPagerIndicator pagerIndicator;
        OfferItemListBinding binding;
        public MyViewHolder(OfferItemListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            viewPager=itemView.findViewById(R.id.viewPager);

        }
    }
}
