package com.dev.todos.Fragment.More;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.todos.Adapter.CountryAdapter;
import com.dev.todos.Model.GetProfileData.GetProfileRequest;
import com.dev.todos.Model.GetProfileData.GetProfileResponse;
import com.dev.todos.Model.SaveProfileData.SaveProfileRequest;
import com.dev.todos.Model.SaveProfileData.SaveProfileResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.databinding.FragmentBankDetailsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailsFragment extends Fragment {

    FragmentBankDetailsBinding binding;
    List<String> countries = new ArrayList<>();
    CountryAdapter countryAdapter;
    int number =0;
    String item ="";
    KProgressHUD hud;
    Snackbar snackBar;
    List<String> type = new ArrayList<>();

    String Name ="";
    String Mobile="";
    String Country="";
    String Email = "";
    String BankId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        type.add(getString(R.string.Saving));
        type.add(getString(R.string.Checking));

        binding.spinner.setItems(type);


        bankList();
        onClickListerner();
        getProfileData();



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
        getProfileRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));

        Call<GetProfileResponse> getProfileResponseCall = ApiClient.getService().profile(getProfileRequest);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){

                        Name = response.body().getUserDetails().getName();
                        Country = response.body().getUserDetails().getCountry();
                        Mobile = response.body().getUserDetails().getMobile();
                        Email = response.body().getUserDetails().getEmail();
                        BankId= response.body().getBankId();

                        if(response.body().getBankCountry().isEmpty()){
                            binding.countryNameTxt.setText(response.body().getUserDetails().getCountry());
                            checkCountry(response.body().getUserDetails().getCountry());
                        }else{
                            binding.countryNameTxt.setText(response.body().getBankCountry());


                            if(binding.countryNameTxt.getText().toString().equals("United States")){

                                if(!response.body().getRoutingNumber().isEmpty()) {
                                    binding.routingNoExt.setText(response.body().getRoutingNumber());
                                }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }
                            }else if(binding.countryNameTxt.getText().toString().equals("United Kingdom")){

                                if(!response.body().getSortCode().isEmpty()) {
                                    binding.routingNoExt.setText(response.body().getSortCode());
                                }


                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Singapore") || binding.countryNameTxt.getText().equals("Brazil")){

                                if(!response.body().getBankCode().isEmpty()) {
                                    binding.bankCodeExt.setText(response.body().getBankCode());
                                }
                                if(!response.body().getBranchCode().isEmpty()) {
                                    binding.branchCodeExt.setText(response.body().getBranchCode());
                                }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("New Zealand") || binding.countryNameTxt.getText().equals("Malaysia")){

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Mexico")){

                                if(!response.body().getClabe().isEmpty()) {
                                    binding.clabeExt.setText(response.body().getClabe());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Japan")){

                                if(!response.body().getBankName().isEmpty()) {
                                    binding.bankExt.setText(response.body().getBankName());
                                }

                                if(!response.body().getBranchName().isEmpty()) {
                                    binding.branchExt.setText(response.body().getBranchName());
                                }

                                if(!response.body().getBankCode().isEmpty()) {
                                    binding.bankCodeExt.setText(response.body().getBankCode());
                                }
                                if(!response.body().getBranchCode().isEmpty()) {
                                    binding.branchCodeExt.setText(response.body().getBranchCode());
                                }

                                if(!response.body().getOwnerName().isEmpty()) {
                                    binding.accountOwnerExt.setText(response.body().getOwnerName());
                                }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("India")){


                                if(!response.body().getIfsc().isEmpty()) {
                                    binding.ifscExt.setText(response.body().getIfsc());
                                }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Hong Kong")){

                                if(!response.body().getClearingCode().isEmpty()) {
                                    binding.clearingCodeExt.setText(response.body().getClearingCode());
                                }

                                if(!response.body().getBranchCode().isEmpty()) {
                                    binding.branchCodeExt.setText(response.body().getBranchCode());
                                }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Australia")){

                                if(!response.body().getBsb().isEmpty()) {
                                    binding.bsbExt.setText(response.body().getBsb()); }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Canada")){
                                if(!response.body().getTransitNumber().isEmpty()) {
                                    binding.transitExt.setText(response.body().getTransitNumber());
                                }
                                if(!response.body().getInstitution().isEmpty()) {
                                    binding.instituteExt.setText(response.body().getInstitution());
                                }

                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else if(binding.countryNameTxt.getText().toString().equals("Ecuador")){
                                
                                if(!response.body().getPassportNumber().isEmpty()) {
                                    binding.pasportExt.setText(response.body().getPassportNumber());
                                }

                                if(!response.body().getOwnerName().isEmpty()) {
                                    binding.accountOwnerExt.setText(response.body().getOwnerName());
                                }

                                if(!response.body().getBankName().isEmpty()) {
                                    binding.bankExt.setText(response.body().getBankName());
                                }


                                if(!response.body().getBankAccountNumber().isEmpty()) {
                                    binding.accountnoExt.setText(response.body().getBankAccountNumber());
                                }

                            }else {
                                if(!response.body().getIban().isEmpty())
                                {
                                    binding.ibanExt.setText(response.body().getIban());
                                }
                            }
                            checkCountry(response.body().getBankCountry());
                        }
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

    private void checkCountry(String countryName) {

        if(countryName.equals("United States")){
            binding.routingNoTxt.setVisibility(View.VISIBLE);
            binding.routingNoExt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number =1;
        }else if(countryName.equals("United Kingdom")){
            binding.sortTxt.setVisibility(View.VISIBLE);
            binding.sortExt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 2;
        }else if(countryName.equals("Singapore") || countryName.equals("Brazil")){
            binding.bankCodeExt.setVisibility(View.VISIBLE);
            binding.bankCodeTxt.setVisibility(View.VISIBLE);
            binding.branchCodeExt.setVisibility(View.VISIBLE);
            binding.branchCodeTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 3;
        }else if(countryName.equals("New Zealand") || countryName.equals("Malaysia")){
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 4;
        }else if(countryName.equals("Mexico")){
            binding.clabeTxt.setVisibility(View.VISIBLE);
            binding.clabeExt.setVisibility(View.VISIBLE);
            number = 5;
        }else if(countryName.equals("Japan")){
            binding.bankExt.setVisibility(View.VISIBLE);
            binding.bankTxt .setVisibility(View.VISIBLE);
            binding.branchExt.setVisibility(View.VISIBLE);
            binding.branchTxt.setVisibility(View.VISIBLE);
            binding.bankCodeExt.setVisibility(View.VISIBLE);
            binding.bankCodeTxt.setVisibility(View.VISIBLE);
            binding.branchCodeExt.setVisibility(View.VISIBLE);
            binding.branchCodeTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            binding.accountOwnerExt.setVisibility(View.VISIBLE);
            binding.accountOwnerTxt.setVisibility(View.VISIBLE);
            number = 6;
        }else if(countryName.equals("India")){
            binding.ifscExt.setVisibility(View.VISIBLE);
            binding.ifscTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 7;
        }else if(countryName.equals("Hong Kong")){
            binding.clearingCodeExt.setVisibility(View.VISIBLE);
            binding.clearingCodeTxt.setVisibility(View.VISIBLE);
            binding.branchCodeExt.setVisibility(View.VISIBLE);
            binding.branchCodeTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 8;
        }else if(countryName.equals("Australia")){
            binding.bsbExt.setVisibility(View.VISIBLE);
            binding.bsbTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 9;
        }else if(countryName.equals("Canada")){
            binding.transitExt.setVisibility(View.VISIBLE);
            binding.transitTxt.setVisibility(View.VISIBLE);
            binding.instituteExt.setVisibility(View.VISIBLE);
            binding.instituteTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            number = 10;
        }else if(countryName.equals("Ecuador")){
            binding.pasportExt.setVisibility(View.VISIBLE);
            binding.pasportTxt.setVisibility(View.VISIBLE);
            binding.bankExt.setVisibility(View.VISIBLE);
            binding.bankTxt.setVisibility(View.VISIBLE);
            binding.accountnoExt.setVisibility(View.VISIBLE);
            binding.accountnoTxt.setVisibility(View.VISIBLE);
            binding.accountOwnerTxt.setVisibility(View.VISIBLE);
            binding.accountOwnerExt.setVisibility(View.VISIBLE);
            binding.accountTypeLL.setVisibility(View.VISIBLE);
            number = 12;
        }else {
            binding.ibanExt.setVisibility(View.VISIBLE);
            binding.ibanTxt.setVisibility(View.VISIBLE);
            number = 11;
        }
    }

    private void onClickListerner() {
        binding.imgBack.setOnClickListener(v -> {
            getFragmentManager().popBackStackImmediate();
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number == 1){
                    if(binding.routingNoExt.getText().toString().isEmpty()){
                        binding.routingNoExt.setError("Please Enter Routing Number");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 2){
                    if(binding.sortExt.getText().toString().isEmpty()){
                        binding.sortExt.setError("Please Enter Sort Number");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 3){
                    if(binding.bankCodeExt.getText().toString().isEmpty()){
                        binding.bankCodeExt.setError("Please Enter Bank Code");
                    }else if(binding.branchCodeExt.getText().toString().isEmpty()){
                        binding.branchCodeExt.setError("Please Enter Branch Code");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 4){
                     if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 5){
                    if(binding.clabeExt.getText().toString().isEmpty()){
                        binding.clabeExt.setError("Please Enter Clabe Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 6){
                    if(binding.bankExt.getText().toString().isEmpty()){
                        binding.bankExt.setError("Please Enter Bank Name");
                    } else if(binding.branchExt.getText().toString().isEmpty()){
                        binding.branchExt.setError("Please Enter Branch Name");
                    } else if(binding.bankCodeExt.getText().toString().isEmpty()){
                        binding.bankCodeExt.setError("Please Enter Bank Code");
                    }else if(binding.branchCodeExt.getText().toString().isEmpty()){
                        binding.branchCodeExt.setError("Please Enter Branch Code");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else if(binding.accountOwnerExt.getText().toString().isEmpty()){
                        binding.accountOwnerExt.setError("Please Enter Account Owner Name");
                    }else{
                        sendToServer();
                    }
                }else if(number == 7){
                    if(binding.ifscExt.getText().toString().isEmpty()){
                        binding.ifscExt.setError("Please Enter IFSC Code");
                    } else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 8){
                    if(binding.clearingCodeExt.getText().toString().isEmpty()){
                        binding.clearingCodeExt.setError("Please Enter Clearing Code");
                    }else if(binding.branchCodeExt.getText().toString().isEmpty()){
                        binding.branchCodeExt.setError("Please Enter Branch Code");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 9){
                    if(binding.bsbExt.getText().toString().isEmpty()){
                        binding.bsbExt.setError("Please Enter BSB Code");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 10){
                    if(binding.transitExt.getText().toString().isEmpty()){
                        binding.transitExt.setError("Please Enter Transit Number");
                    }else if(binding.instituteExt.getText().toString().isEmpty()){
                        binding.instituteExt.setError("Please Enter Institution Number");
                    }else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else{
                        sendToServer();
                    }
                }else if(number == 11){
                    if(binding.ibanExt.getText().toString().isEmpty()){
                        binding.ibanExt.setError("Please Enter IBAN Number");
                    }else{
                        sendToServer();
                    }
                }else{
                    if(binding.accountOwnerExt.getText().toString().isEmpty()){
                        binding.accountOwnerExt.setError("Please Enter Owner Name");
                    }else if(binding.pasportExt.getText().toString().isEmpty()){
                        binding.pasportExt.setError("Please Enter ID/ Passport Number");
                    }else if(binding.bankExt.getText().toString().isEmpty()){
                        binding.bankExt.setError("Please Enter Bank Name");
                    } else if(binding.accountnoExt.getText().toString().isEmpty()){
                        binding.accountnoExt.setError("Please Enter Account Number");
                    }else if( binding.spinner.getText().toString().isEmpty()){
                        binding.spinner.setError("Please Enter Account Type");
                    }else{
                        sendToServer();
                    }
                }
            }
        });

        binding.spinner.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            item = newItem;
            binding.spinner.setTextColor(Color.BLACK);
            // Toast.makeText(getContext(), newItem, Toast.LENGTH_SHORT).show();
        });

        binding.countryNameRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryDialog();
            }
        });
    }

    private void sendToServer() {
        hud= KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        SaveProfileRequest saveProfileRequest = new SaveProfileRequest();
        saveProfileRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERID,""));
        saveProfileRequest.setName(Name);
        saveProfileRequest.setMobile(Mobile);
        saveProfileRequest.setCountry(Country);
        saveProfileRequest.setEmail(Email);
        saveProfileRequest.setDialCode("");
        saveProfileRequest.setPassword("");
        saveProfileRequest.setRoutingNumber(binding.routingNoExt.getText().toString().trim());
        saveProfileRequest.setSortCode(binding.sortExt.getText().toString().trim());
        saveProfileRequest.setIban(binding.ibanExt.getText().toString().trim());
        saveProfileRequest.setBankCode(binding.bankCodeExt.getText().toString().trim());
        saveProfileRequest.setBranchCode(binding.branchCodeExt.getText().toString().trim());
        saveProfileRequest.setClabe(binding.clabeExt.getText().toString().trim());
        saveProfileRequest.setBranchName(binding.branchExt.getText().toString().trim());
        saveProfileRequest.setOwnerName(binding.accountOwnerExt.getText().toString().trim());
        saveProfileRequest.setIfsc(binding.ifscExt.getText().toString().trim());
        saveProfileRequest.setClearingCode(binding.clearingCodeExt.getText().toString().trim());
        saveProfileRequest.setBsb(binding.bsbExt.getText().toString().trim());
        saveProfileRequest.setInstitution(binding.instituteExt.getText().toString().trim());
        saveProfileRequest.setPassportNumber(binding.pasportExt.getText().toString().trim());
        saveProfileRequest.setTransitNumber(binding.transitExt.getText().toString().trim());
        saveProfileRequest.setBankCountry(binding.countryNameTxt.getText().toString().trim());
        saveProfileRequest.setBankName(binding.bankExt.getText().toString().trim());
        saveProfileRequest.setBankId(BankId);
        saveProfileRequest.setPassword("");
        saveProfileRequest.setBankAccountNumber(binding.accountnoExt.getText().toString().trim());
        saveProfileRequest.setBankAccountType(item);
        saveProfileRequest.setImage("");

        Call<SaveProfileResponse> saveProfileResponseCall = ApiClient.getService().saveprofile(saveProfileRequest);
        saveProfileResponseCall.enqueue(new Callback<SaveProfileResponse>() {
            @Override
            public void onResponse(Call<SaveProfileResponse> call, Response<SaveProfileResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                getString(R.string.profileupdate), Snackbar.LENGTH_LONG);
                        snackBar.show();

                        getFragmentManager().popBackStackImmediate();
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

    private void bankList() {
        countries.add("Australia");
        countries.add("Austria");
        countries.add("Belgium");
        countries.add("Brazil");
        countries.add("Bulgaria");
        countries.add("Canada");
        countries.add("Cyprus");
        countries.add("Czech Republic");
        countries.add("Czechia");
        countries.add("Denmark");
        countries.add("Ecuador");
        countries.add("Estonia");
        countries.add("Finland");
        countries.add("France");
        countries.add("Greece");
        countries.add("Germany");
        countries.add("Hong Kong");
        countries.add("India");
        countries.add("Ireland");
        countries.add("Italy");
        countries.add("Japan");
        countries.add("Latvia");
        countries.add("Lithuania");
        countries.add("Luxemburgo");
        countries.add("Malaysia");
        countries.add("Malta");
        countries.add("Mexico");
        countries.add("Norway");
        countries.add("New Zealand");
        countries.add("Netherlands");
        countries.add("Poland");
        countries.add("Portugal");
        countries.add("Romania");
        countries.add("Singapore");
        countries.add("Sweden");
        countries.add("Switzerland");
        countries.add("Slovakia");
        countries.add("Slovenia");
        countries.add("Spain");
        countries.add("United States");
        countries.add("United Kingdom");
     }

    private void CountryDialog() {
        View view = getLayoutInflater().inflate(R.layout.dailog_country, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
        ImageView backIV = view.findViewById(R.id.backIV);
        RecyclerView recyclerViewDialog = view.findViewById(R.id.recyclerViewDialog);

        recyclerViewDialog.setHasFixedSize(true);
        recyclerViewDialog.setLayoutManager(new LinearLayoutManager(getActivity()));


        if(countryAdapter == null){
            countryAdapter = new CountryAdapter(getContext());
            recyclerViewDialog.setAdapter(countryAdapter);
            countryAdapter.setUpList(countries);
        }else{
            countryAdapter = new CountryAdapter(getContext());
            recyclerViewDialog.setAdapter(countryAdapter);
            countryAdapter.setUpList(countries);

        }

        countryAdapter.setOnClickListener(new CountryAdapter.OnClickListener() {
            @Override
            public void onClick(String countries) {
                dialog.dismiss();
                binding.countryNameTxt.setText(countries);
                clearCountry();
                checkCountry(binding.countryNameTxt.getText().toString());
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void clearCountry() {
        binding.routingNoTxt.setVisibility(View.GONE);
        binding.routingNoExt.setVisibility(View.GONE);
        binding.accountnoExt.setVisibility(View.GONE);
        binding.accountnoTxt.setVisibility(View.GONE);
        binding.sortTxt.setVisibility(View.GONE);
        binding.sortExt.setVisibility(View.GONE);
        binding.bankCodeExt.setVisibility(View.GONE);
        binding.bankCodeTxt.setVisibility(View.GONE);
        binding.branchCodeExt.setVisibility(View.GONE);
        binding.branchCodeTxt.setVisibility(View.GONE);
        binding.clabeTxt.setVisibility(View.GONE);
        binding.clabeExt.setVisibility(View.GONE);
        binding.ibanExt.setVisibility(View.GONE);
        binding.ibanTxt.setVisibility(View.GONE);
        binding.pasportExt.setVisibility(View.GONE);
        binding.pasportTxt.setVisibility(View.GONE);
        binding.bankExt.setVisibility(View.GONE);
        binding.bankTxt.setVisibility(View.GONE);
        binding.accountOwnerTxt.setVisibility(View.GONE);
        binding.accountOwnerExt.setVisibility(View.GONE);
        binding.accountTypeLL.setVisibility(View.GONE);
        binding.bsbExt.setVisibility(View.GONE);
        binding.bsbTxt.setVisibility(View.GONE);
        binding.transitExt.setVisibility(View.GONE);
        binding.transitTxt.setVisibility(View.GONE);
        binding.instituteExt.setVisibility(View.GONE);
        binding.instituteTxt.setVisibility(View.GONE);
        binding.clearingCodeExt.setVisibility(View.GONE);
        binding.clearingCodeTxt.setVisibility(View.GONE);
        binding.ifscExt.setVisibility(View.GONE);
        binding.ifscTxt.setVisibility(View.GONE);
        binding.bankExt.setVisibility(View.GONE);
        binding.bankTxt .setVisibility(View.GONE);
        binding.branchExt.setVisibility(View.GONE);
        binding.branchTxt.setVisibility(View.GONE);


    }
}