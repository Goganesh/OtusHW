package ru.otus.homework.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.dao.Dao;
import ru.otus.homework.api.service.DbExecutor;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.dao.DaoImpl;
import ru.otus.homework.model.User;
import ru.otus.homework.server.DataSourceH2;
import ru.otus.homework.sessionmanager.SessionManagerJdbc;

import static org.junit.Assert.*;

public class DbExecutorImplTest {
    private SessionManager sessionManager;
    private DbExecutor<User> dbExecutor;

    @Before
    public void before(){
        sessionManager = new SessionManagerJdbc(new DataSourceH2());
        dbExecutor = new DbExecutorImpl<User>(sessionManager);
    }

    @After
    public void after(){
        sessionManager = null;
        dbExecutor = null;
    }

    @Test
    public void allCheckTest() throws NoAnnotationException {
        String name = "Georgy";
        int age = 29;
        User user1 = new User(name, age);
        long id = dbExecutor.create(user1);
        assertTrue(id != 0);

        User loadUser = dbExecutor.Load(id, User.class);
        assertTrue(loadUser.getId() == id && loadUser.getAge() == age && loadUser.getName().equals(name));

        String newName = "Super Georgy";
        int newAge = age + 50;
        loadUser.setName(newName);
        loadUser.setAge(newAge);
        dbExecutor.update(loadUser);
        loadUser = dbExecutor.Load(id, User.class);
        assertTrue(loadUser.getId() == id && loadUser.getAge() == newAge && loadUser.getName().equals(newName));
    }
}