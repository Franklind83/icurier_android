package com.dev.todos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.dev.todos.Model.Description.DescResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.databinding.ActivityContactBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends BaseActivity {

    ActivityContactBinding binding;

    int MY_PERMISSIONS_REQUEST_CALL_PHONE =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contact);

        setStatusBarGradiant(ContactActivity.this);
        getApiCall();
        setOnClickListerner();
    }

    private void setOnClickListerner() {

        binding.imgBack.setOnClickListener(v -> finish());


        binding.phone1Txt.setOnClickListener(v -> {
            String number = ("tel:" + binding.phone1Txt.getText().toString());
          Intent mIntent = new Intent(Intent.ACTION_CALL);
            mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(ContactActivity.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ContactActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //You already have permission
                try {
                    startActivity(mIntent);
                } catch(SecurityException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.phone2Txt.setOnClickListener(v -> {

            String number = ("tel:" + binding.phone2Txt.getText().toString());
            Intent mIntent = new Intent(Intent.ACTION_CALL);
            mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(ContactActivity.this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ContactActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //You already have permission
                try {
                    startActivity(mIntent);
                } catch(SecurityException e) {
                    e.printStackTrace();
                }
            }
        });
       



        binding.urlTxt.setOnClickListener(v -> {
            String url = "https://"+binding.urlTxt.getText().toString().trim();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
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
                    binding.emailTxt.setText(response.body().getContactPageEmail());
                    binding.phone1Txt.setText(response.body().getContactPageTelephone1());
                    binding.phone2Txt.setText(response.body().getContactPageTelephone2());
                    binding.urlTxt.setText(response.body().getContactPageWebsiteLink());
                    binding.taglineTxt.setText(response.body().getContactPageDescription());

                        binding.imgFacebook.setOnClickListener(v -> {
                            String url = response.body().getContactPageFacebookLink();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        });

                        binding.imgInsta.setOnClickListener(v -> {
                            String url = response.body().getContactPageInstagramLink();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        });


                        binding.emailTxt.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"+response.body().getContactPageEmail())); // only email apps should handle this
                            intent.putExtra(Intent.EXTRA_EMAIL,"");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "");
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        });
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