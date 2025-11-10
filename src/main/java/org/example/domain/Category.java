package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private double budgetLimit;
    private double spent = 0;
    private final List<Transaction> categoryTransactions = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, double budgetLimit) {
        this.name = name;
        this.budgetLimit = budgetLimit;
    }

    public String getName() {
        return name;
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public double getSpent() {
        return spent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public void addSpent(double value) {
        this.spent += value;
    }

    public void addTransaction(Transaction t) {
        this.categoryTransactions.add(t);
        this.addSpent(t.isIncome() ? t.getAmount() : -t.getAmount());
    }

    public boolean isLimitExceeded() {
        return spent > budgetLimit;
    }

    @Override
    public String toString() {
        if (isLimitExceeded()) {
            System.out.println("Бюджет превышен");
        }

        return name + " | Потрачено: " + spent +
                (budgetLimit > 0 ? " / Лимит: " + budgetLimit : "");
    }
}