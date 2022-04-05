package com.dev.todos.Activity;



import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dev.todos.Adapter.ShopperNotLoginAdapter;
import com.dev.todos.Model.FilterShopper.FilterShopperRequest;
import com.dev.todos.Model.FilterShopper.FilterShopperResponse;
import com.dev.todos.Model.GetTravellerList.TravellerListRequest;
import com.dev.todos.Model.GetTravellerList.TravellerListResponse;
import com.dev.todos.Model.GetTravellerList.TripListItem;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.databinding.ActivityShopperListNotLoginBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopperListNotLoginActivity extends BaseActivity {

    private ActivityShopperListNotLoginBinding binding;
    List<TripListItem> tripListItems = new ArrayList<>();
    ShopperNotLoginAdapter shopperNotLoginAdapter;
    String txt ="";
    int REQUEST_CODE_AUTOCOMPLETE = 101;
    String city="", state="", country="";
    String city1="", state1="", country1="";
    String city2="", state2="", country2="";
    FragmentManager fragmentManager;
    String fromlat="", fromlong="", tolat="", tolong="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopper_list_not_login);
        Log.e("lang1",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,""));
        if (Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,"en").equals("en")){
            setLocale(ShopperListNotLoginActivity.this,"en");
        }else{
            setLocale(ShopperListNotLoginActivity.this,"es");

        }

        getApiCall();
        initView();
        onClickListener();
        setStatusBarGradiant(ShopperListNotLoginActivity.this);
    }

    private void getApiCall() {

        showLoading(ShopperListNotLoginActivity.this);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date= simpleDateFormat.format(new Date());

        TravellerListRequest travellerListRequest = new TravellerListRequest();
        travellerListRequest.setUserId("0");
        travellerListRequest.setCurrentDate(date);

        Call<TravellerListResponse> travellerListResponseCall = ApiClient.getService().travellerlist(travellerListRequest);
        travellerListResponseCall.enqueue(new Callback<TravellerListResponse>() {
            @Override
            public void onResponse(Call<TravellerListResponse> call, Response<TravellerListResponse> response) {
                if(response.isSuccessful()){
                    hideLoading();
                    if(response.body().getStatus().equals("1")){
                        binding.txtNoOrder.setVisibility(View.GONE);
                        tripListItems = response.body().getTripList();
                        setDataByView(tripListItems);
                    }else{
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        showSnackbar(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<TravellerListResponse> call, Throwable t) {
                hideLoading();
                binding.txtNoOrder.setVisibility(View.VISIBLE);
                showSnackbar(getString(R.string.nointernet));
            }
        });
    }

    private void setDataByView(List<TripListItem> tripListItems) {
        if(shopperNotLoginAdapter == null){
            shopperNotLoginAdapter = new ShopperNotLoginAdapter(getApplicationContext(),fragmentManager);
            binding.recyclerView.setAdapter(shopperNotLoginAdapter);
            shopperNotLoginAdapter.setUpList(tripListItems);
        }else{
            shopperNotLoginAdapter.setUpList(tripListItems);
        }
    }

    private void initView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onClickListener() {

        binding.icdownLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.typeLl.getVisibility() == View.VISIBLE) {
                    binding.typeLl.setVisibility(View.GONE);
                } else {
                    binding.typeLl.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.shopperTxt.setOnClickListener(view -> {
            binding.typeLl.setVisibility(View.GONE);
        });

        binding.travellerTxt.setOnClickListener(view -> {
            binding.typeLl.setVisibility(View.GONE);
            startActivity(new Intent(getApplicationContext(),TravellerListNotLoginActivity.class));
        });

        binding.cardLL.setOnClickListener(view ->{
            getApiCall();
        });

        binding.loginLL.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.putExtra("Activity","NewLogin");
            startActivity(intent);
        });
        binding.aereoplaneLL.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),TravellerListNotLoginActivity.class));
        });

        binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                i.putExtra("Activity","AddOrder");
                i.putExtra(Keys.trip_id,"");
                i.putExtra(Keys.traveller_id,"");
                startActivity(i);
                finishAffinity();
            }
        });


        binding.imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.filterLayout.setVisibility(View.VISIBLE);
                binding.toolBarRl.setVisibility(View.GONE);

                for (int i = 0; i < binding.frameLayout.getChildCount(); i++) {
                    View view = binding.frameLayout.getChildAt(i);
                    view.setEnabled(false); // Or whatever you want to do with the view.
                    view.setVisibility(View.INVISIBLE); // or View.GONE as you needed

                }
            }
        });


        //filter

        binding.tvSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "f";
                showAutocomplete();
            }
        });

        binding.tvDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "t";
                showAutocomplete();
            }
        });

        binding.tvSinceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(ShopperListNotLoginActivity.this, binding.tvSinceDate);
            }
        });


        binding.tvUntillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(ShopperListNotLoginActivity.this, binding.tvUntillDate);
            }
        });

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.filterLayout.setVisibility(View.GONE);
                binding.toolBarRl.setVisibility(View.VISIBLE);


                for (int i = 0; i <  binding.frameLayout.getChildCount(); i++) {
                    View view =  binding.frameLayout.getChildAt(i);
                    view.setEnabled(true); // Or whatever you want to do with the view.
                    view.setVisibility(View.VISIBLE); // or View.GONE as you needed
                }

                binding.typeLl.setVisibility(View.GONE);
                binding.txtNoOrder.setVisibility(View.GONE);
            }
        });

        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String src = "", dest = "", fromDate = "", ToDate = "";

                boolean status = false;
                if (binding.tvSource.getText().toString().trim().equals("Location From")) {
                    src = "";
                } else {
                    status = true;
                    src = binding.tvSource.getText().toString().trim();
                }

                if (binding.tvDestination.getText().toString().trim().equals("Location To")) {

                    dest = "";

                } else {
                    dest = binding.tvDestination.getText().toString().trim();
                    status = true;

                }

                if (binding.tvSinceDate.getText().toString().trim().equals("Since Date")) {
                    fromDate = "";
                } else {
                    fromDate = binding.tvSinceDate.getText().toString().trim();
                    status = true;
                }
                if (binding.tvUntillDate.getText().toString().trim().equals("Until Date")) {
                    ToDate = "";
                } else {
                    ToDate = binding.tvUntillDate.getText().toString().trim();
                    status = true;
                }
                if (status == false) {
                    Toast.makeText(getApplicationContext(), "Please Select Atleast One field", Toast.LENGTH_SHORT).show();
                }else{
                    binding.filterLayout.setVisibility(View.GONE);
                    binding.toolBarRl.setVisibility(View.VISIBLE);
                    for (int i = 0; i <  binding.frameLayout.getChildCount(); i++) {
                        View view =  binding.frameLayout.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                        view.setVisibility(View.VISIBLE); // or View.GONE as you needed
                    }

                        applyFilterShopperRequest(src, dest, fromDate, ToDate);

                }

                binding.typeLl.setVisibility(View.GONE);
            }
        });

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.tvSource.setText("Location From");
                binding.tvDestination.setText("Location To");
                binding.tvSinceDate.setText("Since Date");
                binding.tvUntillDate.setText("Until Date");

                binding.filterLayout.setVisibility(View.GONE);
                binding.toolBarRl.setVisibility(View.VISIBLE);

                for (int i = 0; i < binding.frameLayout.getChildCount(); i++) {
                    View view = binding.frameLayout.getChildAt(i);
                    view.setEnabled(true); // Or whatever you want to do with the view.
                    view.setVisibility(View.VISIBLE); // or View.GONE as you needed
                }



                    getApiCall();


                binding.typeLl.setVisibility(View.GONE);
                binding.txtNoOrder.setVisibility(View.GONE);

            }
        });

        binding.closeSourceImg.setOnClickListener(v -> binding.tvSource.setText("Location From"));

        binding.closeDestImg.setOnClickListener(v -> binding.tvDestination.setText("Location To"));

        binding.closeSinDateImg.setOnClickListener(v -> binding.tvSinceDate.setText("Since Date"));

        binding.closeDestDateImg.setOnClickListener(v -> binding.tvUntillDate.setText("Until Date"));
    }

    private void applyFilterShopperRequest(String src, String dest, String fromDate, String toDate) {
        try {


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date d = simpleDateFormat.parse(fromDate);
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            fromDate = simpleDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date d = simpleDateFormat.parse(toDate);
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            toDate = simpleDateFormat.format(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String userId = "0";

        String locationFrom="";
        if(binding.tvSource.getText().toString().equals("Location From")){
            locationFrom = "";
        }else{
            locationFrom = binding.tvSource.getText().toString();
        }

        String locationTo="";
        if(binding.tvDestination.getText().toString().equals("Location To")){
            locationTo = "";
        }else{
            locationTo = binding.tvDestination.getText().toString();
        }

        FilterShopperRequest filterOrderRequest = new FilterShopperRequest();
        filterOrderRequest.setLocationFrom(locationFrom);
        filterOrderRequest.setLocationTo(locationTo);
        filterOrderRequest.setSinceDate(fromDate.trim());
        filterOrderRequest.setLocationFromCity(city1);
        filterOrderRequest.setLocationFromCountry(country1);
        filterOrderRequest.setLocationFromState(state1);
        filterOrderRequest.setLocationToCity(city2);
        filterOrderRequest.setLocationToCountry(country2);
        filterOrderRequest.setLocationToState(state2);
        filterOrderRequest.setUntilDate(toDate.trim());
        filterOrderRequest.setUserId(userId);
        filterOrderRequest.setLocation_latfrom(fromlat);
        filterOrderRequest.setLocation_longfrom(fromlong);
        filterOrderRequest.setLocation_latto(tolat);
        filterOrderRequest.setLocation_longto(tolong);


       showLoading(ShopperListNotLoginActivity.this);

        Call<FilterShopperResponse> responceCall = ApiClient.getService().filtershopper(filterOrderRequest);

        responceCall.enqueue(new Callback<FilterShopperResponse>() {
            @Override
            public void onResponse(Call<FilterShopperResponse> call, Response<FilterShopperResponse> response) {
               hideLoading();
                if(response.isSuccessful()){

                    if(response.body().getStatus().equals("1")){
                        binding.txtNoOrder.setVisibility(View.GONE);
                        tripListItems.clear();
                        tripListItems = response.body().getTripList();
                        setDataByViewShopper(tripListItems);
                    }else{
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.txtNoOrder.setText("No Trips Available");
                        binding.txtNoOrder.setBackgroundColor(Color.parseColor("#CB020000"));
                        binding.txtNoOrder.setTextColor(Color.parseColor("#ffffff"));
                       /* snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();*/

                    }
                }
            }

            @Override
            public void onFailure(Call<FilterShopperResponse> call, Throwable t) {
                hideLoading();

            }
        });


    }

    private void setDataByViewShopper(List<TripListItem> tripListItems) {
        if(shopperNotLoginAdapter == null){
            shopperNotLoginAdapter = new ShopperNotLoginAdapter(getApplicationContext(),fragmentManager);
            binding.recyclerView.setAdapter(shopperNotLoginAdapter);
            shopperNotLoginAdapter.setUpList(tripListItems);
        }else{
            binding.recyclerView.setAdapter(shopperNotLoginAdapter);
            shopperNotLoginAdapter.setUpList(tripListItems);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (txt.equals("f")) {
                    setAddress(binding.tvSource, place);

                    city1 = city;
                    state1 = state;
                    country1 = country;

                    fromlat = String.valueOf(place.getLatLng().latitude);
                    fromlong = String.valueOf(place.getLatLng().longitude);

                } else if (txt.equals("t")) {
                    setAddress(binding.tvDestination, place);

                    country2 = country;
                    city2 = city;
                    state2 = state;
                    tolat = String.valueOf(place.getLatLng().latitude);
                    tolong = String.valueOf(place.getLatLng().longitude);
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
            }
        }

    }

    private void showAutocomplete() {
        // Initialize Places.
        Places.initialize(getApplicationContext(),getString(R.string.google_place_api_key));


        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.PHONE_NUMBER, Place.Field.RATING, Place.Field.WEBSITE_URI);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getApplicationContext());
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);

    }


    public void setAddress(TextView txtView, Place place) {
        try {
            List<Address> addressList = new Geocoder(getApplicationContext(), Locale.getDefault()).getFromLocation(
                    place.getLatLng().latitude, place.getLatLng().longitude, 1);

            if (addressList.size() > 0) {

                    String add = place.getAddress();
                    txtView.setText(add);
                    txtView.setError(null);

                if(addressList.get(0).getLocality() != null) {
                    city = addressList.get(0).getLocality();

                }else{

                    city = addressList.get(0).getSubAdminArea();
                }
                state = addressList.get(0).getAdminArea();
                country = addressList.get(0).getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void selectDate(Context context, final TextView textView) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int m = month + 1;
                textView.setText(dayOfMonth + "/" +  m + "/" + year);
                textView.setError(null);

            }
        }, year, month, day);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}
