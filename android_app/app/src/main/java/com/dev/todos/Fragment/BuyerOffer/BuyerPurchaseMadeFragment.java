package com.dev.todos.Fragment.BuyerOffer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.TravellerOffer.OrderDetailsFragment;
import com.dev.todos.Fragment.TravellerOffer.OrderToTripFragment;
import com.dev.todos.Fragment.TravellerOffer.PurchaseMadeFragment;
import com.dev.todos.Fragment.TravellerOffer.SatisfiedBuyerFragment;
import com.dev.todos.Model.GetPurchaseData.GetPurchaseRequest;
import com.dev.todos.Model.GetPurchaseData.GetPurchaseResponse;
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
import com.dev.todos.databinding.FragmentBuyerPurchaseMadeBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuyerPurchaseMadeFragment extends Fragment {

    FragmentBuyerPurchaseMadeBinding binding;
    String orderId;
    ProgressDialog progressDialog;
    String s;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    String txt = "";
    String image1 = "", image2 = "", image3 = "", image4 = "";
    String purchaseId = "0";
    ArrayList<String> imageList;
    String travelDate, purchaseDate = "";
    Bundle bundle;
    boolean isPurchseMade;
    Button btnFollowing;

    LinearLayout imgProduct2, imgProduct3, imgProduct4;
    ImageView imgSelectProduct1, imgSelectProduct2, imgSelectProduct3, imgSelectProduct4;

    TextView txtPurchaseDate;



    CardView dot1, dot2, dot3, dot4;
    View view1, view2, view3, view4;
    TextView tv1, tv2, tv3, tv4;
    private boolean imageUploadedStaus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_buyer_purchase_made, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = UseMe.showProgressDialog(getActivity());


       // String mydate = Sharedpreferences.readFromPreferences(getContext(),Keys.travel_date,"");

        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
        Date d1= null;
        try {
            d1 = simpleDateFormat1.parse(Sharedpreferences.readFromPreferences(getContext(), Keys.travel_date,""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat1=new SimpleDateFormat("MMM dd, yyyy");
        final String date1=  simpleDateFormat1.format(d1);


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


        if (StaticTransitData.offerStatus.trim().equals("no_info")) {

            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv1.setTextColor(getResources().getColor(R.color.btn_color));

        } else if (StaticTransitData.offerStatus.trim().equals("purchase_made")) {

            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv1.setTextColor(getResources().getColor(R.color.btn_color));

            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv2.setTextColor(getResources().getColor(R.color.btn_color));

        } else if (StaticTransitData.offerStatus.trim().equals("order_delivered")) {

            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv1.setTextColor(getResources().getColor(R.color.btn_color));

            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv2.setTextColor(getResources().getColor(R.color.btn_color));

            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv3.setTextColor(getResources().getColor(R.color.btn_color));

        } else if (StaticTransitData.offerStatus.trim().equals("satisfied_buyer")) {

            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv1.setTextColor(getResources().getColor(R.color.btn_color));

            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv2.setTextColor(getResources().getColor(R.color.btn_color));

            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv3.setTextColor(getResources().getColor(R.color.btn_color));

            dot4.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
            view4.setBackgroundColor(getResources().getColor(R.color.btn_color));
            tv4.setTextColor(getResources().getColor(R.color.btn_color));

        }

        //need to add
        //   Log.d("TAG", "onViewCreated: "+StaticTransitData.TravelFrom);
        binding.txtOriginCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivery_from,""));
        binding.txtDestinationCity.setText(Sharedpreferences.readFromPreferences(getContext(),Keys.delivered_to,""));
        binding.txtdate.setText(date1);
        orderId = Sharedpreferences.readFromPreferences(getActivity(),Keys.order_id,"");
        txtPurchaseDate = view.findViewById(R.id.txtPurchaseDate);
        btnFollowing = view.findViewById(R.id.btnFollowing);

        imgProduct2 = view.findViewById(R.id.imgProduct2);
        imgProduct3 = view.findViewById(R.id.imgProduct3);
        imgProduct4 = view.findViewById(R.id.imgProduct4);

        imgSelectProduct1 = view.findViewById(R.id.imgSelectProduct1);
        imgSelectProduct2 = view.findViewById(R.id.imgSelectProduct2);
        imgSelectProduct3 = view.findViewById(R.id.imgSelectProduct3);
        imgSelectProduct4 = view.findViewById(R.id.imgSelectProduct4);





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
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new BuyerOrderToTripFragment());
            }
        });

       /* binding.llOrderDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new BuyerOrderDeliverFragment());
            }
        });

        binding.llSatisfiedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null,
                        R.id.frameLayoout, new BuyerSatisfiedBuyerFragment());
            }
        });*/

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

                    SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
                    Date d1= null;
                    try {
                        d1 = simpleDateFormat1.parse(Sharedpreferences.readFromPreferences(getContext(), Keys.travel_date,""));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy");
                    final String date1=  simpleDateFormat1.format(d1);

                    binding.txt.setText(getString(R.string.travellerpuschase)+date1+getString(R.string.updated));

                    String img1 = response.body().getPurchaseData().getPurchaseImgaes().get(0);
                    String img2 = response.body().getPurchaseData().getPurchaseImgaes().get(1);
                    String img3 = response.body().getPurchaseData().getPurchaseImgaes().get(2);
                    String img4 = response.body().getPurchaseData().getPurchaseImgaes().get(3);



                    if (!img1.equals("")) {

                        image1 = img1;
                        imgSelectProduct1.setVisibility(View.VISIBLE);
                        UseMe.setImage(getActivity(),img1,binding.imgSelectProduct1);
                    }else{
                        imgSelectProduct1.setVisibility(View.GONE);
                    }

                    if (!img2.equals("")) {
                        //   UseMe.setImage(getContext(), img2, imgProduct2);

                        // loadUpdateImage(img2, imgProduct2, imgSelectProduct2);

                        // UseMe.LongOperation longOperation = new UseMe.LongOperation(ClsPurchaseMade.this, 2, getContext());
                        //  longOperation.execute(((BitmapDrawable) imgProduct2.getDrawable()).getBitmap());
                        image2 = img2;
                        imgSelectProduct2.setVisibility(View.VISIBLE);
                        imgProduct2.setVisibility(View.VISIBLE);
                        UseMe.setImage(getActivity(),img2,binding.imgSelectProduct2);

                    }else{
                        imgSelectProduct2.setVisibility(View.GONE);
                        imgProduct2.setVisibility(View.GONE);

                    }

                    if (!img3.equals("")) {

                        image3 = img3;
                        imgSelectProduct3.setVisibility(View.VISIBLE);
                        imgProduct3.setVisibility(View.VISIBLE);
                        UseMe.setImage(getActivity(),img3,binding.imgSelectProduct3);


                    }else{
                        imgSelectProduct3.setVisibility(View.GONE);
                        imgProduct3.setVisibility(View.GONE);

                    }

                    if (!img4.equals("")) {
                        image4 = img4;
                        imgSelectProduct4.setVisibility(View.VISIBLE);
                        imgProduct4.setVisibility(View.VISIBLE);
                        UseMe.setImage(getActivity(),img4,binding.imgSelectProduct4);

                    }else{
                        imgSelectProduct4.setVisibility(View.GONE);
                        imgProduct4.setVisibility(View.GONE);
                    }



                    try {
                        if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("no_info")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));


                        } else if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("purchase_made")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderToTripFragment());
                                }
                            });

                            binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerPurchaseMadeFragment());
                                }
                            });


                        } else if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("order_delivered")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv3.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderToTripFragment());
                                }
                            });

                            binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerPurchaseMadeFragment());
                                }
                            });

                            binding.llOrderDel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderDeliverFragment());
                                }
                            });


                        } else if (Sharedpreferences.readFromPreferences(getContext(), AppConstant.OFFERSTATUS,"").equals("satisfied_buyer")) {

                            dot1.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view1.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv1.setTextColor(getResources().getColor(R.color.btn_color));

                            dot2.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view2.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv2.setTextColor(getResources().getColor(R.color.btn_color));

                            dot3.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view3.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv3.setTextColor(getResources().getColor(R.color.btn_color));

                            dot4.setCardBackgroundColor(getResources().getColor(R.color.btn_color));
                            view4.setBackgroundColor(getResources().getColor(R.color.btn_color));
                            tv4.setTextColor(getResources().getColor(R.color.btn_color));

                            binding.llOederToManage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderToTripFragment());
                                }
                            });

                            binding.llPurchaseMade.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerPurchaseMadeFragment());
                                }
                            });

                            binding.llOrderDel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerOrderDeliverFragment());
                                }
                            });
                            binding.llSatisfiedUser.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                                            R.id.frameLayoout,new BuyerSatisfiedBuyerFragment());
                                }
                            });

                        }
                    }catch (Exception e){}


                }


            }

            @Override
            public void onFailure(Call<GetPurchaseResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }



}