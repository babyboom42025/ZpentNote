package com.example.zpentnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class EditGoal extends AppCompatActivity {

    FirebaseFirestore db;
    TextInputEditText typeEdit1,typeEdit2,typeEdit3,typeEdit4,typeEdit5,typeEdit6,typeEdit7,typeEdit8,typeEdit9;
    TextView goalUpdate,goalDelete,goalBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        db=FirebaseFirestore.getInstance();
        typeEdit1 = findViewById(R.id.typeEdit1);
        typeEdit2 = findViewById(R.id.typeEdit2);
        typeEdit3 = findViewById(R.id.typeEdit3);
        typeEdit4 = findViewById(R.id.typeEdit4);
        typeEdit5 = findViewById(R.id.typeEdit5);
        typeEdit6 = findViewById(R.id.typeEdit6);
        typeEdit7 = findViewById(R.id.typeEdit7);
        typeEdit8 = findViewById(R.id.typeEdit8);
        typeEdit9 = findViewById(R.id.typeEdit9);
        goalUpdate = findViewById(R.id.goalupdate);
        goalDelete = findViewById(R.id.goaldelete);
        goalBack = findViewById(R.id.goalback);
        
        showGoalData();
        
        goalUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateGoalData();
                startActivity(new Intent(getApplicationContext(),Setting.class));
            }
        });
        goalDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteGoalData();
                startActivity(new Intent(getApplicationContext(),Setting.class));
            }
        });
        goalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Setting.class));
            }
        });

    }

    private void DeleteGoalData() {
        CollectionReference goalsRef = db.collection("Goal");
        String uid = FirebaseAuth.getInstance().getUid();

        goalsRef.whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String documentId = documentSnapshot.getId();

                            // Update the document with 0 for all fields
                            goalsRef.document(documentId)
                                    .update(
                                            "type1", 0L,
                                            "type2", 0L,
                                            "type3", 0L,
                                            "type4", 0L,
                                            "type5", 0L,
                                            "type6", 0L,
                                            "type7", 0L,
                                            "type8", 0L,
                                            "type9", 0L
                                    )
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Delete Successfully.",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Delete Error.",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
    }

    private void UpdateGoalData() {

        CollectionReference goalsRef = db.collection("Goal");
        String uid = FirebaseAuth.getInstance().getUid();

        goalsRef.whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String documentId = documentSnapshot.getId();

                            // Update the document with user input data
                            goalsRef.document(documentId)
                                    .update(
                                            "type1", Long.parseLong(typeEdit1.getText().toString()),
                                            "type2", Long.parseLong(typeEdit2.getText().toString()),
                                            "type3", Long.parseLong(typeEdit3.getText().toString()),
                                            "type4", Long.parseLong(typeEdit4.getText().toString()),
                                            "type5", Long.parseLong(typeEdit5.getText().toString()),
                                            "type6", Long.parseLong(typeEdit6.getText().toString()),
                                            "type7", Long.parseLong(typeEdit7.getText().toString()),
                                            "type8", Long.parseLong(typeEdit8.getText().toString()),
                                            "type9", Long.parseLong(typeEdit9.getText().toString())
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
                });
    }

    private void showGoalData() {
        CollectionReference goalsRef = db.collection("Goal");
        DocumentReference goalDocRef = goalsRef.document("gid");
        goalsRef.whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String type1 = String.valueOf(documentSnapshot.getLong("type1"));
                            String type2 = String.valueOf(documentSnapshot.getLong("type2"));
                            String type3 = String.valueOf(documentSnapshot.getLong("type3"));
                            String type4 = String.valueOf(documentSnapshot.getLong("type4"));
                            String type5 = String.valueOf(documentSnapshot.getLong("type5"));
                            String type6 = String.valueOf(documentSnapshot.getLong("type6"));
                            String type7 = String.valueOf(documentSnapshot.getLong("type7"));
                            String type8 = String.valueOf(documentSnapshot.getLong("type8"));
                            String type9 = String.valueOf(documentSnapshot.getLong("type9"));

                            // Update your TextInputEditText fields with the retrieved data
                            // Repeat this for other fields
                            typeEdit1.setText(type1);
                            typeEdit2.setText(type2);
                            typeEdit3.setText(type3);
                            typeEdit4.setText(type4);
                            typeEdit5.setText(type5);
                            typeEdit6.setText(type6);
                            typeEdit7.setText(type7);
                            typeEdit8.setText(type8);
                            typeEdit9.setText(type9);
                            // Repeat this for other fields
                        }
                    }
                });
    }


}