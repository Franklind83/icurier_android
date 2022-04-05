package com.dev.todos.Fragment.Notification;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Adapter.NotificationAdapter;
import com.dev.todos.R;
import com.dev.todos.RoomDatabase.AppDatabase;
import com.dev.todos.RoomDatabase.GetNotificationData;
import com.dev.todos.RoomDatabase.RoomDatabaseClass;
import com.dev.todos.RoomDatabase.TaskDao;
import com.dev.todos.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationFragment extends Fragment {

    FragmentNotificationBinding binding;
    List<RoomDatabaseClass> taskList;

    List<RoomDatabaseClass> taskListnew = new ArrayList<>();

    NotificationAdapter adapter;
    AppDatabase db;
    GetNotificationData userServices;

    RoomDatabaseClass roomDatabaseClass = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "Todos")
                .build();

        userServices = new GetNotificationData();
        try {
            taskList = userServices.getAllUsers();
            Collections.reverse(taskList);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        initView();
        getBack();
    }

    private void getBack() {

        if (taskList.size() == 0) {

        } else {
            for (int i = 0; i < taskList.size(); i++) {
                roomDatabaseClass = new RoomDatabaseClass();
                ;
                roomDatabaseClass.setId(taskList.get(i).getId());
                roomDatabaseClass.setIsRead("1");
                roomDatabaseClass.setTitle(taskList.get(i).getTitle());
                roomDatabaseClass.setSubtitle(taskList.get(i).getSubtitle());
                roomDatabaseClass.setUserid(taskList.get(i).getUserid());
                roomDatabaseClass.setCurrentTime(taskList.get(i).getCurrentTime());
                Log.d("TAG", "onClick: " + roomDatabaseClass.getId());

                taskListnew.add(roomDatabaseClass);
            }


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            // and now I am updating the element in databaseRoom
                            db.taskDao().updateData(taskListnew);
                        }
                    });
                }
            });
        }
    }

    private void initView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(taskList.size()==0){
            binding.notxt.setVisibility(View.VISIBLE);
        }else{
            binding.notxt.setVisibility(View.GONE);
        }

        if(adapter ==null){
            adapter = new NotificationAdapter(getActivity());
            binding.recyclerView.setAdapter(adapter);
            adapter.setUpList(taskList);
        }else{
            adapter.setUpList(taskList);
        }


    }

}