package com.dev.todos.Fragment.TripAndOffers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Adapter.OfferAdapter;
import com.dev.todos.Model.SingleorderList.OrderOfferListItem;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentOffersBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OffersFragment extends Fragment {

    FragmentOffersBinding binding;
    private KProgressHUD hud;
   List<ProductListItem> productLists;
    List<OrderOfferListItem> orderList = new ArrayList<>();

    String Frag="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = getArguments();

        String Orderid = bundle.getString("OrderId");
        Frag = bundle.getString("Fragment");


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.imgBack.setOnClickListener(v -> getFragmentManager().popBackStackImmediate());

        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put(Keys.order_id, Orderid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getProductList(UseMe.getJsonObject(jsonObject1));


    }

    private void getProductList(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        Call<SingleOrderResponse> orderListResponceCall = ApiClient.getService().getSingleDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderResponse>() {
            @Override
            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    productLists = response.body().getOrderDetails().getProductList();
                    orderList = response.body().getOrderDetails().getOrderOfferList();
                    if(orderList.isEmpty()){
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.notxt.setVisibility(View.VISIBLE);
                        }else{
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        binding.notxt.setVisibility(View.GONE);
                        binding.recyclerView.setAdapter(new OfferAdapter(getActivity(), getFragmentManager(),orderList,response.body().getOrderDetails(),response.body().getOrderDetails().getProductList(),Frag));

                    }
                } else {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.notxt.setText(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                hud.dismiss();
            }
        });
    }
}