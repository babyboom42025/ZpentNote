package com.example.zpentnote;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.zpentnote.Expense.ExpenseModel;
import com.example.zpentnote.Expense.myadapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView review;
    ArrayList<ExpenseModel> datalist;
    FirebaseFirestore db;
    myadapter adapter;
    PieChart degraph;
    long goal=0;
    long expense = 0;

    ScrollView scrollView2;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout2);
        swipeRefreshLayout.setOnRefreshListener(this);
        degraph = findViewById(R.id.degraph);
        review=findViewById(R.id.review);
        scrollView2 = findViewById(R.id.scroolView2);
        review.setLayoutManager (new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new myadapter(datalist);
        review.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        ConDetail();
        SetGraph();
    }
    private void ConDetail() {
        String receivedData = getIntent().getStringExtra("key");
        String receivedMonth = getIntent().getStringExtra("month");
        if(receivedData!=null){
            if (receivedData.equals("type1")){
                db.collection("expenses")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าอาหารและเครื่องดื่ม")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                    }
                                    System.out.println("expense1 = "+expense);
                                }
                            }
                        });

                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type1");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }
                        });
            }
            else if (receivedData.equals("type2")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าที่พักอาศัย")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }

                                }
                            }
                        });

                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type2");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type3")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าสาธารณูปโภค")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }

                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type3");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type4")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าเดินทาง")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }

                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type4");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type5")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าแต่งกาย")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }

                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type5");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type6")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าใช้จ่ายในบ้าน")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }

                                }
                            }
                        });

                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type6");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type7")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าใช้จ่ายเกี่ยวกับสุขภาพ")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }

                                }
                            }
                        });

                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type7");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type8")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าสันทนาการและสังคม")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type8");
                                        System.out.println("goal: " + document);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }
                        });
            }

            else if (receivedData.equals("type9")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","อื่นๆ...")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense += document.getLong("amount");
                                System.out.println("expense = "+expense);
                            }
                        }
                    }
                });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type9")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าของขวัญและการกุศล")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type10")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าใช้จ่ายทางการศึกษา")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type11")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าใช้จ่ายเกี่ยวกับพาหนะ")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type12")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าใช้จ่ายเกี่ยวกับธนาคาร")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type13")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ค่าใช้จ่ายเบ็ดเตล็ด")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

            else if (receivedData.equals("type14")){
                db.collection("expenses")
                        .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","อื่นๆ")
                        .whereEqualTo("month",receivedMonth)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d:list)
                                {
                                    ExpenseModel obj=d.toObject(ExpenseModel.class);
                                    datalist.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        expense += document.getLong("amount");
                                        System.out.println("expense = "+expense);
                                    }
                                }
                            }
                        });
                db.collection("Goal")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        goal += document.getLong("type9");
                                        System.out.println("goal: " + goal);
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                SetGraph();
                            }

                        });
            }

        }
    }

    private void SetGraph() {
        List<PieEntry> pieEntryList = new ArrayList<>();
        List<Integer> colorsList = new ArrayList<>();

        float remainingGoal = goal - expense; // Calculate the remaining goal

        if (remainingGoal > 0) {
            pieEntryList.add(new PieEntry(remainingGoal, "Goal")); // Add remaining goal entry
            colorsList.add(getResources().getColor(R.color.teal_700));
        }

        if (expense != 0) {
            pieEntryList.add(new PieEntry(expense, "Expense")); // Add expense entry
            colorsList.add(getResources().getColor(R.color.color2));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntryList, ""); // Set empty label
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        PieData pieData = new PieData(pieDataSet);
        degraph.setData(pieData);
        degraph.invalidate();

        degraph.getDescription().setText(" ");
        degraph.getDescription().setTextSize(12f);
        degraph.getDescription().setTextColor(getResources().getColor(R.color.black));
        degraph.getDescription().setEnabled(true);
    }

    @Override
    public void onRefresh() {
        SetGraph();
        scrollView2.smoothScrollTo(0, 0);
        swipeRefreshLayout.setRefreshing(false);

    }
}