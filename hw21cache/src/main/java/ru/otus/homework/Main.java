package ru.otus.homework;

import org.hibernate.SessionFactory;
import ru.otus.homework.dao.UserDao;
import ru.otus.homework.cache.Cache;
import ru.otus.homework.cache.CacheImpl;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.database.DbServer;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DbServer server = new DbServer();
        SessionFactory sessionFactory = DbServer.getSessionFactory();

        UserDao userDao = new UserDaoImpl(sessionFactory);
        Cache cache = new CacheImpl<>(1000 * 60);
        UserServiceImpl userService = new UserServiceImpl(userDao, cache);

        User user = new User();
        String name = "Georgy";
        user.setName(name);
        int age = 29;
        user.setAge(age);

        long id = userService.saveUser(user);
        //check create
        System.out.println("id is " + id);

        //check load
        User loadedUser = userService.getUser(id);
        System.out.println("age is " + loadedUser.getAge() + " name is " + loadedUser.getName());
    }

}
