package org.example.service.finance;

import org.example.domain.Category;
import org.example.domain.Transaction;
import org.example.domain.User;
import org.example.exceptions.BalanceDeficitException;
import org.example.exceptions.CategoryIsAlreadyExist;
import org.example.exceptions.CategoryNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.example.service.auth.AuthServiceImpl;

import java.util.Set;

public interface FinanceService {
    void addIncome(double amount, String category);

    void addExpense(double amount, String category);

    void addCategory(Category category) throws CategoryIsAlreadyExist;

    boolean removeCategory(String categoryName) throws CategoryNotFoundException;

    Set<Category> getCategories();

    void setCategoryBudget(String categoryName, double budget) throws CategoryNotFoundException;

    void sendTransactionToAnotherUser(User user, Transaction transaction, String comment);

    void transferMoney(AuthServiceImpl authServiceImpl, User user, String userToTransfer, double amount, String comment) throws UserNotFoundException, BalanceDeficitException;
}
