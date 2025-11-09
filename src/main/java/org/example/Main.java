package org.example;

import org.example.domain.User;
import org.example.exceptions.BalanceDeficitException;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserNotFoundException;
import org.example.service.AuthServiceImpl;
import org.example.service.FinanceServiceImpl;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws UserAlreadyExistsException {
        Scanner sc = new Scanner(System.in);
        AuthServiceImpl auth = new AuthServiceImpl();

        System.out.print("Логин: ");
        String login = sc.nextLine();
        System.out.print("Пароль: ");
        String pwd = sc.nextLine();

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

        FinanceServiceImpl fs = new FinanceServiceImpl(user);
        try {
            fs.transferMoney(auth,user,"newmitherfucker",333,"comment");
        } catch (UserNotFoundException e) {
         } catch (BalanceDeficitException e) {
         }


        auth.saveUsers();


        while (true) {
            System.out.println("""
                    1. Добавить доход
                    2. Добавить расход
                    3. Установить бюджет
                    4. Отчет
                    5. Перевод
                    0. Выход
                    """);
            switch (sc.nextLine()) {
                case "1" -> {
                    System.out.print("Сумма: ");
                    double a = Double.parseDouble(sc.nextLine());
                    System.out.print("Категория: ");
                    fs.addIncome(a, sc.nextLine());
                }
                case "2" -> {
                    System.out.print("Сумма: ");
                    double a = Double.parseDouble(sc.nextLine());
                    System.out.print("Категория: ");
                    fs.addExpense(a, sc.nextLine());
                }
                case "3" -> {
                    System.out.print("Категория: ");
                    String cat = sc.nextLine();
                    System.out.print("Лимит: ");
//                    fs.setBudget(cat, Double.parseDouble(sc.nextLine()));
                }
//                case "4" -> fs.showReport();
                case "5" -> {
                    System.out.print("Логин получателя: ");
                    String to = sc.nextLine();
                    System.out.print("Сумма: ");
//                    fs.transfer(to, Double.parseDouble(sc.nextLine()), auth);
                }
                case "0" -> {
                    auth.saveUsers();
                    System.out.println("Данные сохранены. Выход...");
                    return;
                }
                default -> System.out.println("Неверная команда");
            }
        }
    }
}