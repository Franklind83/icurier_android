package com.dev.todos.Fragment.WithoutLogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Activity.LoginActivity;
import com.dev.todos.Activity.ShopperListNotLoginActivity;
import com.dev.todos.Adapter.TravellerNotLoginAdapter;
import com.dev.todos.Model.GetOrderList.OrderListItem;
import com.dev.todos.Model.GetOrderList.OrderListRequest;
import com.dev.todos.Model.GetOrderList.OrderListResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.databinding.FragmentTravellerWithoutLoginBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Util.BaseActivity.setStatusBarGradiant;


public class TravellerWithoutLoginFragment extends Fragment {

    TravellerNotLoginAdapter travellerNotLoginAdapter;
    List<OrderListItem> orderLists;
    KProgressHUD hud;
    Snackbar snackBar;

    FragmentTravellerWithoutLoginBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_traveller_without_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        onClickListener();
        getApiCall();
    }

    private void getApiCall() {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        OrderListRequest orderListRequest = new OrderListRequest();
        orderListRequest.setUserId("0");
        orderListRequest.setPostString("all");

        Call<OrderListResponse> orderListResponseCall = ApiClient.getService().orderlist(orderListRequest);
        orderListResponseCall.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    if(response.body().getStatus().equals("1")){
                        binding.txtNoOrder.setVisibility(View.GONE);
                        orderLists = response.body().getOrderList();
                        setDataByView(orderLists);
                    }else{
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                hud.dismiss();
                binding.txtNoOrder.setVisibility(View.VISIBLE);
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }

    private void setDataByView(List<OrderListItem> orderLists) {

        if(travellerNotLoginAdapter == null){
            travellerNotLoginAdapter = new TravellerNotLoginAdapter(getActivity(),getFragmentManager());
            binding.recyclerView.setAdapter(travellerNotLoginAdapter);
            travellerNotLoginAdapter.setUplist(orderLists);
        }else{
            travellerNotLoginAdapter.setUplist(orderLists);
        }
    }

    private void initView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void onClickListener() {

        binding.icdownLl.setOnClickListener(view -> binding.typeLl.setVisibility(View.VISIBLE));
        binding.shopperTxt.setOnClickListener(view -> {
            binding.typeLl.setVisibility(View.GONE);
            startActivity(new Intent(getActivity(), ShopperListNotLoginActivity.class));
        });
        binding.travellerTxt.setOnClickListener(view -> {
            binding.typeLl.setVisibility(View.GONE);
        });

        binding.cardLL.setOnClickListener(view -> startActivity(new Intent(getActivity(),ShopperListNotLoginActivity.class)));
        binding.loginLL.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));

        });
        binding.aereoplaneLL.setOnClickListener(view -> {

        });
    }
}