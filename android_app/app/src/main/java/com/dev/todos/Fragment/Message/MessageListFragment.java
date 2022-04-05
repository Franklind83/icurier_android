package com.dev.todos.Fragment.Message;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.todos.Adapter.MessageListAdapter;
import com.dev.todos.Model.MessageList.ChatResponse;
import com.dev.todos.Network.ApiClient;
import com.dev.todos.R;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessageListFragment extends Fragment {

    RecyclerView recyclerView;
    TextView txtNoChat;
    private KProgressHUD hud;
  //  ClsSharePreferance sharePreferance;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_list_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //UseMe.isBottomNavVisible(true);
      ////  progressDialog=UseMe.showProgressDialog(getActivity());

        try {
            UseMe.isBottomNavVisible(true);
        }catch (Exception e){}
        recyclerView=view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtNoChat=view.findViewById(R.id.txtNoOrder);
       // sharePreferance=new ClsSharePreferance(getActivity());
    }

    @Override
    public void onStart() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Keys.user_id, Sharedpreferences.readFromPreferences(getActivity(), AppConstant.USERID,""));
            getChatList(UseMe.getJsonObject(jsonObject));

        }catch (Exception e){
            e.printStackTrace();
        }
        super.onStart();
    }

    public void getChatList(JsonObject jsonObject){
        hud=   KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Call<ChatResponse> chatResponceCall= ApiClient.getService().getRecentChat(jsonObject);

        chatResponceCall.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                hud.dismiss();
                if (response.body().getStatus().equals(Keys.status_succes)){
                    recyclerView.setAdapter(new MessageListAdapter(getActivity(),getFragmentManager(),response.body().getChatList()));
                    txtNoChat.setVisibility(View.GONE);
                }
                else {

                    txtNoChat.setVisibility(View.VISIBLE);
                    txtNoChat.setText(getString(R.string.nochat));

                }

            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                hud.dismiss();
            }
        });



    }
}
