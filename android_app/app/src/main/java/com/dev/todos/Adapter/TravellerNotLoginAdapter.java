package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.dev.todos.Activity.ShopperListNotLoginActivity;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.OrderSummaryFragment;
import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Model.GetOrderList.OrderListItem;
import com.dev.todos.Model.GetOrderList.ProductListItem;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.ImageSlider;
import com.dev.todos.Util.Keys;
import com.dev.todos.databinding.ItemTravellerBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.dev.todos.Util.BaseActivity.setLocale;

public class TravellerNotLoginAdapter extends RecyclerView.Adapter<TravellerNotLoginAdapter.MyViewHolder> {

    List<OrderListItem> orderLists;
    Context context;
    List<String> sliderImage;
    List<ProductListItem> productLists;
    double total = 0.0;
    String userstatus ="";
    Boolean traveller_status = false;

    OnClick_Event onClick_event;

    FragmentManager fragmentManager;

    public TravellerNotLoginAdapter(Context applicationContext, FragmentManager fragmentManager) {
        this.context = applicationContext;
        this.fragmentManager= fragmentManager;
    }


    public void setUplist(List<OrderListItem> orderLists) {
        this.orderLists = orderLists;
        sliderImage=new ArrayList<>();
        productLists=new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTravellerBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_traveller, parent, false);
        Log.e("lang10",Sharedpreferences.readFromPreferences(context, AppConstant.APPLANGUAGE,""));
        if (Sharedpreferences.readFromPreferences(context, AppConstant.APPLANGUAGE,"en").equals("en")){
            language("en");
        }else{
            language("es");

        }

        return new MyViewHolder(binding);
    }

    public interface OnClick_Event {
        void setOnItemClickListener(OrderListItem orderListItem, int postion);
    }

    public void setOnItemClickListener(OnClick_Event onClick_event) {
        this.onClick_event = onClick_event;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {

            BaseActivity.setProfile(context, orderLists.get(position).getUserImage(), holder.binding.imgProfile);
            holder.binding.txtDestinationCity.setText(orderLists.get(position).getProductList().get(0).getLocationTo());
            holder.binding.txtOriginCity.setText(orderLists.get(position).getProductList().get(0).getLocationFrom());
            holder.binding.txtName.setText(orderLists.get(position).getUserName());
            holder.binding.txtNoOfProducts.setText(context.getString(R.string.no_product) + orderLists.get(position).getProductList().size());
            SpannableString content = new SpannableString(orderLists.get(position).getOrderOfferList().size()+" "+context.getString(R.string.offer));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            holder.binding.txtOffers.setText(content);


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = simpleDateFormat.parse(orderLists.get(position).getProductList().get(0).getCreatedDate());
                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date1 = simpleDateFormat.format(date);
                holder.binding.txtOrderOn.setText(context.getString(R.string.order_on_new)+date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(int i=0; i< orderLists.get(position).getProductList().size();i++){
                total = total + Double.parseDouble(orderLists.get(position).getProductList().get(i).getDeliveryReward());
                Log.d("TAG", "onBindViewHolder: "+total);
            }
            String price = getPriceTotal(total,orderLists.get(position).getTotalOrderPrice());

            holder.binding.txtTotalPrice.setText(context.getString(R.string.top) + price);
            total = 0.0;
            holder.binding.txtReward.setText("$"+orderLists.get(position).getTotalDeliveryReward());
            sliderImage=new ArrayList<>();

            if(orderLists.get(position).getProductList().size() == 1){
                for (int i = 0; i <orderLists.get(position).getProductList().get(0).getProductImageList().size() ; i++) {
                    if (!orderLists.get(position).getProductList().get(0).getProductImageList().get(i).equals("")){
                        sliderImage.add(orderLists.get(position).getProductList().get(0).getProductImageList().get(i));
                    }
            }

            }else {
                for(int j=0;j<orderLists.get(position).getProductList().size();j++) {
                    for (int i = 0; i < orderLists.get(position).getProductList().get(j).getProductImageList().size(); i++) {
                        if (!orderLists.get(position).getProductList().get(j).getProductImageList().get(i).equals("")) {
                            sliderImage.add(orderLists.get(position).getProductList().get(j).getProductImageList().get(i));
                        }
                    }
                }
            }

            // UseMe.setImage(context,orderLists.get(position).getProduct_list().get(0).getProduct_image_list().get(0),holder.binding.imgProduct);
            try {
                holder.binding.viewPager.setAdapter(new ImageSlider(context, sliderImage));
                holder.binding.pagerIndicator.setPager(holder.binding.viewPager);


            }catch (Exception e){}

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin()) {
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

                        bundle.putString(Keys.bringProduct, orderLists.get(position).getBringProduct());
                        bundle.putBoolean(Keys.isFromMyOrder, traveller_status);
                        bundle.putString(Keys.name,orderLists.get(position).getUserName());
                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
                        bundle.putString(Keys.user2_id,orderLists.get(position).getUserId());
                        bundle.putString(Keys.userstatus,userstatus);
                        try {
                            bundle.putString(Keys.image1, orderLists.get(position).getProductList().get(0).getProductImageList().get(0));
                            bundle.putString(Keys.image2, orderLists.get(position).getProductList().get(0).getProductImageList().get(1));
                            bundle.putString(Keys.image3, orderLists.get(position).getProductList().get(0).getProductImageList().get(2));
                            bundle.putString(Keys.image4, orderLists.get(position).getProductList().get(0).getProductImageList().get(3));
                        }catch (Exception e){}
                        bundle.putString(Keys.reward,orderLists.get(position).getTotalDeliveryReward());
                        bundle.putString(Keys.total_order_price,holder.binding.txtTotalPrice.getText().toString());
                        bundle.putInt(Keys.noOfOrder,orderLists.get(position).getProductList().size());
                        try {
                            bundle.putString(Keys.product_id, orderLists.get(position).getProductList().get(position).getProductId());
                        }catch (Exception e){}
                        bundle.putString(Keys.order_id,orderLists.get(position).getOrderId());
                        bundle.putStringArrayList(Keys.imageList, (ArrayList<String>) orderLists.get(position).getProductList().get(0).getProductImageList());
                        bundle.putString(Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());
                        bundle.putString(Keys.latfrom, orderLists.get(position).getProductList().get(0).getLocation_latfrom());
                        bundle.putString(Keys.longfrom,orderLists.get(position).getProductList().get(0).getLocation_longfrom());
                        bundle.putString(Keys.latto, orderLists.get(position).getProductList().get(0).getLocation_latto());
                        bundle.putString(Keys.longto,orderLists.get(position).getProductList().get(0).getLocation_longto());
                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new OrderSummaryFragment());
                    }
                    else {
                        String userid = "";
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

                        bundle.putString(Keys.bringProduct, orderLists.get(position).getBringProduct());
                        bundle.putBoolean(Keys.isFromMyOrder, traveller_status);
                        bundle.putString(Keys.name,orderLists.get(position).getUserName());
                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
                        bundle.putString(Keys.user2_id,orderLists.get(position).getUserId());
                        bundle.putString(Keys.userstatus,userstatus);
                        try {
                            bundle.putString(Keys.image1, orderLists.get(position).getProductList().get(0).getProductImageList().get(0));
                            bundle.putString(Keys.image2, orderLists.get(position).getProductList().get(0).getProductImageList().get(1));
                            bundle.putString(Keys.image3, orderLists.get(position).getProductList().get(0).getProductImageList().get(2));
                            bundle.putString(Keys.image4, orderLists.get(position).getProductList().get(0).getProductImageList().get(3));
                        }catch (Exception e){}
                        bundle.putString(Keys.reward,orderLists.get(position).getTotalDeliveryReward());
                        bundle.putString(Keys.total_order_price,holder.binding.txtTotalPrice.getText().toString());
                        bundle.putInt(Keys.noOfOrder,orderLists.get(position).getProductList().size());
                        try {
                            bundle.putString(Keys.product_id, orderLists.get(position).getProductList().get(position).getProductId());
                        }catch (Exception e){}
                        bundle.putString(Keys.order_id,orderLists.get(position).getOrderId());
                        bundle.putStringArrayList(Keys.imageList, (ArrayList<String>) orderLists.get(position).getProductList().get(0).getProductImageList());
                        bundle.putString(Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());

                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, android.R.id.content, new OrderSummaryFragment());


                    }
                }
            });

            holder.binding.txtOffers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isLogin()) {
                        onClick_event.setOnItemClickListener(orderLists.get(position), position);
                    }else{
                        onClick_event.setOnItemClickListener(orderLists.get(position), position);
                    }
                }
            });

//            holder.binding.viewPager.setOnTouchListener(new View.OnTouchListener() {
//                @SuppressLint("ClickableViewAccessibility")
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                        String userid = Sharedpreferences.readFromPreferences(context, AppConstant.USERID,"");
//                        if(orderLists.get(position).getUserId().equals(userid)){
//                            userstatus = "true";
//                            traveller_status = true;
//                        }else{
//                            userstatus = "false";
//                            traveller_status = false;
//
//                            if(orderLists.get(position).getBringProduct().equals("no")){
//                                userstatus = "I Will Bring";
//                            }else{
//                                userstatus = "SEND MESSAGE";
//                            }
//                        }
//                        Bundle bundle = new Bundle();
//
//                        bundle.putString(Keys.bringProduct, orderLists.get(position).getBringProduct());
//                        bundle.putBoolean(Keys.isFromMyOrder, traveller_status);
//                        bundle.putString(Keys.name,orderLists.get(position).getUserName());
//                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
//                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
//                        bundle.putString(Keys.user2_id,orderLists.get(position).getUserId());
//                        bundle.putString(Keys.userstatus,userstatus);
//                        try {
//                            bundle.putString(Keys.image1, orderLists.get(position).getProductList().get(0).getProductImageList().get(0));
//                            bundle.putString(Keys.image2, orderLists.get(position).getProductList().get(0).getProductImageList().get(1));
//                            bundle.putString(Keys.image3, orderLists.get(position).getProductList().get(0).getProductImageList().get(2));
//                            bundle.putString(Keys.image4, orderLists.get(position).getProductList().get(0).getProductImageList().get(3));
//                        }catch (Exception e){}
//                        bundle.putString(Keys.reward,orderLists.get(position).getTotalDeliveryReward());
//                        bundle.putString(Keys.total_order_price,holder.binding.txtTotalPrice.getText().toString());
//                        bundle.putInt(Keys.noOfOrder,orderLists.get(position).getProductList().size());
//                        try {
//                            bundle.putString(Keys.product_id, orderLists.get(position).getProductList().get(position).getProductId());
//                        }catch (Exception e){}
//                        bundle.putString(Keys.order_id,orderLists.get(position).getOrderId());
//                        bundle.putStringArrayList(Keys.imageList, (ArrayList<String>) orderLists.get(position).getProductList().get(0).getProductImageList());
//                        bundle.putString(Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
//                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());
//
//                        new ChangeFragment(context).changeFragmentWithBackStack1(fragmentManager, bundle, R.id.frameLayoout, new OrderSummaryFragment());
////                    Fragment fragment=new OrderSummaryFragment();
////                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
////                    fragmentTransaction.replace(R.id.frameLayoout,fragment);
////                    fragmentTransaction.addToBackStack(null);
////                    fragment.setArguments(bundle);
////                    fragmentTransaction.commitAllowingStateLoss();
//
//
//                    return false;
//                }
//            });
//

            holder.binding.travellerProfileLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("travellerId", orderLists.get(position).getUserId());
                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new TravellerProfileFragment());
                    }
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getPriceTotal(double price, String totalOrderPrice) {

        price = total + Double.parseDouble(totalOrderPrice);
        Log.d("TAG", "onBindViewHolder: 1"+price);

        return String.valueOf(price);
    }


    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemTravellerBinding binding;
        public MyViewHolder(ItemTravellerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }




    private boolean isLogin() {
        return Sharedpreferences.readFromPreferences(context, AppConstant.IS_LOGIN, false);
    }

    private void language(String text){
        Locale locale = new Locale(text);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }

}
