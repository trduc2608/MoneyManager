package com.example.moneymanager.Model;

public class Transaction {
    private int id;
    private String date;
    private String note;
    private double amount;
    private String type;

    public Transaction(int id, String date, String note, double amount, String type) {
        this.id = id;
        this.date = date;
        this.note = note;
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
