package com.example.zpentnote.Expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zpentnote.R;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    ArrayList<ExpenseModel> datalist;

    public myadapter(ArrayList<ExpenseModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_row,parent,false);
       return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.note.setText(datalist.get(position).getNote());
        holder.category.setText(datalist.get(position).getCategory());
        holder.time.setText(datalist.get(position).getTime());
        holder.amount.setText(String.valueOf(datalist.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView note,category,time,amount;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            category = itemView.findViewById(R.id.category);
            time = itemView.findViewById(R.id.time);
            amount = itemView.findViewById(R.id.amount);

        }
    }
}
