package org.example.service.auth;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserNotFoundException;

import java.util.Map;

public interface AuthService {
    public Map<String, User> getUsers();

    public void saveUsers();

    public User login(String login, String password) throws UserNotFoundException;

    User register(String login, String password) throws UserAlreadyExistsException;
}
