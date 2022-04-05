package com.dev.todos.Fragment.Message;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Adapter.ChatAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.OrderSummaryFragment;
import com.dev.todos.Model.Message.ChatDataItem;
import com.dev.todos.Model.Message.MessageResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Activity.BottomnavigationActivity.llMain;

public class MessageFragment extends Fragment {

    RecyclerView recyclerView;
    String profilImage = "", name = "", userId = "";
    TextView txtName;
    TextView tvProductDetails;
    ImageView imgProfile;
    String orderId = "", user2Id = "";
    ProgressDialog progressDialog;
    EditText edtMessage;
    String date = "";
    KProgressHUD hud;
    String user1_id = "";
    String totalPrice = "";
    ChatAdapter mAdapter;
    List<ChatDataItem> chatDataArrayList = new ArrayList<>();
    //  LinearLayout llMain;
    Timer timer;
    MyTimerTask myTimerTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     //   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            UseMe.isBottomNavVisible(false);
        }catch (Exception e){}

        recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);


        edtMessage = view.findViewById(R.id.edtMessage);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(new Date());
        ///   llMain = view.findViewById(R.id.llMain);
        tvProductDetails = view.findViewById(R.id.tvProductDetails);
        tvProductDetails.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        try {

            Bundle bundle = new Bundle();
            bundle = getArguments();
            userId = Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,"");


            profilImage = bundle.getString("CustomerProfile");
            name = bundle.getString(Keys.name);
            txtName = view.findViewById(R.id.txtName);
            imgProfile = view.findViewById(R.id.imgProfile);
            txtName.setText(name);
            orderId = bundle.getString(Keys.order_id);
            user2Id = bundle.getString(Keys.user2_id);
            user1_id = bundle.getString(Keys.user1_id);


            if (user2Id.trim().equals(userId.trim())) {
                user2Id = user1_id;
            } else {
                user1_id = userId;
            }
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.order_id, orderId);
            jsonObject.put(Keys.user1_id, userId);
            jsonObject.put(Keys.user2_id, user2Id);
            getChatData(UseMe.getJsonObject(jsonObject));


            try {


                totalPrice = bundle.getString(Keys.total_order_price);
                //  userId1=bundle.getString(Keys.user1_id);

            } catch (Exception e) {

            }

            UseMe.setImage(getActivity(), profilImage, imgProfile);

            if (StaticClass.fromUser == true) {

            } else {
                final JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put(Keys.order_id, orderId);
                jsonObject2.put(Keys.user1_id, userId);
                jsonObject2.put(Keys.user2_id, bundle.getString(Keys.user2_id));
                getChatData(UseMe.getJsonObject(jsonObject2));

                tvProductDetails.setVisibility(View.VISIBLE);

                tvProductDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //jump to product summary

                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.order_id, orderId);
                        bundle.putString(Keys.name, name);
                        bundle.putString(Keys.profileImage, profilImage);
                        bundle.putString(Keys.user2_id, user2Id);
                        bundle.putString(Keys.total_order_price, totalPrice);

                        StaticClass.fromChat = true;
                        new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                                new OrderSummaryMessageFragment());


                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.imgSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtMessage.getText().toString().isEmpty()) {
                    edtMessage.setError("Write Something..");
                } else {

                    UseMe.hideKeyboard(getActivity());
                    try {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put(Keys.order_id, orderId);
                        jsonObject1.put(Keys.user1_id, userId);
                        jsonObject1.put(Keys.user2_id, user2Id);
                        jsonObject1.put(Keys.message, edtMessage.getText().toString());
                        jsonObject1.put(Keys.message_from, userId);
                        jsonObject1.put(Keys.date, date);
                        jsonObject1.put(Keys.time, getReminingTime());
                        addChat(UseMe.getJsonObject(jsonObject1));
                        edtMessage.setText("");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });



        timer = new Timer();
        myTimerTask = new MyTimerTask();

        timer.schedule(myTimerTask, 10000, 10000);


    }

    public void getChatData(JsonObject jsonObject) {


        //progressDialog.show();
        Call<MessageResponse> chatResponceCall = ApiClient.getService().getMessage(jsonObject);
        chatResponceCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body().getStatus().equals(Keys.status_succes)) {

                    chatDataArrayList = response.body().getChatData();

                    //dd/MM/yyyy '@'

                    try {
                        Collections.reverse(chatDataArrayList);

                    } catch (Exception e) {

                    }
                    try {
                            mAdapter = new ChatAdapter(getActivity(), chatDataArrayList, profilImage);
                            recyclerView.setAdapter(mAdapter);
                           // recyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
                    } catch (Exception e) {

                    }
                } else {
                   // Toast.makeText(getActivity(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
            }
        });

    }

    public void addChat(JsonObject jsonObject) {

        Call<Responce> responceCall = ApiClient.getService().addChat(jsonObject);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    if (response.body().getStatus().equals(Keys.status_succes)) {
                        try {


                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(Keys.order_id, orderId);
                            jsonObject.put(Keys.user1_id, userId);
                            jsonObject.put(Keys.user2_id, user2Id);
                            getChatData(UseMe.getJsonObject(jsonObject));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                    }


                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {

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

    @Override
    public void onDestroy() {
        super.onDestroy();

        StaticClass.fromUser = false;

        if (timer != null) {
            timer.cancel();
        }


    }


    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Keys.order_id, orderId);

                jsonObject.put(Keys.user1_id, userId);
                jsonObject.put(Keys.user2_id, user2Id);
                getChatData(UseMe.getJsonObject(jsonObject));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}