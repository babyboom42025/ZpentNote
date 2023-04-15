package com.example.zpentnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked){
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
}