package org.example;

import org.example.domain.Category;
import org.example.domain.User;
import org.example.exceptions.*;
import org.example.service.WalletDataLoader;
import org.example.service.auth.AuthService;
import org.example.service.auth.AuthServiceImpl;
import org.example.service.finance.FinanceService;
import org.example.service.finance.FinanceServiceImpl;
import org.example.utils.DataValidator;

import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService auth = new AuthServiceImpl();

    public static void main(String[] args) throws UserAlreadyExistsException {

        System.out.print("Логин: ");
        String login = scanner.nextLine();
        System.out.print("Пароль: ");
        String pwd = scanner.nextLine();

        User user;
        try {
            user = auth.login(login, pwd);

            if (user == null) {
                System.out.println("Неверный пароль, попробуйте еще раз.");
                return;
            }
        } catch (UserNotFoundException e) {
            System.out.println("Пользователь не найден, создаем нового...");
            user = auth.register(login, pwd);
        }

        FinanceService finService = new FinanceServiceImpl(user);

        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput("Выберите действие: ");

            try {
                switch (choice) {
                    case 1 -> addIncome(finService);
                    case 2 -> addExpense(finService);
                    case 3 -> createCategory(finService);
                    case 4 -> removeCategory(finService);
                    case 5 -> renameCategory(finService);
                    case 6 -> showCategories(finService);
                    case 7 -> setBudgetToCategory(finService);
                    case 8 -> showReport(finService);
                    case 9 -> createTransactionToAnotherUser(finService,user);
                    case 10 -> saveDataToFile(user);
                    case 0 -> {
                        System.out.println("Выход из программы");
                        running = false;
                    }
                    default -> System.out.println("Неверный выбор");
                }
            } catch (Exception | CategoryNotFoundException | UserNotFoundException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();

    }

    private static void saveDataToFile(User user) {
        WalletDataLoader.saveUser(user);
    }

    private static void createTransactionToAnotherUser(FinanceService fs, User user) throws UserNotFoundException, BalanceDeficitException {
        System.out.println("Введите имя пользователя для перевода: ");
        String transferUserName = DataValidator.getValidString();
        System.out.println("Введите сумму для перевода: ");
        double transferAmount = DataValidator.getValidDouble();
        System.out.println("Введите комментарий для получателя: ");
        String comment =  DataValidator.getValidString();
        fs.transferMoney(auth,user, transferUserName, transferAmount, comment);
    }

    private static void showReport(FinanceService fs) {
        fs.showTransactionsReport();
    }

    private static void setBudgetToCategory(FinanceService fs) throws CategoryNotFoundException {
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidString();
        System.out.println("Введите бюджет категории: ");
        double budget = DataValidator.getValidDouble();
        fs.setCategoryBudget(category,budget);
    }

    private static void showCategories(FinanceService fs) {
        fs.showCategoriesReport();
    }

    private static void renameCategory(FinanceService fs) throws CategoryNotFoundException {
        System.out.println("Введите имя категории для изменения: ");
        String categoryName = DataValidator.getValidString();
        System.out.println("Введите новое имя категории: ");
        String categoryNewName = DataValidator.getValidString();
        fs.changeCategoryName(categoryName,categoryNewName);
    }

    private static void removeCategory(FinanceService fs) throws CategoryNotFoundException {
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidString();
        fs.removeCategory(category);
    }

    private static void createCategory(FinanceService fs) throws CategoryIsAlreadyExist {
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidString();
        System.out.println("Введите бюджет категории: ");
        double budget = DataValidator.getValidDouble();
        fs.addCategory(new Category(category,budget));
    }

    private static void addExpense(FinanceService fs) {
        System.out.println("Введите имя категории в которой будет произведен расход: ");
        String category = DataValidator.getValidString();
        System.out.println("Введите сумму: ");
        double amount = DataValidator.getValidDouble();
        fs.addExpense(amount,category);
    }

    private static void addIncome(FinanceService fs) {
        System.out.println("Введите имя категории в которой будет засчитан доход: ");
        String category = DataValidator.getValidString();
        System.out.println("Введите сумму: ");
        double amount = DataValidator.getValidDouble();
        fs.addIncome(amount,category);

    }

    private static void printMenu() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      💰 МЕНЮ УПРАВЛЕНИЯ 💰                   ║");
        System.out.println("╠══════╦═══════════════════════════════════════════════════════╣");
        System.out.println("║ 1️⃣   ║ Добавить доход                                       ║");
        System.out.println("║ 2️⃣   ║ Добавить расход                                      ║");
        System.out.println("║ 3️⃣   ║ Создать категорию                                    ║");
        System.out.println("║ 4️⃣   ║ Удалить категорию                                    ║");
        System.out.println("║ 5️⃣   ║ Переименовать категорию                              ║");
        System.out.println("║ 6️⃣   ║ Показать категории                                   ║");
        System.out.println("║ 7️⃣   ║ Установить бюджет в категории                        ║");
        System.out.println("║ 8️⃣   ║ Отчёт по транзакциям                                 ║");
        System.out.println("║ 9️⃣   ║ Перевод                                              ║");
        System.out.println("║ 🔟   ║ Вывести информацию о пользователе в файл             ║");
        System.out.println("║ 0️⃣   ║ Выход                                                ║");
        System.out.println("╚══════╩══════════════════════════════════════════════════════╝");
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
    }



}