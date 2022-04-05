package com.dev.todos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dev.todos.Model.TripList;
import com.dev.todos.R;
import com.dev.todos.databinding.NewAssoicateItemBinding;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssocaiteTripsAdapter extends RecyclerView.Adapter<AssocaiteTripsAdapter.MyViewHolder> {

    List<TripList> TripList;
    Context context;
    FragmentManager fragmentManager;
    OnClick_Event onClick_event;

    public AssocaiteTripsAdapter(FragmentActivity context, FragmentManager fragmentManager, List<TripList> tripList) {
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.TripList = tripList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewAssoicateItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.new_assoicate_item,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AssocaiteTripsAdapter.MyViewHolder holder, int position) {

        String date = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date d = simpleDateFormat.parse(TripList.get(position).getTravel_date());
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            date = simpleDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.traveldate.setText("Travel Date: "+date);
        holder.binding.Formcity.setText(TripList.get(position).getLocation_from());
        holder.binding.Tocity.setText(TripList.get(position).getLocation_to());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_event.setOnItemClickListener(TripList.get(position), position);
            }
        });

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ViewPagerIndicator pagerIndicator;
        NewAssoicateItemBinding binding;
        public MyViewHolder(NewAssoicateItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            viewPager=itemView.findViewById(R.id.viewPager);

        }
    }

    public interface OnClick_Event {
        void setOnItemClickListener(TripList tripList, int postion);
    }

    public void setOnItemClickListener(OnClick_Event onClick_event) {
        this.onClick_event = onClick_event;
    }

    @Override
    public int getItemCount() {
        return TripList.size();
    }
}
