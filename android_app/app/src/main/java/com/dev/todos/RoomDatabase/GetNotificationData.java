package com.dev.todos.RoomDatabase;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GetNotificationData {
    private AppDatabase db ;

    public GetNotificationData(){
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Todos").build();
    }

    public List<RoomDatabaseClass> getAllUsers() throws ExecutionException, InterruptedException {

            return new GetUsersAsyncTask().execute().get();

    }


    @SuppressLint("StaticFieldLeak")
    public class GetUsersAsyncTask extends AsyncTask<Void, Void, List<RoomDatabaseClass>>
    {
        @Override
        protected List<RoomDatabaseClass> doInBackground(Void... url) {
            return db.taskDao().getAll();
        }
    }
}
