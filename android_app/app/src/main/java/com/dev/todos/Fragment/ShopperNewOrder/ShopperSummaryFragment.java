package com.dev.todos.Fragment.ShopperNewOrder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Activity.TermsActivity;
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
import com.dev.todos.databinding.FragmentShopperSummaryBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.checkorder;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image1;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image2;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image3;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.image4;

import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.travellerId;
import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.tripId;

public class ShopperSummaryFragment extends Fragment {

    float reward = 0, totalPrice = 0;

    float qty =0;
    float Totalprice = 0;

    Sharedpreferences sharePreferance;
    String mainTotal;
   // String travellerId, tripId, orderId, productId;
    Bundle bundle;
    androidx.appcompat.app.AlertDialog alertDialog;

    FragmentShopperSummaryBinding binding;
    private KProgressHUD hud,hud1;
    Button btnPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopper_summary, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnPost.setEnabled(true);
      /*  SpannableString content = new SpannableString(getString(R.string.terms_cond));
        content.setSpan(new UnderlineSpan(), 0, 20, 0);*/
       // binding.checkbox.setText(content);
        binding.txtProductsInOrderNo.setText(String.valueOf(BottomnavigationActivity.productDetailArrayList.size()));

        setTotal();

        btnPost = view.findViewById(R.id.btnPost);
        try {

            bundle = new Bundle();
            bundle = getArguments();
           /* travellerId = bundle.getString(Keys.traveller_id);
            tripId = bundle.getString(Keys.trip_id);
            orderId = bundle.getString(Keys.order_id);
            productId = bundle.getString(Keys.product_id);*/
            if (travellerId == null || travellerId.equals("")) {
                travellerId = "0";
                tripId = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.txtReward.setText("$" + reward);
        binding.txtTotalPrice.setText("$" + totalPrice);


         float total = reward + totalPrice;
        binding.txtTotal.setText("$" + total);
        mainTotal = String.valueOf(total);
        binding.txtOriginCity.setText(BottomnavigationActivity.productDetailArrayList.get(0).getFromLocation());
        binding.txtDestinationCity.setText(BottomnavigationActivity.productDetailArrayList.get(0).getDeliveryLocation());

        binding.btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Total", binding.txtTotal.getText().toString());
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                        new ShopperProductListFragment());
            }
        });

       /* binding.delivertDetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null, R.id.frameLayoout,
                        new ShopperDeliveryFragment());
            }
        });

        binding.productdetailsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Keys.productName,bundle.getString(Keys.productName));

                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                        new AddShopperFragment());
            }
        });*/

        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkorder = true;
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null, R.id.frameLayoout,
                        new AddShopperFragment());
            }
        });

        binding.productOverViewTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscardDialog();
            }
        });

        binding.termTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TermsActivity.class);
                intent.putExtra("Activity","Terms");
                startActivity(intent);
            }
        });


       binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.checkbox.isChecked()) {

                    String userId =  Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,"");
                    binding.btnPost.setEnabled(false);


                    try {
                        JSONObject jsonObject = new JSONObject();

                            jsonObject.put(Keys.user_id, userId);
                            jsonObject.put(Keys.total_order_price, mainTotal);
                            jsonObject.put(Keys.traveller_id, travellerId);
                            jsonObject.put(Keys.trip_id, tripId);

                            JSONArray jsonArray = new JSONArray();
                            for (int i = 0; i < BottomnavigationActivity.productDetailArrayList.size(); i++) {
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put(Keys.article_name, BottomnavigationActivity.productDetailArrayList.get(i).getAricaleName());
                                jsonObject1.put(Keys.article_comment, BottomnavigationActivity.productDetailArrayList.get(i).getCommentsOfTheArticle());
                                jsonObject1.put(Keys.buy_item_link, BottomnavigationActivity.productDetailArrayList.get(i).getBuyItem());
                                jsonObject1.put(Keys.item_price, BottomnavigationActivity.productDetailArrayList.get(i).getPriceOfItem());
                                jsonObject1.put(Keys.item_quantity, BottomnavigationActivity.productDetailArrayList.get(i).getQunt());
                                jsonObject1.put(Keys.location_from, BottomnavigationActivity.productDetailArrayList.get(i).getFromLocation());
                                jsonObject1.put(Keys.location_to, BottomnavigationActivity.productDetailArrayList.get(i).getDeliveryLocation());

                                jsonObject1.put(Keys.location_from_city, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_from_city());
                                jsonObject1.put(Keys.location_to_city, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_to_city());
                                jsonObject1.put(Keys.location_from_state, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_from_state());
                                jsonObject1.put(Keys.location_to_state, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_to_state());
                                jsonObject1.put(Keys.location_from_country, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_from_country());
                                jsonObject1.put(Keys.location_to_country, BottomnavigationActivity.productDetailArrayList.get(0).getLocation_to_country());

                                jsonObject1.put(Keys.delivery_deadline, BottomnavigationActivity.productDetailArrayList.get(i).getDeadLineDate());
                                jsonObject1.put(Keys.delivery_reward, BottomnavigationActivity.productDetailArrayList.get(i).getReward().replace("$", ""));
                                jsonObject1.put(Keys.created_date, BottomnavigationActivity.productDetailArrayList.get(i).getCurrentDate());
                                jsonObject1.put(Keys.created_time, BottomnavigationActivity.productDetailArrayList.get(i).getCurrentTime());
                                jsonObject1.put(Keys.latfrom, BottomnavigationActivity.productDetailArrayList.get(i).getLocation_latfrom());
                                jsonObject1.put(Keys.longfrom, BottomnavigationActivity.productDetailArrayList.get(i).getLocation_longfrom());
                                jsonObject1.put(Keys.longto, BottomnavigationActivity.productDetailArrayList.get(i).getLocation_longto());
                                jsonObject1.put(Keys.latto, BottomnavigationActivity.productDetailArrayList.get(i).getLocation_latto());

                                JSONArray jsonArray1 = new JSONArray();

                                for (int j = 0; j < BottomnavigationActivity.productDetailArrayList.get(i).getPhotos().size(); j++) {
                                    jsonArray1.put(BottomnavigationActivity.productDetailArrayList.get(i).getPhotos().get(j));
                                    Log.e("JSon", "" + BottomnavigationActivity.productDetailArrayList.get(i).getPhotos().get(j)+"---"+BottomnavigationActivity.productDetailArrayList.get(i).getPhotos().size());

                                }

                                // jsonArray1.put(BottomnavigationActivity.productDetailArrayList.get(i).getPhotos());
                                jsonObject1.put(Keys.product_image_list, jsonArray1);
                                jsonArray.put(jsonObject1);
                            }
                            jsonObject.put(Keys.product_list, jsonArray);
                            JsonObject jsonObject1 = new JsonParser().parse(jsonObject.toString()).getAsJsonObject();


                            addProduct(jsonObject1);

                            Log.e("JSon", "" + jsonObject);


                        //}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), getString(R.string.accept_term_and_condition), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setTotal() {
        reward = 0;
        qty = 0;
        Totalprice = 0;
        totalPrice = 0;
        for (int i = 0; i < BottomnavigationActivity.productDetailArrayList.size(); i++) {
            reward = reward + Float.parseFloat(BottomnavigationActivity.productDetailArrayList.get(i).getReward());
            qty = Float.parseFloat(BottomnavigationActivity.productDetailArrayList.get(i).getQunt());
            Totalprice = qty *Float.parseFloat(BottomnavigationActivity.productDetailArrayList.get(i).getPriceOfItem());
            totalPrice = totalPrice + Totalprice;

            Log.d("TAG", "onViewCreated: "+Totalprice);

        }
    }

    public String getReminingTime() {

        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    }

    public void addProduct(final JsonObject jsonObject) {

        Log.e("Add product", "productAdd");
        hud1=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Log.e("json object",jsonObject+"");
        Call<Responce> responceCall = ApiClient.getService().addOrderWed(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
               // progressDialog.dismiss();
                hud1.dismiss();
                binding.btnPost.setEnabled(true);
                Log.e("json object response",response.body()+"");

                if (response.body().getStatus().equals(Keys.status_succes)) {
                    image1 ="";
                    image2 ="";
                    image3 = "";
                    image4 = "";
                    tripId ="";travellerId ="";
                    hud1.dismiss();
                    setalertDialog(getString(R.string.orderadded));
                 //   Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    hud1.dismiss();
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
               // progressDialog.dismiss();
              //  UseMe.internateErrorDialog(getActivity());
                binding.btnPost.setEnabled(true);
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
                        null, R.id.frameLayoout, new AddShopperFragment());
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

    public void setalertDialog(String msg){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(msg);

        oktext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                try{
                    UseMe.clearAllFragment(getFragmentManager());
                }catch (Exception e){}
                BottomnavigationActivity.productDetailArrayList.clear();

                Log.e("StaticClass.fromTraveller",StaticClass.fromTraveller+"");
                if(StaticClass.fromTraveller){
                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                            null, R.id.frameLayoout, new TravellerFragment());
                }else{
                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                            null, R.id.frameLayoout, new MyOrderFragment());
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