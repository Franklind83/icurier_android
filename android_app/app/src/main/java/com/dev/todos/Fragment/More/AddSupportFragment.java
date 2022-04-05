package com.dev.todos.Fragment.More;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Model.AddSupport.AddSupportRequest;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.databinding.FragmentAddSupportBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddSupportFragment extends Fragment {

    FragmentAddSupportBinding binding;
    private KProgressHUD hud;
    private Snackbar snackBar;
    List<String> country = new ArrayList<>();
    String item ="";
    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_support, container, false);
       // binding = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_add_support);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });


        country.add(getString(R.string.Aboutbuyertraveller));
        country.add(getString(R.string.Aboutpayment));
        country.add(getString(R.string.Regarding));
        country.add(getString(R.string.Comments));
        country.add(getString(R.string.functionality));
        country.add(getString(R.string.Others));


        binding.spinner.setItems(country);

       binding.spinner.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
           item = newItem;
           //binding.spinner.setTextColor(Color.WHITE);
          // Toast.makeText(getContext(), newItem, Toast.LENGTH_SHORT).show();
       });

       binding.btnSend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(binding.edtsubject.getText().toString().isEmpty()){
                   binding.edtsubject.setError(getString(R.string.Entersubject));
               }else if(binding.descEdt.getText().toString().isEmpty()){
                   binding.descEdt.setError(getString(R.string.Enterdescription));
               }else if (item.isEmpty()){
                   Toast.makeText(getActivity(), getString(R.string.Enterreason), Toast.LENGTH_SHORT).show();
               }else{
                   getApiCall();
               }

           }
       });
    }

    private void getApiCall() {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        SimpleDateFormat df1 = new SimpleDateFormat("hh:mm a");
        String formattedTime = df1.format(c.getTime());


        AddSupportRequest addSupportRequest = new AddSupportRequest();
        addSupportRequest.setSubject(binding.edtsubject.getText().toString());
        addSupportRequest.setDescription(binding.descEdt.getText().toString());
        addSupportRequest.setReason(item);
        addSupportRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));
        addSupportRequest.setDate(formattedDate);
        addSupportRequest.setTime(formattedTime);

        Call<Responce> responceCall = ApiClient.getService().addSupport(addSupportRequest);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    if(response.body().getStatus().equals("1")){
                        setalertDialog(response.body().getMsg());

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

    public void setalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                getFragmentManager().popBackStackImmediate();

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}