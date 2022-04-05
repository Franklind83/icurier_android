package com.dev.todos.Fragment.ShopperNewOrder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Adapter.ImageAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentShopperOverViewBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image1;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.imageUrl;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.imageUrl2;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.imageUrl3;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.imageUrl4;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.isFromUpdateOrder;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.productId;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.orderId;
import static com.dev.todos.Url.WebService.BASE_URL;
import static com.dev.todos.Url.WebService.social_login;
import static com.dev.todos.Util.UseMe.hideKeyboard;


public class ShopperOverViewFragment extends Fragment {

    FragmentShopperOverViewBinding binding;

    String travellerId, tripId;
   // boolean isFromUpdate;
  //  String orderId, productId, image;
    String imgPath1, imgUri1, imgPath2, imgUri2, imgPath3, imgUri3,
            imgPath4, imgUri4,image,latto,longto,latfrom,longfrom;
    KProgressHUD hud;
    String strDelvDate = "";
    boolean datacheck= false;
    androidx.appcompat.app.AlertDialog alertDialog;

    float reward = 0, totalPrice = 0;

    float qty =0;
    float Totalprice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopper_over_view, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            hideKeyboard(getActivity());
        }catch (Exception e){}

        Bundle bundle = new Bundle();
        bundle = getArguments();

        travellerId = bundle.getString(Keys.traveller_id);
        tripId = bundle.getString(Keys.trip_id);
        imgPath1 = bundle.getString(Keys.imagePath1);
        imgUri1 = bundle.getString(Keys.imageUri1);
        imgPath2 = bundle.getString(Keys.imagePath1);
        imgUri2 = bundle.getString(Keys.imageUri1);
        imgPath3 = bundle.getString(Keys.imagePath1);
        imgUri3 = bundle.getString(Keys.imageUri1);
        imgPath4 = bundle.getString(Keys.imagePath1);
        imgUri4 = bundle.getString(Keys.imageUri1);
        latfrom = bundle.getString(Keys.latfrom);
        longfrom = bundle.getString(Keys.longfrom);
        latto = bundle.getString(Keys.latto);
        longto = bundle.getString(Keys.longto);



        if (UseMe.uriArrayList.size() > 0) {
            try {
                ViewPager viewPager = view.findViewById(R.id.viewPager);
                ViewPagerIndicator pagerIndicator = view.findViewById(R.id.pager_indicator);
                ImageAdapter adapter = new ImageAdapter(getContext());
                viewPager.setAdapter(adapter);
                pagerIndicator.setPager(viewPager);
            }catch (Exception e){}
           // viewPager.setCurrentItem(adapter.getCount() - 1);

        } else {
           /* try {
                ViewPager viewPager = view.findViewById(R.id.viewPager);
                ViewPagerIndicator pagerIndicator = view.findViewById(R.id.pager_indicator);
                ImageAdapter2 adapter = new ImageAdapter2(getContext());
                viewPager.setAdapter(adapter);
                pagerIndicator.setPager(viewPager);
            }catch (Exception e){}*/
           // viewPager.setCurrentItem(adapter.getCount() - 1);
        }

        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = new Bundle();
                  //  bundle.putString(Keys.traveller_id, travellerId);
                  //  bundle.putString(Keys.trip_id, tripId);
                   // bundle.putBoolean(Keys.isFromUpdate, isFromUpdate);
                   // bundle.putString(Keys.order_id, orderId);
                    bundle.putString(Keys.productName,bundle.getString(Keys.productName));
                   // bundle.putString(Keys.product_id, productId);
                    assert getFragmentManager() != null;
                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                            new ShopperSummaryFragment());

                }catch (Exception e){}
            }
        });


        binding.txtProductName.setText(bundle.getString(Keys.productName));
        binding.txtDescription.setText(bundle.getString(Keys.articleComment));
        // binding.txtDescription.setText(Keys.d));
        binding.txtOrigin.setText(bundle.getString(Keys.locationFrom));
        binding.txtDestination.setText(bundle.getString(Keys.locationTo));

        SpannableString content = new SpannableString(bundle.getString(Keys.productLink));
        content.setSpan(new UnderlineSpan(), 0, content.length(),0);
        binding.txtProductLink.setText(content);
        binding.txtQunt.setText(bundle.getString(Keys.qunt));
        Float qunt = Float.parseFloat(bundle.getString(Keys.qunt));
        Float price = Float.parseFloat(bundle.getString(Keys.price));

        Float total = price * qunt;

        binding.txtProductPrice.setText("$" +total);
        binding.txtDelvDate.setText(bundle.getString(Keys.date));
        binding.txtReward.setText("$" + bundle.getString(Keys.reward));
     //   isFromUpdate = bundle.getBoolean(Keys.isFromUpdate);

        image = image1;
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

       /* if (!BottomnavigationActivity.productDetailArrayList.isEmpty()) {

            for(int i=0;i<BottomnavigationActivity.productDetailArrayList.size();i++){
                if(bundle.getString(Keys.productName).equals(BottomnavigationActivity.productDetailArrayList.get(i).getAricaleName())){
                    BottomnavigationActivity.productDetailArrayList.remove(BottomnavigationActivity.productDetailArrayList.size()-1);
                    BottomnavigationActivity.productDetail = new ProductDetail();
                    datacheck = true;
                }
            }


            if(!datacheck){
                BottomnavigationActivity.productDetail = new ProductDetail();
            }
            Log.d("TAG", "onClick: "+BottomnavigationActivity.productDetailArrayList.size());
        }*/


       /* SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String d = simpleDateFormat1.format(new Date());

        BottomnavigationActivity.productDetail.setFromLocation(bundle.getString(Keys.locationFrom));
        BottomnavigationActivity.productDetail.setDeliveryLocation(bundle.getString(Keys.location_to));
        BottomnavigationActivity.productDetail.setPriceOfItem(bundle.getString(Keys.price.replace("$", "")));
        BottomnavigationActivity.productDetail.setAricaleName(bundle.getString(Keys.productName));
        BottomnavigationActivity.productDetail.setCommentsOfTheArticle(bundle.getString(Keys.articleComment));
        BottomnavigationActivity.productDetail.setDeadLineDate(bundle.getString(Keys.delivery_date));
        BottomnavigationActivity.productDetail.setReward(bundle.getString(Keys.reward));
        BottomnavigationActivity.productDetail.setQunt(bundle.getString(Keys.qunt));
        BottomnavigationActivity.productDetail.setCurrentDate(d);
        BottomnavigationActivity.productDetail.setDescription(bundle.getString(Keys.productName));
        BottomnavigationActivity.productDetail.setCurrentTime(getReminingTime());
        BottomnavigationActivity.productDetail.setBuyItem(bundle.getString(Keys.productLink));

        BottomnavigationActivity.productDetail.setProductImage(bundle.getString(Keys.imagePath1));
        BottomnavigationActivity.productDetail.setProductUri(bundle.getString(Keys.imageUri1));
        BottomnavigationActivity.productDetail.setLocation_from_city(bundle.getString(Keys.location_from_city));
        BottomnavigationActivity.productDetail.setLocation_from_state(bundle.getString(Keys.location_from_state));
        BottomnavigationActivity.productDetail.setLocation_from_country(bundle.getString(Keys.location_from_country));
        BottomnavigationActivity.productDetail.setLocation_to_city(bundle.getString(Keys.location_to_city));
        BottomnavigationActivity.productDetail.setLocation_to_state(bundle.getString(Keys.location_to_state));
        BottomnavigationActivity.productDetail.setLocation_to_country(bundle.getString(Keys.location_to_country));

        ArrayList<String> images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        BottomnavigationActivity.productDetail.setPhotos(images);


        BottomnavigationActivity.productDetailArrayList.add(BottomnavigationActivity.productDetail);*/


        binding.txtProductLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://" + binding.txtProductLink.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.btnUpdate.setEnabled(false);

                for (int i = 0; i < BottomnavigationActivity.productDetailArrayList.size(); i++) {
                    reward = 0;
                    qty = 0;
                    reward = reward + Float.parseFloat(BottomnavigationActivity.productDetailArrayList.get(i).getReward());
                    qty = Float.parseFloat(BottomnavigationActivity.productDetailArrayList.get(i).getQunt());
                    Totalprice = 0;
                    Totalprice = qty *Float.parseFloat(BottomnavigationActivity.productDetailArrayList.get(i).getPriceOfItem());
                    totalPrice = 0;
                    totalPrice =Totalprice + reward;

                    Log.d("TAG", "onClick: "+totalPrice);
                }


                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put(Keys.order_id, orderId);
                        jsonObject.put(Keys.product_id, productId);
                    jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));
                    jsonObject.put(Keys.total_order_price, totalPrice);
                    jsonObject.put(Keys.traveller_id, travellerId);
                    jsonObject.put(Keys.trip_id, tripId);

                    jsonObject.put(Keys.article_name, BottomnavigationActivity.productDetailArrayList.get(0).getAricaleName());
                    jsonObject.put(Keys.article_comment, BottomnavigationActivity.productDetailArrayList.get(0).getCommentsOfTheArticle());
                    jsonObject.put(Keys.buy_item_link, BottomnavigationActivity.productDetailArrayList.get(0).getBuyItem());
                    jsonObject.put(Keys.item_price, BottomnavigationActivity.productDetailArrayList.get(0).getPriceOfItem());
                    jsonObject.put(Keys.item_quantity, BottomnavigationActivity.productDetailArrayList.get(0).getQunt());
                    jsonObject.put(Keys.location_from, BottomnavigationActivity.productDetailArrayList.get(0).getFromLocation());
                    jsonObject.put(Keys.location_to, BottomnavigationActivity.productDetailArrayList.get(0).getDeliveryLocation());
                    jsonObject.put(Keys.location_from_city, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_from_city());
                    jsonObject.put(Keys.location_to_city, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_to_city());
                    jsonObject.put(Keys.location_from_state, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_from_state());
                    jsonObject.put(Keys.location_to_state, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_to_state());
                    jsonObject.put(Keys.location_from_country, BottomnavigationActivity.productDetailArrayList.get(0).getDeliveryLocation());
                    jsonObject.put(Keys.location_to_country, BottomnavigationActivity.productDetailArrayList.get(0).getDeliveryLocation());
                    jsonObject.put(Keys.delivery_deadline, BottomnavigationActivity.productDetailArrayList.get(0).getDeadLineDate());
                    jsonObject.put(Keys.delivery_reward, BottomnavigationActivity.productDetailArrayList.get(0).getReward().replace("$", ""));
                    jsonObject.put(Keys.created_date, BottomnavigationActivity.productDetailArrayList.get(0).getCurrentDate());
                    jsonObject.put(Keys.created_time, BottomnavigationActivity.productDetailArrayList.get(0).getCurrentTime());
                        Log.e("errorsa",""+latfrom+" "+longfrom+" "+longto+" "+latto);


                    jsonObject.put(Keys.latfrom, latfrom);
                    jsonObject.put(Keys.longfrom,longfrom);
                    jsonObject.put(Keys.longto, longto);
                    jsonObject.put(Keys.latto, latto);

                        JSONArray jsonArray1 = new JSONArray();
                    for (int j = 0; j < BottomnavigationActivity.productDetailArrayList.get(0).getPhotos().size(); j++) {
                        jsonArray1.put(BottomnavigationActivity.productDetailArrayList.get(0).getPhotos().get(j));
                    }

                    // jsonArray1.put(BottomnavigationActivity.productDetailArrayList.get(i).getPhotos());
                    jsonObject.put(Keys.product_image_list, jsonArray1);
                    JsonObject jsonObject1 = new JsonParser().parse(jsonObject.toString()).getAsJsonObject();

                    updateOrder(jsonObject1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });

        if (!isFromUpdateOrder) {
            UseMe.stringArrayList.clear();

            UseMe.stringArrayList.add(imgPath1);
            UseMe.stringArrayList.add(imgPath2);
            UseMe.stringArrayList.add(imgPath3);
            UseMe.stringArrayList.add(imgPath4);

        }else {
            UseMe.stringArrayList.clear();

            if(!imageUrl.equals("")){
                UseMe.stringArrayList.add(imageUrl);
            }

            if(!imageUrl2.equals("")){
                UseMe.stringArrayList.add(imageUrl2);
            }
            if(!imageUrl3.equals("")){
                UseMe.stringArrayList.add(imageUrl3);
            }
            if(!imageUrl4.equals("")){
                UseMe.stringArrayList.add(imageUrl4);
            }


            /*UseMe.stringArrayList.add(imageUrl2);
            UseMe.stringArrayList.add(imageUrl3);
            UseMe.stringArrayList.add(imageUrl4);*/
            Log.d("TAG", "onViewCreated: "+imageUrl +"-----"+imageUrl2+"---"+imageUrl3+"----"+imageUrl4);
         //   binding.imgProduct.setImageBitmap(decodedByte);
            binding.btnUpdate.setVisibility(View.VISIBLE);
            binding.btnFollowing.setVisibility(View.GONE);
            binding.btnDiscard.setVisibility(View.GONE);
        }



        binding.delivertDetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

       /* binding.productdetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null, R.id.frameLayoout,
                        new AddShopperFragment());
            }
        });*/

        binding.btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscardDialog();

            }
        });


    }

    public void showDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.do_you_want_to_discart));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BottomnavigationActivity.productDetailArrayList = new ArrayList<>();
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                        null, R.id.frameLayoout, new ShopperOverViewFragment());
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


    public void loadUpdateImage(String path, final ImageView imageView) {


        Picasso.get().load(BASE_URL + "media/" + path).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }


    public void updateOrder(final JsonObject jsonObject) {

        Log.e("Add product", "productAdd");

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<Responce> responceCall = ApiClient.getService().updateOrder(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                binding.btnUpdate.setEnabled(true);
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    hud.dismiss();


                    setalertDialog(getString(R.string.productupdated));
                 //   Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } else {
                    hud.dismiss();
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                binding.btnUpdate.setEnabled(true);
              hud.dismiss();
            }
        });
    }

    public String getReminingTime() {
        String delegate = "HH:mm";
        String time = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
        time = time.replace(".", "");
        time = time.toUpperCase();
        return time;
    }


    public void setalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                try {
                    orderId = "";
                    productId = "";
                    Log.e("isFromUpdateOrder",isFromUpdateOrder+"");
                    isFromUpdateOrder = false;
                    UseMe.clearAllFragment(getFragmentManager());
                    Log.e("StaticClass.fromTraveller in overview",StaticClass.fromTraveller+"");

//                    if(StaticClass.fromTraveller){
//                        new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
//                                null, R.id.frameLayoout, new TravellerFragment());
//                    }else{
                        new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                                null, R.id.frameLayoout, new MyOrderFragment());
//                    }
                }catch (Exception e){}

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

}