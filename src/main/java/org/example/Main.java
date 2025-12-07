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

    public static void main(String[] args){
        System.out.println("Github Actions MIFI");
    }

//    public static void main(String[] args) throws UserAlreadyExistsException {
//
//        User user = null;
//        boolean runningAuthorize = true;
//
//        while (runningAuthorize) {
//            System.out.print("Логин: ");
//            String login = DataValidator.getValidString();
//
//            System.out.print("Пароль: ");
//            String pwd = DataValidator.getValidString();
//
//            try {
//                user = auth.login(login, pwd);
//                if (user == null) {
//                    System.out.println("Неверный пароль, попробуйте еще раз.");
//                } else {
//                    runningAuthorize = false;
//                }
//            } catch (UserNotFoundException e) {
//                System.out.println("Пользователь не найден, создаем нового...");
//                user = auth.register(login, pwd);
//                runningAuthorize = false;
//            }
//        }
//
//        FinanceService finService = new FinanceServiceImpl(user);
//
//        boolean runningFinances = true;
//
//        while (runningFinances) {
//            printMenu();
//            int choice = getIntInput("Выберите действие: ");
//
//            try {
//                switch (choice) {
//                    case 1 -> addIncome(finService);
//                    case 2 -> addExpense(finService);
//                    case 3 -> createCategory(finService);
//                    case 4 -> removeCategory(finService);
//                    case 5 -> renameCategory(finService);
//                    case 6 -> showCategories(finService);
//                    case 7 -> setBudgetToCategory(finService);
//                    case 8 -> showCategoryInfo(finService);
//                    case 9 -> showTransactionInfo(finService);
//                    case 10 -> walletInfo(finService);
//                    case 11 -> createTransactionToAnotherUser(finService, user);
//                    case 12 -> saveDataToFile(user);
//                    case 0 -> {
//                        System.out.println("Выход из программы");
//                        runningFinances = false;
//                    }
//                    default -> System.out.println("Неверный выбор");
//                }
//            } catch (Exception | CategoryNotFoundException | UserNotFoundException e) {
//                System.out.println("Ошибка: " + e.getMessage());
//            }
//        }
//
//        scanner.close();
//
//    }

    private static void showCategoryInfo(FinanceService finService) throws CategoryNotFoundException {
        System.out.println("Оставьте строку пустой для работы с категорией по умолчанию");
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidCategoryName();
        finService.showCategoryInfo(category);
    }

    private static void walletInfo(FinanceService finService) {
        finService.showWalletInfo();
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
        String comment = DataValidator.getValidString();
        fs.transferMoney(auth, user, transferUserName, transferAmount, comment);
        auth.saveUsers();
    }

    private static void showTransactionInfo(FinanceService fs) {
        fs.showTransactionsInfo();
    }

    private static void setBudgetToCategory(FinanceService fs) throws CategoryNotFoundException {
        System.out.println("Оставьте строку пустой для работы с категорией по умолчанию");
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidCategoryName();
        System.out.println("Введите бюджет категории: ");
        double budget = DataValidator.getValidDouble();
        fs.setCategoryBudget(category, budget);
        auth.saveUsers();
    }

    private static void showCategories(FinanceService fs) {
        fs.showCategoriesInfo();
    }

    private static void renameCategory(FinanceService fs) throws CategoryNotFoundException {
        System.out.println("Введите имя категории для изменения: ");
        String categoryName = DataValidator.getValidString();
        System.out.println("Введите новое имя категории: ");
        String categoryNewName = DataValidator.getValidString();
        fs.changeCategoryName(categoryName, categoryNewName);
        auth.saveUsers();
    }

    private static void removeCategory(FinanceService fs) throws CategoryNotFoundException {
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidString();
        if (fs.removeCategory(category)) {
            System.out.println("Категория удалена");
            auth.saveUsers();
        }
    }

    private static void createCategory(FinanceService fs) throws CategoryIsAlreadyExist {
        System.out.println("Оставьте строку пустой для работы с категорией по умолчанию");
        System.out.println("Введите имя категории: ");
        String category = DataValidator.getValidCategoryName();
        System.out.println("Введите бюджет категории: ");
        double budget = DataValidator.getValidDouble();
        fs.addCategory(new Category(category, budget));
        auth.saveUsers();
    }

    private static void addExpense(FinanceService fs) {
        System.out.println("Оставьте строку пустой для работы с категорией по умолчанию");
        System.out.println("Введите имя категории в которой будет произведен расход: ");
        String category = DataValidator.getValidCategoryName();
        System.out.println("Введите сумму: ");
        double amount = DataValidator.getValidDouble();
        fs.addExpense(amount, category);
        auth.saveUsers();
    }

    private static void addIncome(FinanceService fs) {
        System.out.println("Введите имя категории в которой будет засчитан доход: ");
        String category = DataValidator.getValidString();
        System.out.println("Введите сумму: ");
        double amount = DataValidator.getValidDouble();
        fs.addIncome(amount, category);
        auth.saveUsers();
    }

    private static void printMenu() {
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   💰  М Е Н Ю  У П Р А В Л Е Н И Я  💰            ║");
        System.out.println("╠═══════╦════════════════════════════════════════════════════════════╣");
        System.out.println("║  [1]  ║ Добавить доход                                            ║");
        System.out.println("║  [2]  ║ Добавить расход                                           ║");
        System.out.println("║  [3]  ║ Создать категорию                                         ║");
        System.out.println("║  [4]  ║ Удалить категорию                                         ║");
        System.out.println("║  [5]  ║ Переименовать категорию                                   ║");
        System.out.println("║  [6]  ║ Показать категории                                        ║");
        System.out.println("║  [7]  ║ Установить бюджет в категории                             ║");
        System.out.println("║  [8]  ║ Информация по конкретной категории                        ║");
        System.out.println("║  [9]  ║ Информация по транзакциям                                 ║");
        System.out.println("║ [10]  ║ Информация о кошельке                                     ║");
        System.out.println("║ [11]  ║ Перевод                                                   ║");
        System.out.println("║ [12]  ║ Вывести информацию о пользователе в файл                  ║");
        System.out.println("║  [0]  ║ Выход                                                     ║");
        System.out.println("╚═══════╩════════════════════════════════════════════════════════════╝");
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
