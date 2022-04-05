package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Activity.LoginActivity;
import com.dev.todos.Fragment.Message.ProductDetailsFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SingleOrderListResponce;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.OrderSummeryItemBinding;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummeryAdapter extends RecyclerView.Adapter<OrderSummeryAdapter.MyviewHolder> {
    Context context;
    FragmentManager fragmentManager;
    List<com.dev.todos.Model.SingleorderList.ProductListItem> productLists;
    ProgressDialog progressDialog;
    boolean isFromMyOrder;
    String orderId;
    String bringProduct="";
    androidx.appcompat.app.AlertDialog alertDialog;
    SingleOrderListResponce.OrderDetalis order_details;

    public OrderSummeryAdapter(Context context, FragmentManager fragmentManager,
                               List<ProductListItem> productLists, boolean isFromMyOrder, String orderId, String bringProduct, SingleOrderListResponce.OrderDetalis order_details) {
        this.productLists = productLists;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.isFromMyOrder = isFromMyOrder;
        this.orderId = orderId;
        this.bringProduct = bringProduct;
        this.order_details = order_details;

    }

    @Override
    public OrderSummeryAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderSummeryItemBinding orderSummeryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_summery_item,
                parent, false);
        return new MyviewHolder(orderSummeryItemBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(OrderSummeryAdapter.MyviewHolder holder, @SuppressLint("RecyclerView") final int position) {

        BaseActivity.setImage(context, productLists.get(position).getProductImageList().get(0), holder.binding.productImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,
                        null, R.id.frameLayoout, new AddShopperFragment());
            }
        });

            holder.itemView.findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(productLists.get(position).getProductId());
                }
            });


        holder.binding.txtProductName.setText(productLists.get(position).getArticleName());
        holder.binding.txtDestinationCity.setText(productLists.get(position).getLocationTo());
        holder.binding.txtOriginCity.setText(productLists.get(position).getLocationFrom());
        holder.binding.txtQunt.setText(context.getString(R.string.quetity) + productLists.get(position).getItemQuantity());
        holder.binding.txtReward.setText("$" + productLists.get(position).getDeliveryReward());
        holder.binding.txtProductPrice.setText("$" + productLists.get(position).getItemPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isLogin()) {

                    if (isFromMyOrder) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(context.getString(R.string.updateproductdetails));
                        builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                Bundle bundle = new Bundle();
                                bundle.putString(Keys.product_id, productLists.get(position).getProductId());
                                bundle.putString(Keys.article_name, productLists.get(position).getArticleName());
                                bundle.putString(Keys.article_comment, productLists.get(position).getArticleComment());
                                bundle.putString(Keys.productLink, productLists.get(position).getBuyItemLink());
                                bundle.putString(Keys.image1, productLists.get(position).getProductImageList().get(0));
                                bundle.putString(Keys.image2, productLists.get(position).getProductImageList().get(1));
                                bundle.putString(Keys.image3, productLists.get(position).getProductImageList().get(2));
                                bundle.putString(Keys.image4, productLists.get(position).getProductImageList().get(3));
                                bundle.putString(Keys.qunt, productLists.get(position).getItemQuantity());
                                bundle.putString(Keys.item_price, productLists.get(position).getItemPrice());
                                bundle.putString(Keys.location_to, productLists.get(position).getLocationTo());
                                bundle.putString(Keys.location_from, productLists.get(position).getLocationFrom());
                                bundle.putString(Keys.reward, productLists.get(position).getDeliveryReward());
                                bundle.putString(Keys.date, productLists.get(position).getDeliveryDeadline());
                                bundle.putString(Keys.isFromUpdate, "true");
                                bundle.putString(Keys.product_id, productLists.get(position).getProductId());
                                bundle.putString(Keys.order_id, orderId);
                                Log.e("in ordersummary ",productLists.get(position).getLocation_latfrom()+"");
                                bundle.putString(Keys.latfrom, productLists.get(position).getLocation_latfrom());
                                bundle.putString(Keys.longfrom, productLists.get(position).getLocation_longfrom());
                                bundle.putString(Keys.latto, productLists.get(position).getLocation_latto());
                                bundle.putString(Keys.longto, productLists.get(position).getLocation_longto());
                                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                                        new AddShopperFragment());


                            }
                        });
                        builder.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    else {

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
                        bundle.putString(Keys.profileImage, order_details.getUser_image());
                        bundle.putString(Keys.name, order_details.getUser_name());
                        bundle.putString(Keys.product_id, productLists.get(position).getProductId());
                        bundle.putString(Keys.order_id, orderId);
                        bundle.putString(Keys.latfrom, productLists.get(position).getLocation_latfrom());
                        bundle.putString(Keys.longfrom, productLists.get(position).getLocation_longfrom());
                        bundle.putString(Keys.latto, productLists.get(position).getLocation_latto());
                        bundle.putString(Keys.longto, productLists.get(position).getLocation_longto());
                        new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout,
                                new ProductDetailsFragment());
                    }

                }else{
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("Activity","NewLogin");
                    context.startActivity(intent);
                }

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
                    //Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                   setalertDialog(response.body().getMsg());
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

        Call<SingleOrderListResponce> orderListResponceCall = ApiClient.getService().getSingleOrderDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderListResponce>() {
            @Override
            public void onResponse(Call<SingleOrderListResponce> call, Response<SingleOrderListResponce> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    productLists = new ArrayList<>();
                    productLists = response.body().getOrder_details().getProduct_list();


                } else {
                    productLists = new ArrayList<>();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SingleOrderListResponce> call, Throwable t) {
                progressDialog.dismiss();


            }
        });

    }

    public void setalertDialog(String msg){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(msg);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put(Keys.order_id, orderId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new ChangeFragment(context).changeFragmentWithoutBackStack(fragmentManager, null,
                        R.id.frameLayoout, new MyOrderFragment());

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    private boolean isLogin() {
        return Sharedpreferences.readFromPreferences(context, AppConstant.IS_LOGIN, false);
    }


}
