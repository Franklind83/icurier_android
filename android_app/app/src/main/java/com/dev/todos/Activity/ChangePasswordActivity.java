package com.dev.todos.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dev.todos.Model.Responce;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.ActivityChangePasswordBinding;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity {

    ActivityChangePasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password);

        setStatusBarGradiant(ChangePasswordActivity.this);

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
                        jsonObject.put("user_id", Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.USERID,""));
                        jsonObject.put("old_password", binding.edtOldPass.getText().toString());
                        jsonObject.put("new_password", binding.edtNewPass.getText().toString());
                        changePass(UseMe.getJsonObject(jsonObject));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void changePass(JsonObject jsonObject) {
        showLoading(ChangePasswordActivity.this);
        Call<Responce> responceCall = ApiClient.getService().changePass(jsonObject);
        responceCall.enqueue(new Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                hideLoading();
                if (response.body().getStatus().equals(Keys.status_succes)) {
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Old password not match", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t) {
                hideLoading();
            }
        });


    }

    public boolean isValid() {
        boolean valid = true;

        if (binding.edtOldPass.getText().toString().length() == 0) {
            binding.edtOldPass.setError(getString(R.string.err_enter_old_pass));
            valid = false;
        } else if (binding.edtNewPass.getText().toString().length() == 0) {
            binding.edtNewPass.setError(getString(R.string.error_password));
            valid = false;
        } else if (binding.edtNewPass.getText().toString().length() < 8) {
            binding.edtNewPass.setError("Enter more than 8 digit password");
            valid = false;
        } else if (!binding.edtConfirmPass.getText().toString().equals(binding.edtNewPass.getText().toString())) {
            binding.edtConfirmPass.setError(getString(R.string.error_con_password));
            valid = false;
        }
        return valid;
    }
}