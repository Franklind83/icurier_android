package com.dev.todos.Fragment.TravellerOffer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.DeliveryInfo.DeliveryInfoRequest;
import com.dev.todos.Model.DeliveryInfo.DeliveryInfoResponse;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.OrderDelivered;
import com.dev.todos.Util.StaticClass;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentOrderDetailsBinding;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsFragment extends Fragment {

    FragmentOrderDetailsBinding binding;
    String orderId;
    ProgressDialog progressDialog;
    boolean isOrderDeliverd;
    Button btnFollowing;
    String deliveryId = "0", delivered_image = "";
    String userId = "";
    EditText edtPersonName;
    EditText edtDocument;
    KProgressHUD hud;
    AlertDialog alertDialog1;
    TextView txtSelectDate;
    ImageView imgSelectDate;
    ImageView imgSelectProduct1;
    ImageView imgProduct1;


    CardView dot1, dot2, dot3, dot4;
    View view1, view2, view3, view4;
    TextView tv1, tv2, tv3, tv4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        progressDialog = UseMe.showProgressDialog(getActivity());
        userId = Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,"");


       String mydate = Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,"");

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = simpleDateFormat2.parse(mydate);
            simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy");
            //mydate = simpleDateFormat2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        dot1 = view.findViewById(R.id.dot1);
        dot2 = view.findViewById(R.id.dot2);
        dot3 = view.findViewById(R.id.dot3);
        dot4 = view.findViewById(R.id.dot4);

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);

        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);



        binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getActivity(), Keys.delivery_from,""));
        binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
        binding.txtDate.setText(mydate);

        imgProduct1 = view.findViewById(R.id.imgProduct1);
        imgSelectProduct1 = view.findViewById(R.id.imgSelectProduct1);
        imgSelectDate = view.findViewById(R.id.imgSelectDate);
        txtSelectDate = view.findViewById(R.id.txtSelectDate);
        edtDocument = view.findViewById(R.id.edtDocument);
        edtPersonName = view.findViewById(R.id.edtPersonName);
        btnFollowing = view.findViewById(R.id.btnFollowing);

///
      // if (StaticTransitData.fromWhom.equals("fromtraveller")) {

            btnFollowing.setText("Submit");
            setEnableViews();

       // } /*else {
        ///    btnFollowing.setText("Submit");
        //    setViewsDisable();
       // }*/

        binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new PurchaseMadeFragment());
            }
        });

        binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new OrderToTripFragment());
            }
        });

        binding.llSatisfiedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new SatisfiedBuyerFragment());
            }
        });


        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if (StaticTransitData.fromWhom.equals("fromtraveller")) {

                    addDeliveredDatatoApi();

                } else {

                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new SatisfiedBuyerFragment());

                }


            }
        });

        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });


        orderId = Sharedpreferences.readFromPreferences(getActivity(),AppConstant.USERID,"");


        getApicall();

            getDeliveryInfo();


    }

    private void setEnableViews() {


        binding.imgSelectProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        binding.imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), binding.txtSelectDate);
            }
        });

    }

    private void setViewsDisable() {

        edtPersonName.setEnabled(false);
        txtSelectDate.setEnabled(false);
        edtDocument.setEnabled(false);
        imgSelectDate.setEnabled(false);
        imgProduct1.setEnabled(false);
        imgSelectProduct1.setEnabled(false);

    }


    void addDeliveredDatatoApi()    {

        String selectedDate = binding.txtSelectDate.getText().toString();
        String recPersonName = binding.edtPersonName.getText().toString();
        String documentNumber = binding.edtDocument.getText().toString();

        OrderDelivered.selectedDate = selectedDate;
        OrderDelivered.image = delivered_image;
        OrderDelivered.orderId = orderId;
        OrderDelivered.documentNumber = documentNumber;
        OrderDelivered.receiverName = recPersonName;
        if (selectedDate.equals("eg: 13/03/2019")) {
            warningsetalertDialog("Select Date");
        } else if (recPersonName.equals("")) {
          //  Toast.makeText(getContext(), "Enter Receiver Name", Toast.LENGTH_SHORT).show();
            binding.edtPersonName.setError("Enter Receiver Name");
        } else if (documentNumber.equals("")) {
           // Toast.makeText(getContext(), "Enter Document Number", Toast.LENGTH_SHORT).show();
            binding.edtDocument.setError("Enter Document Number");
        } else {


            progressDialog.show();

            try {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = simpleDateFormat.parse(selectedDate);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String deldate = simpleDateFormat.format(date);

                OrderDelivered.orderId = orderId;
                OrderDelivered.deliveryDate = deldate;
                OrderDelivered.delivery_id = deliveryId;
                OrderDelivered.image = delivered_image;
                OrderDelivered.receiverName = recPersonName;
                OrderDelivered.documentNumber = documentNumber;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Keys.order_id,  Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));
                jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getActivity(),AppConstant.USERID,""));
                jsonObject.put(Keys.delivery_id, deliveryId);
                jsonObject.put(Keys.delivery_date, deldate);
                jsonObject.put(Keys.delivered_to, recPersonName);

                if (delivered_image.contains(".jpg") || delivered_image.contains(".png")) {
                    jsonObject.put(Keys.delivered_image, "");

                } else {
                    jsonObject.put(Keys.delivered_image, delivered_image);

                }

                jsonObject.put(Keys.delivered_person_doc_no, documentNumber);
                addDeliveryInfo(UseMe.getJsonObject(jsonObject));

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    public void getDeliveryInfo() {
        progressDialog.show();

        DeliveryInfoRequest deliveryInfoRequest = new DeliveryInfoRequest();
        deliveryInfoRequest.setOrderId(Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,""));

        Call<DeliveryInfoResponse> deliveryInfoResponceCall = ApiClient.getService().getDelivery(deliveryInfoRequest);
        deliveryInfoResponceCall.enqueue(new Callback<DeliveryInfoResponse>() {
            @Override
            public void onResponse(Call<DeliveryInfoResponse> call, Response<DeliveryInfoResponse> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    deliveryId = response.body().getDeliveryData().getDeliveryId();
                    try {
                        Date d = simpleDateFormat.parse(response.body().getDeliveryData().getDeliveryDate());
                        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String date = simpleDateFormat.format(d);
                        binding.txtSelectDate.setText(date);

                        OrderDelivered.selectedDate = date;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    binding.edtDocument.setText(response.body().getDeliveryData().getDeliveredPersonDocNo());
                    binding.edtPersonName.setText(response.body().getDeliveryData().getDeliveredTo());

                    dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv1.setTextColor(getResources().getColor(R.color.btn_color));

                    dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv2.setTextColor(getResources().getColor(R.color.btn_color));

                    dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv3.setTextColor(getResources().getColor(R.color.btn_color));

                    setViewsDisable();
                    binding.btnFollowing.setVisibility(View.GONE);

                }else{
                    dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv1.setTextColor(getResources().getColor(R.color.btn_color));

                    dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv2.setTextColor(getResources().getColor(R.color.btn_color));


                    setEnableViews();
                    binding.btnFollowing.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<DeliveryInfoResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }


    public void addDeliveryInfo(JsonObject jsonObject) {
        Call<Responce> responceCall = ApiClient.getService().addDelivery(jsonObject);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                progressDialog.dismiss();

                String result = response.body().getMsg();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    isOrderDeliverd = true;
                    OrderDelivered.isOrderDeliverd = true;


                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new SatisfiedBuyerFragment());

                    //show popoup to send feedback to buyer
                    //ok
                    //then


                  /*  androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.feedback_dialog, null);
                    final TextView tvDesc = view.findViewById(R.id.tvDesc);
                    final MaterialRatingBar rating1 = view.findViewById(R.id.rating1);
                    final EditText edFeedBack = view.findViewById(R.id.edFeedBack);
                    Button btnSubmit = view.findViewById(R.id.btnSubmit);
                    tvDesc.setText("Helps " + StaticTransitData.fromWhom + " to improve their performance by rating them.");
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();


                            float rating = rating1.getRating();
                            String strFeedback = edFeedBack.getText().toString();

                            if (rating == 0) {
                                Toast.makeText(getContext(), "Please Give Ratings", Toast.LENGTH_SHORT).show();
                            } else if (strFeedback.equals("")) {
                                Toast.makeText(getContext(), "Please Give Some Reviews", Toast.LENGTH_SHORT).show();

                            } else {
                                addFeedBack(rating, strFeedback);

                            }
                        }
                    });
                    builder.setView(view);
                    alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    alertDialog.show();*/


                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }


    private void addFeedBack(float rating, String strFeedback) {

        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.order_id, Sharedpreferences.readFromPreferences(getActivity(), Keys.order_id,""));
            jsonObject.put(Keys.date, StaticClass.getDate());
            jsonObject.put("rating_for", "b");
            jsonObject.put("user_id", userId);
            jsonObject.put("rating", "" + rating);
            jsonObject.put("review", strFeedback);
            jsonObject.put("rating_from_user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<Responce> responceCall = ApiClient.getService().addRating(UseMe.getJsonObject(jsonObject));
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                progressDialog.dismiss();


                String result = response.body().getMsg();

                if (response.body().getStatus().equals(Keys.status_succes)) {


                    Toast.makeText(getContext(), "" + result, Toast.LENGTH_SHORT).show();


                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new SatisfiedBuyerFragment());


                } else {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                }

            }


            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }


    public void selectImage() {

        CropImage.activity(null)
                .start(getContext(), OrderDetailsFragment.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {

                binding.imgSelectProduct1.setVisibility(View.GONE);
                binding.imgProduct1.setVisibility(View.VISIBLE);
                binding.imgProduct1.setImageURI(data.getData());
                //delivered_image = encodeImage(data.getData().getPath());

                Uri resultUri = result.getUri();


                // delivered_image = UseMe.getBase64StringFromUri(data.getData().getPath());
                delivered_image = UseMe.getBase64StringFromUri(resultUri.getPath());
                binding.imgProduct1.setImageURI(resultUri);

                OrderDelivered.image = delivered_image;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }

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

    public void warningsetalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(view5 -> alertDialog1.dismiss());

        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.setCancelable(false);
        alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog1.show();
    }

    private void getApicall() {

        if(hud == null){
            hud=   KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait...")
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }else{
            hud.dismiss();
        }

        OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
        orderStatusRequest.setOrderId(Sharedpreferences.readFromPreferences(getContext(),Keys.order_id,""));

        Call<OrderStatusResponse> orderStatusResponseCall = ApiClient.getService().getOrderStatus(orderStatusRequest);
        orderStatusResponseCall.enqueue(new Callback<OrderStatusResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {

                if(response.isSuccessful()){
                    if (response.body().getStatus().equals(Keys.status_succes)) {
                        hud.dismiss();


                        if (response.body().getOrderInfoStatus().equals("no_info")){
                            binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                        } else if (response.body().getOrderInfoStatus().equals("purchase_made")) {
                            binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv2.setTextColor(getResources().getColor(R.color.btn_color));


                        }else if (response.body().getOrderInfoStatus().equals("order_delivered")) {
                            binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv3.setTextColor(getResources().getColor(R.color.btn_color));
                        }else if (response.body().getOrderInfoStatus().equals("satisfied_buyer")) {
                            binding.dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            binding.tv3.setTextColor(getResources().getColor(R.color.btn_color));
                        }

                    } else {
                        //Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        hud.dismiss();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
                hud.dismiss();
            }
        });


    }

}