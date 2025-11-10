package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.domain.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final Path FILE = Path.of("resources/users.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void save(Map<String, User> users) {
        try {
            String json = gson.toJson(users);
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, json);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных пользователей: " + e.getMessage());
        }
    }

    public static Map<String, User> load() {
        if (!Files.exists(FILE)) {
            return new HashMap<>();
        }

        try {
            String json = Files.readString(FILE);
            Type type = new TypeToken<Map<String, User>>() {
            }.getType();
            Map<String, User> users = gson.fromJson(json, type);
            return users != null ? users : new HashMap<>();
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке данных пользователей: " + e.getMessage());
        }

        return new HashMap<>();
    }
}
