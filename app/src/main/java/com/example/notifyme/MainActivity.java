package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    NotificationManager notificationManager;
    private static  final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private  static  final int NOTIFICATION_ID = 0;
    private Button updateNotification;
    private Button cancelNotification;
    private Button notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notify = findViewById(R.id.button_notify);
        updateNotification = findViewById(R.id.button_update);
        cancelNotification = findViewById(R.id.button_cancel);
        createNotification();

        setNotificationButtonState(true,false, false);

        sendNotification();


        updateNotification();

        cancelNotification();


    }


    private void cancelNotification() {
        cancelNotification.setOnClickListener(v->{
            cancel();
            setNotificationButtonState(true, false, false);
        });


    }

    private void cancel() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void updateNotification() {
        updateNotification.setOnClickListener(v->{
            update();
            setNotificationButtonState(false, false, true);
        });

    }

    private  void setNotificationButtonState(Boolean isNotify, Boolean isUpdate, Boolean isCanceled){
       notify.setEnabled(isNotify);
       updateNotification.setEnabled(isUpdate);
       cancelNotification.setEnabled(isCanceled);
    }

    private void update() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder builder = getNotificationBuilder();
        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap).setBigContentTitle("Notification Update"));
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void sendNotification() {
        notify.setOnClickListener(v->{
            send();
            setNotificationButtonState(false, true, true);

        });

    }

    private void send() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder(){

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Hey")
                .setContentText("Another day to code")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return  builder;
    }

    public void onCLick(View view) {
        sendNotification();

    }

  public void  createNotification(){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID, "Mascot Notifications", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setDescription("Noitificstion from mascot");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}