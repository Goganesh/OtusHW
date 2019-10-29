package ru.otus.homework.api.service;

import ru.otus.homework.model.User;

public interface UserService {
    long saveUser(User user);
    User getUser(long id);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserWithAllInfo(long id);
}
