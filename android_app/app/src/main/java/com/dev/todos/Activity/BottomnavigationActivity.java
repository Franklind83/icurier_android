package com.dev.todos.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.BuyerOffer.BuyerOrderDeliverFragment;
import com.dev.todos.Fragment.BuyerOffer.BuyerPurchaseMadeFragment;
import com.dev.todos.Fragment.BuyerOffer.BuyerSatisfiedBuyerFragment;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Message.MessageFragment;
import com.dev.todos.Fragment.Message.MessageListFragment;
import com.dev.todos.Fragment.More.ConfigurationFragment;
import com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment;
import com.dev.todos.Fragment.TripAndOffers.AddTripFragment;
import com.dev.todos.Fragment.TripAndOffers.MakeOfferFragment;
import com.dev.todos.Fragment.TripAndOffers.MyOfferFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.Model.DeletedUser.DeletedUserRequest;
import com.dev.todos.Model.DeletedUser.DeletedUserResponse;
import com.dev.todos.Model.ProductDetail;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;


import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.ActivityBottomnavigationBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.FirebaseNotification.MyFirebaseService.notificationData;
import static com.dev.todos.Fragment.Home.TravellerFragment.TravellerId;
import static com.dev.todos.Fragment.Home.TravellerFragment.Tripid;

public class BottomnavigationActivity extends BaseActivity {

    public static LinearLayout llMain;

    private ActivityBottomnavigationBinding binding;
    private static long back_pressed;
    boolean doubleBackToExitPressedOnce = false;

    public static ArrayList<ProductDetail> productDetailArrayList;
    public  static ProductDetail productDetail;
    AlertDialog alertDialog,alertDialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottomnavigation);

        llMain = findViewById(R.id.llMain);

        getApiCall();

        productDetailArrayList=new ArrayList<>();
        productDetail=new ProductDetail();

        productDetailArrayList.clear();

        Intent i = getIntent();
        String Activity = i.getStringExtra("Activity");


        if(Activity.equals("TravellerMakeOffer")){
            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());

            String Userid2= i.getStringExtra(Keys.user2_id);
            String orderId= i.getStringExtra(Keys.order_id);
            String date= i.getStringExtra(Keys.date);

            if(Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.USERID,"").equals(Userid2)){
                setalertDialog(getString(R.string.youcannot));
            }else{
                Bundle bundle = new Bundle();
                bundle.putString(Keys.date, date);
                bundle.putString(Keys.order_id, orderId);
                bundle.putString(Keys.user2_id, Userid2);
                new ChangeFragment(getApplicationContext()).changeFragmentWithBackStack(getSupportFragmentManager(),
                        bundle, R.id.frameLayoout,
                        new MakeOfferFragment());
            }

        }else if(Activity.equals("TravellerFragment")){
            binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
            binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


            binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
            binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
            binding.imgOffer.setImageResource(R.drawable.offer_gray);
            binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
            binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
            binding.imgMore.setImageResource(R.drawable.ic_more_black);

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new MyOrderFragment());
        }
        else if (Activity.equals("AddTraveller")){

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());

            StaticClass.fromTraveller = true;
            new ChangeFragment(getApplicationContext()).changeFragmentWithBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new AddTripFragment());
        }
        else if(Activity.equals("ExistingOrder")){

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());

            Tripid = i.getStringExtra(Keys.trip_id);
            TravellerId =i.getStringExtra(Keys.traveller_id);

            if(Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.USERID,"").equals(i.getStringExtra(Keys.traveller_id))){
                setalertDialog(getString(R.string.hiretravellercannot));
            }else {

                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.offer_gray);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);

                new ChangeFragment(getApplicationContext()).changeFragmentWithoutBackStack(getSupportFragmentManager(), null, R.id.frameLayoout,
                        new MyOrderFragment());
            }

        }
        else if(Activity.equals("NewOrder")){

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());



            Bundle bundle = new Bundle();
            bundle.putString(Keys.trip_id,i.getStringExtra(Keys.trip_id));
            bundle.putString(Keys.traveller_id,i.getStringExtra(Keys.traveller_id));

            if(Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.USERID,"").equals(i.getStringExtra(Keys.traveller_id))){
                setalertDialog(getString(R.string.hiretravellercannot));
            }else {
                StaticClass.fromTraveller = true;
                new ChangeFragment(getApplicationContext()).changeFragmentWithBackStack(getSupportFragmentManager(), bundle, R.id.frameLayoout,
                        new AddShopperFragment());
            }

        }
        else if(Activity.equals("AddOrder")){

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());


            Bundle bundle = new Bundle();
            bundle.putString(Keys.trip_id,"");
            bundle.putString(Keys.traveller_id,"");

            StaticClass.fromTraveller = true;
            new ChangeFragment(getApplicationContext()).changeFragmentWithBackStack(getSupportFragmentManager(), bundle, R.id.frameLayoout,
                    new AddShopperFragment());
        }
        else if(Activity.equalsIgnoreCase("Notification")){
            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());

                Log.d("TAG", "onCreate: "+notificationData.get("title"));

            if(notificationData.get("status").equalsIgnoreCase("chat")){
                Bundle bundle = new Bundle();
                bundle.putString(Keys.user2_id,notificationData.get("user2_id"));
                bundle.putString(Keys.user1_id, notificationData.get("user1_id"));
                bundle.putString(Keys.order_id, notificationData.get("order_id"));
                bundle.putString(Keys.name, notificationData.get("user1_name"));
                bundle.putString(Keys.profileImage, notificationData.get("user2_profile"));
                bundle.putString("CustomerProfile", notificationData.get("user1_profile"));

                StaticClass.fromChat = true;
                StaticClass.fromUser = false;

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        bundle, R.id.frameLayoout, new MessageFragment());

            }else if(notificationData.get("status").equalsIgnoreCase("hired")){
                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));

                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.white));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));

                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.ic_offer_white);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_black);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        null, R.id.frameLayoout, new MyOfferFragment());

            }else if(notificationData.get("status").equalsIgnoreCase("offer_added")){
                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.offer_gray);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        null, R.id.frameLayoout, new MyOrderFragment());
            }else if(notificationData.get("status").equalsIgnoreCase("accepted_offer")){
                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.offer_gray);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        null, R.id.frameLayoout, new MyOrderFragment());
            }else if(notificationData.get("status").equalsIgnoreCase("purchase_info")){
                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.offer_gray);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);

                Sharedpreferences.saveToPreferences(getApplicationContext(), Keys.travel_date,notificationData.get("created_date"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.delivery_from,notificationData.get("location_from"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.delivered_to,notificationData.get("location_to"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.order_id,notificationData.get("order_id"));

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        null, R.id.frameLayoout, new MyOrderFragment());
            }else if(notificationData.get("status").equalsIgnoreCase("delivery_info")){
                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.offer_gray);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);

                Sharedpreferences.saveToPreferences(getApplicationContext(), Keys.travel_date,notificationData.get("created_date"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.delivery_from,notificationData.get("location_from"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.delivered_to,notificationData.get("location_to"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.order_id,notificationData.get("order_id"));

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        null, R.id.frameLayoout, new MyOrderFragment());
            }else if(notificationData.get("status").equalsIgnoreCase("satisfied_order")){
                binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
                binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
                binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


                binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
                binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
                binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
                binding.imgOffer.setImageResource(R.drawable.offer_gray);
                binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
                binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
                binding.imgMore.setImageResource(R.drawable.ic_more_black);


                Sharedpreferences.saveToPreferences(getApplicationContext(), Keys.travel_date,notificationData.get("created_date"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.delivery_from,notificationData.get("location_from"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.delivered_to,notificationData.get("location_to"));
                Sharedpreferences.saveToPreferences(getApplicationContext(),Keys.order_id,notificationData.get("order_id"));

                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithBackStack(getSupportFragmentManager(),
                        null, R.id.frameLayoout, new MyOrderFragment());
            }else if(notificationData.get("status").equalsIgnoreCase("logout_user")){
                new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                        R.id.frameLayoout,new TravellerFragment());

                setalert("Your account has been deleted by admin. For details please contact admin");
            }


        }
        else{
            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());
        }


        onClickListener();
        setStatusBarGradiant(BottomnavigationActivity.this);
    }

    private void getApiCall() {
        DeletedUserRequest deletedUserRequest = new DeletedUserRequest();
        deletedUserRequest.setUserId(Sharedpreferences.readFromPreferences(getApplicationContext(),AppConstant.USERID,""));

        Call<DeletedUserResponse> deletedUserResponseCall = ApiClient.getService().deleteduser(deletedUserRequest);
        deletedUserResponseCall.enqueue(new Callback<DeletedUserResponse>() {
            @Override
            public void onResponse(Call<DeletedUserResponse> call, Response<DeletedUserResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        if(response.body().getIsUserDeleted().equals("Yes")){
                            setalert("Your account has been deleted by admin. For details please contact admin");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DeletedUserResponse> call, Throwable t) {

            }
        });
    }


    private void onClickListener() {

        binding.llMore.setOnClickListener(v -> {

            binding.llMore.setBackgroundColor(getResources().getColor(R.color.btn_color));
            binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));

            binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyOrder.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMore.setTextColor(getResources().getColor(R.color.white));
            binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));

            binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);

            binding.imgOffer.setImageResource(R.drawable.offer_gray);
            binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
            binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_black);
            binding.imgMore.setImageResource(R.drawable.ic_more_white);

            TravellerId ="";
            UseMe.clearAllFragment(getSupportFragmentManager());

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),
                    null,R.id.frameLayoout,new ConfigurationFragment());
        });


        binding.llmessage.setOnClickListener(v -> {
            binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llmessage.setBackgroundColor(getResources().getColor(R.color.btn_color));

            binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyOrder.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyTrip.setTextColor(getResources().getColor(R.color.white));

            binding.imgMyTrip.setImageResource(R.drawable.ic_msg_white);

            binding.imgOffer.setImageResource(R.drawable.offer_gray);
            binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
            binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_black);
            binding.imgMore.setImageResource(R.drawable.ic_more_black);

            TravellerId ="";
            UseMe.clearAllFragment(getSupportFragmentManager());

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new MessageListFragment());

        });

        binding.llHome.setOnClickListener(v -> {
            binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llHome.setBackgroundColor(getResources().getColor(R.color.btn_color));
            binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));

            binding.txtHome.setTextColor(getResources().getColor(R.color.white));
            binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyOrder.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));

            binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
            binding.imgOffer.setImageResource(R.drawable.offer_gray);
            binding.imgHome.setImageResource(R.drawable.ic_home_black_24dp);
            binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_black);
            binding.imgMore.setImageResource(R.drawable.ic_more_black);

            TravellerId ="";
            UseMe.clearAllFragment(getSupportFragmentManager());

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new TravellerFragment());
        });

        binding.llOffer.setOnClickListener(v -> {
            binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llOffer.setBackgroundColor(getResources().getColor(R.color.btn_color));
            binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));

            binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtOffer.setTextColor(getResources().getColor(R.color.white));
            binding.txtMyOrder.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));

            binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
            binding.imgOffer.setImageResource(R.drawable.ic_offer_white);
            binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
            binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_black);
            binding.imgMore.setImageResource(R.drawable.ic_more_black);

            UseMe.clearAllFragment(getSupportFragmentManager());
            TravellerId ="";
            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new MyOfferFragment());
        });

        binding.llMyOrder.setOnClickListener(v -> {
            binding.llMore.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llHome.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llMyOrder.setBackgroundColor(getResources().getColor(R.color.btn_color));
            binding.llOffer.setBackgroundColor(getResources().getColor(R.color.white));
            binding.llmessage.setBackgroundColor(getResources().getColor(R.color.white));


            binding.txtHome.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtOffer.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyOrder.setTextColor(getResources().getColor(R.color.white));
            binding.txtMore.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.txtMyTrip.setTextColor(getResources().getColor(R.color.lite_white2));
            binding.imgMyTrip.setImageResource(R.drawable.ic_msg_gray);
            binding.imgOffer.setImageResource(R.drawable.offer_gray);
            binding.imgHome.setImageResource(R.drawable.ic_home_black_new);
            binding.imgMyOrder.setImageResource(R.drawable.ic_my_order_white);
            binding.imgMore.setImageResource(R.drawable.ic_more_black);

            TravellerId ="";
            UseMe.clearAllFragment(getSupportFragmentManager());

            new ChangeFragment(BottomnavigationActivity.this).changeFragmentWithoutBackStack(getSupportFragmentManager(),null,
                    R.id.frameLayoout,new MyOrderFragment());
        });
    }

    @Override
    public void onBackPressed() {

        List<androidx.fragment.app.Fragment> fragmentList = getSupportFragmentManager().getFragments();

        int i = getSupportFragmentManager().getBackStackEntryCount();

        if (i == 0) {

            if (doubleBackToExitPressedOnce) {
                finish();
                //super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,getString(R.string.back), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        } else {
            boolean handled = false;
            for (Object f : fragmentList) {
                if (f instanceof TravellerFragment) {
                    handled = ((TravellerFragment) f).onBackPressed();

                    if (handled) {


                        if (doubleBackToExitPressedOnce) {
                            finish();
                            //super.onBackPressed();
                            return;
                        }

                        this.doubleBackToExitPressedOnce = true;
                        Toast.makeText(this,getString(R.string.back), Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);


                        break;
                    } else {

                        super.onBackPressed();

                    }
                } else if (f instanceof TravellerFragment) {
                    handled = ((TravellerFragment) f).onBackPressed();

                    if (handled) {


                        if (doubleBackToExitPressedOnce) {
                            finish();
                            //super.onBackPressed();
                            return;
                        }

                        this.doubleBackToExitPressedOnce = true;
                        Toast.makeText(this, getString(R.string.back), Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);


                        break;
                    } else {

                        super.onBackPressed();

                    }
                }
            }

            if (!handled) {
                super.onBackPressed();
            }
        }
    }
    public void setalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(BottomnavigationActivity.this);
        LayoutInflater layoutInflater= LayoutInflater.from(BottomnavigationActivity.this);
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
    public void setalert(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(BottomnavigationActivity.this);
        LayoutInflater layoutInflater= LayoutInflater.from(BottomnavigationActivity.this);
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
                Sharedpreferences.clearData(getApplicationContext());
                disconnectFromFacebook();
                Log.e("lang2",Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.APPLANGUAGE,""));
                Sharedpreferences.saveToPreferences(getApplicationContext(),AppConstant.APPLANGUAGE,"es");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.setCancelable(false);
        alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog1.show();
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


}
