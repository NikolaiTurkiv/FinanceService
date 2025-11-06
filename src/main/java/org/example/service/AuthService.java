package org.example.service;

import org.example.domain.User;
import org.example.domain.Wallet;
import org.example.exceptions.UserAlreadyExistsException;

import java.util.Map;

public class AuthService {
    private Map<String, User> users;

    public AuthService() {
        this.users = DataStorage.load();
    }

    public User login(String login, String password) {
        User user = users.get(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(String login, String password) throws UserAlreadyExistsException {
        if (users.containsKey(login)) {
            throw new UserAlreadyExistsException("Пользователь уже существует");
        }

        User newUser = new User(login, password, new Wallet());
        users.put(login, newUser);
        DataStorage.save(users);
        return newUser;
    }
}
