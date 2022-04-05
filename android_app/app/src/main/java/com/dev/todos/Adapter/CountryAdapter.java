package com.dev.todos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.R;
import com.dev.todos.databinding.ItemCountryBinding;
import com.dev.todos.databinding.ItemRatingBinding;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> {

    Context context;
    List<String> countries;
    OnClickListener listener;
    public CountryAdapter(Context context) {
        this.context = context;
    }

    public void setUpList(List<String> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_country,parent,false);
        return new CountryAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.MyViewHolder holder, int position) {
        holder.binding.txtName.setText(countries.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            listener.onClick(countries.get(position));
            }
        });
    }

        @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        public void onClick(String countries);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCountryBinding binding;
        public MyViewHolder(ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }


    }


}
