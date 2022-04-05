package com.dev.todos.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Activity.LoginActivity;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.OrderSummaryFragment;


import com.dev.todos.Model.Filter.ProductListItem;
import com.dev.todos.Model.GetOrderList.OrderListItem;
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

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    List<OrderListItem> orderLists;
    Context context;
    List<String> sliderImage;
    List<ProductListItem> productLists;
    double total = 0.0;
    String userstatus ="";

    FilterAdapter.OnClick_Event onClick_event;

    FragmentManager fragmentManager;
    public FilterAdapter(Context applicationContext, FragmentManager fragmentManager) {
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
    public FilterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTravellerBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_traveller, parent, false);
        return new FilterAdapter.MyViewHolder(binding);
    }

    public void setOnItemClickListener(FilterAdapter.OnClick_Event onClick_event) {
        this.onClick_event = onClick_event;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.MyViewHolder holder, int position) {
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
            for (int i = 0; i <orderLists.get(position).getProductList().get(0).getProductImageList().size() ; i++) {
                if (!orderLists.get(position).getProductList().get(0).getProductImageList().get(i).equals("")){
                    sliderImage.add(orderLists.get(position).getProductList().get(0).getProductImageList().get(i));

                }

            }

            // UseMe.setImage(context,orderLists.get(position).getProduct_list().get(0).getProduct_image_list().get(0),holder.binding.imgProduct);
            holder.binding.viewPager.setAdapter(new ImageSlider(context,sliderImage));
            holder.binding.pagerIndicator.setPager(holder.binding.viewPager);


            holder.binding.viewPager.setAdapter(new ImageSlider(context, sliderImage));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin()) {
                        String userid = Sharedpreferences.readFromPreferences(context, AppConstant.USERID,"");
                        if(orderLists.get(position).getUserId().equals(userid)){
                            userstatus = "true";
                        }else{
                            userstatus = "false";

                            if(orderLists.get(position).getBringProduct().equals("no")){
                                userstatus = "I Will Bring";
                            }else{
                                userstatus = "SEND MESSAGE";
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.order_id, orderLists.get(position).getOrderId());
                        bundle.putString(Keys.bringProduct, orderLists.get(position).getBringProduct());
                        bundle.putBoolean(Keys.isFromMyOrder, true);
                        bundle.putString(Keys.name,orderLists.get(position).getUserName());
                        bundle.putString(Keys.profileImage,orderLists.get(position).getUserImage());
                        bundle.putString(Keys.userstatus,userstatus);
                        bundle.putString(Keys.reward,orderLists.get(position).getTotalDeliveryReward());
                        Log.d("TAG", "onClick: "+holder.binding.txtTotalPrice.getText().toString());
                        bundle.putString(Keys.total_order_price,holder.binding.txtTotalPrice.getText().toString());
                        bundle.putInt(Keys.noOfOrder,orderLists.get(position).getProductList().size());
                        bundle.putString(Keys.order_id,orderLists.get(position).getOrderId());
                        bundle.putStringArrayList(Keys.imageList, (ArrayList<String>) orderLists.get(position).getProductList().get(0).getProductImageList());
                        bundle.putString(Keys.delivery_from,orderLists.get(position).getProductList().get(0).getLocationFrom());
                        bundle.putString(Keys.delivery_to,orderLists.get(position).getProductList().get(0).getLocationTo());

                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new OrderSummaryFragment());
                    } else {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });

            holder.binding.txtOffers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isLogin()) {
                        onClick_event.setOnItemClickListener(orderLists.get(position), position);
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

    public interface OnClick_Event {
        void setOnItemClickListener(OrderListItem orderListItem, int postion);
    }


    private boolean isLogin() {
        return Sharedpreferences.readFromPreferences(context, AppConstant.IS_LOGIN, false);
    }

}
