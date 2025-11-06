package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public void addTransaction(Transaction t) {  }
    public void updateBalance(double amount) {  }
}
