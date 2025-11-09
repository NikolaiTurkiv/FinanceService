package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.domain.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WalletDataLoader {
    private static final Path FILE = Path.of("resources/user.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
// Выгрузка данных по конкретному пользователю
    public static void saveUser(User user) {
        try {
            String json = gson.toJson(user);
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, json);
            System.out.println("Данные пользователя сохранены в файл user.json");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных пользователей: " + e.getMessage());
        }
    }
}
