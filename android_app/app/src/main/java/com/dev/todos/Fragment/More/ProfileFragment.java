package com.dev.todos.Fragment.More;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Activity.ChangePasswordActivity;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.GetProfileData.GetProfileRequest;
import com.dev.todos.Model.GetProfileData.GetProfileResponse;
import com.dev.todos.Model.SaveProfileData.SaveProfileRequest;
import com.dev.todos.Model.SaveProfileData.SaveProfileResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Url.WebService;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentProfileBinding;
import com.ehsanmashhadi.library.view.CountryPicker;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment{

    FragmentProfileBinding binding;
    CountryPicker countryPicker;
    Bundle bundle;
    private KProgressHUD hud;

    int personalflag=0,paymentFlag=0,personalRanFlag=0;
    String Dialcode="";
    Snackbar snackBar;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_STORAGE_REQUEST_CODE = 110;
    private static final int MY_READ_EXTERNAL_STORAGE_REQUEST_CODE = 105;
    String encodedImage="";
    private Uri mCropImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProfileData();

        UseMe.isBottomNavVisible(true);
        onClickListener();

    }

    private void getProfileData() {
        hud=  KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        GetProfileRequest getProfileRequest = new GetProfileRequest();
        getProfileRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERID,""));

        Call<GetProfileResponse> getProfileResponseCall = ApiClient.getService().profile(getProfileRequest);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){

                        Sharedpreferences.saveToPreferences(getActivity(), AppConstant.PROFILEIMAGE, response.body().getUserDetails().getProfileImage());
                        String path = response.body().getUserDetails().getProfileImage();

                        Picasso.get()
                                .load(WebService.BASE_URL + "media/" + path)
                                .error(R.drawable.placeholder)
                                .placeholder(R.drawable.placeholder)
                                .into( binding.imgProfile);

                        Picasso.get()
                                .load(WebService.BASE_URL + "media/" + path)
                                .error(R.drawable.placeholder)
                                .placeholder(R.drawable.placeholder)
                                .into(binding.llProfile);

                        Dialcode = response.body().getUserDetails().getDialCode();
                        binding.nameET.setText(response.body().getUserDetails().getName());
                        binding.txtCountry.setText(response.body().getUserDetails().getCountry());
                        binding.UserPhoneET.setText(response.body().getUserDetails().getMobile());
                        binding.emailET.setText(response.body().getUserDetails().getEmail());
                        binding.buyerStarTxt.setText(response.body().getUserDetails().getRatingAsB());
                        binding.travelStarTxt.setText(response.body().getUserDetails().getRatingAsT());
                        binding.ratingbar.setRating(Float.parseFloat(response.body().getUserDetails().getRatingAsB()));
                        binding.ratingbar1.setRating(Float.parseFloat(response.body().getUserDetails().getRatingAsT()));

                            binding.countrynameTxt.setText(response.body().getBankCountry());
                            binding.bankET.setText(response.body().getBankName());
                            binding.idET.setText(response.body().getBankId());
                            binding.accountnoET.setText(response.body().getBankAccountNumber());
                            binding.accountTypeEt.setText(response.body().getBankAccountType());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }



    private void onClickListener() {

        binding.txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        binding.imgBack.setOnClickListener(v -> {
            getFragmentManager().popBackStackImmediate();
        });

        binding.seeRatingButton.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                    R.id.frameLayoout,new RatingFragment());
        });

        binding.walletLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new WalletFragment());
            }
        });

        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((ActivityCompat.checkSelfPermission((getActivity()),
                        Manifest.permission.CAMERA)) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                } else {
                    if ((ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
                    } else {
                        CropImage.activity().start(getContext(),ProfileFragment.this);
                    }
                }

            }
        });


        binding.txtCountry.setOnClickListener(view1 -> {
            countryPicker = new CountryPicker.Builder(getContext())
                    .showingFlag(true)
                    .enablingSearch(true)
                    .sortBy(CountryPicker.Sort.COUNTRY)
                    .setViewType(CountryPicker.ViewType.BOTTOMSHEET)
                    .setCountrySelectionListener(country ->
                            setCountryname(country.getName(),country.getDialCode())).build();

            countryPicker.show((AppCompatActivity) getContext());
        });

        /*binding.countrynameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        binding.personalInfoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personalflag==0){
                    binding.forwardImg.setRotation(90);
                    binding.personalLL.setVisibility(View.VISIBLE);
                    personalflag =1;
                }else{
                    binding.forwardImg.setRotation(0);
                    binding.personalLL.setVisibility(View.GONE);
                    personalflag =0;
                }
            }
        });

        binding.paymentInfoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paymentFlag==0){
                    binding.forwardImg1.setRotation(90);
                    binding.paymentLL.setVisibility(View.VISIBLE);
                    paymentFlag=1;
                }else{
                    binding.forwardImg1.setRotation(0);
                    binding.paymentLL.setVisibility(View.GONE);
                    paymentFlag=0;
                }
            }
        });

        binding.personalRanLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(personalRanFlag==0){
                    binding.forwardImg2.setRotation(90);
                    binding.personalRLL.setVisibility(View.VISIBLE);
                    personalRanFlag =1;
                }else{
                    binding.forwardImg2.setRotation(0);
                    binding.personalRLL.setVisibility(View.GONE);
                    personalRanFlag =0;
                }
            }
        });

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileData();
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Country",binding.txtCountry.getText().toString());
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),bundle,
                        R.id.frameLayoout,new BankDetailsFragment());

            }
        });
    }



    private void setCountryname(String name, String dialCode) {
        binding.txtCountry.setText(name);
        Dialcode = dialCode;
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start((Activity) getContext());

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            if (requestCode == MY_READ_EXTERNAL_STORAGE_REQUEST_CODE) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(this, "Storage permission granted", Toast.LENGTH_LONG).show();
//                    CropImage.startPickImageActivity(this);

                    startCropImageActivity(mCropImageUri);


                } else {

//                    Toast.makeText(this, "Storage permission denied", Toast.LENGTH_LONG).show();

                }

            }

            if (requestCode == MY_CAMERA_REQUEST_CODE) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                    if ((ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_STORAGE_REQUEST_CODE);
                    } else {

                        startCropImageActivity(mCropImageUri);
                    }

                } else {
//                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
                }
            }

            if (requestCode == MY_STORAGE_REQUEST_CODE) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
//                    CropImage.startPickImageActivity(this);
                    startCropImageActivity(mCropImageUri);

                } else {

//                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();

                }

            }
        }

    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser


        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getContext(), data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageUri)) {
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
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), result.getUri());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 35, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();

                    binding.imgProfile.setImageURI(result.getUri());
                    binding.llProfile.setImageURI(result.getUri());
                    encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);



                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void saveProfileData() {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        SaveProfileRequest saveProfileRequest = new SaveProfileRequest();
        saveProfileRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERID,""));
        saveProfileRequest.setName(binding.nameET.getText().toString());
        saveProfileRequest.setMobile(binding.UserPhoneET.getText().toString());
        saveProfileRequest.setCountry(binding.txtCountry.getText().toString());
        saveProfileRequest.setEmail(binding.emailET.getText().toString());
        saveProfileRequest.setDialCode(Dialcode);
        saveProfileRequest.setRoutingNumber("");
        saveProfileRequest.setSortCode("");
        saveProfileRequest.setPassword("");
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
        saveProfileRequest.setImage(encodedImage);

        Call<SaveProfileResponse> saveProfileResponseCall = ApiClient.getService().saveprofile(saveProfileRequest);
        saveProfileResponseCall.enqueue(new Callback<SaveProfileResponse>() {
            @Override
            public void onResponse(Call<SaveProfileResponse> call, Response<SaveProfileResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){
                        binding.forwardImg.setRotation(0);
                        binding.personalLL.setVisibility(View.GONE);
                        personalflag =0;
                        binding.forwardImg1.setRotation(0);
                        binding.paymentLL.setVisibility(View.GONE);
                        paymentFlag=0;
                        binding.forwardImg2.setRotation(0);
                        binding.personalRLL.setVisibility(View.GONE);
                        personalRanFlag =0;
                        getProfileData();
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                getString(R.string.profileupdate), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }else{
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveProfileResponse> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }
}
