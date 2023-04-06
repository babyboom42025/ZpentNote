package com.example.zpentnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetID extends AppCompatActivity {

    private EditText forgetEmail;
    private Button resetBtn;
    private ProgressBar progressBar;
    private ProgressDialog mDialog;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_id);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mDialog = new ProgressDialog(this);
        forgetEmail = findViewById(R.id.forgetEmail);
        resetBtn = findViewById(R.id.ResetBtn);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }
    private void resetPassword (){

        String email = forgetEmail.getText().toString().trim();

        if(email.isEmpty()){
            forgetEmail.setError("Please enter the email you want to change.");
            forgetEmail.requestFocus();
            return;
        }
        if (!email.equals("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$")){
            forgetEmail.setError("Please enter a type of Email");
            forgetEmail.requestFocus();
            return;
        }
        mDialog.setMessage("Processing..");
        mDialog.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mDialog.dismiss();
                    Toast.makeText(ForgetID.this,"Completed, please check your email for changing your password.",Toast.LENGTH_LONG).show();
                }else {
                    mDialog.dismiss();
                    Toast.makeText(ForgetID.this,"Error, please try again",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}