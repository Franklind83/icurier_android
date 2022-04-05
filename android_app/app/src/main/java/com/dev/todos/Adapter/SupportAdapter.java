package com.dev.todos.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dev.todos.Model.GetSupport.SupportListItem;
import com.dev.todos.R;
import com.dev.todos.databinding.OrderItemNewBinding;
import com.dev.todos.databinding.SupportItemBinding;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.MyViewHolder>{



    Context context;
    FragmentManager fragmentManager;
    List<SupportListItem> supportListItems;

    public SupportAdapter(FragmentActivity activity, FragmentManager fragmentManager) {
        this.context=activity;
        this.fragmentManager=fragmentManager;
    }


    @Override
    public SupportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SupportItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.support_item,parent,false);
        return new SupportAdapter.MyViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull SupportAdapter.MyViewHolder holder, int position) {
        holder.binding.subject.setText(supportListItems.get(position).getSubject());
        holder.binding.reasonTxt.setText(supportListItems.get(position).getReason());

        SpannableString spannableString = new SpannableString(context.getString(R.string.desc)+supportListItems.get(position).getDescription().replace("\n",""));
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.BLACK);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        spannableString.setSpan(bss, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(foregroundSpan, 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.binding.descTxt.setText(spannableString);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = simpleDateFormat.parse(supportListItems.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = simpleDateFormat.format(d);

        holder.binding.dateTxt.setText(formatedDate);
        holder.binding.timeTxt.setText(supportListItems.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return supportListItems.size();
    }

    public void setUplist(List<SupportListItem> supportListItems) {
        this.supportListItems = supportListItems;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SupportItemBinding binding;

        ViewPager viewPager;
        ViewPagerIndicator viewPagerIndicator;
        public MyViewHolder(SupportItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            //viewPager=itemView.findViewById(R.id.viewPager);
            // viewPagerIndicator=itemView.findViewById(R.id.indicator);
        }
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
