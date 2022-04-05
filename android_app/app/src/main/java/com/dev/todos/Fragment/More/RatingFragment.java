package com.dev.todos.Fragment.More;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Adapter.RatingAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.More.ProfileFragment;
import com.dev.todos.Model.GetRating.GetRatingRequest;
import com.dev.todos.Model.GetRating.GetRatingResponse;
import com.dev.todos.Model.GetRating.RatingListItem;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.databinding.FragmentRatingBinding;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RatingFragment extends Fragment {

    FragmentRatingBinding binding;
    String Ratingtype="all";
    KProgressHUD hud;
    Snackbar snackBar;
    RatingAdapter ratingAdapter;
    List<RatingListItem> ratingListItems = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

          BaseActivity.setProfile(getContext(), Sharedpreferences.readFromPreferences(getActivity(), AppConstant.PROFILEIMAGE, ""), binding.imgProfile);
          BaseActivity.setProfile(getContext(), Sharedpreferences.readFromPreferences(getActivity(), AppConstant.PROFILEIMAGE, ""), binding.llProfile);

        initView();
        getRatingList(Ratingtype);
        onclickListener();
    }

    private void getRatingList(String ratingtype) {
        hud=  KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        GetRatingRequest getRatingRequest = new GetRatingRequest();
        getRatingRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERID,""));
        getRatingRequest.setRatingFor(ratingtype);

        Call<GetRatingResponse> getRatingResponseCall = ApiClient.getService().rating(getRatingRequest);
        getRatingResponseCall.enqueue(new Callback<GetRatingResponse>() {
            @Override
            public void onResponse(Call<GetRatingResponse> call, Response<GetRatingResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    assert response.body() != null;
                    if(response.body().getStatus().equals("1")){
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        binding.sorryTxt.setVisibility(View.GONE);
                        ratingListItems.clear();
                        ratingListItems = response.body().getRatingList();
                        setDataByView(ratingListItems);
                    }else{
                       binding.recyclerView.setVisibility(View.GONE);
                        binding.sorryTxt.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRatingResponse> call, Throwable t) {
                hud.dismiss();
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });

    }

    private void setDataByView(List<RatingListItem> ratingListItems) {
        if(ratingAdapter == null){
            ratingAdapter = new RatingAdapter(getActivity());
            binding.recyclerView.setAdapter(ratingAdapter);
            ratingAdapter.setUpList(ratingListItems);
        }else{
            ratingAdapter.setUpList(ratingListItems);
        }
    }

    private void initView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void onclickListener() {

        binding.imgBack.setOnClickListener(view -> {
           getFragmentManager().popBackStackImmediate();
        });

        binding.everyoneLL.setOnClickListener(view -> {
            getRatingList("all");
            binding.eveyoneViewLL.setVisibility(View.VISIBLE);
        binding.buyerViewLL.setVisibility(View.INVISIBLE);
        binding.travellerViewLL.setVisibility(View.INVISIBLE);
        });

        binding.buyerLL.setOnClickListener(view -> {
            getRatingList("b");
            binding.eveyoneViewLL.setVisibility(View.INVISIBLE);
            binding.buyerViewLL.setVisibility(View.VISIBLE);
            binding.travellerViewLL.setVisibility(View.INVISIBLE);
        });

        binding.travellerLL.setOnClickListener(view -> {
            getRatingList("t");
            binding.eveyoneViewLL.setVisibility(View.INVISIBLE);
            binding.buyerViewLL.setVisibility(View.INVISIBLE);
            binding.travellerViewLL.setVisibility(View.VISIBLE);
        });
    }
}