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
import android.widget.ScrollView;
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

    TextView addExpenses,itemPrice1,itemPrice2,itemPrice3,itemPrice4,itemPrice5,itemPrice6,itemPrice7,itemPrice8,itemPrice9;

    LinearLayout mType1,mType2,mType3,mType4,mType5,mType6,mType7,mType8,mType9;

    ImageView setting;

    PieChart pieChart;
    ScrollView scrollView;

    String[] items = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    AutoCompleteTextView autoCompleteTxt;
    long expense1 =0,expense2= 0,expense3= 0,expense4= 0,expense5= 0,expense6= 0,expense7= 0,expense8= 0,expense9= 0;
    long goal1 = 0,goal2 = 0,goal3 = 0,goal4 = 0,goal5 = 0,goal6 = 0,goal7 = 0,goal8 = 0,goal9 = 0;

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
        Datatype2();
        Datatype3();
        Datatype4();
        Datatype5();
        Datatype6();
        Datatype7();
        Datatype8();
        Datatype9();
        setUpGraph();

        addExpenses = findViewById(R.id.addExpenses);
        setting = findViewById(R.id.setting);
        pieChart  =findViewById(R.id.pieChart);
        mType2 = findViewById(R.id.mType2);
        mType3 = findViewById(R.id.mType3);
        mType4 = findViewById(R.id.mType4);
        mType5 = findViewById(R.id.mType5);
        mType6 = findViewById(R.id.mType6);
        mType7 = findViewById(R.id.mType7);
        mType8 = findViewById(R.id.mType8);
        mType9 = findViewById(R.id.mType9);
        scrollView =findViewById(R.id.ScrollView);

        System.out.println("Data in graph"+expense1);


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
                String dataToSend = "type1";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });
        mType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type2";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });
        mType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type3";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });
        mType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type4";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });

        mType5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type5";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });

        mType6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type6";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });

        mType7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type7";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });

        mType8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type8";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });

        mType9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type9";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                startActivity(intent);
            }
        });
    }


    private void setUpGraph() {
        List<PieEntry> pieEntryList=new ArrayList<>();
        List<Integer> colorsList=new ArrayList<>();
        if (expense1!=0){
            pieEntryList.add(new PieEntry(expense1,"type1"));
            colorsList.add(getResources().getColor(R.color.color1));
        }
        if (expense2!=0){
            pieEntryList.add(new PieEntry(expense2,"type2"));
            colorsList.add(getResources().getColor(R.color.color2));
        }
        if (expense3!=0){
            pieEntryList.add(new PieEntry(expense3,"type3"));
            colorsList.add(getResources().getColor(R.color.color3));
        }
        if (expense4!=0){
            pieEntryList.add(new PieEntry(expense4,"type4"));
            colorsList.add(getResources().getColor(R.color.color4));
        }
        if (expense5!=0){
            pieEntryList.add(new PieEntry(expense5,"type5"));
            colorsList.add(getResources().getColor(R.color.color5));
        }
        if (expense6!=0){
            pieEntryList.add(new PieEntry(expense6,"type6"));
            colorsList.add(getResources().getColor(R.color.color6));
        }
        if (expense7!=0){
            pieEntryList.add(new PieEntry(expense7,"type7"));
            colorsList.add(getResources().getColor(R.color.color7));
        }
        if (expense8!=0){
            pieEntryList.add(new PieEntry(expense8,"type8"));
            colorsList.add(getResources().getColor(R.color.color_min));
        }
        if (expense9!=0){
            pieEntryList.add(new PieEntry(expense9,"type9"));
            colorsList.add(getResources().getColor(R.color.color_max));
        }
        PieDataSet pieDataSet=new PieDataSet(pieEntryList,String.valueOf
                (expense1-expense2-expense3-expense4-expense5-expense6-expense7-expense8-expense9));
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        PieData pieDat = new PieData(pieDataSet);

        if (pieChart != null) {
            pieChart.setData(pieDat);
            pieChart.invalidate();
            pieChart.setEnabled(true);
            System.out.println("Data in graph"+expense1);
        }

    }

    @Override
    public void onRefresh() {
        expense1 = 0;
        goal1 = 0;
        System.out.println("refresh expense :"+expense1);
        System.out.println("refresh goal :"+goal1);
        scrollView.smoothScrollTo(0, 0);
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
                                System.out.println("goal: "+document);}
                            updateColor1(expense1, goal1);
                            System.out.println("goal "+goal1);
                            System.out.println("expense "+expense1);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    private void updateColor1(long expense1, long goal1) {
        if (goal1 == 0 && expense1 != 0) {
            System.out.println("5");
            mType1.setBackgroundResource(R.color.color_max);
        } else if (goal1 != 0 && expense1 == 0) {
            System.out.println("6");
            mType1.setBackgroundResource(R.color.color_min);
        } else if (expense1 <= goal1 * 0.5) {
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
        System.out.println("expense " + expense1);
    }


    public void Datatype2(){
        mType2 = findViewById(R.id.mType2);
        itemPrice2 = findViewById(R.id.itemPrice2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทการโดยสาร")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense2 += document.getLong("amount");
                                String expenseT2 = Long.toString(expense2).trim();
                                itemPrice2.setText(expenseT2);
                                System.out.println("expense type2 "+expense2);
                                System.out.println("Price type2 "+itemPrice2);
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
                                goal2 += document.getLong("type2");
                                System.out.println("goal: "+document);
                            }
                            if(goal2==0 && expense2!=0){
                                System.out.println("5");
                                mType2.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal2!=0 && expense2==0){
                                System.out.println("6");
                                mType2.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense2 <= goal2 * 0.5) {
                                mType2.setBackgroundResource(R.color.color_min);
                            } else if (expense2 <= goal2 * 0.7) {
                                mType2.setBackgroundResource(R.color.color_mid);
                            } else if (expense2 <= goal2) {
                                mType2.setBackgroundResource(R.color.color_max);
                            } else if (expense2 > goal2) {
                                mType2.setBackgroundResource(R.color.color_over);
                            } else {
                                mType2.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal2 "+goal2);
                            System.out.println("expense2 "+expense2);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void Datatype3(){
        mType3 = findViewById(R.id.mType3);
        itemPrice3 = findViewById(R.id.itemPrice3);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทอาหาร")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense3 += document.getLong("amount");
                                String expenseT3 = Long.toString(expense3).trim();
                                itemPrice3.setText(expenseT3);
                                System.out.println("expense type2 "+expense3);
                                System.out.println("Price type2 "+itemPrice3);
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
                                goal3 += document.getLong("type3");
                                System.out.println("goal: "+document);
                            }
                            if(goal3==0 && expense3!=0){
                                System.out.println("5");
                                mType3.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal3!=0 && expense3==0){
                                System.out.println("6");
                                mType3.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense3 <= goal3 * 0.5) {
                                mType3.setBackgroundResource(R.color.color_min);
                            } else if (expense3 <= goal3 * 0.7) {
                                mType3.setBackgroundResource(R.color.color_mid);
                            } else if (expense3 <= goal3) {
                                mType3.setBackgroundResource(R.color.color_max);
                            } else if (expense3 > goal3) {
                                mType3.setBackgroundResource(R.color.color_over);
                            } else {
                                mType3.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal3 "+goal3);
                            System.out.println("expense3 "+expense3);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void Datatype4(){
        mType4 = findViewById(R.id.mType4);
        itemPrice4 = findViewById(R.id.itemPrice4);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทการออกกำลังกาย")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense4 += document.getLong("amount");
                                String expenseT4 = Long.toString(expense4).trim();
                                itemPrice4.setText(expenseT4);
                                System.out.println("expense type4 "+expense4);
                                System.out.println("Price type4 "+itemPrice4);
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
                                goal4 += document.getLong("type4");
                                System.out.println("goal: "+document);
                            }
                            if(goal4==0 && expense4!=0){
                                System.out.println("5");
                                mType4.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal4!=0 && expense4==0){
                                System.out.println("6");
                                mType4.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense4 <= goal4 * 0.5) {
                                mType4.setBackgroundResource(R.color.color_min);
                            } else if (expense4 <= goal4 * 0.7) {
                                mType4.setBackgroundResource(R.color.color_mid);
                            } else if (expense4 <= goal4) {
                                mType4.setBackgroundResource(R.color.color_max);
                            } else if (expense4 > goal4) {
                                mType4.setBackgroundResource(R.color.color_over);
                            } else {
                                mType4.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal4 "+goal4);
                            System.out.println("expense4 "+expense4);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void Datatype5(){
        mType5 = findViewById(R.id.mType5);
        itemPrice5 = findViewById(R.id.itemPrice5);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทความบันเทิง")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense5 += document.getLong("amount");
                                String expenseT5 = Long.toString(expense5).trim();
                                itemPrice5.setText(expenseT5);
                                System.out.println("expense type5 "+expense5);
                                System.out.println("Price type5 "+itemPrice5);
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
                                goal5 += document.getLong("type5");
                                System.out.println("goal: "+document);
                            }
                            if(goal5==0 && expense5!=0){
                                System.out.println("5");
                                mType5.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal5!=0 && expense5==0){
                                System.out.println("6");
                                mType5.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense5 <= goal5 * 0.5) {
                                mType5.setBackgroundResource(R.color.color_min);
                            } else if (expense5 <= goal5 * 0.7) {
                                mType5.setBackgroundResource(R.color.color_mid);
                            } else if (expense5 <= goal5) {
                                mType5.setBackgroundResource(R.color.color_max);
                            } else if (expense5 > goal5) {
                                mType5.setBackgroundResource(R.color.color_over);
                            } else {
                                mType5.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal5 "+goal5);
                            System.out.println("expense5 "+expense5);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void Datatype6(){
        mType6 = findViewById(R.id.mType6);
        itemPrice6 = findViewById(R.id.itemPrice6);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทโทรศัพท์มือถือ")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense6 += document.getLong("amount");
                                String expenseT6 = Long.toString(expense6).trim();
                                itemPrice6.setText(expenseT6);
                                System.out.println("expense type6 "+expense6);
                                System.out.println("Price type6 "+itemPrice6);
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
                                goal6 += document.getLong("type6");
                                System.out.println("goal: "+document);
                            }
                            if(goal6==0 && expense6!=0){
                                System.out.println("5");
                                mType6.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal6!=0 && expense6==0){
                                System.out.println("6");
                                mType6.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense6 <= goal6 * 0.5) {
                                mType6.setBackgroundResource(R.color.color_min);
                            } else if (expense6 <= goal6 * 0.7) {
                                mType6.setBackgroundResource(R.color.color_mid);
                            } else if (expense6 <= goal6) {
                                mType6.setBackgroundResource(R.color.color_max);
                            } else if (expense6 > goal6) {
                                mType6.setBackgroundResource(R.color.color_over);
                            } else {
                                mType6.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal6 "+goal6);
                            System.out.println("expense6 "+expense6);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void Datatype7(){
        mType7 = findViewById(R.id.mType7);
        itemPrice7 = findViewById(R.id.itemPrice7);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทสังสรรค์")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense7 += document.getLong("amount");
                                String expenseT7 = Long.toString(expense7).trim();
                                itemPrice7.setText(expenseT7);
                                System.out.println("expense type7 "+expense7);
                                System.out.println("Price type7 "+itemPrice7);
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
                                goal7 += document.getLong("type7");
                                System.out.println("goal: "+document);
                            }
                            if(goal7==0 && expense7!=0){
                                System.out.println("5");
                                mType7.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal7!=0 && expense7==0){
                                System.out.println("6");
                                mType7.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense7 <= goal7 * 0.5) {
                                mType7.setBackgroundResource(R.color.color_min);
                            } else if (expense7 <= goal7 * 0.7) {
                                mType7.setBackgroundResource(R.color.color_mid);
                            } else if (expense7 <= goal7) {
                                mType7.setBackgroundResource(R.color.color_max);
                            } else if (expense7 > goal7) {
                                mType7.setBackgroundResource(R.color.color_over);
                            } else {
                                mType7.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal7 "+goal7);
                            System.out.println("expense7 "+expense7);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void Datatype8(){
        mType8 = findViewById(R.id.mType8);
        itemPrice8 = findViewById(R.id.itemPrice8);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","ประเภทจิปาถะ")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense8 += document.getLong("amount");
                                String expenseT8 = Long.toString(expense8).trim();
                                itemPrice8.setText(expenseT8);
                                System.out.println("expense type8 "+expense8);
                                System.out.println("Price type8 "+itemPrice8);
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
                                goal8 += document.getLong("type8");
                                System.out.println("goal: "+document);
                            }
                            if(goal8==0 && expense8!=0){
                                System.out.println("5");
                                mType8.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal8!=0 && expense8==0){
                                System.out.println("6");
                                mType8.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense8 <= goal8 * 0.5) {
                                mType8.setBackgroundResource(R.color.color_min);
                            } else if (expense8 <= goal8 * 0.7) {
                                mType8.setBackgroundResource(R.color.color_mid);
                            } else if (expense8 <= goal8) {
                                mType8.setBackgroundResource(R.color.color_max);
                            } else if (expense8> goal8) {
                                mType8.setBackgroundResource(R.color.color_over);
                            } else {
                                mType8.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal8 "+goal8);
                            System.out.println("expense8 "+expense8);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    public void Datatype9(){
        mType9 = findViewById(R.id.mType9);
        itemPrice9 = findViewById(R.id.itemPrice9);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category","อื่นๆ...")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                expense9 += document.getLong("amount");
                                String expenseT9 = Long.toString(expense9).trim();
                                itemPrice9.setText(expenseT9);
                                System.out.println("expense type9 "+expense9);
                                System.out.println("Price type9 "+itemPrice9);
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
                                goal9 += document.getLong("type9");
                                System.out.println("goal: "+document);
                            }
                            if(goal9==0 && expense9!=0){
                                System.out.println("5");
                                mType9.setBackgroundResource(R.color.color_max);
                            }
                            else if(goal8!=0 && expense8==0){
                                System.out.println("6");
                                mType9.setBackgroundResource(R.color.color_min);
                            }
                            else if (expense9<= goal9 * 0.5) {
                                mType9.setBackgroundResource(R.color.color_min);
                            } else if (expense9 <= goal9 * 0.7) {
                                mType9.setBackgroundResource(R.color.color_mid);
                            } else if (expense9 <= goal9) {
                                mType9.setBackgroundResource(R.color.color_max);
                            } else if (expense9> goal9) {
                                mType9.setBackgroundResource(R.color.color_over);
                            } else {
                                mType9.setBackgroundResource(R.color.color_def);
                            }

                            System.out.println("goal9 "+goal9);
                            System.out.println("expense9"+expense9);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

}