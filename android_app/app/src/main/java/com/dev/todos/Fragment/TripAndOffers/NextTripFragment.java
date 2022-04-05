package com.dev.todos.Fragment.TripAndOffers;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.todos.Adapter.MyOfferAdapter;
import com.dev.todos.Fragment.TravellerOffer.OrderDetailsFragment;
import com.dev.todos.Fragment.TravellerOffer.PurchaseMadeFragment;
import com.dev.todos.Fragment.TravellerOffer.SatisfiedBuyerFragment;
import com.dev.todos.Model.MyOrderResponce;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.Util.UseMe;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextTripFragment extends Fragment {
    TabLayout tabLayout;
    private KProgressHUD hud;
    ViewPager viewPager;
    ArrayList<Fragment> fragmentArrayList;
    RecyclerView recyclerView;
    LinearLayout viewActive,viewTransit,viewReceived,viewCancelled;
    Snackbar snackBar;
    TextView txtOrders,txtPendingzOrders,txtAcceptedOrders,txtDeliveredOrders;
    TextView txtNoOrders;

    String tripId,travelDate;

    String Type = "all";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_next_trip, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtOrders=view.findViewById(R.id.txtOrders);
        txtPendingzOrders=view.findViewById(R.id.txtPendingOrders);
        txtAcceptedOrders=view.findViewById(R.id.txtAcceptedOrders);
        txtDeliveredOrders=view.findViewById(R.id.txtDeliverdOrders);
        txtNoOrders=view.findViewById(R.id.txtNoOrder);
        txtNoOrders.setVisibility(View.GONE);

        recyclerView=view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewCancelled=view.findViewById(R.id.viewCancelled);
        viewReceived=view.findViewById(R.id.viewRecieved);
        viewTransit=view.findViewById(R.id.viewTransit);
        viewActive=view.findViewById(R.id.viewActive);

        try {
            UseMe.isBottomNavVisible(true);
            Bundle bundle = new Bundle();
            bundle = getArguments();
            tripId = bundle.getString(Keys.trip_id);
            travelDate=bundle.getString(Keys.travel_date);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Keys.trip_id, tripId);
                jsonObject.put(Keys.status, Type);
                getMyOffer(UseMe.getJsonObject(jsonObject), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        view.findViewById(R.id.llActive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(Keys.trip_id, tripId);
                    Type = "all";
                    jsonObject.put(Keys.status,Type);

                    getMyOffer(UseMe.getJsonObject(jsonObject),false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        view.findViewById(R.id.llTransit).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(Keys.trip_id, tripId);
                    Type = "pending";
                    jsonObject.put(Keys.status,Type);

                    getMyOffer(UseMe.getJsonObject(jsonObject),false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        view.findViewById(R.id.llReceived).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(Keys.trip_id, tripId);
                    Type = "accepted";
                    jsonObject.put(Keys.status,Type);

                    getMyOffer(UseMe.getJsonObject(jsonObject),true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        view.findViewById(R.id.llCancelled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put(Keys.trip_id, tripId);
                    Type = "delivered";
                    jsonObject.put(Keys.status,Type);
                    getMyOffer(UseMe.getJsonObject(jsonObject),false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });




    }



    public void getMyOffer(JsonObject jsonObject, final boolean isAccepted){
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Call<MyOrderResponce> myOrderResponceCall= ApiClient.getService().getMyOffer(jsonObject);

        myOrderResponceCall.enqueue(new Callback<MyOrderResponce>() {
            @Override
            public void onResponse(Call<MyOrderResponce> call, Response<MyOrderResponce> response) {
                hud.dismiss();
                txtAcceptedOrders.setText(response.body().getAccepted_order_count()+"\n"+getString(R.string.Accepted));
                txtDeliveredOrders.setText(response.body().getDelivered_order_count()+"\n"+getString(R.string.Delivered));
                txtPendingzOrders.setText(response.body().getPending_order_count()+"\n"+getString(R.string.Pending));
                txtOrders.setText(response.body().getAll_order_count()+ "\n"+getString(R.string.ordersnew));

                if(Type.equals("all")){
                    viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                    viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                }else if (Type.equals("pending")){
                    viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                    viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                }else if (Type.equals("accepted")){
                    viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                    viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));

                }else if (Type.equals("delivered")){
                    viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                    viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));

                }

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new MyOfferAdapter(getActivity(), getFragmentManager(), response.body().getTravel_order_list(),
                            isAccepted,travelDate,Type));
                    txtNoOrders.setVisibility(View.GONE);
                }
                else {
                    txtNoOrders.setText(getString(R.string.noorders));
                    recyclerView.setVisibility(View.GONE);
                    txtNoOrders.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<MyOrderResponce> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }

}