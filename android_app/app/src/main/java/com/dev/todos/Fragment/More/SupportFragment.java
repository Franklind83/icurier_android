package com.dev.todos.Fragment.More;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Adapter.SupportAdapter;
import com.dev.todos.Adapter.TravellerNotLoginAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.GetSupport.SupportListItem;
import com.dev.todos.Model.GetSupport.SupportRequest;
import com.dev.todos.Model.GetSupport.SupportResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.databinding.FragmentSupportBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupportFragment extends Fragment {

    FragmentSupportBinding binding;
    private KProgressHUD hud;
    private Snackbar snackBar;
    List<SupportListItem> supportListItems = new ArrayList<>();
    SupportAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_support, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setOnclickListener();

    }

    @Override
    public void onStart() {
        getApiCall();
        super.onStart();
    }

    private void getApiCall() {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        SupportRequest supportRequest = new SupportRequest();
        supportRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));

        Call<SupportResponse> supportResponseCall = ApiClient.getService().support(supportRequest);
        supportResponseCall.enqueue(new Callback<SupportResponse>() {
            @Override
            public void onResponse(Call<SupportResponse> call, Response<SupportResponse> response) {
                hud.dismiss();
                if(response.body().getStatus().equals("1")){
                    supportListItems = response.body().getSupportList();
                    if(supportListItems.size() == 0){
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                    }else {
                        binding.txtNoOrder.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        setDataByView(supportListItems);
                    }
                }else{
                    binding.txtNoOrder.setVisibility(View.VISIBLE);
                    binding.txtNoOrder.setText(response.body().getMsg());
                   /* snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                    snackBar.show();*/

                }
            }

            @Override
            public void onFailure(Call<SupportResponse> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }

    private void setDataByView(List<SupportListItem> supportListItems) {

        Collections.reverse(supportListItems);

        if(adapter == null){
            adapter = new SupportAdapter(getActivity(),getFragmentManager());
            binding.recyclerView.setAdapter(adapter);
            adapter.setUplist(supportListItems);
        }else{
            binding.recyclerView.setAdapter(adapter);
            adapter.setUplist(supportListItems);
        }
    }

    private void setOnclickListener() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new AddSupportFragment());
            }
        });
    }
}