package com.dev.todos.Fragment.TripAndOffers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillManager;
import android.widget.TextView;
import android.widget.Toast;



import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;


import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;



import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;

import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentAddTripBinding;


import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AddTripFragment extends Fragment {

    TextView txtDate, txtFromCity, txtToCity;
    String txt = "";
    ProgressDialog progressDialog;
    String city="0", state="0", country="0";
    String city1="", state1="", country1="";
    String city2="", state2="", country2="";
    String fromlat="", fromlong="", tolat="", tolong="";    FragmentAddTripBinding binding;
    int REQUEST_CODE_AUTOCOMPLETE = 101;
    private KProgressHUD hud;
    private Snackbar snackBar;
    String Activity = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_trip, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     //   Places.initialize(getActivity(), getString(R.string.google_place_api_key));
        txtFromCity = view.findViewById(R.id.txtTravelCityFrom);
        txtToCity = view.findViewById(R.id.txtToCity);
        txtDate = view.findViewById(R.id.txtDate);



        view.findViewById(R.id.imgSelectDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), txtDate);
            }
        });


        txtToCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "t";
               showAutocomplete();
            }
        });

        txtFromCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "f";
                showAutocomplete();

            }
        });


        binding.btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    String date = "";
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date d = simpleDateFormat.parse(binding.txtDate.getText().toString());
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        date = simpleDateFormat.format(d);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));
                        jsonObject.put(Keys.location_from, binding.txtTravelCityFrom.getText().toString());
                        jsonObject.put(Keys.location_to, binding.txtToCity.getText().toString());
                        jsonObject.put(Keys.location_from_city, city1);
                        jsonObject.put(Keys.location_from_state, state1);
                        jsonObject.put(Keys.location_from_country, country1);
                        jsonObject.put(Keys.location_to_city, city2);
                        jsonObject.put(Keys.location_to_state, state2);
                        jsonObject.put(Keys.location_to_country, country2);
                        jsonObject.put(Keys.travel_date, date);
                        jsonObject.put(Keys.latfrom,fromlat);
                        jsonObject.put(Keys.longfrom, fromlong);
                        jsonObject.put(Keys.latto,tolat);
                        jsonObject.put(Keys.longto,tolong);
                        addTrip(UseMe.getJsonObject(jsonObject));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

    }

    public boolean isValid() {
        boolean valid = true;
        if (binding.txtTravelCityFrom.getText().toString().length() == 0) {
            binding.txtTravelCityFrom.setError(getString(R.string.slelect_travel_from_city));
            valid = false;
        } else if (binding.txtToCity.getText().toString().length() == 0) {
            binding.txtToCity.setError(getString(R.string.select_travel_to_city));
            valid = false;
        } else if (binding.txtDate.getText().toString().length() == 0) {
            binding.txtDate.setError(getString(R.string.select_date));
            valid = false;
        }
        return valid;

    }

    public void addTrip(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<Responce> responceCall = ApiClient.getService().addTrip(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    Toast.makeText(getActivity(),getString(R.string.tripadded), Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();



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
                    setAddress(txtFromCity, place);
                    city1 = city;
                    state1 = state;
                    country1 = country;
                    fromlat = String.valueOf(place.getLatLng().latitude);
                    fromlong = String.valueOf(place.getLatLng().longitude);

                } else if (txt.equals("t")) {
                    setAddress(txtToCity, place);
                    country2 = country;
                    city2 = city;
                    state2 = state;
                    tolat = String.valueOf(place.getLatLng().latitude);
                    tolong = String.valueOf(place.getLatLng().longitude);
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                //Status status = Autocomplete.getStatusFromIntent(data);
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
            }
        }

    }

    private void showAutocomplete() {
        // Initialize Places.
        Places.initialize(getContext(),getString(R.string.google_place_api_key));


        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);

    }


    public void setAddress(TextView txtView, Place place) {
        try {
            List<Address> addressList = new Geocoder(getActivity(), Locale.getDefault()).getFromLocation(
                    place.getLatLng().latitude, place.getLatLng().longitude, 1);

            Log.d("TAG", "setAddress: "+addressList.get(0).getLocality()+"---"+addressList.get(0));

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
}