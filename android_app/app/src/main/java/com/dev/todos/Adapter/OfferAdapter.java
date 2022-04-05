package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Message.MessageFragment;
import com.dev.todos.Fragment.Payment.PaymentFragment;
import com.dev.todos.Model.SingleorderList.OrderDetails;
import com.dev.todos.Model.SingleorderList.OrderOfferListItem;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.NewOfferItemBinding;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    Context context;
    FragmentManager fragmentManager;
    List<OrderOfferListItem> offerList;
    OrderDetails orderDetails;
    List<ProductListItem> productList;
    AlertDialog alertDialog;
    String Frag="";
    public OfferAdapter(Context context, FragmentManager fragmentManager, List<OrderOfferListItem> offerList, OrderDetails orderDetails, List<ProductListItem> productList, String frag) {
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.offerList = offerList;
        this.orderDetails = orderDetails;
        this.productList = productList;
        this.Frag = frag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewOfferItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.new_offer_item,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        try {

            if (Sharedpreferences.readFromPreferences(context, AppConstant.USERID, "").equals(orderDetails.getUserId())) {
                holder.binding.liner.setVisibility(View.VISIBLE);
            } else {
                holder.binding.liner.setVisibility(View.GONE);
            }

            if(orderDetails.getHireStatus().equals("yes")){
                holder.binding.hiredTxt.setVisibility(View.VISIBLE);
            }else{
                holder.binding.hiredTxt.setVisibility(View.GONE);
            }

            UseMe.setImage(context,offerList.get(position).getUserImage(),holder.binding.imgProfile);
            holder.binding.ratingBar.setRating(Float.parseFloat(offerList.get(position).getRatingAsT()));
            holder.binding.txtName.setText(offerList.get(position).getUserName());
            holder.binding.txtReward.setText("$" + orderDetails.getTotalDeliveryReward());
            holder.binding.productpriceTxt.setText("$" + orderDetails.getTotalOrderPrice());
            holder.binding.txtSource.setText(offerList.get(position).getDeliveryFrom());
            holder.binding.txtDestination.setText(offerList.get(position).getDeliveryTo());
            holder.binding.rewardTxt.setText("$" + offerList.get(position).getReward());

            holder.binding.acceptofferTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(offerList.get(position).getTermsAndCondition().equals("0")){
                        setalertDialog(context.getString(R.string.traveller));
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("Activity",Frag);
                        bundle.putString(Keys.total_order_price, orderDetails.getTotalOrderPrice());
                        bundle.putString(Keys.reward, offerList.get(position).getReward());
                        bundle.putString(Keys.shipping_cost, offerList.get(position).getShippingCost());
                        bundle.putString(Keys.taxes_fees, offerList.get(position).getTaxesFees());
                        bundle.putString(Keys.productInorder, String.valueOf(productList.size()));
                        bundle.putString(Keys.order_id, orderDetails.getOrderId());
                        bundle.putString(Keys.offer_order_id, offerList.get(position).getOfferOrderId());

                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,
                                bundle, R.id.frameLayoout, new PaymentFragment());
                    }
                }
            });

            holder.binding.sendMessageTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.user2_id, offerList.get(position).getUserId());
                    bundle.putString(Keys.user1_id, orderDetails.getUserId());
                    bundle.putString(Keys.order_id, offerList.get(position).getOrderId());
                    bundle.putString(Keys.name, offerList.get(position).getUserName());
                    bundle.putString(Keys.profileImage, orderDetails.getUserImage());
                    bundle.putString("CustomerProfile", offerList.get(position).getUserImage());

                    StaticClass.fromChat = true;
                    StaticClass.fromUser = false;

                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,
                            bundle, R.id.frameLayoout, new MessageFragment());
                }
            });
        } catch (Exception e){}
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ViewPagerIndicator pagerIndicator;
        NewOfferItemBinding binding;
        public MyViewHolder(NewOfferItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            viewPager=itemView.findViewById(R.id.viewPager);
        }
    }
    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public void setalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}
