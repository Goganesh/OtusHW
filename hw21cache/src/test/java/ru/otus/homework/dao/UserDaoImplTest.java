package ru.otus.homework.dao;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.otus.homework.cache.Cache;
import ru.otus.homework.cache.CacheImpl;
import ru.otus.homework.database.DbServer;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;
import ru.otus.homework.service.UserServiceImpl;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    DbServer server = new DbServer();
    private SessionFactory sessionFactory;
    private UserDao userDao;
    private UserService userService;
    private Cache cache;

    private static long id;
    private static String name = "Georgy";
    private static int age = 29;

    @Before
    public void before(){
        sessionFactory = DbServer.getSessionFactory();
        userDao = new UserDaoImpl(sessionFactory);
        cache = new CacheImpl(1000*60);
        userService = new UserServiceImpl(userDao, cache);
    }

    @After
    public void after(){
        sessionFactory = null;
        userDao = null;
        userService = null;
    }


    @Test
    @DisplayName("1. Проверка сохранения юзера и вложенных сущностей")
    public void checkCreateMethod() {
        User user = new User();

        user.setName(name);
        user.setAge(age);

        id = userService.saveUser(user);

        //check create User
        assertNotEquals(id , 0);
    }

    @Test
    @DisplayName("2. Проверка загрузки сохраненного юзера")
    public void checkLoadMethod() {
        checkCreateMethod();
        User loadedUser = userService.getUser(id);

        //check loadedUser
        assertTrue(loadedUser.getId() == id && loadedUser.getAge() == age && loadedUser.getName().equals(name));
    }

    @Test
    @DisplayName("3. Проверка обновления юзера")
    public void checkUpdateMethod(){
        checkCreateMethod();

        User updatedUser = new User();

        updatedUser.setId(id);
        int newAge = 900;
        updatedUser.setAge(newAge);
        String newName = "Super Georgy";
        updatedUser.setName(newName);

        userService.updateUser(updatedUser);

        updatedUser = userService.getUser(id);

        //check updatedUser
        assertTrue(updatedUser.getId() == id && updatedUser.getAge() == newAge && updatedUser.getName().equals(newName));
    }
}