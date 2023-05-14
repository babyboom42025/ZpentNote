package com.example.zpentnote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zpentnote.Expense.ExpenseModel;
import com.example.zpentnote.Expense.myadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    RecyclerView review;
    ArrayList<ExpenseModel> datalist;
    FirebaseFirestore db;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        review=findViewById(R.id.review);
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
                        });
            }

        }
    }
}