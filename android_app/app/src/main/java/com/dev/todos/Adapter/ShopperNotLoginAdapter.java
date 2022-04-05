package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Activity.LoginActivity;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.More.TravellerProfileFragment;
import com.dev.todos.Model.GetTravellerList.TripListItem;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.databinding.ItemShopperBinding;

import java.util.List;
import java.util.Locale;

import static com.dev.todos.Util.BaseActivity.setLocale;

public class ShopperNotLoginAdapter extends RecyclerView.Adapter<ShopperNotLoginAdapter.MyViewHolder>  {

    Context context;
    List<TripListItem> tripListItems;
    OnClick_Event onClick_event;
    FragmentManager fragmentManager;

    public  ShopperNotLoginAdapter(Context applicationContext, FragmentManager fragmentManager) {
        this.context = applicationContext;
        this.fragmentManager = fragmentManager;
    }


    public void setUpList(List<TripListItem> tripListItems) {
        this.tripListItems = tripListItems;
        notifyDataSetChanged();
    }

    public interface OnClick_Event {
        void setOnItemClickListener(TripListItem tripListItem, int postion);
    }

    public void setOnItemClickListener(OnClick_Event onClick_event) {
        this.onClick_event = onClick_event;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShopperBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_shopper,parent,false);
        Log.e("lang9",Sharedpreferences.readFromPreferences(context, AppConstant.APPLANGUAGE,""));
        if (Sharedpreferences.readFromPreferences(context, AppConstant.APPLANGUAGE,"en").equals("en")){
            language("en");
        }else{
            language("es");

        }

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.txtName.setText(tripListItems.get(position).getUserName());
        holder.binding.txtDestination.setText(tripListItems.get(position).getLocationTo());
        holder.binding.txtSource.setText(tripListItems.get(position).getLocationFrom());

        holder.binding.ratingBar.setRating(Float.parseFloat(tripListItems.get(position).getUserRating()));
        BaseActivity.setProfile(context,tripListItems.get(position).getUserImage(),holder.binding.profileImage);

       /* String usename = getColoredSpanned(tripListItems.get(position).getUserName(),"#FF0000");
        String locationto = getColoredSpanned(tripListItems.get(position).getLocationTo(),"#FF0000");
        String guest = getColoredSpanned("Guest","#FF0000");*/

        holder.binding.detailTxt.setText(Html.fromHtml(context.getString(R.string.hello)+" "+tripListItems.get(position).getUserName()+" "+context.getString(R.string.yourtravel)+" "+tripListItems.get(position).getLocationTo()+" "+context.getString(R.string.wouldlike)+" "+Sharedpreferences.readFromPreferences(context,AppConstant.USERNAME,"")+"."));

        holder.binding.txtHire.setOnClickListener(v -> {
            if (isLogin()) {
                onClick_event.setOnItemClickListener(tripListItems.get(position), position);
            } else {
                Intent intent= new Intent(context, LoginActivity.class);
                intent.putExtra("Activity","Shopper");
                intent.putExtra(Keys.trip_id,tripListItems.get(position).getTripId());
                intent.putExtra(Keys.traveller_id,tripListItems.get(position).getUserId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.binding.profileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("travellerId", tripListItems.get(position).getUserId());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new TravellerProfileFragment());
                }
              //  new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, R.id.frameLayoout, new ProfileFragment());
            }
        });

        String formatedDate=BaseActivity.getDateFormatmain(tripListItems.get(position).getTravelDate());
        holder.binding.txtTravelDate.setText(formatedDate);

        Bundle bundle=new Bundle();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  new ClsChangeFragment(context).changeFragmentWithBackStack(fragmentManager,null,R.id.frameLayoout,new ClsTravelDetailsFrag());
            }
        });

    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    public int getItemCount() {
        return tripListItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemShopperBinding binding;
        public MyViewHolder(ItemShopperBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    private boolean isLogin() {
        return Sharedpreferences.readFromPreferences(context, AppConstant.IS_LOGIN, false);
    }

    private void language(String text){
        Locale locale = new Locale(text);

        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }


}
