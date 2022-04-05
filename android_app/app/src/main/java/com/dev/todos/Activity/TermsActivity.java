package com.dev.todos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dev.todos.Model.Description.DescResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.databinding.ActivityTermsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsActivity extends BaseActivity {

    ActivityTermsBinding binding;
    String Activity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_terms);

        setStatusBarGradiant(TermsActivity.this);

        Intent intent = getIntent();

        Activity = intent.getStringExtra("Activity");

        getApiCall();

        if(Activity.equals("Terms")){
            binding.titleTxt.setText(getText(R.string.terms_condition));
            }
        else if(Activity.equals("Faq")){
            binding.titleTxt.setText("faqs");
           }
        else{
            binding.titleTxt.setText(getText(R.string.privacy));
           }


        binding.imgBack.setOnClickListener(v -> finish());
    }

    private void getApiCall() {
        showLoading(this);
        Call<DescResponse> descResponseCall = ApiClient.getService().desc();
        descResponseCall.enqueue(new Callback<DescResponse>() {
            @Override
            public void onResponse(Call<DescResponse> call, Response<DescResponse> response) {
                hideLoading();
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        if(Activity.equals("Terms")){
                          binding.Text.setText(response.body().getTermsAndConditionPageDescription());
                        }
                        else if(Activity.equals("Faq")){
                            binding.Text.setText(response.body().getFaqPageDescription());
                        }
                        else{
                            binding.Text.setText(response.body().getPrivacyPolicyPageDescription());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DescResponse> call, Throwable t) {
                hideLoading();
            }
        });
    }


}