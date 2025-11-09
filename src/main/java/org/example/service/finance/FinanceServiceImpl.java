package org.example.service.finance;

import org.example.domain.Category;
import org.example.domain.Transaction;
import org.example.domain.User;
import org.example.exceptions.BalanceDeficitException;
import org.example.exceptions.CategoryIsAlreadyExist;
import org.example.exceptions.CategoryNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.example.service.auth.AuthService;
import org.example.utils.Constants;

public class FinanceServiceImpl implements FinanceService {
    private User user;

    public FinanceServiceImpl(User user) {
        this.user = user;
    }

    @Override
    public void addIncome(double amount, String category) {
        user.getWallet().addTransaction(new Transaction(amount, category.toUpperCase(), true));
    }

    @Override
    public void addExpense(double amount, String category) {
        user.getWallet().addTransaction(new Transaction(amount, category.toUpperCase(), false));
    }

    @Override
    public void addCategory(Category category) throws CategoryIsAlreadyExist {
        if (!user.getWallet().addCategory(category)) {
            throw new CategoryIsAlreadyExist("Категория уже существует");
        }
    }

    @Override
    public boolean removeCategory(String categoryName) throws CategoryNotFoundException {
        return user.getWallet().removeCategory(categoryName.toUpperCase());
    }

    @Override
    public void changeCategoryName(String catName, String newName) throws CategoryNotFoundException {
        user.getWallet().changeCategoryName(catName, newName);
    }

    @Override
    public void setCategoryBudget(String categoryName, double budget) throws CategoryNotFoundException {
        user.getWallet().setBudgetToCategory(categoryName, budget);
    }

    @Override
    public void transferMoney(AuthService authServiceImpl, User user, String userToTransfer, double amount, String comment) throws UserNotFoundException, BalanceDeficitException {
        User to = authServiceImpl.getUsers().get(userToTransfer);

        if (to == null)
            throw new UserNotFoundException("Пользователь для приема транзакции не найден");

        if (amount <= 0 || user.getWallet().getBalance() < amount)
            throw new BalanceDeficitException("Недостаточно денег на балансе");

        to.getWallet().addTransaction(new Transaction(amount, Constants.CATEGORY_TRANSFER, true, comment));
        user.getWallet().addTransaction(new Transaction(amount, Constants.CATEGORY_TRANSFER, false, comment));
    }

    @Override
    public void showTransactionsInfo() {
        user.getWallet().showTransactionsReport();
    }

    @Override
    public void showCategoriesInfo() {
        user.getWallet().showCategoriesReport();
    }

    @Override
    public void showCategoryInfo(String catName) throws CategoryNotFoundException {
        user.getWallet().showCategoryReport(catName);
    }

    @Override
    public void showWalletInfo() {
        user.getWallet().walletInfo();
    }
}
