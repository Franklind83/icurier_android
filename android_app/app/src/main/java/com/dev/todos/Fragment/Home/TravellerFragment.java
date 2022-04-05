package com.dev.todos.Fragment.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Adapter.FilterAdapter;
import com.dev.todos.Adapter.ShopperNotLoginAdapter;
import com.dev.todos.Adapter.TravellerNotLoginAdapter;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.Notification.NotificationFragment;
import com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment;
import com.dev.todos.Fragment.TripAndOffers.AddTripFragment;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.TripAndOffers.OffersFragment;
import com.dev.todos.Model.Filter.FilterRequest;
import com.dev.todos.Model.Filter.FilterResponse;
import com.dev.todos.Model.FilterShopper.FilterShopperRequest;
import com.dev.todos.Model.FilterShopper.FilterShopperResponse;
import com.dev.todos.Model.GetOrderList.OrderListItem;
import com.dev.todos.Model.GetOrderList.OrderListRequest;
import com.dev.todos.Model.GetOrderList.OrderListResponse;
import com.dev.todos.Model.GetTravellerList.TravellerListRequest;
import com.dev.todos.Model.GetTravellerList.TravellerListResponse;
import com.dev.todos.Model.GetTravellerList.TripListItem;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.RoomDatabase.DatabaseClient;
import com.dev.todos.RoomDatabase.GetNotificationData;
import com.dev.todos.RoomDatabase.RoomDatabaseClass;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.ActivityBottomnavigationBinding;
import com.dev.todos.databinding.FragmentTravellerBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.checkorder;

public class TravellerFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private FragmentTravellerBinding binding;
    private TravellerNotLoginAdapter travellerNotLoginAdapter;
    private List<OrderListItem> orderLists = new ArrayList<>();
    private KProgressHUD hud;
    private Snackbar snackBar;
    String Type = "traveller";
    String userid = "";
    String city = "", state = "", country = "";
    String city1 = "", state1 = "", country1 = "";
    String city2 = "", state2 = "", country2 = "";
    String fromlat = "", fromlong = "", tolat = "", tolong = "";
    int REQUEST_CODE_AUTOCOMPLETE = 101;
    public static String Tripid = "", TravellerId = "";
    String txt = "";
    int Count = 0;
    int Flag = 0;

    private List<TripListItem> tripListItems = new ArrayList<>();
    private ShopperNotLoginAdapter shopperNotLoginAdapter;
    FilterAdapter filterAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_traveller, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NotNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            UseMe.isBottomNavVisible(true);

            BaseActivity.setProfile(getActivity(), Sharedpreferences.readFromPreferences(getActivity(), AppConstant.PROFILEIMAGE, ""), binding.imgProfile);

            checkorder = false;


            initView();

            onClickListener();


            GetNotificationData userServices = new GetNotificationData();
            List<RoomDatabaseClass> taskList = userServices.getAllUsers();

            if (taskList.size() == 0) {
                binding.linearNoti.setVisibility(View.GONE);
            } else {
                Count = 0;
                for (int i = 0; i < taskList.size(); i++) {
                    if (taskList.get(i).getIsRead().equals("0")) {
                        Count = Count + 1;
                    }
                }

                if (Count == 0) {
                    binding.linearNoti.setVisibility(View.GONE);
                } else {
                    binding.textNo.setText("" + Count);
                    binding.linearNoti.setVisibility(View.VISIBLE);

                }

            }
        } catch (Exception e) {
        }

    }

    private void getApiCallTraveller() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        if (isLogin()) {
            userid = Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, "");
        } else {
            userid = "0";
        }

        OrderListRequest orderListRequest = new OrderListRequest();
        orderListRequest.setUserId(userid);
        orderListRequest.setPostString("all");

        Call<OrderListResponse> orderListResponseCall = ApiClient.getService().orderlist(orderListRequest);
        orderListResponseCall.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(@NotNull Call<OrderListResponse> call, @NotNull Response<OrderListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        hud.dismiss();
                    } catch (Exception e) {
                    }
                    if (response.body().getStatus().equals("1")) {
                        orderLists = response.body().getOrderList();
                        if (orderLists.size() == 0) {
                            binding.txtNoOrder.setVisibility(View.VISIBLE);
                        } else {
                            binding.txtNoOrder.setVisibility(View.GONE);
                            binding.recyclerView.setVisibility(View.VISIBLE);
                            setDataByViewTraveller(orderLists);
                        }
                    } else {
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        binding.txtNoOrder.setText(response.body().getMsg());
                        snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "" + response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                hud.dismiss();
                snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();

            }
        });
    }

    private void getApiCallShopper() {
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());

        if (isLogin()) {
            userid = Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, "");
        } else {
            userid = "0";
        }
        TravellerListRequest travellerListRequest = new TravellerListRequest();
        travellerListRequest.setUserId(userid);
        travellerListRequest.setCurrentDate(date);

        Call<TravellerListResponse> travellerListResponseCall = ApiClient.getService().travellerlist(travellerListRequest);
        travellerListResponseCall.enqueue(new Callback<TravellerListResponse>() {
            @Override
            public void onResponse(Call<TravellerListResponse> call, Response<TravellerListResponse> response) {
                if (response.isSuccessful()) {
                    hud.dismiss();
                    if (response.body().getStatus().equals("1")) {
                        binding.txtNoOrder.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        tripListItems = response.body().getTripList();
                        setDataByViewShopper(tripListItems);
                    } else {
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "" + response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();

                    }
                }
            }

            @Override
            public void onFailure(Call<TravellerListResponse> call, Throwable t) {
                hud.dismiss();
                binding.txtNoOrder.setVisibility(View.VISIBLE);
                snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });
    }

    private void setDataByViewTraveller(List<OrderListItem> orderLists) {
        StaticClass.fromTraveller = true;
        if (travellerNotLoginAdapter == null) {
            travellerNotLoginAdapter = new TravellerNotLoginAdapter(getActivity(), getFragmentManager());
            binding.recyclerView.setAdapter(travellerNotLoginAdapter);
            travellerNotLoginAdapter.setUplist(orderLists);
        } else {
            binding.recyclerView.setAdapter(travellerNotLoginAdapter);
            travellerNotLoginAdapter.setUplist(orderLists);
        }

        travellerNotLoginAdapter.setOnItemClickListener(new TravellerNotLoginAdapter.OnClick_Event() {
            @Override
            public void setOnItemClickListener(OrderListItem orderListItem, int postion) {

                Bundle bundle = new Bundle();
                bundle.putString("OrderId", orderListItem.getOrderId());
                bundle.putString("Fragment", "FromTraveller");

                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                        new OffersFragment());
            }
        });

    }

    private void setDataByViewShopper(List<TripListItem> tripListItems) {
        if (shopperNotLoginAdapter == null) {
            shopperNotLoginAdapter = new ShopperNotLoginAdapter(getActivity(), getFragmentManager());
            binding.recyclerView.setAdapter(shopperNotLoginAdapter);
            shopperNotLoginAdapter.setUpList(tripListItems);
        } else {
            binding.recyclerView.setAdapter(shopperNotLoginAdapter);
            shopperNotLoginAdapter.setUpList(tripListItems);
        }

        shopperNotLoginAdapter.setOnItemClickListener(new ShopperNotLoginAdapter.OnClick_Event() {
            @Override
            public void setOnItemClickListener(TripListItem tripListItem, int postion) {
                binding.bottomsheet.setVisibility(View.VISIBLE);

                binding.newOrderTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.trip_id, tripListItem.getTripId());
                        bundle.putString(Keys.traveller_id, tripListItem.getUserId());

                        binding.bottomsheet.setVisibility(View.GONE);
                        new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                                new AddShopperFragment());
                    }
                });

                binding.existingOrderTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*   Bundle bundle = new Bundle();*/
                        Tripid = tripListItem.getTripId();
                        TravellerId = tripListItem.getUserId();

                        binding.bottomsheet.setVisibility(View.GONE);

                        Intent i = new Intent(getContext(), BottomnavigationActivity.class);
                        i.putExtra("Activity", "TravellerFragment");
                        startActivity(i);
                    }
                });
            }
        });


        binding.cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.bottomsheet.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void onClickListener() {

        binding.notificationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new NotificationFragment());
            }
        });

        binding.addNewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Type.equals("shopper")) {
                    StaticClass.fromTraveller = true;

                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.isFromUpdate, "false");
                    bundle.putString(Keys.qunt, "1");
                    assert getFragmentManager() != null;
                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle,
                            R.id.frameLayoout, new AddShopperFragment());

                } else if (Type.equals("traveller")) {
                    StaticClass.fromTraveller = true;
                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new AddTripFragment());
                }
            }
        });

        binding.icdownLl.setOnClickListener(new View.OnClickListener() {
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
            binding.tvSource.setText(getString(R.string.LocationFrom));
            binding.tvDestination.setText(getString(R.string.LocationTo));
            binding.tvSinceDate.setText(getString(R.string.SinceDate));
            binding.tvUntillDate.setText(getString(R.string.UntilDate));
            orderLists.clear();
            Type = "shopper";
            binding.frameLayout.setBackgroundResource(R.drawable.add_product);
            binding.recyclerView.setBackgroundColor(Color.parseColor("#D1000000"));
            binding.txtNoOrder.setTextColor(Color.parseColor("#ffffff"));
            binding.typeLl.setVisibility(View.GONE);
            binding.txtOrder.setText(getText(R.string.toshopper));
            tripListItems.clear();
            getApiCallShopper();
        });
        binding.travellerTxt.setOnClickListener(view -> {
            binding.tvSource.setText(getString(R.string.LocationFrom));
            binding.tvDestination.setText(getString(R.string.LocationTo));
            binding.tvSinceDate.setText(getString(R.string.SinceDate));
            binding.tvUntillDate.setText(getString(R.string.UntilDate));
            tripListItems.clear();
            Type = "traveller";
            binding.frameLayout.setBackgroundColor(Color.parseColor("#EFEFF4"));
            binding.recyclerView.setBackgroundColor(Color.parseColor("#EFEFF4"));
            binding.txtNoOrder.setTextColor(Color.parseColor("#D3D3D3"));

            binding.typeLl.setVisibility(View.GONE);
            binding.txtOrder.setText(getText(R.string.totraveller));
            orderLists.clear();
            getApiCallTraveller();
        });

        binding.imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.filterLayout.setVisibility(View.VISIBLE);
                binding.toolbar.setVisibility(View.GONE);

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
                UseMe.selectDate(getActivity(), binding.tvSinceDate);
            }
        });

        binding.tvSinceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), binding.tvSinceDate);
            }
        });

        binding.tvUntillDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), binding.tvUntillDate);
            }
        });

        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.filterLayout.setVisibility(View.GONE);
                binding.toolbar.setVisibility(View.VISIBLE);


                for (int i = 0; i < binding.frameLayout.getChildCount(); i++) {
                    View view = binding.frameLayout.getChildAt(i);
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
                if (binding.tvSource.getText().toString().trim().equals(getString(R.string.LocationFrom))) {
                    src = "";
                } else {
                    status = true;
                    src = binding.tvSource.getText().toString().trim();
                }

                if (binding.tvDestination.getText().toString().trim().equals(getString(R.string.LocationTo))) {

                    dest = "";

                } else {
                    dest = binding.tvDestination.getText().toString().trim();
                    status = true;

                }

                if (binding.tvSinceDate.getText().toString().trim().equals(getString(R.string.SinceDate))) {
                    fromDate = "";
                } else {
                    fromDate = binding.tvSinceDate.getText().toString().trim();
                    status = true;
                }
                if (binding.tvUntillDate.getText().toString().trim().equals(getString(R.string.UntilDate))) {
                    ToDate = "";
                } else {
                    ToDate = binding.tvUntillDate.getText().toString().trim();
                    status = true;
                }
                if (status == false) {
                    Toast.makeText(getContext(), "Please Select Atleast One field", Toast.LENGTH_SHORT).show();
                } else {
                    binding.filterLayout.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.VISIBLE);
                    for (int i = 0; i < binding.frameLayout.getChildCount(); i++) {
                        View view = binding.frameLayout.getChildAt(i);
                        view.setEnabled(true); // Or whatever you want to do with the view.
                        view.setVisibility(View.VISIBLE); // or View.GONE as you needed
                    }


                    if (Type.equals("traveller")) {
                        applyFilterRequest(src, dest, fromDate, ToDate);
                    } else {
                        applyFilterShopperRequest(src, dest, fromDate, ToDate);
                    }

                }

                binding.typeLl.setVisibility(View.GONE);
            }
        });

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.tvSource.setText(getString(R.string.LocationFrom));
                binding.tvDestination.setText(getString(R.string.LocationTo));
                binding.tvSinceDate.setText(getString(R.string.SinceDate));
                binding.tvUntillDate.setText(getString(R.string.UntilDate));

                binding.filterLayout.setVisibility(View.GONE);
                binding.toolbar.setVisibility(View.VISIBLE);

                for (int i = 0; i < binding.frameLayout.getChildCount(); i++) {
                    View view = binding.frameLayout.getChildAt(i);
                    view.setEnabled(true); // Or whatever you want to do with the view.
                    view.setVisibility(View.VISIBLE); // or View.GONE as you needed
                }


                if (Type.equals("traveller")) {
                    getApiCallTraveller();
                } else {
                    getApiCallShopper();
                }

                binding.typeLl.setVisibility(View.GONE);
                binding.txtNoOrder.setVisibility(View.GONE);

            }
        });

        binding.closeSourceImg.setOnClickListener(v -> binding.tvSource.setText(getString(R.string.LocationFrom)));

        binding.closeDestImg.setOnClickListener(v -> binding.tvDestination.setText(getString(R.string.LocationTo)));

        binding.closeSinDateImg.setOnClickListener(v -> binding.tvSinceDate.setText(getString(R.string.SinceDate)));

        binding.closeDestDateImg.setOnClickListener(v -> binding.tvUntillDate.setText(getString(R.string.UntilDate)));
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
        String userId = "";
        if (isLogin()) {

            userId = Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, "");

        } else {

            userId = "0";
        }

        String locationFrom = "";
        if (binding.tvSource.getText().toString().equals(getString(R.string.LocationFrom))) {
            locationFrom = "";
        } else {
            locationFrom = binding.tvSource.getText().toString();
        }

        String locationTo = "";
        if (binding.tvDestination.getText().toString().equals(getString(R.string.LocationTo))) {
            locationTo = "";
        } else {
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


        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<FilterShopperResponse> responceCall = ApiClient.getService().filtershopper(filterOrderRequest);

        responceCall.enqueue(new Callback<FilterShopperResponse>() {
            @Override
            public void onResponse(Call<FilterShopperResponse> call, Response<FilterShopperResponse> response) {
                hud.dismiss();
                if (response.isSuccessful()) {
                    hud.dismiss();
                    if (response.body().getStatus().equals("1")) {
                        binding.txtNoOrder.setVisibility(View.GONE);
                        tripListItems.clear();
                        tripListItems = response.body().getTripList();
                        setDataByViewShopper(tripListItems);
                    } else {
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.txtNoOrder.setText(getString(R.string.notripaa));
                        binding.txtNoOrder.setBackgroundColor(Color.parseColor("#CB020000"));
                        binding.txtNoOrder.setTextColor(Color.parseColor("#ffffff"));
                    }
                }
            }

            @Override
            public void onFailure(Call<FilterShopperResponse> call, Throwable t) {
                hud.dismiss();

            }
        });


    }

    public boolean isLogin() {
        return Sharedpreferences.readFromPreferences(getContext(), AppConstant.IS_LOGIN, false);
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
        Places.initialize(getContext(), getString(R.string.google_place_api_key));


        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.ID, Place.Field.PHONE_NUMBER, Place.Field.RATING, Place.Field.WEBSITE_URI);


        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getContext());
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

                if (addressList.get(0).getLocality() != null) {

                    city = addressList.get(0).getLocality();
//                    city = String.valueOf(addressList.get(0).getLatitude());

                } else {

                    city = addressList.get(0).getSubAdminArea();
//                    city = String.valueOf(addressList.get(0).getLatitude());
                }
                state = addressList.get(0).getAdminArea();
                country = addressList.get(0).getCountryName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void applyFilterRequest(String src, String dest, String fromDate, String toDate) {
        try {

            if (fromDate.toString().trim().length() <= 0 || fromDate.isEmpty() || fromDate == null) {

            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date d = simpleDateFormat.parse(fromDate);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                fromDate = simpleDateFormat.format(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            if (toDate.toString().trim().length() <= 0 || toDate.isEmpty() || toDate == null) {

            } else {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date d = simpleDateFormat.parse(toDate);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                toDate = simpleDateFormat.format(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String userId = "";
        if (isLogin()) {

            userId = Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID, "");

        } else {

            userId = "0";
        }

        String locationFrom = "";
        if (binding.tvSource.getText().toString().equals(getString(R.string.LocationFrom))) {
            locationFrom = "";
        } else {
            locationFrom = binding.tvSource.getText().toString();
        }

        String locationTo = "";
        if (binding.tvDestination.getText().toString().equals(getString(R.string.LocationTo))) {
            locationTo = "";
        } else {
            locationTo = binding.tvDestination.getText().toString();
        }

        FilterRequest filterOrderRequest = new FilterRequest();
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


        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<FilterResponse> responceCall = ApiClient.getService().filter(filterOrderRequest);

        responceCall.enqueue(new Callback<FilterResponse>() {
            @Override
            public void onResponse(Call<FilterResponse> call, Response<FilterResponse> response) {
                hud.dismiss();
                if (response.isSuccessful()) {
                    hud.dismiss();
                    assert response.body() != null;
                    if (response.body().getStatus().equals("1")) {
                        List<OrderListItem> orderList = new ArrayList<>();
                        orderList = response.body().getOrderList();
                        if (orderLists.size() == 0) {
                            binding.txtNoOrder.setVisibility(View.VISIBLE);
                        } else {
                            binding.txtNoOrder.setVisibility(View.GONE);
                            setDataByViewFilterTraveller(orderList);
                        }
                    } else {
                        binding.txtNoOrder.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.txtNoOrder.setText(getString(R.string.noorders));
                        binding.txtNoOrder.setBackgroundColor(Color.parseColor("#ffffff"));
                        binding.txtNoOrder.setTextColor(Color.parseColor("#000000"));

                    }
                }
            }

            @Override
            public void onFailure(Call<FilterResponse> call, Throwable t) {
                hud.dismiss();

            }
        });


    }

    private void setDataByViewFilterTraveller(List<OrderListItem> orderList) {
        if (filterAdapter == null) {
            filterAdapter = new FilterAdapter(getActivity(), getFragmentManager());
            binding.recyclerView.setAdapter(filterAdapter);
            filterAdapter.setUplist(orderList);
        } else {
            binding.recyclerView.setAdapter(filterAdapter);
            filterAdapter.setUplist(orderList);
        }

        filterAdapter.setOnItemClickListener(new FilterAdapter.OnClick_Event() {
            @Override
            public void setOnItemClickListener(OrderListItem orderListItem, int postion) {
                Bundle bundle = new Bundle();
                bundle.putString("OrderId", orderListItem.getOrderId());
                bundle.putString("Fragment", "FromTraveller");

                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                        new OffersFragment());
            }


        });

    }

    boolean status;

    @Override
    public void onDestroy() {
        super.onDestroy();
        status = false;
    }

    @Override
    public void onStart() {
        requestRead();
        Count = 0;
        getApiCallTraveller();
        super.onStart();
    }

    public void requestRead() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            //  readFile();
        }
    }

    /**
     * onRequestPermissionsResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // readFile();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public boolean onBackPressed() {

        return status;
    }


}
