package com.dev.todos.Fragment.More;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Adapter.WalletAdapter;
import com.dev.todos.Adapter.WalletAdapter1;
import com.dev.todos.Model.TravellerWallet.TravellerWalletRequest;
import com.dev.todos.Model.TravellerWallet.TravellerWalletResponse;
import com.dev.todos.Model.WalletCommission.OrderListItem;
import com.dev.todos.Model.WalletCommission.WalletRequest;
import com.dev.todos.Model.WalletCommission.WalletResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentWalletBinding;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    FragmentWalletBinding binding;
    List<OrderListItem> orderList = new ArrayList<>();
    WalletAdapter walletAdapter;
    KProgressHUD hud,hud1;
    String Type = "CommissionEarned";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UseMe.isBottomNavVisible(false);
        initView();
        getApiCall();

        binding.commsionLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "CommissionEarned";
                binding.viewRecieved.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                binding.viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                getApiCall();
            }
        });

        binding.lltraveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "TravellerCommissionEarned";
                binding.viewRecieved.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray_two));
                binding.viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                getTravellerApiCall();
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void initView() {
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void getTravellerApiCall() {
        hud1=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        TravellerWalletRequest travellerWalletRequest = new TravellerWalletRequest();
        travellerWalletRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));

        Call<TravellerWalletResponse> travellerWalletResponseCall = ApiClient.getService().Travellerwallet(travellerWalletRequest);
        travellerWalletResponseCall.enqueue(new Callback<TravellerWalletResponse>() {
            @Override
            public void onResponse(Call<TravellerWalletResponse> call, Response<TravellerWalletResponse> response) {
                hud1.dismiss();
                if(response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {
                        binding.txtTotal.setText("$"+response.body().getTotalCommissionEarned());
                        orderList.clear();
                        orderList = response.body().getOrderList();
                        settDataByViewTraveller(orderList);
                    } else {
                        binding.txtNoOrder.setText(response.body().getMsg());
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<TravellerWalletResponse> call, Throwable t) {
                hud1.dismiss();
            }
        });
    }

    private void getApiCall() {
        hud =   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));

        Call<WalletResponse> walletResponseCall = ApiClient.getService().wallet(walletRequest);
        walletResponseCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                hud.dismiss();
                if(response.isSuccessful()) {
                    if (response.body().getStatus().equals("1")) {
                        binding.txtTotal.setText("$"+response.body().getTotalCommissionEarned());
                        orderList.clear();
                        orderList = response.body().getOrderList();
                        settDataByView(orderList);
                    } else {
                        binding.txtNoOrder.setText(response.body().getMsg());
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                hud.dismiss();
            }
        });
    }

    private void settDataByView(List<OrderListItem> orderList) {

        if(walletAdapter == null){
            WalletAdapter walletAdapter = new WalletAdapter(getContext());
            binding.rv.setAdapter(walletAdapter);
            walletAdapter.setUpList(orderList,Type);
        }else{
            WalletAdapter walletAdapter = new WalletAdapter(getContext());
            binding.rv.setAdapter(walletAdapter);
            walletAdapter.setUpList(orderList,Type);
        }
    }


    private void settDataByViewTraveller(List<OrderListItem> orderList) {
            WalletAdapter1 walletAdapter1 = new WalletAdapter1(getContext());
            binding.rv.setAdapter(walletAdapter1);
            walletAdapter1.setUpList(orderList,Type);
    }

}