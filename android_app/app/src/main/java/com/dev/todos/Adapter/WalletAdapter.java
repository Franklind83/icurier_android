package com.dev.todos.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Model.WalletCommission.OrderListItem;
import com.dev.todos.R;
import com.dev.todos.databinding.WalletItemBinding;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {
    Context context;
    List<OrderListItem> orderList;
    String Type ="";

    public WalletAdapter(Context context) {
        this.context = context;
    }

    public void setUpList(List<OrderListItem> orderList, String type) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }


    @Override
    public WalletAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WalletItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wallet_item,parent,false);
        return  new WalletAdapter.MyViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final WalletAdapter.MyViewHolder holder, final int position) {

        holder.binding.orderTxt.setText(context.getString(R.string.order_no)+orderList.get(position).getOrderId());

      //  if(Type.equalsIgnoreCase("CommissionEarned")){
            if(orderList.get(position).getPayed_status().equalsIgnoreCase("NotPayed")){
                holder.binding.mainTxt.setTextColor(context.getColor(R.color.green));
                holder.binding.mainTxt.setText("$"+orderList.get(position).getCommissionEarned());
            }else{
                holder.binding.mainTxt.setTextColor(context.getColor(R.color.red));
                holder.binding.mainTxt.setText("$"+orderList.get(position).getCommissionEarned());
            }
       /* }else{
            holder.binding.mainTxt.setTextColor(context.getColor(R.color.green));
            holder.binding.mainTxt.setText("$"+orderList.get(position).getCommissionEarned());
        }*/

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        WalletItemBinding binding;
        public MyViewHolder(WalletItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
