package org.example.service;

import org.example.domain.Category;
import org.example.domain.Transaction;
import org.example.domain.User;
import org.example.exceptions.BalanceDeficitException;
import org.example.exceptions.CategoryIsAlreadyExist;
import org.example.exceptions.CategoryNotFoundException;
import org.example.exceptions.UserNotFoundException;

import java.util.Set;

public class FinanceService {
    private User user;

    public FinanceService(User user) {
        this.user = user;
    }

    public void addIncome(double amount, String category) {
        user.getWallet().addTransaction(new Transaction(amount, category.toUpperCase(), true));
    }

    public void addExpense(double amount, String category) {
        user.getWallet().addTransaction(new Transaction(amount, category.toUpperCase(), false));
    }

    public void addCategory(Category category) throws CategoryIsAlreadyExist {
        if (!user.getWallet().addCategory(category)) {
            throw new CategoryIsAlreadyExist("Категория уже существует");
        }
    }

    public boolean removeCategory(String categoryName) throws CategoryNotFoundException {
        return user.getWallet().removeCategory(categoryName.toUpperCase());
    }

    public Set<Category> getCategories() {
        user.getWallet().getCategories().forEach(System.out::println);
        return user.getWallet().getCategories();
    }

    public void setCategoryBudget(String categoryName, double budget) throws CategoryNotFoundException {
        user.getWallet().setBudgetToCategory(categoryName, budget);
    }

    public void sendTransactionToAnotherUser(User user, Transaction transaction, String comment) {
        transaction.setComment(comment);
        user.getWallet().addTransaction(transaction);
    }

    public void transferMoney(AuthService authService, User user, String userToTransfer, double amount,String comment) throws UserNotFoundException, BalanceDeficitException {
        User to = authService.getUsers().get(userToTransfer);

        if (to == null)
            throw new UserNotFoundException("Пользователь для приема транзакции не найден");

        if (amount <= 0 || user.getWallet().getBalance() < amount)
            throw new BalanceDeficitException("Недостаточно денег на балансе");

        to.getWallet().addTransaction(new Transaction(amount, "", true, comment));
        user.getWallet().addTransaction(new Transaction(amount, "", false, comment));


    }
}

