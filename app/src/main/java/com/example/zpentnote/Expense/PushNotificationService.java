package com.example.zpentnote.Expense;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.example.zpentnote.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remotemessage) {

        String title = remotemessage.getNotification().getTitle();
        String body = remotemessage.getNotification().getBody();
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATIONS";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this,CHANNEL_ID)
                .setContentText(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.zpn_white)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1,notification.build());
        super.onMessageReceived(remotemessage);

    }
}
