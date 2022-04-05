package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Message.ProductDetailsFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SingleOrderListResponce;
import com.dev.todos.Model.SingleorderList.OrderDetails;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.OrderSummeryItemBinding;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryMessageAdapter extends RecyclerView.Adapter<OrderSummaryMessageAdapter.MyviewHolder> {
    Context context;
    FragmentManager fragmentManager;
    List<ProductListItem> productLists;
    SingleOrderListResponce.OrderDetalis orderDetails;
    ProgressDialog progressDialog;
    boolean isFromMyOrder;
    String orderId;

    String username = "", userimage = "";

    public OrderSummaryMessageAdapter(Context context, FragmentManager fragmentManager,
                                      List<ProductListItem> productLists, boolean isFromMyOrder, String orderId, SingleOrderListResponce.OrderDetalis order_details) {
        this.productLists = productLists;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.isFromMyOrder = isFromMyOrder;
        this.orderId = orderId;
        this.orderDetails = order_details;

    }

    @Override
    public OrderSummaryMessageAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderSummeryItemBinding orderSummeryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_summery_item,
                parent, false);
        return new OrderSummaryMessageAdapter.MyviewHolder(orderSummeryItemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderSummaryMessageAdapter.MyviewHolder holder, @SuppressLint("RecyclerView") final int position) {

        BaseActivity.setImage(context, productLists.get(position).getProductImageList().get(0), holder.binding.productImage);
//        holder.binding.txtProductName.setText(productLists.get(position).getArticle_name());
//        holder.binding.txtDestinationCity.setText(productLists.get(position).getLocation_to());
//        holder.binding.txtOriginCity.setText(productLists.get(position).getLocation_from());
//        holder.binding.txtProductPrice.setText("$ "+productLists.get(position).getItem_price());
//        holder.binding.txtReward.setText(productLists.get(position).getDelivery_reward());
//        holder.binding.txtQunt.setText("Quantity: "+productLists.get(position).getItem_quantity());


      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,
                        null, R.id.frameLayoout, new ProductDetailsFragment());
            }
        });*/

      /*  holder.itemView.findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(productLists.get(position).getProductId());
            }
        });*/


        holder.binding.txtProductName.setText(productLists.get(position).getArticleName());
        holder.binding.txtDestinationCity.setText(productLists.get(position).getLocationTo());
        holder.binding.txtOriginCity.setText(productLists.get(position).getLocationFrom());
        holder.binding.txtQunt.setText(context.getString(R.string.quetity) + productLists.get(position).getItemQuantity());
        holder.binding.txtReward.setText("$" + productLists.get(position).getDeliveryReward());
        holder.binding.txtProductPrice.setText("$" + productLists.get(position).getItemPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(Keys.product_id, productLists.get(position).getProductId());
                bundle.putString(Keys.article_name, productLists.get(position).getArticleName());
                bundle.putString(Keys.article_comment, productLists.get(position).getArticleComment());
                bundle.putString(Keys.productLink, productLists.get(position).getBuyItemLink());

                UseMe.stringArrayList.clear();
                if (!productLists.get(position).getProductImageList().get(0).equals("")) {
                    UseMe.stringArrayList.add(productLists.get(position).getProductImageList().get(0));
                }

                if (!productLists.get(position).getProductImageList().get(1).equals("")) {
                    UseMe.stringArrayList.add(productLists.get(position).getProductImageList().get(1));
                }

                if (!productLists.get(position).getProductImageList().get(2).equals("")) {
                    UseMe.stringArrayList.add(productLists.get(position).getProductImageList().get(2));
                }

                if (!productLists.get(position).getProductImageList().get(3).equals("")) {
                    UseMe.stringArrayList.add(productLists.get(position).getProductImageList().get(3));
                }
                bundle.putString(Keys.qunt, productLists.get(position).getItemQuantity());
                bundle.putString(Keys.item_price, productLists.get(position).getItemPrice());
                bundle.putString(Keys.location_to, productLists.get(position).getLocationTo());
                bundle.putString(Keys.location_from, productLists.get(position).getLocationFrom());
                bundle.putString(Keys.reward, productLists.get(position).getDeliveryReward());
                bundle.putString(Keys.date, productLists.get(position).getCreatedDate());
                bundle.putString(Keys.delivery_deadline, productLists.get(position).getDeliveryDeadline());
                bundle.putString(Keys.profileImage, orderDetails.getUser_image());
                bundle.putString(Keys.name, orderDetails.getUser_name());
                bundle.putString(Keys.product_id, productLists.get(position).getProductId());
                bundle.putString(Keys.order_id, orderId);
                bundle.putString(Keys.latfrom, productLists.get(position).getLocation_latfrom());
                bundle.putString(Keys.longfrom, productLists.get(position).getLocation_longfrom());
                bundle.putString(Keys.latto, productLists.get(position).getLocation_latto());
                bundle.putString(Keys.longto, productLists.get(position).getLocation_longto());
                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                        new ProductDetailsFragment());


            }
        });

        if (isFromMyOrder) {
            holder.binding.imgDelete.setVisibility(View.VISIBLE);


        } else {
            holder.binding.imgDelete.setVisibility(View.GONE);


        }


    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        OrderSummeryItemBinding binding;

        public MyviewHolder(OrderSummeryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void deletProduct(final JsonObject jsonObject) {

        final Call<Responce> responceCall = ApiClient.getService().removeProduct(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put(Keys.order_id, orderId);
                        new ChangeFragment(context).changeFragmentWithoutBackStack(fragmentManager, null,
                                R.id.frameLayoout, new MyOrderFragment());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }


    public void showDialog(final String productId) {
        AlertDialog.Builder a = new AlertDialog.Builder(context);
        a.setMessage(context.getString(R.string.deleteproduct));
        a.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(Keys.product_id, productId);
                    deletProduct(UseMe.getJsonObject(jsonObject));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        a.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        a.show();
    }

    public void getSingleOrderList(JsonObject jsonObject) {
        progressDialog.show();

        Call<SingleOrderResponse> orderListResponceCall = ApiClient.getService().getSingleDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderResponse>() {
            @Override
            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    productLists = new ArrayList<>();
                    productLists = response.body().getOrderDetails().getProductList();


                } else {
                    productLists = new ArrayList<>();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                progressDialog.dismiss();


            }
        });

    }

}
