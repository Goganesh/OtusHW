package ru.otus.homework.service;

import ru.otus.homework.model.User;

public interface UserService {
    long saveUser(User user);
    User getUser(long id);
    void updateUser(User user);
    void deleteUser(User user);
}
