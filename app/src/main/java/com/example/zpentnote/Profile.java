package com.example.zpentnote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    Button proBack,uploadBtn;
    CircleImageView profileBtn;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        proBack =findViewById(R.id.profileBack);
        profileBtn = findViewById(R.id.setting_profile_image);
        uploadBtn = findViewById(R.id.uploadProfileBtn);

        proBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Setting.class));
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri == null){
                    Toast.makeText(Profile.this,"Empty Image.",Toast.LENGTH_SHORT).show();
                }else {
                    uploadImage();
                    startActivity(new Intent(getApplicationContext(), Setting.class));
                    finish();
                }
            }
        });


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }

        });

        
    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setMessage("Processing..");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE ที่ dd เดือน MMMM พ.ศ. yyyy", new Locale("th", "TH"));
        Date now =new Date();
        String fileName ="profile";
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(Profile.this,"Upload Complete.",Toast.LENGTH_SHORT).show();
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(Profile.this,"Upload Error, Please try again.",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && data !=null && data.getData() !=null){

            imageUri =data.getData();
            profileBtn.setImageURI(imageUri);
        }
    }
}