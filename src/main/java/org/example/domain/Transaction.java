package org.example.domain;

public class Transaction {
    private double amount;
    private String category;
    private boolean isIncome;
    private long timestamp;
    private String comment;


    public Transaction(double amount, String category,boolean isIncome) {
        this.amount = amount;
        this.category = category;
        this.isIncome = isIncome;
        this.timestamp = System.currentTimeMillis();
    }

    public Transaction(double amount, String category,boolean isIncome, String comment) {
        this.amount = amount;
        this.category = category;
        this.isIncome = isIncome;
        this.timestamp = System.currentTimeMillis();
        this.comment = comment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
