package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private final String DEFAULT_CATEGORY = "OTHER";

    private String name;
    private double budgetLimit;
    private double spent = 0;
    private List<Double> spending =  new ArrayList<>();

    public void addSpent(double value) {
        spent += value;
        addSpending(value);
    }

    public boolean isOverBudget() {
        return spent > budgetLimit;
    }

    public List<Double> getSpending() {
        return spending;
    }

    public void setSpending(List<Double> spending) {
        this.spending = spending;
    }

    public void addSpending(Double spending) {
        this.spending.add(spending);
    }

    public void removeSpending(Double spending) {
        this.spending.remove(spending);
    }

    public double getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public String getName() {
        return name.toUpperCase();
    }
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.name = name;
    }

}
