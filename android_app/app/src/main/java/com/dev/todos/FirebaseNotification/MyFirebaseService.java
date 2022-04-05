package com.dev.todos.FirebaseNotification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.R;
import com.dev.todos.RoomDatabase.DatabaseClient;
import com.dev.todos.RoomDatabase.RoomDatabaseClass;
import com.dev.todos.Sharedpreferences;
import com.dev.todos.Util.AppConstant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import java.util.Random;


public class MyFirebaseService extends FirebaseMessagingService {

    Intent intent;
    static public Map<String, String> notificationData;
    RoomDatabaseClass roomDatabaseClass;


    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> data = message.getData();
        Log.d("dev", "sendMyNotification: " + message + "---" + message.getData());

            Date currentTime = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            roomDatabaseClass = new RoomDatabaseClass();
            roomDatabaseClass.setTitle(data.get("title"));
            roomDatabaseClass.setIsRead("0");
            roomDatabaseClass.setUserid(Sharedpreferences.readFromPreferences(getApplicationContext(), AppConstant.USERID, ""));
            roomDatabaseClass.setSubtitle(data.get("body"));
            roomDatabaseClass.setCurrentTime(sdf.format(currentTime));
            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .taskDao()
                    .insert(roomDatabaseClass);

        sendMyNotification(data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendMyNotification(Map<String, String> data) {



        try {

            notificationData = data;
            intent = new Intent(this, BottomnavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Activity","Notification");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int notificationId = new Random().nextInt(60000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Do something for lollipop and above versions
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Todos", importance);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                   /* .setColor(getResources().getColor(R.color.colorPrimary))*/
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_SOUND)
                    .setContentTitle(data.get("title"))
                    .setContentText(data.get("body"))
                    .setAutoCancel(true)
                    .setTicker(data.get("title")) // Sets the text that is displayed in the status bar when the cricket_notification first arrives
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("body")))
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(notificationId, notificationBuilder.build());
        } else {
            // do something for phones running an SDK before lollipop
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_SOUND)
                    .setContentTitle(data.get("title"))
                    .setContentText(data.get("body"))
                    .setAutoCancel(true)
                    .setTicker(data.get("title")) // Sets the text that is displayed in the status bar when the cricket_notification first arrives
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("body")))
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notificationBuilder.build());
        }

        }catch (Exception e){}
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("token",""+token);
        Sharedpreferences.saveToPreferences(getApplicationContext(), AppConstant.TOKEN,token);
    }
}