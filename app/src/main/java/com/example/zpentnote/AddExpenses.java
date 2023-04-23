package com.example.zpentnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zpentnote.Expense.ExpenseModel;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public class AddExpenses extends AppCompatActivity {


    Spinner Excategory;
    TextView save,back;
    private ExpenseModel expenseModel;

    EditText Examount,Exnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Excategory = findViewById(R.id.category);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);



        expenseModel=(ExpenseModel)getIntent().getSerializableExtra("Expense");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createExpense();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }

    private void createExpense() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        Examount = findViewById(R.id.amount);
        Exnote = findViewById(R.id.note);

        String expenseId = UUID.randomUUID().toString();
        String note = Exnote.getText().toString().trim();
        String category = Excategory.getSelectedItem().toString().trim();
        String amount = Examount.getText().toString().trim();
        String time = formattedDateTime.trim();


        if (category.equals("...")) {

            Toast.makeText(AddExpenses.this,"Please select category.",Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(amount)) {

            Examount.setError("โปรดกรอกจำนวน");
            return;
        }
        if (!TextUtils.isEmpty(amount)&&!category.equals("...")){
            ExpenseModel expenseModel = new ExpenseModel(expenseId,note,category,Long.parseLong(amount),time,
                    FirebaseAuth.getInstance().getUid());

            FirebaseFirestore
                    .getInstance()
                    .collection("expenses")
                    .document(expenseId)
                    .set(expenseModel);
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            Toast.makeText(AddExpenses.this,"Add Expense Complete.",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(AddExpenses.this,"Add Expense Error, Please try again.",Toast.LENGTH_SHORT).show();
        }



    }
}