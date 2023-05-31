package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.View;
import android.app.NotificationManager;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button button;
    String CHANNEL_ID = "CUSTOM_NOTIFICATION_CHANNEL_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void addNotification() {
        @SuppressLint("RemoteViewLayout") RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.activity_notification_view);
       // notificationLayout.setTextViewText(R.id.title, "Notifications Example");
        notificationLayout.setTextViewText(R.id.text, "The latest reading of your blood pressure is critical");
        notificationLayout.setTextViewText(R.id.text1, "170");
        notificationLayout.setTextViewText(R.id.text2, "120");
        notificationLayout.setTextViewText(R.id.text3, "Sys");
        notificationLayout.setTextViewText(R.id.text4, "Dia");
        notificationLayout.setImageViewResource(R.id.imageView3 , R.drawable.leading);
        notificationLayout.setImageViewResource(R.id.imageView2 , R.drawable.rpm_logo);
        Notification customNotification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle())
                        .setCustomContentView(notificationLayout).setCustomHeadsUpContentView(notificationLayout)
                        .setCustomBigContentView(notificationLayout).setShowWhen(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        Intent notificationIntent = new Intent(this, NotificationView.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int notificationId = new Random().nextInt(100);
        createNotificationChannel();
        notificationIntent.putExtra("message", "Hello This is my new Notification");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, customNotification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setLightColor(Color.BLUE);
            channel.enableLights(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}