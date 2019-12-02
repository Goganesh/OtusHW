package ru.otus.homework.api.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.User;

import java.util.List;

public interface UserDao {
    User findById(long id);

    List<User> list();

    List<User> listByCriteria(Criterion criterion);

    User findByCriteria(Criterion criterion);

    User findByCriteria(Criteria executableCriteria);

    long saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);


    SessionManager getSessionManager();

}
