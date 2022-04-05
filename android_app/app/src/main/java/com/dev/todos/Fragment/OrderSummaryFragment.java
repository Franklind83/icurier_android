package com.dev.todos.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Activity.LoginActivity;
import com.dev.todos.Adapter.OrderSummeryAdapter;
import com.dev.todos.Fragment.Message.MessageFragment;
import com.dev.todos.Fragment.MyOrder.MyOrderFragment;
import com.dev.todos.Fragment.TripAndOffers.MakeOfferFragment;
import com.dev.todos.Model.Responce;
import com.dev.todos.Model.SingleOrderListResponce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.UseMe;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Url.WebService.BASE_URL;

public class OrderSummaryFragment extends Fragment {
    RecyclerView recyclerView;
    private KProgressHUD hud;
    String orderId, bringProduct, delDate, usetName, userProfile, userId2, userstatus;
    boolean isFormMyOrder = false;
    Button btnCancel, btnBring, btnSendMessage;
    TextView txtOrderContain, txtTotal;
    androidx.appcompat.app.AlertDialog alertDialog;

    double Total = 0.0;
    String imageurl1, imageurl2, imageurl3, imageurl4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_order_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnBring = view.findViewById(R.id.btnBringProduct);
        btnCancel = view.findViewById(R.id.btnCancelOrder);
        txtOrderContain = view.findViewById(R.id.txtOrderContains);
        txtTotal = view.findViewById(R.id.txtTotal);



        try {
            Bundle bundle = new Bundle();
            bundle = getArguments();
            orderId = bundle.getString(Keys.order_id);
            userId2 = bundle.getString(Keys.user2_id);
            delDate = bundle.getString(Keys.date);
            usetName = bundle.getString(Keys.name);
            userProfile = bundle.getString(Keys.profileImage);
            imageurl1 = bundle.getString(Keys.image1);
            imageurl2 = bundle.getString(Keys.image2);
            imageurl3 = bundle.getString(Keys.image3);
            imageurl4 = bundle.getString(Keys.image4);
            userstatus = bundle.getString(Keys.userstatus);
            bringProduct = bundle.getString(Keys.bringProduct);

            String total = bundle.getString(Keys.total_order_price);
            String newString = total.replace("Order Price", "");
            String newString1 = newString.replace("Total", "TOTAL");
            txtTotal.setText(newString1);

          //  Log.d("TAG", "onViewCreated: "+newString);
            if(userstatus.equals("true")){

                view.findViewById(R.id.btnCancelOrder).setVisibility(View.VISIBLE);
                view.findViewById(R.id.btnBringProduct).setVisibility(View.GONE);
            }else{
                view.findViewById(R.id.btnCancelOrder).setVisibility(View.GONE);
                view.findViewById(R.id.btnBringProduct).setVisibility(View.VISIBLE);


            }

            /*if (isFormMyOrder) {
                txtOrderContain.setText("Please Select the Product Which You Want to Update");
                btnCancel.setVisibility(View.VISIBLE);
                btnBring.setVisibility(View.GONE);
                view.findViewById(R.id.imgDelet).setVisibility(View.GONE);

            } else {
                btnCancel.setVisibility(View.GONE);
                btnBring.setVisibility(View.VISIBLE);
                view.findViewById(R.id.imgDelet).setVisibility(View.GONE);

            }*/

            isFormMyOrder =bundle.getBoolean(Keys.isFromMyOrder);
            if (isFormMyOrder) {
                txtOrderContain.setText(getString(R.string.pleaseselect));
                btnCancel.setVisibility(View.VISIBLE);
                btnBring.setVisibility(View.GONE);

            } else {
                btnCancel.setVisibility(View.GONE);
                btnBring.setVisibility(View.VISIBLE);

            }

            if (!bringProduct.equals("no")) {
                btnBring.setVisibility(View.VISIBLE);
                btnBring.setText(getActivity().getResources().getString(R.string.send_message));
            }else{

            }
            view.findViewById(R.id.imgDelet).setVisibility(View.GONE);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.order_id, orderId);
            getSingleOrderList(UseMe.getJsonObject(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fragments = getFragmentManager().getBackStackEntryCount();
                if (fragments == 1) {
                    getFragmentManager().popBackStack();
                } else if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                } else {
                    getFragmentManager().popBackStack();
                }
                //getFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.btnCancelOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelDialog(orderId);
            }
        });

        view.findViewById(R.id.imgDelet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(orderId);
            }
        });
        view.findViewById(R.id.btnBringProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnBring.getText().toString().equals(getString(R.string.send_message))) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.date, delDate);
                    bundle.putString(Keys.order_id, orderId);
                    bundle.putString(Keys.name, usetName);
                    bundle.putString(Keys.profileImage, userProfile);
                    bundle.putString("CustomerProfile", userProfile);
                    bundle.putString(Keys.user2_id, userId2);

                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), bundle, R.id.frameLayoout,
                            new MessageFragment());
                } else {
                    if(isLogin()) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Keys.date, delDate);
                        bundle.putString(Keys.order_id, orderId);
                        bundle.putString(Keys.user2_id, userId2);

                        StaticClass.fromTraveller = true;
                        new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                                bundle, R.id.frameLayoout,
                                new MakeOfferFragment());
                    }else{
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("Activity","TravellerMakeOffer");
                        intent.putExtra(Keys.order_id,orderId);
                        intent.putExtra(Keys.user2_id, userId2);
                        intent.putExtra(Keys.date, delDate);
                        startActivity(intent);
                    }
                }
            }
        });

    }


    public void getSingleOrderList(JsonObject jsonObject) {
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<SingleOrderListResponce> orderListResponceCall = ApiClient.getService().getSingleOrderDetail(jsonObject);

        orderListResponceCall.enqueue(new Callback<SingleOrderListResponce>() {
            @Override
            public void onResponse(Call<SingleOrderListResponce> call, Response<SingleOrderListResponce> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {

                  /*  Total =  Double.parseDouble(response.body().getOrder_details().getTotal_order_price());
                    txtTotal.setText("Total: $" + response.body().getOrder_details().getTotal_order_price());*/

                    if (!isFormMyOrder) {
                        txtOrderContain.setText(getString(R.string.ordercontain) +" " + response.body().getOrder_details().getProduct_list().size() +" "+ getString(R.string.Products));

                    }
                    recyclerView.setAdapter(new OrderSummeryAdapter(getActivity(), getFragmentManager(),
                            response.body().getOrder_details().getProduct_list(), isFormMyOrder, orderId,bringProduct,response.body().getOrder_details()));


                }
            }

            @Override
            public void onFailure(Call<SingleOrderListResponce> call, Throwable t) {
                hud.dismiss();

            }
        });

    }

    public void deleteOrder(JsonObject jsonObject) {
        Call<Responce> responceCall = ApiClient.getService().deletOrder(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                    if (response.body().getStatus().equals(Keys.status_succes)) {
                        setalertDialog(getString(R.string.productdeleted),"delete");
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {


            }
        });
    }

    public void showDialog(final String orderId) {
        AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
        a.setMessage(getString(R.string.are_you_want_to_remove_this_order));
        a.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Keys.order_id, orderId);
                deleteOrder(UseMe.getJsonObject(jsonObject));

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        a.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        a.show();
    }

    public void cancelOrder(JsonObject jsonObject) {
        Call<Responce> responceCall = ApiClient.getService().cancelOrder(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                try {
                    if (response.body().getStatus().equals(Keys.status_succes)) {
                       // Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        setalertDialog(getString(R.string.ordercancel),"cancel");
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {


            }
        });

    }

    public void showCancelDialog(final String orderId) {
        AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
        a.setMessage(getString(R.string.cancelorder));
        a.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(Keys.order_id, orderId);
                    jsonObject.put(Keys.status, "cancel");
                    cancelOrder(UseMe.getJsonObject(jsonObject));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        a.setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        a.show();
    }

    String base64 = "";

    public String loadUpdateImage(String path, final ImageView imageView) {


        Picasso.get().load(BASE_URL + "media/" + path).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                base64 = UseMe.getBase64(bitmap);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                base64 = "";

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                base64 = "";

            }
        });
        return base64;

    }

    public void setalertDialog(String msg, String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(msg);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new MyOrderFragment());

            }
        });

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    private boolean isLogin() {
        return Sharedpreferences.readFromPreferences(getActivity(), AppConstant.IS_LOGIN, false);
    }

}