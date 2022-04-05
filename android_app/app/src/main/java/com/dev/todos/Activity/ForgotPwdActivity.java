package com.dev.todos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.ActivityForgotPwdBinding;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.todos.Util.Keys.forgetPassword;

public class ForgotPwdActivity extends BaseActivity {

    ActivityForgotPwdBinding binding;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_pwd);

        setStatusBarGradiant(ForgotPwdActivity.this);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(Keys.email, binding.edtEmail.getText().toString());
                        forgetPassword(UseMe.getJsonObject(jsonObject));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public boolean isValid() {
        boolean valid = true;
        if (binding.edtEmail.getText().toString().length() == 0) {
            binding.edtEmail.setError(getApplicationContext().getResources().getString(R.string.error_email));
            valid = false;
        } else if (binding.edtEmail.getText().toString().matches(ContactsContract.CommonDataKinds.Email.ADDRESS)) {
            binding.edtEmail.setError(getApplicationContext().getResources().getString(R.string.error_valid_email));
            valid = false;

        }

        return valid;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Link to reset password send on your E mail Id. Please reset the password and login again.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();

    }

    public void forgetPassword(JsonObject jsonObject) {
        showLoading(ForgotPwdActivity.this);

        Call<Responce> responceCall = ApiClient.getService().forgetPass(jsonObject);

        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hideLoading();

                String result =response.body().getMsg();


                if (response.body().getStatus().equals(Keys.status_succes)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPwdActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.ok_alert, null);
                    Button oktext = view.findViewById(R.id.oktext);
                    TextView mainTxt = view.findViewById(R.id.mainTxt);
                    mainTxt.setText("New Password has been sent to your Email Address");
                    oktext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            finish();
                        }
                    });
                    builder.setView(view);
                    alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    alertDialog.show();


                    //  showDialog();

                } else {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hideLoading();
            }
        });
    }
}