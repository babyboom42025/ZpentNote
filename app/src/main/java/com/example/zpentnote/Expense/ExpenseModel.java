package com.example.zpentnote.Expense;

import java.io.Serializable;

public class ExpenseModel implements Serializable {

    private String expenseId;
    private String note;
    private String category;
    private long amount;
    private String time;
    private String uid;
    private String month;




    public ExpenseModel(String expenseId, String note, String category, long amount, String time,String month, String uid) {
        this.expenseId = expenseId;
        this.note = note;
        this.category = category;
        this.amount = amount;
        this.time = time;
        this.uid = uid;
        this.month = month;
    }
    public ExpenseModel(){

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}


