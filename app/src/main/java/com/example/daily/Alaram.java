package com.example.daily;

import android.content.BroadcastReceiver;
import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.example.daily.App.CHANNEL_1_ID;

public class Alaram extends BroadcastReceiver {
    final Random myRandom = new Random();
    int pho;
    String phone;
    @Override
    public void onReceive(Context context, Intent intent) {


                  NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            int notoficationId = intent.getIntExtra("NotifiactionId", 0);
            String message = intent.getStringExtra("todo").toString();
            phone = intent.getStringExtra("phone").toString();

            //pho = Integer.parseInt(phone);

        String pend = intent.getStringExtra("pending_amount").toString();
            Intent mainIntent = new Intent(context, AfterLogin.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);


            Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentTitle("Pending amount notification from Daily App")
                    .setContentText("Mr."+message+" has to pay: "+pend+" Rupees")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    //.setChannelId(CHANNEL_1_ID)
                    .build();
        notificationManager.notify(myRandom.nextInt(1000), notification);
        SmsManager smsMgrVar = SmsManager.getDefault();
            smsMgrVar.sendTextMessage(""+phone, null, "Mr."+message+" has to pay: "+pend+" Rupees", null, null);
        Toast.makeText(context, "Message Sent",
                Toast.LENGTH_LONG).show();

    }
}