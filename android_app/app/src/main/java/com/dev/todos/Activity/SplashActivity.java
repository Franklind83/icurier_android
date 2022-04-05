package com.dev.todos.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.APPLANGUAGE,"es");

        try {
            Log.e("lang",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,""));
//            Log.e("lang7",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE_Updated,""));
//            Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.APPLANGUAGE,"es");
//        setLocale(SplashActivity.this,"es");
//        if (Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE_Updated,"en").equals("en")){
            if (Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.lang_val,"false").equals("false"))
            {         setLocale(SplashActivity.this,"es");
                    Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.APPLANGUAGE,"es");
                    Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.lang_val,"false");

            }
            else
            {
                Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.APPLANGUAGE,"en");   Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.lang_val,"true");
                setLocale(SplashActivity.this,"en");
            }
//
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isLogin()) {
                        Intent i = new Intent(getApplicationContext(),BottomnavigationActivity.class);
                        i.putExtra("Activity","NewLogin");
                        startActivity(i);
                        finish();
                    } else {
                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       finish();
                    }

                }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLogin() {
        return Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.IS_LOGIN, false);
    }

}