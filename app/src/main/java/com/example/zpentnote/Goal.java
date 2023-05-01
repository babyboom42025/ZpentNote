package com.example.zpentnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zpentnote.Expense.ExpenseModel;
import com.example.zpentnote.Expense.GoalModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class Goal extends AppCompatActivity {

    EditText Gtype1,Gtype2,Gtype3,Gtype4,Gtype5,Gtype6,Gtype7,Gtype8,Gtype9;
    private GoalModel goalModel;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        goalModel=(GoalModel) getIntent().getSerializableExtra("Goal");
        submit =findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGoal();
            }
        });

    }

    private void createGoal() {
        String gid = UUID.randomUUID().toString();
        Gtype1 = findViewById(R.id.type1);
        Gtype2 = findViewById(R.id.type2);
        Gtype3 = findViewById(R.id.type3);
        Gtype4 = findViewById(R.id.type4);
        Gtype5 = findViewById(R.id.type5);
        Gtype6 = findViewById(R.id.type6);
        Gtype7 = findViewById(R.id.type7);
        Gtype8 = findViewById(R.id.type8);
        Gtype9 = findViewById(R.id.type9);



        String type1 = Gtype1.getText().toString().trim();
        String type2 = Gtype2.getText().toString().trim();
        String type3 = Gtype3.getText().toString().trim();
        String type4 = Gtype4.getText().toString().trim();
        String type5 = Gtype5.getText().toString().trim();
        String type6 = Gtype6.getText().toString().trim();
        String type7 = Gtype7.getText().toString().trim();
        String type8 = Gtype8.getText().toString().trim();
        String type9 = Gtype9.getText().toString().trim();

        if (type1.equals("0")&&type2.equals("0")&&type3.equals("0")&&type4.equals("0")&&type5.equals("0")&&type6.equals("0")
                &&type7.equals("0")&&type8.equals("0")&&type9.equals("0")) {

            Toast.makeText(Goal.this,"Please provide at least one information to proceed.",Toast.LENGTH_SHORT).show();
            return;
        }

        GoalModel goalModel = new GoalModel(Long.parseLong(type1),Long.parseLong(type2),Long.parseLong(type3),Long.parseLong(type4),Long.parseLong(type5),
                Long.parseLong(type6),Long.parseLong(type7),Long.parseLong(type8),
                Long.parseLong(type9),FirebaseAuth.getInstance().getUid(),gid);


        FirebaseFirestore
                .getInstance()
                .collection("Goal")
                .document(gid)
                .set(goalModel);
        finish();
        Toast.makeText(Goal.this,"SetUp Complete, Welcome.",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}