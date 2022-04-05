package com.dev.todos.Fragment.More;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.More.RatingFragment;
import com.dev.todos.Model.GetProfileData.GetProfileRequest;
import com.dev.todos.Model.GetProfileData.GetProfileResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Url.WebService;
import com.dev.todos.databinding.FragmentTravellerProfileBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TravellerProfileFragment extends Fragment {


    FragmentTravellerProfileBinding binding;
    KProgressHUD hud;
    Snackbar snackBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_traveller_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String travellerId = bundle.getString("travellerId");
        getProfileData(travellerId);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                        new RatingFragment());
            }
        });


    }

    private void getProfileData(String travellerId) {
        hud=  KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        GetProfileRequest getProfileRequest = new GetProfileRequest();
        getProfileRequest.setUserId(travellerId);

        Call<GetProfileResponse> getProfileResponseCall = ApiClient.getService().profile(getProfileRequest);
        getProfileResponseCall.enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){

                     //   Sharedpreferences.saveToPreferences(getActivity(), AppConstant.PROFILEIMAGE, response.body().getUserDetails().getProfileImage());
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


                        binding.nameET.setText(response.body().getUserDetails().getName());
                        binding.txtCountry.setText(response.body().getUserDetails().getCountry());
                        binding.emailET.setText(response.body().getUserDetails().getEmail());

                        binding.ratingbar.setRating(Float.parseFloat(response.body().getUserDetails().getRating()));
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
}