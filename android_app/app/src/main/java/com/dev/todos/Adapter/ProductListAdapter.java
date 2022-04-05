package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.dev.todos.Model.ProductDetail;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.OrderSummeryItemBinding;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    public ProductListAdapter(Context context, ArrayList<ProductDetail> productDetailArrayList) {
        this.context = context;
        this.productDetailArrayList = productDetailArrayList;
    }

    Context context;
    ArrayList<ProductDetail> productDetailArrayList;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderSummeryItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_summery_item,
                parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            if (productDetailArrayList.get(position).getProductUri().equals("")) {
                BaseActivity.setImage(context, productDetailArrayList.get(position).getProductImage(),
                        holder.binding.productImage);
            } else {
                holder.binding.productImage.setImageURI(Uri.parse(productDetailArrayList.get(position).getProductUri()));
            }

            holder.binding.txtProductName.setText(productDetailArrayList.get(position).getAricaleName());


            holder.binding.txtProductPrice.setText("$" + productDetailArrayList.get(position).getPriceOfItem());
            holder.binding.txtReward.setText("$" + productDetailArrayList.get(position).getReward());
            holder.binding.txtQunt.setText(context.getString(R.string.quetity) + productDetailArrayList.get(position).getQunt());
            holder.binding.txtOriginCity.setText(productDetailArrayList.get(position).getFromLocation());
            holder.binding.txtDestinationCity.setText(productDetailArrayList.get(position).getDeliveryLocation());
            holder.binding.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(position);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return productDetailArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        OrderSummeryItemBinding binding;


        public MyViewHolder(OrderSummeryItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public void showDeleteDialog(final int postion){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.deleteorder));
        builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                productDetailArrayList.remove(postion);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }
}
