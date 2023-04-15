package com.example.zpentnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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


        editgoal=findViewById(R.id.editgoal);
        profileBtn=findViewById(R.id.profileBtn);
        notification=findViewById(R.id.Notification);
        aboutUs=findViewById(R.id.aboutUs);
        logout=findViewById(R.id.logOutButton);

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
            NotificationChannel channel = new NotificationChannel("notification","notification", NotificationManager.IMPORTANCE_DEFAULT);
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
                    System.out.println(LocalTime.now());

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

    private void settimenotification() {
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = localTime.format(formatter);
        if (formattedDate == "00:50:00"){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Setting.this,"notification");
            builder.setContentTitle("ZPN");
            builder.setContentTitle("Hello");
            builder.setSmallIcon(R.drawable.zpn_tran);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Setting.this);
            managerCompat.notify(1,builder.build());
            Toast.makeText(getApplicationContext(),"Notification is ON",Toast.LENGTH_SHORT).show();
            System.out.println(LocalTime.now());
        }
    }
}