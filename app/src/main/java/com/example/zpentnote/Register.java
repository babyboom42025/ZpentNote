package com.example.zpentnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private EditText reEmail;
    private EditText rePassword;
    private EditText confirmPass;
    private Button regisBtn;

    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mAuth=FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        registeration();
    }

    private void registeration() {
        reEmail =findViewById(R.id.reEmail);
        rePassword =findViewById(R.id.rePassword);
        confirmPass = findViewById(R.id.confirmPass);
        regisBtn = findViewById(R.id.RegisBtn);

        regisBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String emailTxt=reEmail.getText().toString().trim();
                String passTxt = rePassword.getText().toString().trim();
                String conPassTxt = confirmPass.getText().toString().trim();

                if (TextUtils.isEmpty(emailTxt)){
                    reEmail.setError("Please enter a Email.");
                    return;
                }
                if (TextUtils.isEmpty(passTxt)){
                    rePassword.setError("Please enter a Password.");
                    return;
                }

                if(emailTxt.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                    reEmail.setError("Please enter type of email.");
                    return;
                }

                if (TextUtils.isEmpty(conPassTxt)){
                    confirmPass.setError("Please confirm your password.");
                    return;
                }
                if(!passTxt.equals(conPassTxt)){
                    confirmPass.setError("Passwords do not match, Please enter again.");
                    return;
                }
                mDialog.setMessage("Processing...");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(emailTxt,passTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Register complete",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Goal.class));
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Register error, please try again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }
}