package com.dev.todos.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Callback;

import static com.dev.todos.Url.WebService.BASE_URL;

public class UseMe {

    public static void selectDate(Context context, final TextView textView) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int m = month + 1;
                textView.setText(dayOfMonth + "/" +  m + "/" + year);
                textView.setError(null);

            }
        }, year, month, day);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,"IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public static JsonObject getJsonObject(JSONObject jsonObject) {
        return new JsonParser().parse(jsonObject.toString()).getAsJsonObject().getAsJsonObject();
    }

    public static String getBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;

    }

    public static String getBase64(String path) {
        Bitmap bitmap = null;
        try {
            bitmap = Picasso.get().load(BASE_URL + "media/" + path).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;

    }

    public static String getDateFormat(String date) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = simpleDateFormat.parse(date);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formatedDate = simpleDateFormat.format(d);
        return formatedDate;

    }

    public static ArrayList<Uri> uriArrayList = new ArrayList<>();
    public static ArrayList<String> stringArrayList = new ArrayList<>();

        public static void isBottomNavVisible(boolean visible) {

        if (visible) {
            BottomnavigationActivity.llMain.setVisibility(View.VISIBLE);
        } else {
            BottomnavigationActivity.llMain.setVisibility(View.GONE);

        }

    }


    public static void clearAllFragment(FragmentManager fragmentManager) {

        for (int i = 0; i < fragmentManager.getBackStackEntryCount() - 1; i++) {
            fragmentManager.popBackStack();


        }
    }


    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;

    }

    public static byte[] buffer;


    public static class LongOperation extends AsyncTask<Bitmap, String, String> {

        private Callback callback;
        private ProgressDialog dialog;
        public int position = 0;
        public Context context;

        public LongOperation(Callback callback, int position, Context context) {

            this.position = position;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected String doInBackground(Bitmap... params) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            params[0].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            buffer = new byte[10240];//specify the size to allow

            buffer = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(buffer, Base64.DEFAULT);

            return encoded;
        }


        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Please Wait");
            dialog.show();
        }


        @Override
        protected void onPostExecute(String result) {
            dialog.cancel();

            callback.callback(result, position);

        }


    }


    public static interface Callback {

        public void callback(final String imageValue, int position);
    }


    public static void setImage(Context context, String path, ImageView imageView) {

            Picasso.get()
                    .load(BASE_URL + "media/" + path)
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

        }

    public static void showImageAlert(String msg, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    public static String getBase64StringFromUri(String uri) {

        InputStream inputStream = null;
        String encodedFile = "", lastVal = "";
        try {
            File f = new File(uri);

            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }
}
