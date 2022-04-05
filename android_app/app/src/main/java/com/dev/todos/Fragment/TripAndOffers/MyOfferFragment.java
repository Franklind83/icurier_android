package com.dev.todos.Fragment.TripAndOffers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dev.todos.Adapter.TripListAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.TripAndOffers.AddTripFragment;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyOfferFragment extends Fragment {

    RecyclerView recyclerView;
    ViewPager pager;
    FrameLayout nxtTab, preTab;
    String status = "next";
    private KProgressHUD hud;
    Snackbar snackBar;
    TextView noTxt;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_offer, container, false);
    }

    @Override
    public void onStart() {
        getMyTrip(status);
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        noTxt = view.findViewById(R.id.noTxt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        nxtTab = view.findViewById(R.id.nxtTab);
        preTab = view.findViewById(R.id.pastTab);



        pager = view.findViewById(R.id.viewPager);

        view.findViewById(R.id.llNxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   pager.setCurrentItem(getItem(+1), true);
                preTab.setVisibility(View.GONE);
                nxtTab.setVisibility(View.VISIBLE);
                status = "next";
                getMyTrip(status);

            }
        });
        view.findViewById(R.id.llNxt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pager.setCurrentItem(getItem(+1), true);
                preTab.setVisibility(View.GONE);
                nxtTab.setVisibility(View.VISIBLE);
                status = "next";
                getMyTrip(status);

            }
        });
        view.findViewById(R.id.llPre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pager.setCurrentItem(getItem(-1), true);
                nxtTab.setVisibility(View.GONE);
                preTab.setVisibility(View.VISIBLE);
                status = "past";
                getMyTrip(status);

            }
        });

        view.findViewById(R.id.llPre2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  pager.setCurrentItem(getItem(-1), true);
                nxtTab.setVisibility(View.GONE);
                preTab.setVisibility(View.VISIBLE);
                status = "past";
                getMyTrip(status);

            }
        });

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new AddTripFragment());
            }
        });


    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }


    public void getMyTrip(String status) {
        JsonObject j = null;
        try {
            hud=   KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));
            jsonObject.put(Keys.status, status);

            Log.d("TAG", "getMyTrip: "+StaticClass.getDate());
            jsonObject.put(Keys.current_date, StaticClass.getDate());
            j = UseMe.getJsonObject(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<Responce> responceCall = ApiClient.getService().getMyTrip(j);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    hud.dismiss();
                    recyclerView.setVisibility(View.VISIBLE);
                    noTxt.setVisibility(View.GONE);
                    Log.e("dlataaaaaaaa",response.body().getTripLists().toString());
                    recyclerView.setAdapter(new TripListAdapter(getActivity(), getFragmentManager(), response.body().getTripLists(),status));
                } else {
                    noTxt.setVisibility(View.VISIBLE);
                    hud.dismiss();
                    recyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                noTxt.setVisibility(View.VISIBLE);
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });


    }


}