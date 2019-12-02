package ru.otus.homework.api.service;

import ru.otus.homework.model.User;

import java.util.List;

public interface UserService {
    long saveUser(User user);
    User getUser(long id);
    User getUserByLogin(String login);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(User user);
    User getUserWithAllInfo(long id);
    boolean authenticate(String name, String password);
}
