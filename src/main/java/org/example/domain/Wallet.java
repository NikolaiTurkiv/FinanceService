package org.example.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wallet {
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();
    private Set<Category> categories = new HashSet<>();


    public void addTransaction(Transaction t) {
        transactions.add(t);
        updateBalance(t.isIncome() ? t.getAmount() : -t.getAmount());
        categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(t.getCategory()))
                .findFirst()
                .ifPresent(c -> c.addSpent(t.getAmount()));
    }

    public void addCategory(Category c) {
        categories.add(c);
    }

    public void updateBalance(double amount) {
        balance += amount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
