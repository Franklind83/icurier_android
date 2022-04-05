package com.dev.todos.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dev.todos.R;
import com.dev.todos.RoomDatabase.RoomDatabaseClass;

import com.dev.todos.databinding.NotificationItemBinding;



import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    Context context;
    FragmentManager fragmentManager;
    List<RoomDatabaseClass> taskList;

    public NotificationAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        NotificationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.notification_item, parent, false);
        return new NotificationAdapter.MyViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(NotificationAdapter.MyViewHolder holder, final int position) {

        holder.binding.text.setText(taskList.get(position).getSubtitle());
        holder.binding.dateTxt.setText(taskList.get(position).getCurrentTime());

        if(taskList.get(position).getIsRead().equals("0")){
            holder.binding.linear.setBackgroundColor(context.getColor(R.color.blue));
            holder.binding.text.setTextColor(Color.WHITE);
            holder.binding.dateTxt.setTextColor(Color.WHITE);
        }else{
            holder.binding.linear.setBackgroundColor(Color.WHITE);
            holder.binding.text.setTextColor(Color.BLACK);
            holder.binding.dateTxt.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setUpList(List<RoomDatabaseClass> taskList) {
        this.taskList = taskList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding binding;

        public MyViewHolder(NotificationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
