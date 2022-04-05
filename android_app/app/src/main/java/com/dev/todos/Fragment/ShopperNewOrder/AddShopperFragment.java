package com.dev.todos.Fragment.ShopperNewOrder;

import android.app.AlertDialog;
import android.content.Context;
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

import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.ProductDetail;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentAddShopperBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import io.reactivex.schedulers.Schedulers;

import static com.dev.todos.Url.WebService.BASE_URL;


public class AddShopperFragment extends Fragment {

    Spinner spinner;
    String s;
    ArrayList<String> qut;
    int ImageCount = 0;
    int PICK_IMAGE = 123;
    ImageView imgProduct1, imgProduct2, imgProduct3, imgProduct4, imgSelect1, imgSelect2,
            imgSelect3, imgSelect4;
    String productImagePath = "", productImageUri = "";
    Uri productImageUri1, productImageUri2,
            productImageUri3, productImageUri4;
    FragmentAddShopperBinding binding;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    String s_qut;
    public static String image1 = "", image2 = "", image3 = "", image4 = "";
    String fromLocation, toLocation, deadLineDate, reward, latfrom = "", latto = "", longfrom = "", longto = "";
    public static String travellerId, orderId, productId, tripId;


    String imageLink;
    public static String imageUrl = "", imageUrl2 = "", imageUrl3 = "", imageUrl4 = "";
    public static boolean isFromUpdateOrder = false;
    public static boolean checkorder = false;
    List<String> productImageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_shopper, container, false);
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();

        if (productImageUri1 != null) {
            binding.imgProduct1.setVisibility(View.VISIBLE);
            binding.imgSelectProduct1.setVisibility(View.INVISIBLE);
            binding.imgProduct1.setImageURI(productImageUri1);
        }

        if (productImageUri2 != null) {
            binding.imgProduct2.setVisibility(View.VISIBLE);
            binding.imgSelectProduct2.setVisibility(View.INVISIBLE);

            binding.imgProduct2.setImageURI(productImageUri2);

        }
        if (productImageUri3 != null) {
            binding.imgProduct3.setVisibility(View.VISIBLE);
            binding.imgSelectProduct3.setVisibility(View.INVISIBLE);

            binding.imgProduct3.setImageURI(productImageUri3);

        }
        if (productImageUri4 != null) {
            binding.imgProduct4.setVisibility(View.VISIBLE);
            binding.imgSelectProduct4.setVisibility(View.INVISIBLE);

            binding.imgProduct4.setImageURI(productImageUri4);

        }


        if (productImageUri1 != null) {

            UseMe.uriArrayList.add(productImageUri1);

        }
        if (productImageUri2 != null) {
            UseMe.uriArrayList.add(productImageUri2);


        }
        if (productImageUri3 != null) {
            UseMe.uriArrayList.add(productImageUri3);

        }
        if (productImageUri4 != null) {
            UseMe.uriArrayList.add(productImageUri4);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle1 = new Bundle();
        bundle1 = getArguments();
        try {
            travellerId = bundle1.getString(Keys.traveller_id);
            tripId = bundle1.getString(Keys.trip_id);
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        imgProduct1 = view.findViewById(R.id.imgProduct1);
        imgProduct2 = view.findViewById(R.id.imgProduct2);
        imgProduct3 = view.findViewById(R.id.imgProduct3);
        imgProduct4 = view.findViewById(R.id.imgProduct4);
        imgSelect1 = view.findViewById(R.id.imgSelectProduct1);
        imgSelect2 = view.findViewById(R.id.imgSelectProduct2);
        imgSelect3 = view.findViewById(R.id.imgSelectProduct3);
        imgSelect4 = view.findViewById(R.id.imgSelectProduct4);

        try {
            //check once
            Bundle bundle = new Bundle();
            bundle = getArguments();
            binding.edtProductLink.setText(bundle.getString(Keys.productLink));
            binding.edtAricleName.setText(bundle.getString(Keys.article_name));
            binding.edtArticleComment.setText(bundle.getString(Keys.article_comment));
            binding.edtPriceOfItem.setText(bundle.getString(Keys.item_price));
            binding.edtQntity.setText(bundle.getString(Keys.qunt));
            fromLocation = bundle.getString(Keys.location_from);
            toLocation = bundle.getString(Keys.location_to);
            reward = bundle.getString(Keys.reward);
            deadLineDate = bundle.getString(Keys.date);
            latfrom = bundle.getString(Keys.latfrom);
            longfrom = bundle.getString(Keys.longfrom);
            latto = bundle.getString(Keys.latto);
            longto = bundle.getString(Keys.longto);

            orderId = bundle.getString(Keys.order_id);
            productId = bundle.getString(Keys.product_id);


            Log.d("TAG", "onViewCreated: " + bundle.getString(Keys.isFromUpdate));
            Log.e("TAGddddddddddddd", "data: " + bundle.getString(Keys.location_from));
            Log.e("TAGddddddddddddd", "data: " + bundle.getString(Keys.location_to));
            Log.e("TAGdddddddddddddlatfrom", "data: " + bundle.getString(Keys.latfrom));
            Log.e("TAGdddddddddddddlatfrom", "data: " + bundle.getString(Keys.longfrom));
            Log.e("TAGdddddddddddddlatfrom", "data: " + bundle.getString(Keys.latto));
            Log.e("TAGdddddddddddddlatfrom", "data: " + bundle.getString(Keys.longto));


            isFromUpdateOrder = Boolean.parseBoolean(bundle.getString(Keys.isFromUpdate));

            if (isFromUpdateOrder) {
                imageUrl = bundle.getString(Keys.image1);
                image1 = loadUpdateImage(imageUrl, imgProduct1, "1");
                imageUrl2 = bundle.getString(Keys.image2);
                image2 = loadUpdateImage(imageUrl2, imgProduct2, "2");
                imageUrl3 = bundle.getString(Keys.image3);
                image3 = loadUpdateImage(imageUrl3, imgProduct3, "3");
                imageUrl4 = bundle.getString(Keys.image4);
                image4 = loadUpdateImage(imageUrl4, imgProduct4, "4");
                latfrom= bundle.getString(Keys.latfrom);
                latto= bundle.getString(Keys.latto);
                longfrom= bundle.getString(Keys.longfrom);
                longto= bundle.getString(Keys.longto);

            }
            productImagePath = imageUrl;


            if (isFromUpdateOrder) {

                if (imageUrl.equals("")) {
                    binding.imgProduct1.setVisibility(View.GONE);
                    binding.imgSelectProduct1.setVisibility(View.VISIBLE);
                } else {
                    binding.imgProduct1.setVisibility(View.VISIBLE);
                    binding.imgSelectProduct1.setVisibility(View.INVISIBLE);
                    // UseMe.stringArrayList.add(bundle.getString(Keys.image1));
                    UseMe.setImage(getActivity(), bundle.getString(Keys.image1), binding.imgProduct1);
                }

                if (imageUrl2.equals("")) {
                    binding.imgSelectProduct2.setVisibility(View.VISIBLE);
                    binding.imgProduct2.setVisibility(View.GONE);
                } else {
                    binding.imgSelectProduct2.setVisibility(View.INVISIBLE);
                    binding.imgProduct2.setVisibility(View.VISIBLE);

                    UseMe.setImage(getActivity(), bundle.getString(Keys.image2), binding.imgProduct2);

                }

                if (imageUrl3.equals("")) {
                    binding.imgSelectProduct3.setVisibility(View.VISIBLE);
                    binding.imgProduct3.setVisibility(View.GONE);
                } else {
                    binding.imgSelectProduct3.setVisibility(View.INVISIBLE);
                    binding.imgProduct3.setVisibility(View.VISIBLE);

                    UseMe.setImage(getActivity(), bundle.getString(Keys.image3), binding.imgProduct3);

                }

                if (imageUrl4.equals("")) {
                    binding.imgSelectProduct4.setVisibility(View.VISIBLE);
                    binding.imgProduct4.setVisibility(View.GONE);
                } else {
                    binding.imgSelectProduct4.setVisibility(View.INVISIBLE);
                    binding.imgProduct4.setVisibility(View.VISIBLE);
                    UseMe.setImage(getActivity(), bundle.getString(Keys.image4), binding.imgProduct4);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        view.findViewById(R.id.btnFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UseMe.uriArrayList.clear();


                if (isValid()) {
                    if (BottomnavigationActivity.productDetailArrayList.isEmpty()) {
                        BottomnavigationActivity.productDetailArrayList = new ArrayList<ProductDetail>();
                    }

                    if (productImageUri1 != null) {
                        UseMe.uriArrayList.add(productImageUri1);
                    }
                    if (productImageUri2 != null) {
                        UseMe.uriArrayList.add(productImageUri2);
                    }
                    if (productImageUri3 != null) {
                        UseMe.uriArrayList.add(productImageUri3);
                    }
                    if (productImageUri4 != null) {
                        UseMe.uriArrayList.add(productImageUri4);
                    }


                    Bundle bundle = new Bundle();

                    Log.d("TAG", "onClick:image1 " + image1);
                    Log.d("TAG", "onClick:image2 " + image2);
                    Log.d("TAG", "onClick:image3 " + image3);
                    Log.d("TAG", "onClick:image4 " + image4);

                    bundle.putString(Keys.productName, binding.edtAricleName.getText().toString());
                    bundle.putString(Keys.articleComment, binding.edtArticleComment.getText().toString());
                    bundle.putString(Keys.price, binding.edtPriceOfItem.getText().toString());
                    bundle.putString(Keys.productLink, binding.edtProductLink.getText().toString());
                    bundle.putString(Keys.qunt, binding.edtQntity.getText().toString());
                    // bundle.putString(Keys.traveller_id, travellerId);
                    // bundle.putString(Keys.trip_id, tripId);
                    bundle.putString(Keys.location_from, fromLocation);
                    bundle.putString(Keys.location_to, toLocation);
                    bundle.putString(Keys.date, deadLineDate);
                    bundle.putString(Keys.reward, reward);

                    bundle.putString(Keys.latfrom,latfrom);
                 bundle.putString(Keys.longfrom,longfrom);
                bundle.putString(Keys.latto,latto);
                   bundle.putString(Keys.longto,longto);


                    // bundle.putString(Keys.order_id, orderId);
                    //  bundle.putString(Keys.product_id, poroductId);
                    //  bundle.putBoolean(Keys.isFromUpdate, isFromUpdateOrder);
                    bundle.putString(Keys.imageUri1, productImageUri);

                    new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),
                            bundle, R.id.frameLayoout, new ShopperDeliveryFragment());

                }
            }
        });

        imgSelect1.setOnClickListener(v -> {
            selectImage("1");

        });
        imgProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage("1");
            }
        });

        imgProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1.equals("")) {
                    showImageAlert("Please Select First Image");
                } else {
                    selectImage("2");
                }
            }
        });

        imgSelect2.setOnClickListener(v -> {
            if (image1.equals("")) {
                showImageAlert("Please Select First Image");
            } else {
                selectImage("2");
            }
        });

        imgProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1.equals("")) {
                    showImageAlert("Please Select First Image");
                } else if (image2.equals("")) {
                    showImageAlert("Please Select Second Image");
                } else {
                    selectImage("3");
                }
            }
        });

        imgSelect3.setOnClickListener(v -> {
            if (image1.equals("")) {
                showImageAlert("Please Select First Image");
            } else if (image2.equals("")) {
                showImageAlert("Please Select Second Image");
            } else {
                selectImage("3");
            }
        });

        imgProduct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image1.equals("")) {
                    showImageAlert("Please Select First Image");
                } else if (image2.equals("")) {
                    showImageAlert("Please Select Second Image");
                } else if (image3.equals("")) {
                    showImageAlert("Please Select Third Image");
                } else {
                    selectImage("4");
                }
            }
        });

        imgSelect4.setOnClickListener(v -> {
            if (image1.equals("")) {
                showImageAlert("Please Select First Image");
            } else if (image2.equals("")) {
                showImageAlert("Please Select Second Image");
            } else if (image3.equals("")) {
                showImageAlert("Please Select Third Image");
            } else {
                selectImage("4");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                Uri resultUri = result.getUri();

                switch (s) {
                    case "1":
                        imgProduct1.setVisibility(View.VISIBLE);
                        imgSelect1.setVisibility(View.GONE);
                        imgProduct1.setImageURI(resultUri);
                        image1 = encodeImage(resultUri.getPath());
                        productImageUri = resultUri.toString();
                        productImageUri1 = resultUri;

                        break;
                    case "2":
                        imgProduct2.setVisibility(View.VISIBLE);
                        imgSelect2.setVisibility(View.GONE);
                        imgProduct2.setImageURI(resultUri);
                        image2 = encodeImage(resultUri.getPath());
                        productImageUri2 = resultUri;

                        break;
                    case "3":
                        imgProduct3.setVisibility(View.VISIBLE);
                        imgSelect3.setVisibility(View.GONE);
                        imgProduct3.setImageURI(resultUri);
                        image3 = encodeImage(resultUri.getPath());
                        productImageUri3 = resultUri;

                        break;
                    case "4":
                        imgProduct4.setVisibility(View.VISIBLE);
                        imgSelect4.setVisibility(View.GONE);
                        imgProduct4.setImageURI(resultUri);
                        image4 = encodeImage(resultUri.getPath());
                        productImageUri4 = resultUri;
                        break;
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
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

    public void selectImage(String s_) {
        s = s_;
        CropImage.activity(null)
                .start(getContext(), AddShopperFragment.this);
    }

    public boolean isValid() {
        boolean valid = true;

        if (binding.edtAricleName.getText().toString().length() == 0) {
            binding.edtAricleName.setError(getActivity().getResources().getString(R.string.enter_article_name));
            valid = false;
        } else if (binding.edtAricleName.getText().toString().length() > 499) {
            binding.edtAricleName.setError(getActivity().getResources().getString(R.string.enter_max_article_name));
            valid = false;
        } else if (binding.edtArticleComment.getText().toString().length() == 0) {
            binding.edtArticleComment.setError(getActivity().getResources().getString(R.string.article_comment));
            valid = false;
        } else if (binding.edtArticleComment.getText().toString().length() > 999) {
            binding.edtArticleComment.setError(getActivity().getResources().getString(R.string.article_max_length_comment));
            valid = false;
        } else if (!isValidUrl(binding.edtProductLink.getText().toString())) {
            binding.edtProductLink.setError(getActivity().getResources().getString(R.string.enter_product_link));
            valid = false;
        } else if (binding.edtProductLink.getText().toString().length() > 499) {
            binding.edtProductLink.setError(getActivity().getResources().getString(R.string.product_link_max_length_comment));
            valid = false;
        } else if (binding.edtPriceOfItem.getText().toString().length() == 0) {
            binding.edtPriceOfItem.setError(getActivity().getResources().getString(R.string.enter_product_price));
            valid = false;
        } else if (binding.edtQntity.getText().toString().length() == 0) {
            binding.edtQntity.setError(getString(R.string.quntity_error));
            valid = false;
        } else if (Integer.parseInt(binding.edtQntity.getText().toString()) > 20) {
            Toast.makeText(getActivity(), "Quantity not greater than 20", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!isFromUpdateOrder) {
            if (image1.equals("")) {
                showImageAlert("Please Add Product Image");
                valid = false;
            }
        }


        String strPrice = binding.edtPriceOfItem.getText().toString();

        if (strPrice.contains(".")) {


            String priceArr[] = strPrice.split(".");

            if (priceArr.length > 1) {
                String first = priceArr[0];
                String second = priceArr[1];
                if (first.length() > 13) {
                    binding.edtPriceOfItem.setError("Upper bound value should be less than 14");
                } else if (second.length() > 2) {
                    binding.edtPriceOfItem.setError("Lower bound value should be less than 3");

                }
                valid = false;
            }


        } else if (strPrice.length() > 13) {
            binding.edtPriceOfItem.setError("Price length should be less than 14 digits");
            valid = false;
        }


        //else if (Integer.parseInt(binding.edtQntity.getText().toString()>20))

        return valid;
    }

    private boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    String base64 = "";

    public String loadUpdateImage(String path, final ImageView imageView, String s) {


        Picasso.get().load(BASE_URL + "media/" + path).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                switch (s) {
                    case "1":
                        productImageUri1 = UseMe.getImageUri(getContext(), bitmap);
                        imageView.setImageBitmap(bitmap);
                        base64 = UseMe.getBase64(bitmap);
                        break;
                    case "2":
                        productImageUri2 = UseMe.getImageUri(getContext(), bitmap);
                        imageView.setImageBitmap(bitmap);
                        base64 = UseMe.getBase64(bitmap);
                        break;
                    case "3":
                        productImageUri3 = UseMe.getImageUri(getContext(), bitmap);
                        imageView.setImageBitmap(bitmap);
                        base64 = UseMe.getBase64(bitmap);
                        break;
                    case "4":
                        productImageUri4 = UseMe.getImageUri(getContext(), bitmap);
                        imageView.setImageBitmap(bitmap);
                        base64 = UseMe.getBase64(bitmap);
                        break;
                }

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


    void showImageAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void setImage(Context context, String path, ImageView imageView, String s) {

        Picasso.get()
                .load(BASE_URL + "media/" + path)
                .error(R.drawable.placeholder)
                .resize(400, 400)

                .placeholder(R.drawable.placeholder)
                .into(imageView);


//        Glide.with(context)
//                .load(Constant.WEB_HOST_URL+path)
//                .into(imageView);

    }


}