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

    }



    private void ConDetail() {
        String receivedData = getIntent().getStringExtra("key");
        if(receivedData!=null){
            if (receivedData.equals("type1")){
                db.collection("expenses")
                        .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                        .whereEqualTo("category","ประเภทหนังสือ")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทการโดยสาร")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทอาหาร")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทการออกกำลังกาย")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทความบันเทิง")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทโทรศัพท์มือถือ")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทสังสรรค์")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .whereEqualTo("category","ประเภทจิปาถะ")
                        .orderBy("time", Query.Direction.DESCENDING)
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
                        .orderBy("time", Query.Direction.DESCENDING)
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
        List<PieEntry> pieEntryList=new ArrayList<>();
        List<Integer> colorsList=new ArrayList<>();
        if (goal!=0){
            pieEntryList.add(new PieEntry(goal,"Goal"));
            colorsList.add(getResources().getColor(R.color.teal_700));
        }
        if (expense!=0){
            pieEntryList.add(new PieEntry(expense,"Expense"));
            colorsList.add(getResources().getColor(R.color.color2));
        }
        PieDataSet pieDataSet=new PieDataSet(pieEntryList,String.valueOf(goal-expense));
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        PieData pieDat = new PieData(pieDataSet);


        degraph.setData(pieDat);
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