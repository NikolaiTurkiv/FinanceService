package org.example.service.auth;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserNotFoundException;

import java.util.Map;

public interface AuthService {
    Map<String, User> getUsers();

    void saveUsers();

    User login(String login, String password) throws UserNotFoundException;

    User register(String login, String password) throws UserAlreadyExistsException;
}
