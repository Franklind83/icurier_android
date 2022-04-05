package com.dev.todos.Activity;


import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Adapter.ShopperNotLoginAdapter;
import com.dev.todos.Fragment.WithoutLogin.TravellerWithoutLoginFragment;
import com.dev.todos.Model.SaveProfileData.SaveProfileRequest;
import com.dev.todos.Model.SaveProfileData.SaveProfileResponse;
import com.dev.todos.Model.Signup.SignupRequest;
import com.dev.todos.Model.Signup.SignupResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;

import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.databinding.ActivitySignupBinding;

import com.ehsanmashhadi.library.model.Country;
import com.ehsanmashhadi.library.view.CountryPicker;
import com.ehsanmashhadi.library.view.RecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends BaseActivity {

    private ActivitySignupBinding binding;
    private Uri mCropImageUri;
    String base64="",stringDialCode="",stringCountry="";
    CountryPicker countryPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        Log.e("lang6",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,""));
        if (Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,"en").equals("en")){
            setLocale(SignupActivity.this,"en");
        }else{
            setLocale(SignupActivity.this,"es");

        }

        if(!Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.USERID,"").equals("")){
          binding.userNameEt.setText(Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.USERNAME,""));
          binding.emailEt.setText(Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.USEREMAIL,""));
        }

        onClickListener();
        transparentStatusAndNavigation();

    }

    private void onClickListener() {
        binding.signInTxt.setOnClickListener(view -> finish());

        binding.signupBtn.setOnClickListener(view -> SignupApiCall());

        binding.imgSelect.setOnClickListener(view -> CropImage.startPickImageActivity(SignupActivity.this));

        binding.countryEt.setOnClickListener(v -> {

            countryPicker = new CountryPicker.Builder(SignupActivity.this)
                    .showingFlag(true)
                    .enablingSearch(true)
                    .sortBy(CountryPicker.Sort.COUNTRY)
                    .setViewType(CountryPicker.ViewType.BOTTOMSHEET)
                    .setCountrySelectionListener(country ->
                                    showCountry(country.getName(),country.getDialCode())).build();

            countryPicker.show(SignupActivity.this);

        });


        binding.cardLL.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), ShopperListNotLoginActivity.class));
        });
        binding.loginLL.setOnClickListener(view -> {
            finish();
        });
        binding.aereoplaneLL.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),TravellerListNotLoginActivity.class));
        });
    }

    private void showCountry(String name, String dialCode) {
        stringDialCode = dialCode;
        binding.countryEt.setText(name);
    }

    private void SignupApiCall() {
        if(binding.userNameEt.getText().toString().isEmpty()){
            binding.userNameEt.setError("Enter username");
            binding.userNameEt.requestFocus();
        }else if(binding.countryEt.getText().toString().isEmpty()){
            binding.countryEt.setError("Enter country");
            binding.countryEt.requestFocus();
        }else if(binding.telephoneEt.getText().toString().isEmpty()){
            binding.telephoneEt.setError("Enter telephone no.");
            binding.telephoneEt.requestFocus();
        }else if(binding.emailEt.getText().toString().isEmpty()){
            binding.emailEt.setError("Enter email");
            binding.emailEt.requestFocus();
        }else if (!binding.emailEt.getText().toString().matches(getString(R.string.email_pattern))){
            binding.emailEt.setError("Enter correct email");
            binding.emailEt.requestFocus();
        }else if(binding.passEt.getText().toString().isEmpty()){
            binding.passEt.setError("Enter password");
            binding.passEt.requestFocus();
        }else if(binding.passEt.getText().length()<8){
            binding.passEt.setError("Enter more than 8 digit password");
            binding.passEt.requestFocus();
        }else if(binding.confirmPassEt.getText().toString().isEmpty()){
            binding.confirmPassEt.setError("Enter confirm password");
            binding.confirmPassEt.requestFocus();
        }else if(!binding.passEt.getText().toString().equals(binding.confirmPassEt.getText().toString())){
            binding.confirmPassEt.setError("Enter correct password");
            binding.confirmPassEt.requestFocus();
        }else{
            if(!Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.USERID,"").equals("")){
                saveProfileData();
            }else{
                signupData();
            }
            }

    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                binding.imgSelect.setImageURI(resultUri);
                base64 =encodeImage(resultUri.getPath());
            //    Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String encodeImage(String path)
    {
        String encImage="";

        try {


            File imagefile = new File(path);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            base64 =encImage = Base64.encodeToString(b, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
            encImage="";
        }
        //Base64.de
        return encImage;

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

    private void signupData() {
        showLoading(SignupActivity.this);
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName(binding.userNameEt.getText().toString());
        signupRequest.setCountry(binding.countryEt.getText().toString());
        signupRequest.setMobile(binding.telephoneEt.getText().toString());
        signupRequest.setEmail(binding.emailEt.getText().toString());
        signupRequest.setPassword(binding.passEt.getText().toString());
        signupRequest.setDialCode(stringDialCode);
        signupRequest.setImage(base64);
        signupRequest.setTokenFrom(getString(R.string.token));
        signupRequest.setUserToken(Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.TOKEN,""));

        Call<SignupResponse> signupResponseCall = ApiClient.getService().signup(signupRequest);
        signupResponseCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    hideLoading();
                    if(response.body().getStatus().equals("1")){
                        Sharedpreferences.saveToPreferences(getApplicationContext(), AppConstant.IS_LOGIN,true);
                        Sharedpreferences.saveToPreferences(getApplicationContext(), AppConstant.USERID,response.body().getUserDetails().getUserId());
                        Sharedpreferences.saveToPreferences(getApplicationContext(), AppConstant.USERNAME,response.body().getUserDetails().getName());
                        Sharedpreferences.saveToPreferences(getApplicationContext(), AppConstant.PROFILEIMAGE,response.body().getUserDetails().getProfileImage());

                        Intent i = new Intent(getApplicationContext(),BottomnavigationActivity.class);
                        i.putExtra("Activity","NewLogin");
                        startActivity(i);
                        finishAffinity();
                    }else{
                        showSnackbar(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                hideLoading();
                showSnackbar(getString(R.string.nointernet));
            }
        });
    }

    private void saveProfileData() {
        showLoading(this);
        SaveProfileRequest saveProfileRequest = new SaveProfileRequest();
        saveProfileRequest.setUserId(Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.USERID,""));
        saveProfileRequest.setName(binding.userNameEt.getText().toString());
        saveProfileRequest.setMobile(binding.telephoneEt.getText().toString());
        saveProfileRequest.setCountry(binding.passEt.getText().toString());
        saveProfileRequest.setEmail(binding.emailEt.getText().toString());
        saveProfileRequest.setPassword(binding.passEt.getText().toString());
        saveProfileRequest.setDialCode(stringDialCode);
        saveProfileRequest.setRoutingNumber("");
        saveProfileRequest.setSortCode("");
        saveProfileRequest.setIban("");
        saveProfileRequest.setBankCode("");
        saveProfileRequest.setBranchCode("");
        saveProfileRequest.setClabe("");
        saveProfileRequest.setBranchName("");
        saveProfileRequest.setOwnerName("");
        saveProfileRequest.setIfsc("");
        saveProfileRequest.setClearingCode("");
        saveProfileRequest.setBsb("");
        saveProfileRequest.setInstitution("");
        saveProfileRequest.setPassportNumber("");
        saveProfileRequest.setTransitNumber("");
        saveProfileRequest.setBankCountry("");
        saveProfileRequest.setBankName("");
        saveProfileRequest.setBankId("");
        saveProfileRequest.setBankAccountNumber("");
        saveProfileRequest.setBankAccountType("");
        saveProfileRequest.setImage(base64);

        Call<SaveProfileResponse> saveProfileResponseCall = ApiClient.getService().saveprofile(saveProfileRequest);
        saveProfileResponseCall.enqueue(new Callback<SaveProfileResponse>() {
            @Override
            public void onResponse(Call<SaveProfileResponse> call, Response<SaveProfileResponse> response) {
                if(response.isSuccessful()){
                    hideLoading();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){
                      // Sharedpreferences.saveToPreferences(getApplicationContext(), AppConstant.PROFILEIMAGE,response.body().getUserDetails().getProfileImage());
                        Intent i = new Intent(getApplicationContext(),BottomnavigationActivity.class);
                        i.putExtra("Activity","NewLogin");
                        startActivity(i);
                        finishAffinity();
                    }else{
                        showSnackbar(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveProfileResponse> call, Throwable t) {
                hideLoading();
            }
        });
    }

}
