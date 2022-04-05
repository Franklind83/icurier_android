package com.dev.todos.Fragment.TravellerOffer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.GetPurchaseData.GetPurchaseRequest;
import com.dev.todos.Model.GetPurchaseData.GetPurchaseResponse;
import com.dev.todos.Model.OrderStatus.OrderStatusRequest;
import com.dev.todos.Model.OrderStatus.OrderStatusResponse;
import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Url.WebService;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.PurchaseMade;
import com.dev.todos.Util.StaticTransitData;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentPurchaseMadeBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class PurchaseMadeFragment extends Fragment implements UseMe.Callback {

    FragmentPurchaseMadeBinding binding;
    String orderId;
    ProgressDialog progressDialog;
    String s;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    String txt = "";
    String image1 = "", image2 = "", image3 = "", image4 = "";
    String purchaseId = "0";
    KProgressHUD hud;
    ArrayList<String> imageList;
    String travelDate, purchaseDate = "";
    Bundle bundle;
    AlertDialog alertDialog1;
    boolean isPurchseMade;
    Button btnFollowing;

    ImageView imgProduct1, imgProduct2, imgProduct3, imgProduct4;
    ImageView imgSelectProduct1, imgSelectProduct2, imgSelectProduct3, imgSelectProduct4;

    TextView txtPurchaseDate;

    CardView dot1, dot2, dot3, dot4;
    View view1, view2, view3, view4;
    TextView tv1, tv2, tv3, tv4;
    private boolean imageUploadedStaus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase_made, container, false);
        return binding.getRoot();
    }

   @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = UseMe.showProgressDialog(getActivity());

        try {
            String date= Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,"");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date d = simpleDateFormat.parse(date);
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = simpleDateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String mydate = Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,"");

        try {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            Date d = simpleDateFormat2.parse(mydate);
            simpleDateFormat2 = new SimpleDateFormat("dd MMM yyyy");
            mydate = simpleDateFormat2.format(d);
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


        binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
        binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
        binding.txtdate.setText(mydate);
        orderId = Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,"");
        txtPurchaseDate = view.findViewById(R.id.txtPurchaseDate);
        btnFollowing = view.findViewById(R.id.btnFollowing);
        imgProduct1 = view.findViewById(R.id.imgProduct1);
        imgProduct2 = view.findViewById(R.id.imgProduct2);
        imgProduct3 = view.findViewById(R.id.imgProduct3);
        imgProduct4 = view.findViewById(R.id.imgProduct4);

        imgSelectProduct1 = view.findViewById(R.id.imgSelectProduct1);
        imgSelectProduct2 = view.findViewById(R.id.imgSelectProduct2);
        imgSelectProduct3 = view.findViewById(R.id.imgSelectProduct3);
        imgSelectProduct4 = view.findViewById(R.id.imgSelectProduct4);


        if (StaticTransitData.fromWhom.equals("fromtraveller")) {

            btnFollowing.setText(getString(R.string.submit));
            setEnableView();

        }


        view.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();


            }
        });


        getPurchaseData();

        binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new OrderToTripFragment());
            }
        });

        binding.llOrderDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new OrderDetailsFragment());
            }
        });

       binding.llSatisfiedUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(), null,
                       R.id.frameLayoout, new SatisfiedBuyerFragment());
           }
       });


        binding.btnFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (StaticTransitData.fromWhom.equals("fromtraveller")) {

                    addPuchaseDatatoApi();

                } else {

                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new OrderDetailsFragment());

                }


            }
        });

    }

    private void setEnableView() {


        binding.imgSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseMe.selectDate(getActivity(), binding.txtPurchaseDate);
            }
        });


        imgSelectProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage("1");

            }
        });
        imgSelectProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (image1.equals("")) {

                    UseMe.showImageAlert("Please Select First Image", getActivity());
                } else {
                    selectImage("2");

                }


            }
        });
        imgSelectProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (image1.equals("")) {

                    UseMe.showImageAlert("Please Select First Image", getActivity());

                } else if (image2.equals("")) {

                    UseMe.showImageAlert("Please Select Second Image", getActivity());


                } else {
                    selectImage("3");

                }


            }
        });
        imgSelectProduct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (image1.equals("")) {

                    UseMe.showImageAlert("Please Select First Image", getActivity());

                } else if (image2.equals("")) {

                    UseMe.showImageAlert("Please Select Second Image", getActivity());


                } else if (image3.equals("")) {


                    UseMe.showImageAlert("Please Select Third Image", getActivity());


                } else {

                    selectImage("4");

                }


            }
        });

    }

    private void setViewsDisable() {

        txtPurchaseDate.setEnabled(false);
        imgProduct1.setEnabled(false);
        imgProduct2.setEnabled(false);
        imgProduct3.setEnabled(false);
        imgProduct4.setEnabled(false);


    }

    void addPuchaseDatatoApi() {

        if (image1 == null) {
            image1 = "";
        }
        if (image1.contains(".png") || image1.contains(".jpg")) {
            image1 = "";


        }
        if (image2 == null) {
            image2 = "";
        }

        if (image2.contains(".png") || image2.contains(".jpg")) {
            image2 = "";

        }
        if (image3 == null) {
            image3 = "";
        }

        if (image3.contains(".png") || image3.contains(".jpg")) {
            image3 = "";

        }
        if (image4 == null) {
            image4 = "";
        }
        if (image4.contains(".png") || image4.contains(".jpg")) {
            image4 = "";

        }


        JSONObject jsonObject = new JSONObject();
        imageList = new ArrayList<>();
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        imageList.add(image4);


        for (int i = 0; i < imageList.size(); i++) {

            if (!imageList.get(i).equals("")) {

                imageUploadedStaus = true;
            }

        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = simpleDateFormat.parse(binding.txtPurchaseDate.getText().toString());
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            purchaseDate = simpleDateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (purchaseDate.trim().equals("")) {
            warningsetalertDialog("Please select purchase date");
        } else if (imageUploadedStaus == false) {
            warningsetalertDialog("Please upload atleast one product image");
        } else {
            progressDialog.show();


            try {

                PurchaseMade.setOrderId(orderId);
                PurchaseMade.setPurchaseMadeDate(purchaseDate);
                PurchaseMade.setBase64ImageArray(imageList);


                jsonObject.put(Keys.order_id, orderId);
                jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));
                jsonObject.put(Keys.purchase_id, purchaseId);
                jsonObject.put(Keys.purchase_date_, purchaseDate);

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < imageList.size(); i++) {

                    if (imageList.get(i).contains(".jpg") || imageList.get(i).contains(".png")) {
                        jsonArray.put("");

                    } else {
                        jsonArray.put(imageList.get(i));

                    }
                }
                jsonObject.put(Keys.images_list, jsonArray);
                JsonObject jsonObject1 = new JsonParser().parse(jsonObject.toString()).getAsJsonObject();
                addPurchaseInfo(jsonObject1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }




    public void getPurchaseData() {

        progressDialog.show();

        GetPurchaseRequest getPurchaseRequest = new GetPurchaseRequest();
        getPurchaseRequest.setOrderId(orderId);

        Call<GetPurchaseResponse> purchaseMadeResponceCall = ApiClient.getService().getpurchase(getPurchaseRequest);

        purchaseMadeResponceCall.enqueue(new Callback<GetPurchaseResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseResponse> call, Response<GetPurchaseResponse> response) {
                progressDialog.dismiss();
                isPurchseMade = true;
                String date = "";
                if (response.body().getStatus().equals(Keys.status_succes)) {

                    dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv1.setTextColor(getResources().getColor(R.color.btn_color));

                    dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv2.setTextColor(getResources().getColor(R.color.btn_color));

                    purchaseId = response.body().getPurchaseData().getPurchaseId();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date d = simpleDateFormat.parse(response.body().getPurchaseData().getPurchaseDate());
                        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date = simpleDateFormat.format(d);
                        binding.txtPurchaseDate.setText(date);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // binding.imgSelectProduct1.setVisibility(View.GONE);


                    String img1 = response.body().getPurchaseData().getPurchaseImgaes().get(0).trim();
                    String img2 = response.body().getPurchaseData().getPurchaseImgaes().get(1).trim();
                    String img3 = response.body().getPurchaseData().getPurchaseImgaes().get(2).trim();
                    String img4 = response.body().getPurchaseData().getPurchaseImgaes().get(3).trim();

                    image1 = img1;
                    image2 = img2;
                    image3 = img3;
                    image4 = img4;

                    if (!img1.equals("")) {
                        //  UseMe.setImage(getContext(), img1, imgProduct1);
                        imgSelectProduct1.setVisibility(View.GONE);
                        // loadUpdateImage(img1, imgProduct1, imgSelectProduct1);
                        image1 = img1;
                        // UseMe.LongOperation longOperation = new UseMe.LongOperation(ClsPurchaseMade.this, 1, getContext());
                        //longOperation.execute(((BitmapDrawable) imgProduct1.getDrawable()).getBitmap());

                        try {
                            loadedBitmap(img1, imgProduct1,1);

                        }catch (Exception e){}

                    }

                    if (!img2.equals("")) {
                        //   UseMe.setImage(getContext(), img2, imgProduct2);
                        imgSelectProduct2.setVisibility(View.GONE);
                        // loadUpdateImage(img2, imgProduct2, imgSelectProduct2);

                        // UseMe.LongOperation longOperation = new UseMe.LongOperation(ClsPurchaseMade.this, 2, getContext());
                        //  longOperation.execute(((BitmapDrawable) imgProduct2.getDrawable()).getBitmap());
                        image2 = img2;

                        try {
                            loadedBitmap(img2, imgProduct2,2);

                        }catch (Exception e){}

                    }

                    if (!img3.equals("")) {
                        UseMe.setImage(getContext(), img3, imgProduct3);
                        imgSelectProduct3.setVisibility(View.GONE);
                        // loadUpdateImage(img3, imgProduct3, imgSelectProduct3);
                        //UseMe.LongOperation longOperation = new UseMe.LongOperation(ClsPurchaseMade.this, 3, getContext());
                        //  longOperation.execute(((BitmapDrawable) imgProduct3.getDrawable()).getBitmap());
                        image3 = img3;

                        try {
                            loadedBitmap(img3, imgProduct3,3);

                        }catch (Exception e){}

                    }

                    if (!img4.equals("")) {
                        UseMe.setImage(getContext(), img4, imgProduct4);
                        imgSelectProduct4.setVisibility(View.GONE);
                        //loadUpdateImage(img4, imgProduct4, imgSelectProduct4);
                        //   UseMe.LongOperation longOperation = new UseMe.LongOperation(ClsPurchaseMade.this, 4, getContext());
                        // longOperation.execute(((BitmapDrawable) imgProduct4.getDrawable()).getBitmap());
                        image4 = img4;
                        try {
                            loadedBitmap(img4, imgProduct4,4);

                        }catch (Exception e){}

                    }

                    setViewsDisable();
                    binding.btnFollowing.setVisibility(View.GONE);

                }else{

                    dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                    view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                    tv1.setTextColor(getResources().getColor(R.color.btn_color));


                    binding.btnFollowing.setVisibility(View.VISIBLE);

                }
               // if (StaticTransitData.fromWhom.equals("frombuyer"))


            }

            @Override
            public void onFailure(Call<GetPurchaseResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {


                Uri resultUri = result.getUri();
                if (s.equals("1")) {
                    binding.imgProduct1.setVisibility(View.VISIBLE);
                    binding.imgSelectProduct1.setVisibility(View.GONE);
                    binding.imgProduct1.setImageURI(resultUri);
                    image1 = encodeImage(resultUri.getPath());
                    // image1 = UseMe.getBase64StringFromUri(resultUri.getPath());

                   /* UseMe.LongOperation longOperation = new UseMe.LongOperation( PurchaseMadeFragment.this, 1, getContext());
                    longOperation.execute(((BitmapDrawable) imgProduct1.getDrawable()).getBitmap());
*/

                } else if (s.equals("2")) {
                    binding.imgProduct2.setVisibility(View.VISIBLE);
                    binding.imgSelectProduct2.setVisibility(View.GONE);
                    binding.imgProduct2.setImageURI(resultUri);
                    image2 = encodeImage(resultUri.getPath());
                    // image2 = UseMe.getBase64StringFromUri(resultUri.getPath());

                 /*   UseMe.LongOperation longOperation = new UseMe.LongOperation( PurchaseMadeFragment.this, 2, getContext());
                    longOperation.execute(((BitmapDrawable) imgProduct2.getDrawable()).getBitmap());
*/

                } else if (s.equals("3")) {
                    binding.imgProduct3.setVisibility(View.VISIBLE);
                    binding.imgSelectProduct3.setVisibility(View.GONE);
                    binding.imgProduct3.setImageURI(resultUri);
                    //  image3 = UseMe.getBase64StringFromUri(resultUri.getPath());
                 /*   UseMe.LongOperation longOperation = new UseMe.LongOperation( PurchaseMadeFragment.this, 3, getContext());
                    longOperation.execute(((BitmapDrawable) imgProduct3.getDrawable()).getBitmap());
*/
                } else if (s.equals("4")) {
                    binding.imgProduct4.setVisibility(View.VISIBLE);
                    binding.imgSelectProduct4.setVisibility(View.GONE);
                    binding.imgProduct4.setImageURI(resultUri);
                    //  image4 = UseMe.getBase64StringFromUri(resultUri.getPath());
                    /*UseMe.LongOperation longOperation = new UseMe.LongOperation(PurchaseMadeFragment.this, 4, getContext());
                    longOperation.execute(((BitmapDrawable) imgProduct4.getDrawable()).getBitmap());*/

                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    public void selectImage(String s_) {
        s = s_;
        CropImage.activity(null)
                .start(getContext(), PurchaseMadeFragment.this);
    }

    public void addPurchaseInfo(JsonObject jsonObject) { //need to add

        Call<Responce> responceCall = ApiClient.getService().addpurshase(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {

                String result = response.body().getMsg();

                progressDialog.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                            R.id.frameLayoout, new OrderDetailsFragment());

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

    String base64 = "";


    public void callback(String imageValue, int position) {

        switch (position) {
            case 1: {
                image1 = imageValue;
                if (image1 == null) {
                    image1 = "";
                }
                break;
            }
            case 2: {
                image2 = imageValue;
                if (image2 == null) {
                    image2 = "";
                }
                break;
            }
            case 3: {
                image3 = imageValue;
                if (image3 == null) {
                    image3 = "";
                }
                break;
            }
            case 4: {
                image4 = imageValue;
                if (image4 == null) {
                    image4 = "";
                }
                break;
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
            Bitmap compressedImageBitmap = new Compressor(getActivity()).compressToBitmap(imagefile);
            /* Bitmap bm = BitmapFactory.decodeStream(fis);*/
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            encImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            encImage = "";
        }
        //Base64.de
        return encImage;

    }


    Bitmap loadedBitmap(String url, final ImageView imageView, final int position) {

        final Bitmap[] myBitmap = {null};


        Glide.with(this)
                .asBitmap()
                .load(WebService.BASE_URL + "media/" + url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);

                        UseMe.LongOperation longOperation = new UseMe.LongOperation(PurchaseMadeFragment.this, position, getContext());
                        longOperation.execute(((BitmapDrawable) imageView.getDrawable()).getBitmap());

                        myBitmap[0] = resource;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        return myBitmap[0];
    }

    public void warningsetalertDialog(String mes){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater= LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.ok_alert, null);
        TextView logoutText = view.findViewById(R.id.mainTxt);
        TextView oktext = view.findViewById(R.id.oktext);

        logoutText.setText(mes);

        oktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });

        builder.setView(view);
        alertDialog1 = builder.create();
        alertDialog1.setCancelable(false);
        alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog1.show();
    }




}