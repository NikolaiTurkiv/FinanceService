package org.example.domain;

import org.example.exceptions.CategoryNotFoundException;
import org.example.utils.Constants;
import org.example.utils.TimeConverter;

import java.util.*;


public class Wallet {
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();
    private final Set<Category> categories = new HashSet<>();

    public Wallet() {
        categories.add(new Category(Constants.DEFAULT_CATEGORY));
        this.balance = 0;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        updateBalance(t.isIncome() ? t.getAmount() : -t.getAmount());

        categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(t.getCategory().isBlank() ? Constants.DEFAULT_CATEGORY : t.getCategory()))
                .findFirst()
                .ifPresentOrElse(
                        c -> {
                            c.addSpent(t.isIncome() ? 0 : t.getAmount());
                            c.addTransaction(t);
                        },
                        () -> {
                            Category newCat = new Category(t.getCategory());
                            if (!t.isIncome()) newCat.addSpent(t.getAmount());
                            newCat.addTransaction(t);
                            categories.add(newCat);
                        }
                );
    }

    public boolean addCategory(Category c) {
        return categories.add(c);
    }

    public double getBalance() {
        return balance;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    private void updateBalance(double delta) {
        balance += delta;
    }

    public void showCategoriesReport() {
        System.out.println("--- Отчет по категориям ---");
        categories.forEach(c ->
                System.out.printf("Категория: %s | Потрачено: %.2f | Лимит: %.2f | Остаток: %.2f%n",
                        c.getName(), c.getSpent(), c.getBudgetLimit(), c.getBudgetLimit() - c.getSpent()));
    }

    public void showTransactionsReport() {
        System.out.println("--- Транзакции ---");
        transactions.stream()
                .sorted(Comparator.comparingLong(Transaction::getTimestamp).reversed())
                .forEach(t -> System.out.printf("[%s] %s %.2f (%s)%n",
                        TimeConverter.millisToDateTime(t.getTimestamp()), t.isIncome() ? "+" : "-", t.getAmount(), t.getCategory()));
    }

    public boolean removeCategory(String catName) throws CategoryNotFoundException {
        Category category = getCategory(catName);

        return categories.remove(category);
    }

    public void setBudgetToCategory(String catName, double budget) throws CategoryNotFoundException {
        Category category = getCategory(catName);

        category.setBudgetLimit(budget);
    }

    public void changCategoryName(String catName, String newName) throws CategoryNotFoundException {
        Category category = getCategory(catName);

        category.setName(newName);

    }

    public void showCategoryReport(String catName) throws CategoryNotFoundException {
        System.out.println("--- Отчет по категории ---");
        Category category = getCategory(catName);

        System.out.printf("Категория: %s | Потрачено: %.2f | Лимит: %.2f | Остаток: %.2f%n",
                category.getName(), category.getSpent(), category.getBudgetLimit(), category.getBudgetLimit() - category.getSpent());
    }

    private Category getCategory(String catName) throws CategoryNotFoundException {
        Category category = categories.stream()
                .filter(c -> c.getName().equalsIgnoreCase(catName))
                .findFirst()
                .orElse(null);

        if (category == null) {
            throw new CategoryNotFoundException("Категория не найдена");
        }

        return category;
    }
}