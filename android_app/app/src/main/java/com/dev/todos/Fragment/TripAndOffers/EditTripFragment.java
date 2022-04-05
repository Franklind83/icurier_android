package com.dev.todos.Fragment.TripAndOffers;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.todos.Model.Responce;
import com.dev.todos.Model.Trip.TripRequest;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentEditTripBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class EditTripFragment extends Fragment {

    FragmentEditTripBinding binding;
    String txt = "";
    String city="", state=" ", country=" ";
    String city1="", state1="", country1="";
    String city2="", state2="", country2="";
    int REQUEST_CODE_AUTOCOMPLETE = 101;
    private KProgressHUD hud;
    private Snackbar snackBar;
    AlertDialog alertDialog;
    String Tripid="";
    String fromlat="", fromlong="", tolat="", tolong="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_trip, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = getArguments();
         Tripid = bundle.getString(Keys.trip_id);
        String Tripdate = bundle.getString(Keys.travel_date);
        String locationFrom = bundle.getString(Keys.location_from);
        String locationTo = bundle.getString(Keys.location_to);

        fromlat=bundle.getString(Keys.latfrom);
        fromlong=bundle.getString(Keys.longfrom);
        tolat=bundle.getString(Keys.latto);
        tolong=bundle.getString(Keys.longto);


        binding.txtTravelCityFrom.setText(locationFrom);
        binding.txtToCity.setText(locationTo);
        String date = parseDateToddMMyyyy(Tripdate);
        binding.txtDate.setText(date);

        getClickListener();
    }

    private void getClickListener() {

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateApi();
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        binding.txtToCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "t";
                showAutocomplete();
            }
        });

        binding.txtTravelCityFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "f";
                showAutocomplete();

            }
        });

        binding.imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), binding.txtDate);
            }
        });
    }

    private void updateApi() {
        JSONObject jsonObject = new JSONObject();
        String date = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date d = simpleDateFormat.parse(binding.txtDate.getText().toString());
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            date = simpleDateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateApicall(date);

    }

    private void updateApicall(String date) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        TripRequest tripRequest = new TripRequest();
        tripRequest.setTripId(Tripid);
        tripRequest.setLocationFrom(binding.txtTravelCityFrom.getText().toString());
        tripRequest.setLocationTo(binding.txtToCity.getText().toString());
        tripRequest.setLocationFromCity(city1);
        tripRequest.setLocationFromState(state1);
        tripRequest.setLocationFromCountry(country1);
        tripRequest.setLocationToCity(city2);
        tripRequest.setLocationToState(state2);
        tripRequest.setLocationToCountry(country2);
        tripRequest.setTraveDate(date);
        tripRequest.setLocation_latfrom(fromlat);
        tripRequest.setLocation_longfrom(fromlong);
        tripRequest.setLocation_latto(tolat);
        tripRequest.setLocation_longto(tolong);




        Call<Responce> responceCall = ApiClient.getService().editTrip(tripRequest);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    setalertDialog(getString(R.string.tripedited));
                  //  Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                    snackBar.show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (txt.equals("f")) {
                    setAddress(binding.txtTravelCityFrom, place);
                    city1 = city;
                    state1 = state;
                    country1 = country;
                    fromlat = String.valueOf(place.getLatLng().latitude);
                    fromlong = String.valueOf(place.getLatLng().longitude);

                } else if (txt.equals("t")) {
                    setAddress(binding.txtToCity, place);
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
        Places.initialize(requireContext(),getString(R.string.google_place_api_key));

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,Place.Field.LAT_LNG);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(requireContext());
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);

    }


    public void setAddress(TextView txtView, Place place) {
        try {
            List<Address> addressList = new Geocoder(getActivity(), Locale.getDefault()).getFromLocation(
                    place.getLatLng().latitude, place.getLatLng().longitude, 1);

            if (addressList.size() > 0) {
                String add = place.getAddress();
                txtView.setText(add);
                txtView.setError(null);

                if(addressList.get(0).getLocality() != null) {
                    city = addressList.get(0).getLocality();
                }else{
                    if(addressList.get(0).getSubAdminArea() != null){
                        city = addressList.get(0).getSubAdminArea();
                    }else{
                        city ="0";
                    }
                }

                if(addressList.get(0).getAdminArea() != null) {
                    state = addressList.get(0).getAdminArea();
                }else{
                    if(addressList.get(0).getAdminArea() != null){
                        state = addressList.get(0).getAdminArea();
                    }else{
                        state ="0";
                    }
                }
                country = addressList.get(0).getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void setalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
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