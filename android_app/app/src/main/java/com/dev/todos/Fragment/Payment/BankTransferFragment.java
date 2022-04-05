package com.dev.todos.Fragment.Payment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment;
import com.dev.todos.Model.GetBankInfo.BankListItem;
import com.dev.todos.Model.GetBankInfo.GetBankInfoResponse;
import com.dev.todos.Model.PaymentBanktransfer.BankTransferRequest;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentBankDetailsBinding;
import com.dev.todos.databinding.FragmentBankTransferBinding;
import com.google.android.gms.common.api.Api;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.google.gson.internal.$Gson$Preconditions;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankTransferFragment extends Fragment {

    FragmentBankTransferBinding binding;
    List<BankListItem> bankList;
    String item ="";
    KProgressHUD hud;
    AlertDialog alertDialog;
    String image1 ="";
    Snackbar snackBar;
    String Activity="";
    String Amt="";
    String Orderid="",Offer_OrderId="";
    List<String> BankName = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_bank_transfer, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle  = getArguments();
        Activity = bundle.getString("Activity");
        Amt = bundle.getString("Total");
        Orderid = bundle.getString(Keys.order_id);
        Offer_OrderId = bundle.getString(Keys.offer_order_id);
        binding.txtTotal.setText("$ "+Amt);



        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.spinnerMain.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            item = newItem;
            binding.spinnerMain.setTextColor(Color.WHITE);
            setDataOfSpinner(item);
            // Toast.makeText(getContext(), newItem, Toast.LENGTH_SHORT).show();
        });

        binding.imageSelectLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage("1");
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.originBankExt.getText().toString().isEmpty()){
                    binding.originBankExt.setError("Enter origin bank");
                }else if (binding.tranNoExt.getText().toString().isEmpty()){
                    binding.tranNoExt.setError("Enter transaction number");
                }else if(image1.isEmpty()){
                    setalertDialog("Please select Image","F");
                }else{
                    SendApiCall();
                }
            }
        });

        getApiCall();
    }

    private void SendApiCall() {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        BankTransferRequest bankTransferRequest = new BankTransferRequest();
        bankTransferRequest.setAmount(Amt);
        bankTransferRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));
        bankTransferRequest.setOriginBank(binding.originBankExt.getText().toString());
        bankTransferRequest.setTransactionNo(binding.tranNoExt.getText().toString());
        bankTransferRequest.setTransactionImage(image1);
        bankTransferRequest.setOfferOrderId(Offer_OrderId);
        bankTransferRequest.setOrderId(Orderid);

        Call<Responce> responceCall = ApiClient.getService().banktransfer(bankTransferRequest);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        setalertDialog("Your order post successfully. We will send you confirmation notification. Thank you.","S");
                       /* JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(Keys.offer_order_id, Offer_OrderId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        acceptOrder(UseMe.getJsonObject(jsonObject));*/
                    }else{
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });

    }


    private void setDataOfSpinner(String item) {
        for(int i=0;i<bankList.size();i++){
            if(bankList.get(i).getBankName().equals(item)){
                binding.bankTxt.setText(bankList.get(i).getBankName());
                binding.ownerTxt.setText(bankList.get(i).getOwnerName());
                binding.accTxt.setText(bankList.get(i).getAccountNo());
                binding.accTyeTxt.setText(bankList.get(i).getAccountType());
                binding.idTxt.setText(bankList.get(i).getIdNo());
                binding.EmailIdTxt.setText(bankList.get(i).getEmail());
            }
        }
    }

    private void getApiCall() {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        Call<GetBankInfoResponse> getBankInfoResponseCall = ApiClient.getService().getbankInfo();
        getBankInfoResponseCall.enqueue(new Callback<GetBankInfoResponse>() {
            @Override
            public void onResponse(Call<GetBankInfoResponse> call, Response<GetBankInfoResponse> response) {
                hud.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        bankList = response.body().getBankList();
                        setDataByView();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetBankInfoResponse> call, Throwable t) {
                hud.dismiss();
            }
        });
    }

    private void setDataByView() {
        for(int i=0;i<bankList.size();i++){
            if(!bankList.get(i).getBankName().equals("")){
            BankName.add(bankList.get(i).getBankName());
            }
        }
        binding.spinnerMain.setItems(BankName);
        binding.spinnerMain.selectItemByIndex(0);

        binding.bankTxt.setText(bankList.get(0).getBankName());
        binding.ownerTxt.setText(bankList.get(0).getOwnerName());
        binding.accTxt.setText(bankList.get(0).getAccountNo());
        binding.accTyeTxt.setText(bankList.get(0).getAccountType());
        binding.idTxt.setText(bankList.get(0).getIdNo());
        binding.EmailIdTxt.setText(bankList.get(0).getEmail());

    }

    public void setalertDialog(String mes, String f){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f.equals("F")){
                    alertDialog.dismiss();
                }else{
                    alertDialog.dismiss();
                    try {
                        UseMe.clearAllFragment(getFragmentManager());
                        if (Activity.equals("FromMyOrder")) {
                            new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                                    null, R.id.frameLayoout, new MyOrderFragment());
                        } else {
                            new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                                    null, R.id.frameLayoout, new TravellerFragment());
                        }
                    }catch (Exception e){}
                }

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }


    private String encodeImage(String path) {
        String encImage = "";

        try {
            File imagefile = new File(path);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap compressedImageBitmap = new Compressor(getActivity()).compressToBitmap(imagefile);
            /* Bitmap bm = BitmapFactory.decodeStream(fis);*/
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            encImage = "";
        }
        //Base64.de
        return encImage;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {

                Uri resultUri = result.getUri();
                binding.imgSelectProduct1.setVisibility(View.GONE);
                binding.imgProduct1.setVisibility(View.VISIBLE);
                    binding.imgProduct1.setImageURI(resultUri);
                    image1 = encodeImage(resultUri.getPath());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void selectImage(String s_) {
        CropImage.activity(null)
                .start(getContext(), BankTransferFragment.this);
    }

    public void acceptOrder(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<Responce> responceCall = ApiClient.getService().acceptOrder(jsonObject);


        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();


                String result = response.body().getMsg();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    setalertDialog("Your order post successfully. We will send you confirmation notification. Thank you.","S");

                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hud.dismiss();

            }
        });
    }
}