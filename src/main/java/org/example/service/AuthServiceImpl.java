package org.example.service;

import org.example.domain.User;
import org.example.domain.Wallet;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserNotFoundException;

import java.util.Map;

public class AuthServiceImpl {
    private final Map<String, User> users;

    public Map<String,User> getUsers(){
        return users;
    }

    public AuthServiceImpl() {
        this.users = DataStorage.load();
    }

    public void saveUsers() {
        DataStorage.save(users);
    }

    public User login(String login, String password) throws UserNotFoundException {
        User user = users.get(login);

        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (user.getPassword().equals(password)) {
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
