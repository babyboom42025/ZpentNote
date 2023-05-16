package com.example.zpentnote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class NotificationReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "MyNotificationChannel";
    private static final CharSequence CHANNEL_NAME = "My Notifications";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isNotificationOn = intent.getBooleanExtra("isNotificationOn", false);
        boolean showAt12PM = intent.getBooleanExtra("showAt12PM", false);
        boolean showAt6PM = intent.getBooleanExtra("showAt6PM", false);

        if (isNotificationOn) {
            if ((showAt12PM && isTimeMatches(12, 0)) || (showAt6PM && isTimeMatches(18, 0))) {
                showNotification(context);
            }
        }
    }

    private boolean isTimeMatches(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        return (currentHour == hour && currentMinute == minute);
    }

    private void showNotification(Context context) {
        // Create the notification channel (for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.zpn_black)
                .setContentTitle("แจ้งเตือน")
                .setContentText("Notification Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
