package com.dev.todos.Fragment.TripAndOffers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Activity.MainActivity;
import com.dev.todos.Activity.TermsActivity;
import com.dev.todos.Adapter.AssocaiteTripsAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.More.ProfileFragment;
import com.dev.todos.Model.GetProfileData.GetProfileRequest;
import com.dev.todos.Model.GetProfileData.GetProfileResponse;
import com.dev.todos.Model.Logout.LogoutRequest;
import com.dev.todos.Model.Logout.LogoutResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.TripList;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentMakeOfferBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class MakeOfferFragment extends Fragment {

    FragmentMakeOfferBinding binding;
    KProgressHUD hud;
    Snackbar snackBar;
    String txt ="";
    int REQUEST_CODE_AUTOCOMPLETE = 101;
    private String city="",state="",country="";
    private String city1="",state1="",country1="";
    private String city2="",state2="",country2="";
    String orderId ="";
    String tripId = "0";
    String deliveryDate ="";
    String locationFrom="",locationTo="";
    AlertDialog alertDialog1;
    androidx.appcompat.app.AlertDialog alertDialog;
    List<TripList> TripList = new ArrayList<>();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_make_offer, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = new Bundle();
        bundle = getArguments();
        orderId = bundle.getString(Keys.order_id);

        getMyTrip("Next");
        getProfiledata();
        onClickListener();
    }

    private void getProfiledata() {
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
                        binding.usernameTxt.setText(response.body().getUserDetails().getName());
                        binding.phoneTxt.setText(response.body().getUserDetails().getMobile());
                        binding.emailTxt.setText(response.body().getUserDetails().getEmail());

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

    public void getMyTrip(String status) {
        JsonObject j = null;
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,""));
            jsonObject.put(Keys.status, status);
            jsonObject.put(Keys.current_date, StaticClass.getDate());
            j = UseMe.getJsonObject(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Call<Responce> responceCall = ApiClient.getService().getMyTrip(j);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {

                if (response.body().getStatus().equals(Keys.status_succes)) {

                    TripList = response.body().getTripLists();
                }

            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
              //  noTxt.setVisibility(View.VISIBLE);
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });


    }


    private void onClickListener() {

        binding.termTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsActivity.class);
                intent.putExtra("Activity","Terms");
                startActivity(intent);
            }
        });
        binding.EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new ProfileFragment());
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getString(R.string.logouttxt));
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            logoutCall();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }

                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        binding.loadnewTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tripLayout.setVisibility(View.GONE);
                binding.newTripLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.txtTravelCityFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "f";
                showAutocomplete();
            }
        });

        binding.txtToCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = "t";
                showAutocomplete();
            }
        });

        binding.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), binding.txtDate);
            }
        });

        binding.AssocaiteTripTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buyerNoTxt.setText(getString(R.string.messagetobuyer));
                AssociateTripPopup();
            }
        });

        binding.assocaitetripText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buyerNoTxt.setText(getString(R.string.messagetobuyer));
                AssociateTripPopup();
            }
        });


        binding.loadTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryDate ="";
                locationFrom ="";
                tripId ="";
                locationTo ="";
                binding.buyerNoTxt.setText(getString(R.string.pleasecompleted));
                binding.buyerLL.setVisibility(View.GONE);
                binding.newTripLayout.setVisibility(View.VISIBLE);
            }
        });
        binding.assTripTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buyerNoTxt.setText(getString(R.string.messagetobuyer));
                AssociateTripPopup();
            }
        });
        binding.makeofferButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {
                String date = "";
                if(tripId.equals("0")) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Log.d("TAG", "onClick: " + binding.txtDate.getText().toString());
                    Date d = null;
                    try {
                        d = simpleDateFormat.parse(binding.txtDate.getText().toString());
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        date = simpleDateFormat.format(d);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(binding.txtTravelCityFrom.getText().toString().isEmpty()){
                        binding.txtTravelCityFrom.setError("Enter From Location");
                        binding.txtTravelCityFrom.requestFocus();
                    }else if(binding.txtToCity.getText().toString().isEmpty()){
                        binding.txtToCity.setError("Enter To Location");
                        binding.txtToCity.requestFocus();
                    }else if(binding.txtDate.getText().toString().isEmpty()){
                        binding.txtDate.setError("Enter Date");
                        binding.txtDate.requestFocus();
                    }else if (binding.edtReward.getText().toString().isEmpty()){
                        binding.edtReward.setError("Enter Reward");
                        binding.edtReward.requestFocus();
                    }else {
                        if (binding.checkbox.isChecked()) {
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String formattedDate = df.format(c);

                            SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
                            String formattedTime = df1.format(c.getTime());


                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, ""));
                                jsonObject.put(Keys.order_id, orderId);
                                jsonObject.put(Keys.delivery_from, binding.txtTravelCityFrom.getText().toString());
                                jsonObject.put(Keys.delivery_to, binding.txtToCity.getText().toString());
                                jsonObject.put(Keys.delivery_date, date);
                                jsonObject.put("return_date", date);
                                jsonObject.put("reward", binding.edtReward.getText().toString());
                                jsonObject.put("created_date", formattedDate);
                                jsonObject.put("created_time", formattedTime);
                                jsonObject.put("trip_id", tripId);
                                jsonObject.put(Keys.location_from_city, city1);
                                jsonObject.put(Keys.location_from_state, state1);
                                jsonObject.put(Keys.location_from_country, country1);
                                jsonObject.put(Keys.location_to_city, city2);
                                jsonObject.put(Keys.location_to_state, state2);
                                jsonObject.put(Keys.location_to_country, country2);
                                jsonObject.put("terms_and_condition", "1");
                                jsonObject.put(Keys.travel_date, date);

                                if (binding.shippingEt.getText().toString().isEmpty()) {
                                    jsonObject.put("shipping_cost", "0");
                                } else {
                                    jsonObject.put("shipping_cost", binding.shippingEt.getText().toString());
                                }

                                if (binding.taxEt.getText().toString().isEmpty()) {
                                    jsonObject.put("taxes_fees", "0");
                                } else {
                                    jsonObject.put("taxes_fees", binding.taxEt.getText().toString());
                                }

                                makeApicall(UseMe.getJsonObject(jsonObject));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(getActivity(), getString(R.string.accept_term_and_condition), Toast.LENGTH_SHORT).show();
                        }

                    }
                }else{


                    if (binding.edtReward.getText().toString().isEmpty()){
                        binding.edtReward.setError("Enter Reward");
                        binding.edtReward.requestFocus();
                    }else {
                        if (binding.checkbox.isChecked()) {
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String formattedDate = df.format(c);

                            SimpleDateFormat df1 = new SimpleDateFormat("hh:mm a");
                            String formattedTime = df1.format(c.getTime());


                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, ""));
                                jsonObject.put(Keys.order_id, orderId);
                                jsonObject.put(Keys.delivery_from, locationFrom);
                                jsonObject.put(Keys.delivery_to, locationTo);
                                jsonObject.put(Keys.delivery_date, deliveryDate);
                                jsonObject.put("return_date", deliveryDate);
                                jsonObject.put("reward", binding.edtReward.getText().toString());
                                jsonObject.put("created_date", formattedDate);
                                jsonObject.put("created_time", formattedTime);
                                jsonObject.put("trip_id", tripId);
                                jsonObject.put(Keys.location_from_city, city1);
                                jsonObject.put(Keys.location_from_state, state1);
                                jsonObject.put(Keys.location_from_country, country1);
                                jsonObject.put(Keys.location_to_city, city2);
                                jsonObject.put(Keys.location_to_state, state2);
                                jsonObject.put(Keys.location_to_country, country2);
                                jsonObject.put("terms_and_condition", "1");
                                jsonObject.put(Keys.travel_date, deliveryDate);

                                if (binding.shippingEt.getText().toString().isEmpty()) {
                                    jsonObject.put("shipping_cost", "0");
                                } else {
                                    jsonObject.put("shipping_cost", binding.shippingEt.getText().toString());
                                }

                                if (binding.taxEt.getText().toString().isEmpty()) {
                                    jsonObject.put("taxes_fees", "0");
                                } else {
                                    jsonObject.put("taxes_fees", binding.taxEt.getText().toString());
                                }

                                makeApicall(UseMe.getJsonObject(jsonObject));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(getActivity(), getString(R.string.accept_term_and_condition), Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });
    }

    private void AssociateTripPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.assocaitetrip_item, null);
        RecyclerView recyclerView =view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        AssocaiteTripsAdapter assocaiteTripsAdapter  = new AssocaiteTripsAdapter(getActivity(), getFragmentManager(),TripList);
        recyclerView.setAdapter(assocaiteTripsAdapter);
        assocaiteTripsAdapter.setOnItemClickListener(new AssocaiteTripsAdapter.OnClick_Event() {
            @Override
            public void setOnItemClickListener(com.dev.todos.Model.TripList tripList, int postion) {
                alertDialog1.dismiss();
                binding.newTripLayout.setVisibility(View.GONE);
                binding.tripLayout.setVisibility(View.GONE);
                binding.buyerLL.setVisibility(View.VISIBLE);

                deliveryDate= tripList.getTravel_date();

                Log.d("TAG", "setOnItemClickListener: "+deliveryDate);
                SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
                Date d = null;
                try {
                    d = dateFormatprev.parse(deliveryDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
                String changedDate = dateFormat.format(d);
                tripId = tripList.getTrip_id();



                binding.txtTravelCityFrom.setText("");
                binding.txtToCity.setText("");
                binding.txtDate.setText("");

                locationFrom =tripList.getLocation_from();
                locationTo =tripList.getLocation_to();

                binding.buyerTxt.setText(getString(R.string.hello)+" "+Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERNAME,"")+ getString(R.string.planatrip) +" "+
                        " "+tripList.getLocation_from()+" "+getString(R.string.totxt)+" "+tripList.getLocation_to()+" "+getString(R.string.fortxt) +" "+changedDate+" "+getString(R.string.acceptmyyoffer));
            }
        });


        ImageView closeImg =view.findViewById(R.id.closeImg);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.setCancelable(false);
        alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog1.show();

    }

    private void makeApicall(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<Responce> responceCall = ApiClient.getService().makeOffer(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hud.dismiss();

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    setalertDialog(getString(R.string.offersent));

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

    private void showAutocomplete() {
        Places.initialize(getContext(),getString(R.string.google_place_api_key));

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getContext());

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.PHONE_NUMBER, Place.Field.RATING, Place.Field.WEBSITE_URI);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);

    }


    public void setAddress(TextView txtView, Place place) {
        try {
            List<Address> addressList = new Geocoder(getActivity(), Locale.getDefault()).getFromLocation(
                    place.getLatLng().latitude, place.getLatLng().longitude, 1);

            if (addressList != null && addressList.size() > 0) {
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

                } else if (txt.equals("t")) {
                    setAddress(binding.txtToCity, place);
                    country2 = country;
                    city2 = city;
                    state2 = state;
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
            }
        }

    }


    private void logoutCall() {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setDevice("android");
        logoutRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));


        Call<LogoutResponse> logoutResponseCall  = ApiClient.getService().logout(logoutRequest);
        logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    if(response.body().getStatus().equals("1")){
//                        Log.e("lang16",Sharedpreferences.readFromPreferences(getContext(), AppConstant.APPLANGUAGE,""));
//                        String language = Sharedpreferences.readFromPreferences(getContext(),AppConstant.APPLANGUAGE,"en");
//                        Sharedpreferences.clearData(getActivity());
//                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.APPLANGUAGE,language);
                        disconnectFromFacebook();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                hud.dismiss();
            }
        });

    }

    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
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
                if(StaticClass.fromTraveller) {
                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null, R.id.frameLayoout,
                            new TravellerFragment());
                }else{
                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null, R.id.frameLayoout,
                            new MyOfferFragment());
                }
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
}