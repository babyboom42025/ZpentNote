package com.example.zpentnote;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create the notification and show it
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1234")
                .setSmallIcon(R.drawable.zpn_black_tran)
                .setContentTitle("ZPN")
                .setContentText("ได้เวลาบันทึกข้อมูลรายจ่ายแล้ว เริ่มกันเลย")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }
}

