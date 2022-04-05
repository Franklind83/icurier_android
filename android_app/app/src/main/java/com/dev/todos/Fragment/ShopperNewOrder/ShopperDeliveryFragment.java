package com.dev.todos.Fragment.ShopperNewOrder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Adapter.AutoCompleteAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.ProductDetail;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentShopperDeliveryBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.checkorder;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image1;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image2;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image3;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image4;


import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.isFromUpdateOrder;
import static com.dev.todos.Url.WebService.BASE_URL;

public class ShopperDeliveryFragment extends Fragment {
    TextView txtDate;
    FragmentShopperDeliveryBinding binding;
    String txt = "";
    String deadlineDate;
    String iamgeLink;
    String imageUri, imagePath;
    String city = "", state = "", country = "";
    String city1 = "", state1 = "", country1 = "", latfrom = "", longgfrom = "";
    String city2 = "", state2 = "", country2 = "", latto = "", longgto = "";
    AutoCompleteAdapter adapter;
    String Flag = "";
    PlacesClient placesClient;
    boolean datacheck = false;
    int REQUEST_CODE_AUTOCOMPLETE = 101;
    ArrayList<String> images = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopper_delivery, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  Places.initialize(getActivity(), getString(R.string.google_place_api_key));
        String apiKey = getString(R.string.google_place_api_key);
        if (apiKey.isEmpty()) {
            //responseView.setText("error");
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apiKey);
        }

        placesClient = Places.createClient(getContext());


        if (!BottomnavigationActivity.productDetailArrayList.isEmpty()) {

            if (checkorder) {
                binding.txtArticleCame.setEnabled(false);
                binding.txtArticleDeliver.setEnabled(false);
                binding.selectDateLL.setEnabled(false);
                binding.edtReward.setText(BottomnavigationActivity.productDetailArrayList.get(0).getReward());
                binding.txtArticleDeliver.setText(BottomnavigationActivity.productDetailArrayList.get(0).getDeliveryLocation());
                binding.txtArticleCame.setText(BottomnavigationActivity.productDetailArrayList.get(0).getFromLocation());
            } else {
                binding.edtReward.setText(BottomnavigationActivity.productDetailArrayList.get(0).getReward());
                binding.txtArticleDeliver.setText(BottomnavigationActivity.productDetailArrayList.get(0).getDeliveryLocation());
                binding.txtArticleCame.setText(BottomnavigationActivity.productDetailArrayList.get(0).getFromLocation());

            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = simpleDateFormat.parse(BottomnavigationActivity.productDetailArrayList.get(0).getDeadLineDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = simpleDateFormat.format(d);

            binding.txtDate.setText(date);

        } else {
            try {

                Log.e("in else","");
                Bundle b = new Bundle();
                b = getArguments();
                binding.txtArticleCame.setText(b.getString(Keys.location_from));
                binding.txtArticleDeliver.setText(b.getString(Keys.location_to));
                binding.edtReward.setText(b.getString(Keys.reward));
                binding.txtDate.setText(b.getString(Keys.date));
                latfrom=b.getString(Keys.latfrom);
                longgfrom=b.getString(Keys.longfrom);
                latto=b.getString(Keys.latto);
                longgto=b.getString(Keys.longto);

                //   Log.d("TAG", "onViewCreated: "+isFromUpdate);
             /*  imagePath=b.getString(Keys.imagePath);
               imageUri=b.getString(Keys.imageUri);*/


                if (isFromUpdateOrder) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = simpleDateFormat.parse(b.getString(Keys.date));
                    simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String date = simpleDateFormat.format(d);

                    binding.txtDate.setText(date);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


// Create a new Places client instance.

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        txtDate = view.findViewById(R.id.txtDate);

        view.findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.productdetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {


                    try {
                        Bundle b = new Bundle();
                        b = getArguments();
                        Bundle b1 = new Bundle();


                        b1.putString(Keys.locationFrom, binding.txtArticleCame.getText().toString());
                        b1.putString(Keys.locationTo, binding.txtArticleDeliver.getText().toString());
                        b1.putString(Keys.date, binding.txtDate.getText().toString());
                        b1.putString(Keys.reward, binding.edtReward.getText().toString());
                        b1.putString(Keys.articleComment, b.getString(Keys.articleComment));
                        b1.putString(Keys.productName, b.getString(Keys.productName));
                        b1.putString(Keys.productLink, b.getString(Keys.productLink));
                        b1.putString(Keys.price, b.getString(Keys.price));
                        b1.putString(Keys.qunt, b.getString(Keys.qunt));
                        b1.putString(Keys.image1, b.getString(Keys.image1));
                        b1.putString(Keys.image, b.getString(Keys.image));
                        b1.putString(Keys.imageUri1, b.getString(Keys.imageUri1));
                        b1.putString(Keys.imagePath1, b.getString(Keys.imagePath1));
                        b1.putString(Keys.delivery_date, deadlineDate);
                        b1.putString(Keys.location_from_city, city1);
                        b1.putString(Keys.location_from_state, state1);
                        b1.putString(Keys.location_from_country, country1);
                        b1.putString(Keys.location_to_city, city2);
                        b1.putString(Keys.location_to_state, state2);
                        b1.putString(Keys.location_to_country, country2);
                        b1.putString(Keys.latfrom, latfrom);
                        b1.putString(Keys.latto, latto);
                        b1.putString(Keys.longfrom, longgfrom);
                        b1.putString(Keys.longto, longgto);

                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        String d = simpleDateFormat1.format(new Date());
                        try {
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                            Date date2 = simpleDateFormat2.parse(binding.txtDate.getText().toString());
                            simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                            deadlineDate = simpleDateFormat2.format(date2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (!BottomnavigationActivity.productDetailArrayList.isEmpty()) {

                            for (int i = 0; i < BottomnavigationActivity.productDetailArrayList.size(); i++) {
                                if (b.getString(Keys.productName).equals(BottomnavigationActivity.productDetailArrayList.get(i).getAricaleName())) {
                                    BottomnavigationActivity.productDetailArrayList.remove(BottomnavigationActivity.productDetailArrayList.size() - 1);
                                    BottomnavigationActivity.productDetail = new ProductDetail();
                                    datacheck = true;
                                }
                            }


                            if (!datacheck) {
                                BottomnavigationActivity.productDetail = new ProductDetail();
                            }
                            Log.d("TAG", "onClick: " + BottomnavigationActivity.productDetailArrayList.size());
                        }


                        BottomnavigationActivity.productDetail.setFromLocation(binding.txtArticleCame.getText().toString());
                        BottomnavigationActivity.productDetail.setDeliveryLocation(binding.txtArticleDeliver.getText().toString());
                        BottomnavigationActivity.productDetail.setDeadLineDate(binding.txtDate.getText().toString());
                        BottomnavigationActivity.productDetail.setPriceOfItem(b.getString(Keys.price).replace("$", ""));
                        BottomnavigationActivity.productDetail.setAricaleName(b.getString(Keys.productName));
                        BottomnavigationActivity.productDetail.setCommentsOfTheArticle(b.getString(Keys.articleComment));
                        BottomnavigationActivity.productDetail.setDeadLineDate(deadlineDate);
                        BottomnavigationActivity.productDetail.setDeliveryLocation(binding.txtArticleDeliver.getText().toString());
                        BottomnavigationActivity.productDetail.setReward(binding.edtReward.getText().toString());
                        BottomnavigationActivity.productDetail.setQunt(b.getString(Keys.qunt));
                        BottomnavigationActivity.productDetail.setCurrentDate(d);
                        BottomnavigationActivity.productDetail.setDescription(b.getString(Keys.article_name));
                        BottomnavigationActivity.productDetail.setCurrentTime(getReminingTime());
                        BottomnavigationActivity.productDetail.setBuyItem(b.getString(Keys.productLink));

                        BottomnavigationActivity.productDetail.setProductImage(b.getString(Keys.imagePath1));
                        BottomnavigationActivity.productDetail.setProductUri(b.getString(Keys.imageUri1));
                        BottomnavigationActivity.productDetail.setLocation_from_city(city1);
                        BottomnavigationActivity.productDetail.setLocation_from_state(state1);
                        BottomnavigationActivity.productDetail.setLocation_from_country(country1);
                        BottomnavigationActivity.productDetail.setLocation_to_city(city2);
                        BottomnavigationActivity.productDetail.setLocation_to_state(state2);
                        BottomnavigationActivity.productDetail.setLocation_to_country(country2);
                        BottomnavigationActivity.productDetail.setLocation_longfrom(longgfrom);
                        BottomnavigationActivity.productDetail.setLocation_latfrom(latfrom);
                        BottomnavigationActivity.productDetail.setLocation_latto(latto);
                        BottomnavigationActivity.productDetail.setLocation_longto(longgto);




                        b1.putString(Keys.location_from_city, city1);
                        b1.putString(Keys.location_from_state, state1);
                        b1.putString(Keys.location_from_country, country1);
                        b1.putString(Keys.location_to_city, city2);
                        b1.putString(Keys.location_to_state, state2);
                        b1.putString(Keys.location_to_country, country2);
                        b1.putString(Keys.latfrom, latfrom);
                        b1.putString(Keys.latto, latto);
                        b1.putString(Keys.longfrom, longgfrom);
                        b1.putString(Keys.longto, longgto);

                        images.add(image1);
                        images.add(image2);
                        images.add(image3);
                        images.add(image4);
                        image1 = "";
                        image2 = "";
                        image3 = "";
                        image4 = "";
                        BottomnavigationActivity.productDetail.setPhotos(images);
                        BottomnavigationActivity.productDetailArrayList.add(BottomnavigationActivity.productDetail);

                        assert getFragmentManager() != null;
                        new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), b1, R.id.frameLayoout, new ShopperOverViewFragment());

                    } catch (Exception e) {
                    }
                }
            }
        });
        view.findViewById(R.id.selectDateLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), txtDate);
            }
        });

        binding.txtArticleCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag = "come";
                autoCompletePlace();
            }
        });

        binding.txtArticleDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag = "dest";
                autoCompletePlace();
            }
        });
    }

    public void autoCompletePlace() {
        // Initialize Places.
        Places.initialize(getContext(), getString(R.string.google_place_api_key));

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                // Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                if (Flag.equals("come")) {
                    binding.txtArticleCame.setText(place.getAddress());
                    getAddress(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude);
                    city1 = city;
                    latfrom = place.getLatLng().latitude + "";
                    longgfrom = place.getLatLng().longitude + "";
                    state1 = state;
                    country1 = country;
                    binding.txtArticleCame.setError(null);
                } else if (Flag.equals("dest")) {
                    binding.txtArticleDeliver.setText(place.getAddress());
                    getAddress(Objects.requireNonNull(place.getLatLng()).latitude, place.getLatLng().longitude);
                    binding.txtArticleDeliver.setError(null);
                    latto = place.getLatLng().latitude + "";
                    longgto = place.getLatLng().longitude + "";
                    city2 = city;
                    state2 = state;
                    country2 = country;
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                // Log.i(TAG, status.getStatusMessage());
            }
        }

    }

    public void getAddress(double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                if (addresses.get(0).getLocality() != null) {
                    city = addresses.get(0).getLocality();
                } else {
                    if (addresses.get(0).getSubAdminArea() != null) {
                        city = addresses.get(0).getSubAdminArea();
                    } else {
                        city = "0";
                    }
                }

                if (addresses.get(0).getAdminArea() != null) {
                    state = addresses.get(0).getAdminArea();
                } else {
                    if (addresses.get(0).getAdminArea() != null) {
                        state = addresses.get(0).getAdminArea();
                    } else {
                        state = "0";
                    }
                }
                country = addresses.get(0).getCountryName();

            }

        } catch (Exception e) {
        }

    }


    public boolean isValid() {
        boolean valid = true;
        if (binding.txtArticleCame.getText().toString().length() == 0) {
            binding.txtArticleCame.setError(getActivity().getResources().getString(R.string.select_country));
            valid = false;
        } else if (binding.txtArticleDeliver.getText().toString().length() == 0) {
            binding.txtArticleDeliver.setError(getActivity().getResources().getString(R.string.select_country));
            valid = false;
        } else if (binding.txtDate.getText().toString().length() == 0) {
            binding.txtDate.setError(getActivity().getResources().getString(R.string.select_date));
            valid = false;
        } else if (binding.edtReward.getText().toString().length() == 0) {
            binding.edtReward.setError(getActivity().getResources().getString(R.string.enter_reward));
            valid = false;
        }
        return valid;

    }

    public String getReminingTime() {
        String delegate = "HH:mm";
        String time = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
        time = time.replace(".", "");
        time = time.toUpperCase();
        return time;
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
            Bitmap bm = BitmapFactory.decodeStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            encImage = "";
        }
        //Base64.de
        return encImage;

    }

    private String getByteArrayFromImageURL(String url) {

        try {
            URL imageUrl = new URL(BASE_URL + "media/" + url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return android.util.Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
        return null;
    }

}