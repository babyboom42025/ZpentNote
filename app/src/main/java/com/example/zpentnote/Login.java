package com.example.zpentnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button loginBtn;
    private TextView forgetBtn;
    private TextView registerBtn;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    CheckBox remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth=FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);


        SharedPreferences preferences =getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if (checkbox.equals("true")){
            Intent intent = new Intent(Login.this, MainActivity.class);
            System.out.println("check true");
            startActivity(intent);
        }else if (checkbox.equals("false")){
            Toast.makeText(this,"Please Sign In",Toast.LENGTH_SHORT).show();
            System.out.println("check false");
        }
        loginDetails();



        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override


            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(Login.this,"Checked",Toast.LENGTH_SHORT).show();
                    System.out.println("true");


                }else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(Login.this,"Unchecked",Toast.LENGTH_SHORT).show();
                    System.out.println("false");
                }
            }
        });
    }



    private void loginDetails(){
        email =findViewById(R.id.email);
        pass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        forgetBtn = findViewById(R.id.forgetBtn);
        registerBtn = findViewById(R.id.registerBtn);
        remember = findViewById(R.id.remember);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = email.getText().toString().trim();
                String passLogin = pass.getText().toString().trim();

                if(TextUtils.isEmpty(emailLogin)){
                        email.setError("Please enter a Email");
                    return;
                }
                if(TextUtils.isEmpty(passLogin)) {
                    pass.setError("Please enter a password.");
                    return;
                }

                mDialog.setMessage("Processing..");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(emailLogin,passLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            mDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login fail, Plese try again",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        //ไปยังหน้า register
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (getApplicationContext(),Register.class));
            }
        });
        //ไปยังหน้า ลืมรหัสผ่าน
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getApplicationContext(),ForgetID.class));
            }
        });



    }
}