package org.example.domain;

public class Transaction {
    private final double amount;
    private final String category;
    private final boolean isIncome;
    private final long timestamp;
    private String comment;


    public Transaction(double amount, String category, boolean isIncome) {
        this.amount = amount;
        this.category = category;
        this.isIncome = isIncome;
        this.timestamp = System.currentTimeMillis();
    }

    public Transaction(double amount, String category, boolean isIncome, String comment) {
        this.amount = amount;
        this.category = category;
        this.isIncome = isIncome;
        this.timestamp = System.currentTimeMillis();
        this.comment = comment;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
