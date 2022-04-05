package com.dev.todos.Fragment.MyOrder;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Adapter.MyOrderListAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment;
import com.dev.todos.Model.GetOrderList.OrderListItem;
import com.dev.todos.Model.GetOrderList.OrderListResponse;
import com.dev.todos.Model.GetOrderList.OrderOfferListItem;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Fragment.Home.TravellerFragment.TravellerId;
import static com.dev.todos.Fragment.Home.TravellerFragment.Tripid;


public class MyOrderFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    RecyclerView recyclerView;
    LinearLayout viewActive, viewTransit, viewReceived, viewCancelled;
    String userId;
    AlertDialog alertDialog,alertDialog1;
    TextView textView;
    private KProgressHUD hud;
    List<OrderOfferListItem> orderOfferList;
    List<OrderListItem> orderList;
    Boolean ischeck = false;
    private Snackbar snackBar;
    TextView txtActive, txtInTransit, txtReceived, txtCancelled;
    MyOrderListAdapter myOrderListAdapter;
    int count = 0;

    String type = "active";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // progressDialog = UseMe.showProgressDialog(getActivity());
        BottomnavigationActivity.productDetailArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        userId = Sharedpreferences.readFromPreferences(getContext(), AppConstant.USERID,"");
        txtActive = view.findViewById(R.id.txtActivr);
        txtInTransit = view.findViewById(R.id.txtInTransit);
        txtReceived = view.findViewById(R.id.txtReceived);
        txtCancelled = view.findViewById(R.id.txtCancelled);


        try {

            jsonObject.put(Keys.user_id, userId);
            jsonObject.put(Keys.post_string, "my");
            jsonObject.put(Keys.order_status, type);
            getMyOrder(UseMe.getJsonObject(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewCancelled = view.findViewById(R.id.viewCancelled);
        viewReceived = view.findViewById(R.id.viewRecieved);
        viewTransit = view.findViewById(R.id.viewTransit);
        viewActive = view.findViewById(R.id.viewActive);
        recyclerView = view.findViewById(R.id.rv);
        textView = view.findViewById(R.id.txtNoOrder);
        textView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.llActive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = Keys.active;
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Keys.user_id, userId);
                    jsonObject.put(Keys.post_string, "my");
                    jsonObject.put(Keys.order_status, type);
                    getMyOrder(UseMe.getJsonObject(jsonObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Keys.isFromUpdate,"false");
                bundle.putString(Keys.qunt,"1");
                StaticClass.fromTraveller = false;
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                        bundle, R.id.frameLayoout, new AddShopperFragment());

            }
        });


        view.findViewById(R.id.llTransit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "accepted";
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Keys.user_id, userId);
                    jsonObject.put(Keys.post_string, "my");
                    jsonObject.put(Keys.order_status, type);
                    getMyOrder(UseMe.getJsonObject(jsonObject));
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });


        view.findViewById(R.id.llReceived).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "delivered";
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Keys.user_id, userId);
                    jsonObject.put(Keys.post_string, "my");
                    jsonObject.put(Keys.order_status, type);
                    getMyOrder(UseMe.getJsonObject(jsonObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        view.findViewById(R.id.llCancelled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "inactive";
                viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Keys.user_id, userId);
                    jsonObject.put(Keys.post_string, "my");
                    jsonObject.put(Keys.order_status, type);
                    getMyOrder(UseMe.getJsonObject(jsonObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {


    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = Keys.active;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = Keys.active;
    }

    @Override
    public void onStart() {
        super.onStart();
        type = Keys.active;


    }*/

  /*  @Override
    public void onResume() {
        super.onResume();
        type = Keys.active;
    }
*/

    public void getMyOrder(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Call<OrderListResponse> orderListResponceCall = ApiClient.getService().getOrderList(jsonObject);
        orderListResponceCall.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
            hud.dismiss();

                if (response.body().getStatus().equals(Keys.status_succes)) {

                    try {
                        Log.d("TAG", "onResponse: "+response.body().getAcceptedOrderCount());
                        orderList =  response.body().getOrderList();


                        txtActive.setText(response.body().getPendingOrderCount() + "\n" + getString(R.string.active));
                        txtCancelled.setText(response.body().getInactiveOrderCount() + "\n" + getString(R.string.cancelled));
                        txtInTransit.setText(response.body().getAcceptedOrderCount() + "\n" + getString(R.string.intransit));
                        txtReceived.setText(response.body().getDeliveredOrderCount() + "\n" + getString(R.string.received));

                        if(type.equals("active")){
                            viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                            viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                        }else if(type.equals("accepted")){
                            viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                            viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                        }else if(type.equals("delivered")){
                            viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));
                            viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                        }else if(type.equals("inactive")){
                            viewReceived.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewActive.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewTransit.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_gray));
                            viewCancelled.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_background_three));

                        }
                    }catch (Exception e){}


                    setDataByView(type,response.body().getOrderList());
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                  //  recyclerView.setAdapter(new MyOrderListAdapter(getActivity(), type, getFragmentManager(),response.body().getOrderList()));

                } else {

                    try {
                        txtActive.setText(response.body().getPendingOrderCount() + "\n" + getString(R.string.active));
                        txtCancelled.setText(response.body().getInactiveOrderCount() + "\n" + getString(R.string.cancelled));
                        txtInTransit.setText(response.body().getAcceptedOrderCount() + "\n" + getString(R.string.intransit));
                        txtReceived.setText(response.body().getDeliveredOrderCount() + "\n" + getString(R.string.received));
                    }catch (Exception e){}

                    textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    textView.setText(getString(R.string.nooder));
                }


            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
             //   progressDialog.dismiss();
            hud.dismiss();
            }
        });


    }



    private void setDataByView(String type, List<OrderListItem> order_list) {

        StaticClass.fromTraveller = false;

        if(myOrderListAdapter == null){
            myOrderListAdapter = new MyOrderListAdapter(getActivity(), type, getFragmentManager(),order_list);
            recyclerView.setAdapter(myOrderListAdapter);
        }else{
            myOrderListAdapter = new MyOrderListAdapter(getActivity(), type, getFragmentManager(),order_list);
            recyclerView.setAdapter(myOrderListAdapter);
        }

        myOrderListAdapter.setOnOrderItemClickListener(new MyOrderListAdapter.OnOrder_Event() {
            @Override
            public void setOnOrderItemClickListener(OrderListItem orderListItem, int postion) {
                if(order_list.get(postion).getOrderOfferList().size() == 0){
                    setalertDialog(orderListItem.getOrderId());
                }else {
                    for(int i=0; i < orderListItem.getOrderOfferList().size();i++) {
                    Log.d("TAG", "setOnOrderItemClickListener: "+TravellerId+"---"+(orderListItem.getOrderOfferList().get(i).getUserId()));
                        if (TravellerId.equals(orderListItem.getOrderOfferList().get(i).getUserId())) {
                            count++;
                        }
                        }

                    if(count==0){
                        setalertDialog(orderListItem.getOrderId());
                    }else{
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                getString(R.string.sameorder), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }

                }
            }
        });
    }


    public void setalertDialog(String orderId){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext(),R.style.WideDialog);
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.traveller_item, null);
        TextView okTxt = view.findViewById(R.id.okTxt);
        TextView cancelTxt = view.findViewById(R.id.cancelTxt);
        
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        okTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApiCall(orderId);

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

    }

    private void getApiCall(String orderId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.order_id, orderId);
            jsonObject.put(Keys.trip_id, Tripid);
            jsonObject.put(Keys.traveller_id, TravellerId);
        }catch (Exception e){}

        Call<Responce> responseCall = ApiClient.getService().hireorder(UseMe.getJsonObject(jsonObject));
        responseCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")) {
                        alertDialog.dismiss();

                        setalertDialogMain(getString(R.string.travellerhiresuccess));
                    }else{
                        snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                snackBar  = Snackbar.make(getActivity().findViewById(android.R.id.content),
                        getString(R.string.nointernet), Snackbar.LENGTH_LONG);
                snackBar.show();
            }
        });

    }

    public void setalertDialogMain(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Keys.user_id, userId);
                    jsonObject.put(Keys.post_string, "my");
                    jsonObject.put(Keys.order_status, "active");
                    TravellerId ="";
                    Tripid = "";

                } catch (Exception e) {
                }
                getMyOrder(UseMe.getJsonObject(jsonObject));

            }
        });

        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.setCancelable(false);
        alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog1.show();
    }



}