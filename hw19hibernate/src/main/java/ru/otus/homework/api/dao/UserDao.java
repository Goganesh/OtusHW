package ru.otus.homework.api.dao;

import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.User;

public interface UserDao {
    User findById(long id);

    long saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    SessionManager getSessionManager();

}
