package com.dev.todos.Activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dev.todos.Adapter.OfferAdapter;
import com.dev.todos.Model.SingleorderList.OrderOfferListItem;
import com.dev.todos.Model.SingleorderList.ProductListItem;
import com.dev.todos.Model.SingleorderList.SingleOrderResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.ActivityOfferBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferActivity extends BaseActivity {

    private KProgressHUD hud;
    List<ProductListItem> productLists;
    List<OrderOfferListItem> orderList = new ArrayList<>();

    ActivityOfferBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_offer);

        setStatusBarGradiant(OfferActivity.this);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent i = getIntent();
        String Orderid = i.getStringExtra("OrderId");

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put(Keys.order_id, Orderid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getProductList(UseMe.getJsonObject(jsonObject1));

    }

    private void getProductList(JsonObject jsonObject) {
        showLoading(OfferActivity.this);
        Call<SingleOrderResponse> orderListResponceCall = ApiClient.getService().getSingleDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderResponse>() {
            @Override
            public void onResponse(Call<SingleOrderResponse> call, Response<SingleOrderResponse> response) {
               hideLoading();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    productLists = response.body().getOrderDetails().getProductList();
                    orderList = response.body().getOrderDetails().getOrderOfferList();
                    if(orderList.isEmpty()){
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.notxt.setVisibility(View.VISIBLE);
                    }else{
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        binding.notxt.setVisibility(View.GONE);
                        Log.d("TAG", "onResponse: "+orderList.size());
                        binding.recyclerView.setAdapter(new OfferAdapter(getApplicationContext(), getSupportFragmentManager(),orderList,response.body().getOrderDetails(),response.body().getOrderDetails().getProductList(),""));

                    }
                } else {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.notxt.setText(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<SingleOrderResponse> call, Throwable t) {
                hideLoading();
            }
        });
    }
}