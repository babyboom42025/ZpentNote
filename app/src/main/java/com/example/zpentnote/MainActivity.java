package com.example.zpentnote;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView addExpenses, itemPrice1, itemPrice2, itemPrice3, itemPrice4, itemPrice5, itemPrice6, itemPrice7, itemPrice8, itemPrice9,Month, itemPrice10, itemPrice11, itemPrice12, itemPrice13, itemPrice14;

    LinearLayout mType1, mType2, mType3, mType4, mType5, mType6, mType7, mType8, mType9, mType10, mType11, mType12, mType13, mType14;

    ImageView setting;
    String selectedMonthValue = "";
    PieChart pieChart;
    ScrollView scrollView;
    String selectmonth = "";
    Spinner monthSpinner;

    long expense1 = 0, expense2 = 0, expense3 = 0, expense4 = 0, expense5 = 0, expense6 = 0, expense7 = 0, expense8 = 0, expense9 = 0, expense10 = 0, expense11 = 0, expense12 = 0, expense13 = 0, expense14 = 0;
    long goal1 = 0, goal2 = 0, goal3 = 0, goal4 = 0, goal5 = 0, goal6 = 0, goal7 = 0, goal8 = 0, goal9 = 0, goal10 = 0, goal11 = 0, goal12 = 0, goal13 = 0, goal14 = 0;

    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        ShowMonth();

        monthSpinner = findViewById(R.id.monthmain);
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        monthSpinner.setSelection(currentMonth);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMonth = parent.getItemAtPosition(position).toString();
                String[] monthValues = getResources().getStringArray(R.array.MonthValues); // Retrieve the array of month values
                int index = Arrays.asList(getResources().getStringArray(R.array.Month)).indexOf(selectedMonth); // Find the index of the selected month
                if (index >= 0 && index < monthValues.length) {
                    selectedMonthValue = monthValues[index]; // Get the corresponding month value
                    Toast.makeText(getApplicationContext(), "Selected Month Value: " + selectedMonthValue, Toast.LENGTH_SHORT).show();
                    expense1 = 0;
                    expense2 = 0;
                    expense3 = 0;
                    expense4 = 0;
                    expense5 = 0;
                    expense6 = 0;
                    expense7 = 0;
                    expense8 = 0;
                    expense9 = 0;
                    expense10 = 0;
                    expense11 = 0;
                    expense12 = 0;
                    expense13 = 0;
                    expense14 = 0;
                    mType1.setBackgroundResource(R.color.color_def);
                    mType2.setBackgroundResource(R.color.color_def);
                    mType3.setBackgroundResource(R.color.color_def);
                    mType4.setBackgroundResource(R.color.color_def);
                    mType5.setBackgroundResource(R.color.color_def);
                    mType6.setBackgroundResource(R.color.color_def);
                    mType7.setBackgroundResource(R.color.color_def);
                    mType8.setBackgroundResource(R.color.color_def);
                    mType9.setBackgroundResource(R.color.color_def);
                    mType10.setBackgroundResource(R.color.color_def);
                    mType11.setBackgroundResource(R.color.color_def);
                    mType12.setBackgroundResource(R.color.color_def);
                    mType13.setBackgroundResource(R.color.color_def);
                    mType14.setBackgroundResource(R.color.color_def);


                    Datatype1();
                    Datatype2();
                    Datatype3();
                    Datatype4();
                    Datatype5();
                    Datatype6();
                    Datatype7();
                    Datatype8();
                    Datatype9();
                    Datatype10();
                    Datatype12();
                    Datatype13();
                    Datatype14();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


        addExpenses = findViewById(R.id.addExpenses);
        setting = findViewById(R.id.setting);
        mType1 = findViewById(R.id.mType1);
        mType2 = findViewById(R.id.mType2);
        mType3 = findViewById(R.id.mType3);
        mType4 = findViewById(R.id.mType4);
        mType5 = findViewById(R.id.mType5);
        mType6 = findViewById(R.id.mType6);
        mType7 = findViewById(R.id.mType7);
        mType8 = findViewById(R.id.mType8);
        mType9 = findViewById(R.id.mType9);
        mType10 = findViewById(R.id.mType10);
        mType11 = findViewById(R.id.mType11);
        mType12 = findViewById(R.id.mType12);
        mType13 = findViewById(R.id.mType13);
        mType14 = findViewById(R.id.mType14);
        scrollView = findViewById(R.id.ScrollView);
        pieChart = findViewById(R.id.graph);


        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddExpenses.class));
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
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });
        mType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type2";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });
        mType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type3";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });
        mType4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type4";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });
        mType5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type5";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type6";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type7";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
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
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type10";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type11";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type12";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type13";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

        mType14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataToSend = "type14";
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("key", dataToSend);
                intent.putExtra("month",selectedMonthValue);
                startActivity(intent);
            }
        });

    }

    private void ShowMonth() {
        Month = findViewById(R.id.currentMonth);
        TimeZone thaiTimeZone = TimeZone.getTimeZone("Asia/Bangkok");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", Locale.US);
        dateFormat.setTimeZone(thaiTimeZone);
        String currentDate = dateFormat.format(calendar.getTime());
        Month.setText(currentDate);
    }


    private void setUpGraph() {

        List<PieEntry> pieEntryList = new ArrayList<>();
        List<Integer> colorsList = new ArrayList<>();
        if (expense1 != 0) {
            pieEntryList.add(new PieEntry(expense1, "1"));
            colorsList.add(getResources().getColor(R.color.color1));
        }
        if (expense2 != 0) {
            pieEntryList.add(new PieEntry(expense2, "2"));
            colorsList.add(getResources().getColor(R.color.color2));
        }
        if (expense3 != 0) {
            pieEntryList.add(new PieEntry(expense3, "3"));
            colorsList.add(getResources().getColor(R.color.color3));
        }
        if (expense4 != 0) {
            pieEntryList.add(new PieEntry(expense4, "4"));
            colorsList.add(getResources().getColor(R.color.color4));
        }
        if (expense5 != 0) {
            pieEntryList.add(new PieEntry(expense5, "5"));
            colorsList.add(getResources().getColor(R.color.color5));
        }
        if (expense6 != 0) {
            pieEntryList.add(new PieEntry(expense6, "6"));
            colorsList.add(getResources().getColor(R.color.color6));
        }
        if (expense7 != 0) {
            pieEntryList.add(new PieEntry(expense7, "7"));
            colorsList.add(getResources().getColor(R.color.color7));
        }
        if (expense8 != 0) {
            pieEntryList.add(new PieEntry(expense8, "8"));
            colorsList.add(getResources().getColor(R.color.color8));
        }
        if (expense9 != 0) {
            pieEntryList.add(new PieEntry(expense9, "9"));
            colorsList.add(getResources().getColor(R.color.color9));
        }
        if (expense10 != 0) {
            pieEntryList.add(new PieEntry(expense10, "10"));
            colorsList.add(getResources().getColor(R.color.color10));
        }
        if (expense11 != 0) {
            pieEntryList.add(new PieEntry(expense11, "11"));
            colorsList.add(getResources().getColor(R.color.color11));
        }
        if (expense12 != 0) {
            pieEntryList.add(new PieEntry(expense12, "12"));
            colorsList.add(getResources().getColor(R.color.color12));
        }
        if (expense13 != 0) {
            pieEntryList.add(new PieEntry(expense13, "13"));
            colorsList.add(getResources().getColor(R.color.color13));
        }
        if (expense14 != 0) {
            pieEntryList.add(new PieEntry(expense14, "14"));
            colorsList.add(getResources().getColor(R.color.color14));
        }
        String totalExpensesLabel = "Total Expenses: " + (expense1 + expense2 + expense3 + expense4 + expense5
                + expense6 + expense7 + expense8 + expense9 + expense10 + expense11 + expense12 + expense13 + expense14);
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,totalExpensesLabel);
        pieDataSet.setColors(colorsList);
        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
        PieData pieDat = new PieData(pieDataSet);


        pieChart.setData(pieDat);
        pieChart.invalidate();

        pieChart.getDescription().setText("Number is type");
        pieChart.getDescription().setTextSize(12f);
        pieChart.getDescription().setTextColor(getResources().getColor(R.color.black));
        pieChart.getDescription().setEnabled(true);


        Con1();
        Con2();
        Con3();
        Con4();
        Con5();
        Con6();
        Con7();
        Con8();
        Con9();
        Con10();
        Con11();
        Con12();
        Con13();
        Con14();
    }

    @Override
    public void onRefresh() {
        scrollView.smoothScrollTo(0, 0);
        swipeRefreshLayout.setRefreshing(false);
        setUpGraph();
        ShowMonth();
    }




    private void Con1() {
        if(goal1==0 && expense1 == 0){
            mType1.setBackgroundResource(R.color.color_def);
        } else if(goal1 == 0 && expense1 != 0){
            mType1.setBackgroundResource(R.color.color_over);
        } else if (expense1 > goal1) {
            mType1.setBackgroundResource(R.color.color_over);
        } else if (expense1 == goal1) {
            mType1.setBackgroundResource(R.color.color_max);
        }else if (expense1 > goal1 * 0.5 ) {
            mType1.setBackgroundResource(R.color.color_mid);
        }else if (expense1 <= goal1 * 0.5 ) {
            mType1.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con2() {
        if(goal2==0 && expense2 == 0){
            mType2.setBackgroundResource(R.color.color_def);
        } else if(goal2 == 0 && expense2 != 0){
            mType2.setBackgroundResource(R.color.color_over);
        } else if (expense2 > goal2) {
            mType2.setBackgroundResource(R.color.color_over);
        } else if (expense2 == goal2) {
            mType2.setBackgroundResource(R.color.color_max);
        }else if (expense2 > goal2 * 0.5 ) {
            mType2.setBackgroundResource(R.color.color_mid);
        }else if (expense2 <= goal2 * 0.5) {
            mType2.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con3() {
        if(goal3==0 && expense3 == 0){
            mType3.setBackgroundResource(R.color.color_def);
        } else if(goal3 == 0 && expense3 != 0){
            mType3.setBackgroundResource(R.color.color_over);
        } else if (expense3 > goal3) {
            mType3.setBackgroundResource(R.color.color_over);
        } else if (expense3 == goal3) {
            mType3.setBackgroundResource(R.color.color_max);
        }else if (expense3 > goal3 * 0.5 ) {
            mType3.setBackgroundResource(R.color.color_mid);
        }else if (expense3 <= goal3 * 0.5) {
            mType3.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con4() {
        if(goal4==0 && expense4 == 0){
            mType4.setBackgroundResource(R.color.color_def);
        } else if(goal4 == 0 && expense4 != 0){
            mType4.setBackgroundResource(R.color.color_over);
        } else if (expense4 > goal4) {
            mType4.setBackgroundResource(R.color.color_over);
        } else if (expense4 == goal4) {
            mType4.setBackgroundResource(R.color.color_max);
        }else if (expense4 > goal4 * 0.5 ) {
            mType4.setBackgroundResource(R.color.color_mid);
        }else if (expense4 <= goal4 * 0.5) {
            mType4.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con5() {
        if(goal5==0 && expense5 == 0){
            mType5.setBackgroundResource(R.color.color_def);
        } else if(goal5 == 0 && expense5 != 0){
            mType5.setBackgroundResource(R.color.color_over);
        } else if (expense5 > goal5) {
            mType5.setBackgroundResource(R.color.color_over);
        } else if (expense5 == goal5) {
            mType5.setBackgroundResource(R.color.color_max);
        }else if (expense5 > goal5 * 0.5 ) {
            mType5.setBackgroundResource(R.color.color_mid);
        }else if (expense5 <= goal5 * 0.5) {
            mType5.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con6() {
        if(goal6==0 && expense6 == 0){
            mType6.setBackgroundResource(R.color.color_def);
        } else if(goal6 == 0 && expense6 != 0){
            mType6.setBackgroundResource(R.color.color_over);
        } else if (expense6 > goal6) {
            mType6.setBackgroundResource(R.color.color_over);
        } else if (expense6 == goal6) {
            mType6.setBackgroundResource(R.color.color_max);
        }else if (expense6 > goal6 * 0.5 ) {
            mType6.setBackgroundResource(R.color.color_mid);
        }else if (expense6 <= goal6 * 0.5) {
            mType6.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con7() {
        if(goal7==0 && expense7 == 0){
            mType7.setBackgroundResource(R.color.color_def);
        } else if(goal7 == 0 && expense7 != 0){
            mType7.setBackgroundResource(R.color.color_over);
        } else if (expense7 > goal7) {
            mType7.setBackgroundResource(R.color.color_over);
        } else if (expense7 == goal7) {
            mType7.setBackgroundResource(R.color.color_max);
        }else if (expense7 > goal7 * 0.5 ) {
            mType7.setBackgroundResource(R.color.color_mid);
        }else if (expense7 <= goal7 * 0.5) {
            mType7.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con8() {
        if(goal8==0 && expense8 == 0){
            mType8.setBackgroundResource(R.color.color_def);
        } else if(goal8 == 0 && expense8 != 0){
            mType8.setBackgroundResource(R.color.color_over);
        } else if (expense8 > goal8) {
            mType8.setBackgroundResource(R.color.color_over);
        } else if (expense8 == goal8) {
            mType8.setBackgroundResource(R.color.color_max);
        }else if (expense8 > goal8 * 0.5 ) {
            mType8.setBackgroundResource(R.color.color_mid);
        }else if (expense8 <= goal8 * 0.5) {
            mType8.setBackgroundResource(R.color.color_min);
        }
    }
    private void Con9() {
        if(goal9==0 && expense9 == 0){
            mType9.setBackgroundResource(R.color.color_def);
        } else if(goal9 == 0 && expense9 != 0){
            mType9.setBackgroundResource(R.color.color_over);
        } else if (expense9 > goal9) {
            mType9.setBackgroundResource(R.color.color_over);
        } else if (expense9 == goal9) {
            mType9.setBackgroundResource(R.color.color_max);
        }else if (expense9 > goal9 * 0.5 ) {
            mType9.setBackgroundResource(R.color.color_mid);
        }else if (expense9 <= goal9 * 0.5) {
            mType9.setBackgroundResource(R.color.color_min);
        }
    }

    private void Con10() {
        if(goal10==0 && expense10 == 0){
            mType10.setBackgroundResource(R.color.color_def);
        } else if(goal10 == 0 && expense10 != 0){
            mType10.setBackgroundResource(R.color.color_over);
        } else if (expense10 > goal10) {
            mType10.setBackgroundResource(R.color.color_over);
        } else if (expense10 == goal10) {
            mType10.setBackgroundResource(R.color.color_max);
        }else if (expense10 > goal10 * 0.5 ) {
            mType10.setBackgroundResource(R.color.color_mid);
        }else if (expense10 <= goal10 * 0.5) {
            mType10.setBackgroundResource(R.color.color_min);
        }
    }

    private void Con11() {
        if(goal11==0 && expense11 == 0){
            mType11.setBackgroundResource(R.color.color_def);
        } else if(goal11 == 0 && expense11 != 0){
            mType11.setBackgroundResource(R.color.color_over);
        } else if (expense11 > goal11) {
            mType11.setBackgroundResource(R.color.color_over);
        } else if (expense11 == goal11) {
            mType11.setBackgroundResource(R.color.color_max);
        }else if (expense11 > goal11 * 0.5 ) {
            mType11.setBackgroundResource(R.color.color_mid);
        }else if (expense11 <= goal11 * 0.5) {
            mType11.setBackgroundResource(R.color.color_min);
        }
    }

    private void Con12() {
        if(goal12==0 && expense12 == 0){
            mType12.setBackgroundResource(R.color.color_def);
        } else if(goal12 == 0 && expense12 != 0){
            mType12.setBackgroundResource(R.color.color_over);
        } else if (expense12 > goal12) {
            mType12.setBackgroundResource(R.color.color_over);
        } else if (expense12 == goal12) {
            mType12.setBackgroundResource(R.color.color_max);
        }else if (expense12 > goal12 * 0.5 ) {
            mType12.setBackgroundResource(R.color.color_mid);
        }else if (expense12 <= goal12 * 0.5) {
            mType12.setBackgroundResource(R.color.color_min);
        }
    }

    private void Con13() {
        if(goal13==0 && expense13 == 0){
            mType13.setBackgroundResource(R.color.color_def);
        } else if(goal13 == 0 && expense13 != 0){
            mType13.setBackgroundResource(R.color.color_over);
        } else if (expense13 > goal13) {
            mType13.setBackgroundResource(R.color.color_over);
        } else if (expense13 == goal13) {
            mType13.setBackgroundResource(R.color.color_max);
        }else if (expense13 > goal13 * 0.5 ) {
            mType13.setBackgroundResource(R.color.color_mid);
        }else if (expense13 <= goal13 * 0.5) {
            mType13.setBackgroundResource(R.color.color_min);
        }
    }

    private void Con14() {
        if(goal14==0 && expense14 == 0){
            mType14.setBackgroundResource(R.color.color_def);
        } else if(goal14 == 0 && expense14 != 0){
            mType14.setBackgroundResource(R.color.color_over);
        } else if (expense14 > goal14) {
            mType14.setBackgroundResource(R.color.color_over);
        } else if (expense14 == goal14) {
            mType14.setBackgroundResource(R.color.color_max);
        }else if (expense14 > goal14 * 0.5 ) {
            mType14.setBackgroundResource(R.color.color_mid);
        }else if (expense14 <= goal14 * 0.5) {
            mType14.setBackgroundResource(R.color.color_min);
        }
    }

    public void Datatype1() {
        mType1 = findViewById(R.id.mType1);
        itemPrice1 = findViewById(R.id.itemPrice1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าอาหารและเครื่องดื่ม")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense1 += document.getLong("amount");
                                    String expenseT1 = Long.toString(expense1).trim();
                                    itemPrice1.setText(expenseT1);
                                    System.out.println("Showdata"+expenseT1);
                                }
                                setUpGraph();
                            } else {
                                expense1 = 0;
                                String expenseT1 = Long.toString(expense1).trim();
                                itemPrice1.setText(expenseT1);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal1 += document.getLong("type1");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }

                });
    }

    public void Datatype2() {
        mType2 = findViewById(R.id.mType2);
        itemPrice2 = findViewById(R.id.itemPrice2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าที่พักอาศัย")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense2 += document.getLong("amount");
                                    String expenseT2 = Long.toString(expense2).trim();
                                    itemPrice2.setText(expenseT2);
                                    System.out.println("Showdata"+expenseT2);
                                }
                                setUpGraph();
                            } else {
                                expense2 = 0;
                                String expenseT2 = Long.toString(expense2).trim();
                                itemPrice2.setText(expenseT2);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal2 += document.getLong("type2");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype3() {
        mType3 = findViewById(R.id.mType3);
        itemPrice3 = findViewById(R.id.itemPrice3);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าสาธารณูปโภค")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense3 += document.getLong("amount");
                                    String expenseT3 = Long.toString(expense3).trim();
                                    itemPrice3.setText(expenseT3);
                                    System.out.println("Showdata"+expenseT3);
                                }
                                setUpGraph();
                            } else {
                                expense3 = 0;
                                String expenseT3 = Long.toString(expense3).trim();
                                itemPrice3.setText(expenseT3);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal3 += document.getLong("type3");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype4() {
        mType4 = findViewById(R.id.mType4);
        itemPrice4 = findViewById(R.id.itemPrice4);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าเดินทาง")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense4 += document.getLong("amount");
                                    String expenseT4 = Long.toString(expense4).trim();
                                    itemPrice4.setText(expenseT4);
                                    System.out.println("Showdata"+expenseT4);
                                }
                                setUpGraph();
                            } else {
                                expense4 = 0;
                                String expenseT4 = Long.toString(expense4).trim();
                                itemPrice4.setText(expenseT4);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal4 += document.getLong("type4");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype5() {
        mType5 = findViewById(R.id.mType5);
        itemPrice5 = findViewById(R.id.itemPrice5);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าแต่งกาย")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense5 += document.getLong("amount");
                                    String expenseT5 = Long.toString(expense5).trim();
                                    itemPrice5.setText(expenseT5);
                                    System.out.println("Showdata"+expenseT5);
                                }
                                setUpGraph();
                            } else {
                                expense5 = 0;
                                String expenseT5 = Long.toString(expense5).trim();
                                itemPrice5.setText(expenseT5);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal5 += document.getLong("type5");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype6() {
        mType6 = findViewById(R.id.mType6);
        itemPrice6 = findViewById(R.id.itemPrice6);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าใช้จ่ายในบ้าน")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense6 += document.getLong("amount");
                                    String expenseT6 = Long.toString(expense6).trim();
                                    itemPrice6.setText(expenseT6);
                                    System.out.println("Showdata"+expenseT6);
                                }
                                setUpGraph();
                            } else {
                                expense6 = 0;
                                String expenseT6 = Long.toString(expense6).trim();
                                itemPrice6.setText(expenseT6);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal6 += document.getLong("type6");
                                System.out.println("goal: " + document);
                            }
                             } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype7() {
        mType7 = findViewById(R.id.mType7);
        itemPrice7 = findViewById(R.id.itemPrice7);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าใช้จ่ายเกี่ยวกับสุขภาพ")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense7 += document.getLong("amount");
                                    String expenseT7 = Long.toString(expense7).trim();
                                    itemPrice7.setText(expenseT7);
                                    System.out.println("Showdata"+expenseT7);
                                }
                                setUpGraph();
                            } else {
                                expense7 = 0;
                                String expenseT7 = Long.toString(expense7).trim();
                                itemPrice7.setText(expenseT7);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal7 += document.getLong("type7");
                                System.out.println("goal: " + document);
                            }
                             } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype8() {
        mType8 = findViewById(R.id.mType8);
        itemPrice8 = findViewById(R.id.itemPrice8);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าสันทนาการและสังคม")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense8 += document.getLong("amount");
                                    String expenseT8 = Long.toString(expense8).trim();
                                    itemPrice8.setText(expenseT8);
                                    System.out.println("Showdata"+expenseT8);
                                }
                                setUpGraph();
                            } else {
                                expense8 = 0;
                                String expenseT8 = Long.toString(expense8).trim();
                                itemPrice8.setText(expenseT8);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal8 += document.getLong("type8");
                                System.out.println("goal: " + document);
                            }
                            } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });
    }

    public void Datatype9() {
        mType9 = findViewById(R.id.mType9);
        itemPrice9 = findViewById(R.id.itemPrice9);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าของขวัญและการกุศล")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense9 += document.getLong("amount");
                                    String expenseT9 = Long.toString(expense9).trim();
                                    itemPrice9.setText(expenseT9);
                                    System.out.println("Showdata"+expenseT9);
                                }
                                setUpGraph();
                            } else {
                                expense9 = 0;
                                String expenseT9 = Long.toString(expense9).trim();
                                itemPrice9.setText(expenseT9);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal9 += document.getLong("type9");
                                System.out.println("goal: " + document);
                            }
                            } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });


    }

    public void Datatype10() {
        mType10 = findViewById(R.id.mType10);
        itemPrice10 = findViewById(R.id.itemPrice10);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าใช้จ่ายทางการศึกษา")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense10 += document.getLong("amount");
                                    String expenseT10 = Long.toString(expense10).trim();
                                    itemPrice10.setText(expenseT10);
                                    System.out.println("Showdata"+expenseT10);
                                }
                                setUpGraph();
                            } else {
                                expense10 = 0;
                                String expenseT10 = Long.toString(expense10).trim();
                                itemPrice10.setText(expenseT10);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal10 += document.getLong("type10");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });


    }

    public void Datatype11() {
        mType11 = findViewById(R.id.mType11);
        itemPrice11 = findViewById(R.id.itemPrice11);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าใช้จ่ายเกี่ยวกับพาหนะ")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense11 += document.getLong("amount");
                                    String expenseT11 = Long.toString(expense11).trim();
                                    itemPrice11.setText(expenseT11);
                                    System.out.println("Showdata"+expenseT11);
                                }
                                setUpGraph();
                            } else {
                                expense11 = 0;
                                String expenseT11 = Long.toString(expense11).trim();
                                itemPrice11.setText(expenseT11);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal11 += document.getLong("type11");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });


    }

    public void Datatype12() {
        mType12 = findViewById(R.id.mType12);
        itemPrice12 = findViewById(R.id.itemPrice12);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าใช้จ่ายเกี่ยวกับธนาคาร")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense12 += document.getLong("amount");
                                    String expenseT12 = Long.toString(expense12).trim();
                                    itemPrice12.setText(expenseT12);
                                    System.out.println("Showdata"+expenseT12);
                                }
                                setUpGraph();
                            } else {
                                expense12 = 0;
                                String expenseT12 = Long.toString(expense12).trim();
                                itemPrice12.setText(expenseT12);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal9 += document.getLong("type12");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });


    }

    public void Datatype13() {
        mType13 = findViewById(R.id.mType13);
        itemPrice13 = findViewById(R.id.itemPrice13);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "ค่าใช้จ่ายเบ็ดเตล็ด")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense13 += document.getLong("amount");
                                    String expenseT13 = Long.toString(expense13).trim();
                                    itemPrice13.setText(expenseT13);
                                    System.out.println("Showdata"+expenseT13);
                                }
                                setUpGraph();
                            } else {
                                expense13 = 0;
                                String expenseT13 = Long.toString(expense13).trim();
                                itemPrice13.setText(expenseT13);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal13 += document.getLong("type13");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });


    }

    public void Datatype14() {
        mType14 = findViewById(R.id.mType14);
        itemPrice14 = findViewById(R.id.itemPrice14);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("expenses")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .whereEqualTo("category", "อื่นๆ")
                .whereEqualTo("month",selectedMonthValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    expense14 += document.getLong("amount");
                                    String expenseT14 = Long.toString(expense14).trim();
                                    itemPrice14.setText(expenseT14);
                                    System.out.println("Showdata"+expenseT14);
                                }
                                setUpGraph();
                            } else {
                                expense14 = 0;
                                String expenseT14 = Long.toString(expense14).trim();
                                itemPrice14.setText(expenseT14);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
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
                                goal14 += document.getLong("type14");
                                System.out.println("goal: " + document);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        setUpGraph();
                    }
                });


    }
};
