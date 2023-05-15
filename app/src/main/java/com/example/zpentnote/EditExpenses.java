package com.example.zpentnote;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;





public class EditExpenses extends AppCompatActivity {


    private FirebaseFirestore db;
    private DocumentReference expenseRef;
    String category,uid,note;
    long amount;
    Spinner edttype;
    TextView edtnote,edtamount,edtupdate,edtdelete,edtback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenses);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        String expenseid = intent.getStringExtra("expenseId");
        uid = intent.getStringExtra("uid");
        note = intent.getStringExtra("note");
        category = intent.getStringExtra("category");
        amount = intent.getLongExtra("amount", 0);
        edtupdate = findViewById(R.id.edtupdate);
        edtdelete = findViewById(R.id.edtdelete);
        edtback = findViewById(R.id.edtback);
        edttype = findViewById(R.id.edttype);
        edtamount = findViewById(R.id.edtamount);
        edtnote = findViewById(R.id.edtnote);


        db = FirebaseFirestore.getInstance();
        expenseRef = db.collection("expenses").document(expenseid);
        showData();

        edtupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long newAmount = Long.parseLong(edtamount.getText().toString());
                String newNote = edtnote.getText().toString();
                String newCategory = edttype.getSelectedItem().toString();
                updateFields(newNote, newCategory, newAmount);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        edtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        edtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    private void DeleteData() {
        expenseRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }


    private void showData() {


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);

        // Specify the layout for the dropdown menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edttype.setAdapter(adapter);

        if (category != null) {
            int position = adapter.getPosition(category);
            edttype.setSelection(position);
        }
        edtnote.setText(note);
        edtamount.setText(String.valueOf(amount));


    }


    private void updateFields(String newNote, String newCategory,long newAmount) {
        expenseRef.update(
                        "note", newNote,
                        "category", newCategory,
                        "amount", newAmount
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Update Successfully.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Update Error.",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}