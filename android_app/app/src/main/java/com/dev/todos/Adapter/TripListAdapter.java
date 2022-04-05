package com.dev.todos.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.TripAndOffers.EditTripFragment;
import com.dev.todos.Fragment.TripAndOffers.NextTripFragment;
import com.dev.todos.Fragment.TripAndOffers.PastTripFragment;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.TripList;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.OfferItemBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;


import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.MyViewHolder> {
    Context context;
    FragmentManager fragmentManager;
    ArrayList<TripList> tripLists;
   KProgressHUD hud;
   String status="";
   androidx.appcompat.app.AlertDialog alertDialog;

    public TripListAdapter(Context context, FragmentManager fragmentManager, ArrayList<TripList> tripLists, String status) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.tripLists = tripLists;
        this.status = status;
    }


    @Override
    public TripListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfferItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.offer_item,parent,false);
        return  new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final TripListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {



        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        try {

            date = simpleDateFormat.parse(tripLists.get(position).getTravel_date());
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        String format = new SimpleDateFormat(" MMM").format(calendar.getTime());
        holder.binding.txtDate.setText(format+" "+calendar.get(Calendar.DATE)+", "+calendar.get(Calendar.YEAR));
        holder.binding.txtFromLocation.setText(tripLists.get(position).getLocation_from());
        holder.binding.txtToLocation.setText(tripLists.get(position).getLocation_to());
        holder.binding.txtOrdersToDeliver.setText(tripLists.get(position).getOrder_delivery_count());


        holder.binding.txtOrders.setText(tripLists.get(position).getOrder_count());
        holder.binding.txtErning.setText("$"+tripLists.get(position).getTotal_earning());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("past")){
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.trip_id, tripLists.get(position).getTrip_id());
                    bundle.putString(Keys.travel_date, holder.binding.txtDate.getText().toString());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new PastTripFragment());

                }else {
                    //context.startActivity(new Intent(context, PagerAct.class));
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.trip_id, tripLists.get(position).getTrip_id());
                    bundle.putString(Keys.travel_date, holder.binding.txtDate.getText().toString());
                    new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager, bundle, R.id.frameLayoout, new NextTripFragment());
                }
            }
        });




        holder.binding.llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tripLists.get(position).getAccepted_status().equals("1")){
                    setalertDialog(context.getString(R.string.trip_cannot_cancel), "1");
                }else {
                    showDialog(context.getString(R.string.notrip),
                            tripLists.get(position).getTrip_id());
                }
            }
        });
        holder.binding.llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dataaaa",""+tripLists.get(position).getLocation_latfrom()+""+tripLists.get(position).getLocation_longfrom());
                Log.e("dataaaa",""+tripLists.get(position).getLocation_latto()+""+tripLists.get(position).getLocation_longto());
                Bundle bundle=new Bundle();
                bundle.putString(Keys.trip_id,tripLists.get(position).getTrip_id());
                bundle.putString(Keys.travel_date,tripLists.get(position).getTravel_date());
                bundle.putString(Keys.location_from,tripLists.get(position).getLocation_from());
                bundle.putString(Keys.location_to,tripLists.get(position).getLocation_to());
                bundle.putString(Keys.latfrom,tripLists.get(position).getLocation_latfrom());
                bundle.putString(Keys.longfrom,tripLists.get(position).getLocation_longfrom());
                bundle.putString(Keys.latto,tripLists.get(position).getLocation_latto());
                bundle.putString(Keys.longto,tripLists.get(position).getLocation_longto());
                new ChangeFragment(context).changeFragmentWithBackStack(fragmentManager,bundle,R.id.frameLayoout,new EditTripFragment());

                /*showEditDilog(tripLists.get(position).getTravel_date(),
                        tripLists.get(position).getTrip_id());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        OfferItemBinding binding;
        public MyViewHolder(OfferItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public void showDialog(String msg, final String tripId){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(Keys.trip_id, tripId);
                    cancelOrder(UseMe.getJsonObject(jsonObject));
                }catch (Exception e){
                    e.printStackTrace();
                }


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

    public void showEditDilog(String date, final String tripId){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Select date to Edit Trip");
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.cancel_trip_layout,null,false);
        builder.setView(v);
        final TextView textView=v.findViewById(R.id.txtDate);
        try {

            textView.setText(UseMe.getDateFormat(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        ImageView imageView=v.findViewById(R.id.imgDate);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(context,textView);
            }
        });


        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Date d= null;
                try {
                    d = simpleDateFormat.parse(textView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String sDate=simpleDateFormat.format(d);
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(Keys.trip_id,tripId);
                    jsonObject.put(Keys.travel_date,sDate);
                    editTrip(UseMe.getJsonObject(jsonObject));
                }catch (Exception e){
                    e.printStackTrace();
                }

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

    public void editTrip(JsonObject jsonObject){
        Call<Responce> responceCall= ApiClient.getService().editTripObject(jsonObject);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if (response.body().getStatus().equals(Keys.status_succes)){
                    getMyTrip("next");
                    Toast.makeText(context,context.getString(R.string.tripedited), Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context,response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {

            }
        });

    }

    public void cancelOrder(JsonObject jsonObject){
        hud=   KProgressHUD.create(Objects.requireNonNull(context))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Call<Responce> responceCall= ApiClient.getService().cancelTrip(jsonObject);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)){
                    setalertDialog(context.getString(R.string.tripcancel),"0");
                  //  Toast.makeText(context,"Trip Cancel Succesfully", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(context,response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();

            }
        });
    }


    public void getMyTrip(String status)  {
        JsonObject j=null;
        hud=   KProgressHUD.create(Objects.requireNonNull(context))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(context, AppConstant.USERID,""));
            jsonObject.put(Keys.status, status);
            jsonObject.put(Keys.current_date, StaticClass.getDate());
            j = UseMe.getJsonObject(jsonObject);


        }catch (Exception e){
            e.printStackTrace();
        }


        Call<Responce> responceCall= ApiClient.getService().getMyTrip(j);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {

                    tripLists=new ArrayList<>();
                    tripLists=response.body().getTripLists();
                    notifyDataSetChanged();

//                    pager.setVisibility(View.VISIBLE);
//                    pager.setAdapter(new OfferPager(getActivity(), getFragmentManager(), response.body().getTripLists()));
                }
                else {
                    tripLists=new ArrayList<>();
                    notifyDataSetChanged();
//                    Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
//                    pager.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();

            }
        });


    }


    public void setalertDialog(String mes, String s){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s.equals("0")) {
                    alertDialog.dismiss();
                    getMyTrip("next");
                }else{
                    alertDialog.dismiss();
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}
