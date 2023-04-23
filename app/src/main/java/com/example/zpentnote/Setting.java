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

import java.util.Calendar;

public class Setting extends AppCompatActivity {

    RelativeLayout editgoal,aboutUs;
    ImageView profileBtn;

    SwitchCompat notification;

    LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        editgoal=findViewById(R.id.editgoal);
        profileBtn=findViewById(R.id.profileBtn);
        notification=findViewById(R.id.Notification);
        aboutUs=findViewById(R.id.aboutUs);
        logout=findViewById(R.id.logOutButton);
            setAlarm1();
            setAlarm2();
            setAlarm3();


        editgoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Goal.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Profile.class));
            }
        });


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1234","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Setting.this,"notification");
                    builder.setContentTitle("ZPN");
                    builder.setContentTitle("Hello");
                    builder.setSmallIcon(R.drawable.zpn_tran);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Setting.this);
                    managerCompat.notify(1,builder.build());
                    Toast.makeText(getApplicationContext(),"Notification is ON",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(getApplicationContext(),"Notification is OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

               SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("remember","false");
               editor.apply();
               finish();
                startActivity(new Intent(getApplicationContext(),Login.class));
               System.out.println("Logout");
            }
        });

        
    }

    public void setAlarm1() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 14);
        calendar1.set(Calendar.MINUTE, 10);

        // Set the alarm to trigger at the specified time every day
        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
        System.out.println("1");
    }
    public void setAlarm2() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY, 14);
        calendar2.set(Calendar.MINUTE, 15);

        // Set the alarm to trigger at the specified time every day
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);
        System.out.println("2");
    }
    public void setAlarm3() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(System.currentTimeMillis());
        calendar3.set(Calendar.HOUR_OF_DAY, 14);
        calendar3.set(Calendar.MINUTE, 20);

        // Set the alarm to trigger at the specified time every day
        alarmManager3.setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent3);
        System.out.println("3");
    }
}