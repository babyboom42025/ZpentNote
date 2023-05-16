package com.example.zpentnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class Setting extends AppCompatActivity {

    RelativeLayout editgoal,aboutUs;
    ImageView profileBtn;

    SwitchCompat notificationSwitch;

    FirebaseFirestore db;
    LinearLayout logout;

    FirebaseStorage storage;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        editgoal = findViewById(R.id.editgoal);
        profileBtn = findViewById(R.id.profileBtn);
        notificationSwitch = findViewById(R.id.Notification);
        aboutUs = findViewById(R.id.aboutUs);
        logout = findViewById(R.id.logOutButton);

        editgoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditGoal.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1234", "notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.co.th/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
                System.out.println("Logout");
            }
        });

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The notification switch is turned on
                    // Set up the notification schedule for 12:00 and 18:00
                    setNotificationSchedule(true);
                } else {
                    // The notification switch is turned off
                    // Cancel the notification schedule
                    setNotificationSchedule(false);
                }
            }
        });

    }

    private void setNotificationSchedule(boolean isEnabled) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("isNotificationOn", isEnabled);
        intent.putExtra("showAt12PM", isEnabled);
        intent.putExtra("showAt6PM", isEnabled);

        PendingIntent pendingIntent12PM = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent6PM = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar12PM = Calendar.getInstance();
        calendar12PM.set(Calendar.HOUR_OF_DAY, 12);
        calendar12PM.set(Calendar.MINUTE, 0);
        calendar12PM.set(Calendar.SECOND, 0);

        Calendar calendar6PM = Calendar.getInstance();
        calendar6PM.set(Calendar.HOUR_OF_DAY, 18);
        calendar6PM.set(Calendar.MINUTE, 0);
        calendar6PM.set(Calendar.SECOND, 0);

        if (isEnabled) {
            // Schedule the notification at 12:00 PM
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar12PM.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent12PM);

            // Schedule the notification at 6:00 PM
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar6PM.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent6PM);
        } else {
            // Cancel the notification schedules
            alarmManager.cancel(pendingIntent12PM);
            alarmManager.cancel(pendingIntent6PM);
        }
    }


}