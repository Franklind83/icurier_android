package com.dev.todos.Fragment.More;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.se.omapi.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dev.todos.Activity.ContactActivity;
import com.dev.todos.Activity.MainActivity;
import com.dev.todos.Activity.SplashActivity;
import com.dev.todos.Activity.TermsActivity;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Model.Language.LangRequest;
import com.dev.todos.Model.Language.LangResponse;
import com.dev.todos.Model.Logout.LogoutRequest;
import com.dev.todos.Model.Logout.LogoutResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.BaseActivity;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentConfigrationBinding;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class ConfigurationFragment extends Fragment{

   FragmentConfigrationBinding binding;
    KProgressHUD hud;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_configration, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.txtName.setText(Sharedpreferences.readFromPreferences(getActivity(),AppConstant.USERNAME,""));
        BaseActivity.setProfile(getActivity(),Sharedpreferences.readFromPreferences(getActivity(),AppConstant.PROFILEIMAGE,""),binding.profileImage);

        binding.llProfile.setOnClickListener(view1 -> {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                      R.id.frameLayoout,new ProfileFragment());
        });

        binding.supportLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(),null,
                        R.id.frameLayoout,new SupportFragment());
            }
        });

        binding.logoutLl.setOnClickListener(view12 -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.logouttxt));
            builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    try {
                        logoutCall();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        });

        setOnClickListener();

    }

    private void setOnClickListener() {
        binding.faqLL.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TermsActivity.class);
            intent.putExtra("Activity","Faq");
            startActivity(intent);
        });

        binding.contactLL.setOnClickListener(v -> startActivity(new Intent(getContext(), ContactActivity.class)));

        binding.privacyLL.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),TermsActivity.class);
            intent.putExtra("Activity","Privacy");
            startActivity(intent);
        });

        binding.termsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TermsActivity.class);
                intent.putExtra("Activity","Terms");
                startActivity(intent);
            }
        });

        binding.languageLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomsheet.setVisibility(View.VISIBLE);

                binding.englishTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.bottomsheet.setVisibility(View.GONE);

                        new iOSDialogBuilder(getContext())
                                .setTitle(getString(R.string.toseechange))
                                .setCancelable(false)
                                .setBoldPositiveLabel(true)
                                .setPositiveListener(getString(R.string.ok),new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        Log.e("lang11",Sharedpreferences.readFromPreferences(getContext(), AppConstant.APPLANGUAGE,""));
                                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.APPLANGUAGE,"en");
                                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.lang_val,"true");


                                        language("en","English");
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeListener(getString(R.string.cancel), new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .build().show();
                    }
                });

                binding.spanishTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.bottomsheet.setVisibility(View.GONE);

                        new iOSDialogBuilder(getContext())
                                .setTitle(getString(R.string.toseechange))
                                .setBoldPositiveLabel(true)
                                .setCancelable(false)
                                .setPositiveListener(getString(R.string.ok),new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        Log.e("lang12",Sharedpreferences.readFromPreferences(getContext(), AppConstant.APPLANGUAGE,""));
                                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.APPLANGUAGE,"es");
                                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.lang_val,"false");

                                        language("es","Spanish");
                                        dialog.dismiss();

                                    }
                                })
                                .setNegativeListener(getString(R.string.cancel), new iOSDialogClickListener() {
                                    @Override
                                    public void onClick(iOSDialog dialog) {
                                        dialog.dismiss();
                                    }
                                })
                                .build().show();
                    }
                });


                binding.cancelTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.bottomsheet.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void logoutCall() {

        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setDevice("android");
        logoutRequest.setUserId(Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));


        Call<LogoutResponse> logoutResponseCall  = ApiClient.getService().logout(logoutRequest);
        logoutResponseCall.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.isSuccessful()){
                    hud.dismiss();
                    if(response.body().getStatus().equals("1")){
                        Log.e("lang13",Sharedpreferences.readFromPreferences(getContext(), AppConstant.APPLANGUAGE,""));
                        String language = Sharedpreferences.readFromPreferences(getContext(),AppConstant.APPLANGUAGE,"en");
                        Sharedpreferences.clearData(getActivity());
                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.APPLANGUAGE,language);
                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.lang_val,"false");
                        disconnectFromFacebook();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }else{
                        Log.e("lang14",Sharedpreferences.readFromPreferences(getContext(), AppConstant.APPLANGUAGE,""));
                        String language = Sharedpreferences.readFromPreferences(getContext(),AppConstant.APPLANGUAGE,"es");
                        Sharedpreferences.clearData(getActivity());
                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.APPLANGUAGE,language);disconnectFromFacebook();
                        Sharedpreferences.saveToPreferences(getContext(),AppConstant.lang_val,"true");disconnectFromFacebook();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                hud.dismiss();
            }
        });

    }



    public void disconnectFromFacebook()
    {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }

    private void language(String text, String spanish){
        getApiCall(spanish);

        Locale locale = new Locale(text);
        Configuration config = getContext().getResources().getConfiguration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());

    }

    private void getApiCall(String spanish) {
        LangRequest langRequest = new LangRequest();
        langRequest.setUserId(Sharedpreferences.readFromPreferences(getContext(),AppConstant.USERID,""));
        langRequest.setLanguage(spanish);

        Call<LangResponse> langResponseCall = ApiClient.getService().language(langRequest);
        langResponseCall.enqueue(new Callback<LangResponse>() {
            @Override
            public void onResponse(Call<LangResponse> call, Response<LangResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("1")){
                        UseMe.clearAllFragment(getFragmentManager());
                        startActivity(new Intent(getContext(), SplashActivity.class));
                        getActivity().finishAffinity();
                    }else{
                        UseMe.clearAllFragment(getFragmentManager());
                        startActivity(new Intent(getContext(), SplashActivity.class));
                        getActivity().finishAffinity();
                    }
                }
            }

            @Override
            public void onFailure(Call<LangResponse> call, Throwable t) {

            }
        });
    }

}
