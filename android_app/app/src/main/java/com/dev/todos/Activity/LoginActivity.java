package com.dev.todos.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.dev.todos.Instagram.AuthenticationDialog;
import com.dev.todos.Instagram.AuthenticationListener;
import com.dev.todos.Instagram.RequestInstagramAPI;
import com.dev.todos.Model.Login.LoginRequest;
import com.dev.todos.Model.Login.LoginResponse;
import com.dev.todos.Model.SocailLogin.SocailLoginResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements AuthenticationListener {

    private ActivityLoginBinding binding;
    CallbackManager callbackManager;
    String token;

    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "Login";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        StrictMode.ThreadPolicy tp = StrictMode.ThreadPolicy.LAX;
        StrictMode.setThreadPolicy(tp);
        Log.e("lang3",Sharedpreferences.readFromPreferences(LoginActivity.this, AppConstant.APPLANGUAGE,""));
        if (Sharedpreferences.readFromPreferences(LoginActivity.this, AppConstant.APPLANGUAGE,"en").equals("en")){
            setLocale(LoginActivity.this,"en");
        }else{
            setLocale(LoginActivity.this,"es");
        }
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("512732257360-plngf38ga5d4cf6kmeokq57gh9isrrpl.apps.googleusercontent.com")
                //.requestIdToken("512732257360-i5ikqkkcc3i28q801mt6prgkpcg7nrvs.apps.googleusercontent.com")
//
                .requestIdToken(getString(R.string.default_web_client_id))

                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Now we will attach a click listener to the sign_in_button
        //and inside onClick() method we are calling the signIn() method that will open
        //google sign in intent
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        FacebookSdk.sdkInitialize(LoginActivity.this);
//        if (android.os.Build.VERSION.SDK_INT > 17) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        callbackManager = CallbackManager.Factory.create();

        initView();
        transparentStatusAndNavigation();

        onClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(this, TermsActivity.class));
        }
    }
    private void initView() {

        callbackManager = CallbackManager.Factory.create();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.TOKEN,token);
                        Log.d("token::::-", token);
                    }
                });


            try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "com.dev.todos",
                        PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {

            } catch (NoSuchAlgorithmException e) {

            }


    }

    private void onClickListener() {
        binding.loginBn.setOnClickListener(view -> loginApiCall());
        binding.forgetPassTxt.setOnClickListener(view -> {

        });
        binding.signUpTxt.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        });

        binding.forgetPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,ForgotPwdActivity.class));
            }
        });

       binding.imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == binding.imgFacebook) {
                    loginWithFacebook();
                }

            }
        });

        binding.imgInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AuthenticationDialog authenticationDialog = new AuthenticationDialog(LoginActivity.this, LoginActivity.this);
                authenticationDialog.setCancelable(true);
                authenticationDialog.show();
            }
        });

    }


    public void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
                LoginActivity.this,
                Arrays.asList("email"));


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.e("Response_facebook====>", "" + response.toString());
                                        Log.e("Response_objecttt====>", "" + object);
                                        try {

                                            String first_name = object.getString("first_name");
                                            Log.e("First_name", "" + first_name);

                                            String last_name = object.getString("last_name");
                                            Log.e("last_name", "" + last_name);
                                            String id = object.getString("id");
                                            Log.e("Name", "" + id);

//                                            facebook_name =first_name.toString()+" "+last_name.toString();
                                            String facebook_name = first_name.toString() + " " + last_name.toString();
                                            Log.e("Name", "" + facebook_name);
                                            String facebook_email = "";

                                            try {

                                                facebook_email = object.getString("email");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                facebook_email = "";
                                            }
                                            Log.e("Email", "" + facebook_email);
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put(Keys.login_from, "fb");
                                                jsonObject.put(Keys.social_id, id);
                                                jsonObject.put(Keys.token_from, "android");
                                                jsonObject.put(Keys.user_token,  Sharedpreferences.readFromPreferences(LoginActivity.this, AppConstant.TOKEN,""));
                                                jsonObject.put(Keys.name, first_name + " " + last_name);
                                                jsonObject.put(Keys.email, facebook_email);
                                                getSucessFacebook(UseMe.getJsonObject(jsonObject));

                                            } catch (Exception e) {
                                                e.printStackTrace();

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }



                                    // App code
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    private void getSucessFacebook(JsonObject jsonObject) {
        showLoading(LoginActivity.this);
        Call<SocailLoginResponse> socailLoginResponseCall = ApiClient.getService().socialLogin(jsonObject);
        socailLoginResponseCall.enqueue(new Callback<SocailLoginResponse>() {
            @Override
            public void onResponse(Call<SocailLoginResponse> call, Response<SocailLoginResponse> response) {
                hideLoading();
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        if(response.body().getUserDetails().getMobile().equals("")) {
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.IS_LOGIN, true);

                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USERID, response.body().getUserDetails().getUserId());
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USERNAME, response.body().getUserDetails().getName());
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USEREMAIL, response.body().getUserDetails().getEmail());

                            Intent i = new Intent(LoginActivity.this,BottomnavigationActivity.class);
                            i.putExtra("Activity","NewLogin");
                            startActivity(i);
                        }else{
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.IS_LOGIN, true);
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USERID, response.body().getUserDetails().getUserId());
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USERNAME, response.body().getUserDetails().getName());
                            Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USEREMAIL, response.body().getUserDetails().getEmail());

                            Intent i = new Intent(LoginActivity.this,BottomnavigationActivity.class);
                            i.putExtra("Activity","NewLogin");
                            startActivity(i);
                            finishAffinity();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SocailLoginResponse> call, Throwable t) {
                hideLoading();
            }
        });
    }


    private void loginApiCall() {

        if(binding.emailEt.getText().toString().isEmpty()){
            binding.emailEt.setError("Enter email");
            binding.emailEt.requestFocus();
        }else if (!binding.emailEt.getText().toString().matches(getString(R.string.email_pattern))){
            binding.emailEt.setError("Enter correct email");
            binding.emailEt.requestFocus();
        }else if(binding.passwordEt.getText().toString().isEmpty()){
            binding.passwordEt.setError("Enter password");
            binding.passwordEt.requestFocus();
        }else{
            try {
                showLoading(LoginActivity.this);
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(binding.emailEt.getText().toString());
                loginRequest.setPassword(binding.passwordEt.getText().toString());
                loginRequest.setTokenFrom(getString(R.string.token));
                loginRequest.setUserToken(Sharedpreferences.readFromPreferences(LoginActivity.this, AppConstant.TOKEN, ""));

                Call<LoginResponse> loginResponseCall = ApiClient.getService().login(loginRequest);
                loginResponseCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            hideLoading();
                            if (response.body().getStatus().equals("1")) {
                                Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.IS_LOGIN, true);
                                Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USERID, response.body().getUserDetails().getUserId());
                                Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.USERNAME, response.body().getUserDetails().getName());
                                Sharedpreferences.saveToPreferences(LoginActivity.this, AppConstant.PROFILEIMAGE, response.body().getUserDetails().getProfileImage());

                                Intent i = new Intent(LoginActivity.this, BottomnavigationActivity.class);
                                i.putExtra("Activity", "NewLogin");
                                startActivity(i);
                                finishAffinity();
                            } else {
                                showSnackbar(response.body().getMsg());
                            }
                        } else {
                            showSnackbar(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hideLoading();
                        showSnackbar(getString(R.string.nointernet));
                    }
                });
            }catch (Exception ex)
            {
                showSnackbar(ex.getMessage().toString());
            }
        }

    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS ,false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onTokenReceived(String auth_token) {
        new RequestInstagramAPI(token).execute();

    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e("user detasils",user.getEmail());
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put(Keys.login_from, "gmail");
                                jsonObject.put(Keys.social_id, user.getUid());
                                jsonObject.put(Keys.token_from, "android");
                                jsonObject.put(Keys.user_token,  Sharedpreferences.readFromPreferences(LoginActivity.this, AppConstant.TOKEN,""));
                                jsonObject.put(Keys.name, user.getDisplayName());
                                jsonObject.put(Keys.email, user.getEmail());

                                getSucessFacebook(UseMe.getJsonObject(jsonObject));

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                            Toast.makeText(LoginActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
