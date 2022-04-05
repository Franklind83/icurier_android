package com.dev.todos.Activity;


import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.dev.todos.Fragment.BuyerOffer.BuyerPurchaseMadeFragment;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.WithoutLogin.TravellerWithoutLoginFragment;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.databinding.ActivityMainBinding;

import java.util.Locale;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);




        transparentStatusAndNavigation();
        onClickListener();
    }

    private void onClickListener() {
        binding.loginLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra("Activity","NewLogin");
                startActivity(intent);
            }
        });

        binding.cardLL.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),ShopperListNotLoginActivity.class);
            startActivity(intent);
        });

        binding.aereoplaneLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TravellerListNotLoginActivity.class));
            }
        });

        binding.TermsCondLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TermsActivity.class);
                intent.putExtra("Activity","Terms");
                startActivity(intent);
            }
        });

        binding.faqLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TermsActivity.class);
                intent.putExtra("Activity","Faq");
                startActivity(intent);
            }
        });

        binding.privacyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TermsActivity.class);
                intent.putExtra("Activity","Privacy");
                startActivity(intent);
            }
        });

        binding.contactsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),ContactActivity.class));
            }
        });
    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS ,false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



    @Override
    public void onBackPressed() {

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
        } else {
            showSnackbar(getString(R.string.back));
            back_pressed = System.currentTimeMillis();
        }


    }

    @Override
    protected void onStart() {
        Log.d("TAG", "onStart: "+Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,"es"));
        Log.e("lang5",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,""));
        Log.e("lang5",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.lang_val,""));

//
//        if (Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,"en").equals("en")){
//            setLocale(MainActivity.this,"en");
//        }else{
//            setLocale(MainActivity.this,"es");
//
//        }
        if (Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.lang_val,"false").equals("false"))
        {   setLocale(MainActivity.this,"es");

        }else{
            setLocale(MainActivity.this,"en");

        }
        super.onStart();
    }





}
