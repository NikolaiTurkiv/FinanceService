package org.example.utils;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class DataValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getValidString() {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(Constants.EXIT_FROM_STRING_INPUT)) {
                throw new InvalidParameterException("Выход из ввода");
            }

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("❌ Поле не может быть пустым. Повторите ввод.");
            System.out.println("❌ Для выхода введите: exit");
        }
    }

    public static double getValidDouble() {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (value == Constants.EXIT_FROM_DOUBLE_INPUT) {
                    throw new InvalidParameterException("Выход из ввода");
                }

                return Math.abs(value);

            } catch (NumberFormatException e) {
                System.out.println("⚠️ Ошибка: введите корректное число. Пример: 123.45");
                System.out.println("❌ Для выхода введите: 0.0001");
            }
        }
    }
}