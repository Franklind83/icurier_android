package com.dev.todos.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Model.GetRating.RatingListItem;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.databinding.ItemRatingBinding;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder>  {

    Context context;
    List<RatingListItem> ratingListItems;
    public RatingAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    public void setUpList(List<RatingListItem> ratingListItems) {
        this.ratingListItems =ratingListItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRatingBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_rating,parent,false);
        return new RatingAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.MyViewHolder holder, int position) {
        BaseActivity.setProfile(context,ratingListItems.get(position).getUserImageFrom(),holder.binding.imgProfile);
        holder.binding.txtName.setText(ratingListItems.get(position).getUserNameFrom());
        String str =  parseDateToddMMyyyy(ratingListItems.get(position).getDate());
        holder.binding.txtDate.setText(str);
        holder.binding.desTxt.setText(ratingListItems.get(position).getUserReview());
        holder.binding.ratingbar.setNumStars(5);
        String Star = ratingListItems.get(position).getUserRating();
        holder.binding.ratingbar.setRating(Float.parseFloat(Star));

    }

        @Override
    public int getItemCount() {
        return ratingListItems.size();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemRatingBinding binding;
        public MyViewHolder(ItemRatingBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }



}
