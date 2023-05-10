package com.example.zpentnote;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView addExpenses,itemPrice1;

    LinearLayout mType1,mType2,mType3,mType4;

    ImageView setting;

    PieChart pieChart;

    String[] items = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    AutoCompleteTextView autoCompleteTxt;
    long expense1 =0,expense2= 0;
    long goal1 = 0,goal2 = 0;

    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

       autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String item = parent.getItemAtPosition(position).toString();
               Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
           }
       });
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Datatype1();
        System.out.println(expense1);
        System.out.println(goal1);

        addExpenses = findViewById(R.id.addExpenses);
        setting = findViewById(R.id.setting);
        pieChart  =findViewById(R.id.pieChart);
        mType2 = findViewById(R.id.mType2);
        mType3 = findViewById(R.id.mType3);
        mType4 = findViewById(R.id.mType4);




        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddExpenses.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Setting.class));
            }
        });



        mType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Details.class));
            }
        });
        mType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Details.class));
            }
        });
        mType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Details.class));
            }
        });
        mType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Details.class));
            }
        });
    }
    @Override
    public void onRefresh() {
        expense1 = 0;
        goal1 = 0;
        System.out.println("refresh expense :"+expense1);
        System.out.println("refresh goal :"+goal1);
        Datatype1();
        swipeRefreshLayout.setRefreshing(false);
    }


    public void Datatype1(){
        mType1 = findViewById(R.id.mType1);
        itemPrice1 = findViewById(R.id.itemPrice1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทหนังสือ")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense1 += document.getLong("amount");
                                String expense = Long.toString(expense1).trim();
                                itemPrice1.setText(expense);
                                System.out.println("expense"+expense1);
                                System.out.println("Price"+itemPrice1);
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection("Goal")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                goal1 += document.getLong("type1");
                                System.out.println("goal: "+document);
                            }
                            if(goal1==0 && expense1!=0){
                                System.out.println("5");
                                mType1.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal1!=0 && expense1==0){
                                System.out.println("6");
                                mType1.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense1 <= goal1 * 0.5) {
                                mType1.setBackgroundResource(R.color.color_min);
                            } else if (expense1 <= goal1 * 0.7) {
                                mType1.setBackgroundResource(R.color.color_mid);
                            } else if (expense1 <= goal1) {
                                mType1.setBackgroundResource(R.color.color_max);
                            } else if (expense1 > goal1) {
                                mType1.setBackgroundResource(R.color.color_over);
                            } else {
                                mType1.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal "+goal1);
                            System.out.println("expense "+expense1);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }



}