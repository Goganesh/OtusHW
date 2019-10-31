package ru.otus.homework.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.database.DbServer;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class DbServiceImplTest {
    DbServer server = new DbServer();
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private UserService userService;

    private static long id;
    private static String name = "Georgy";
    private static int age = 29;
    private static String street = "Moscow";
    private static String number1 = "150-93-97";
    private static String number2 = "150-02-73";

    @Before
    public void before(){
        sessionManager = new SessionManagerHibernate(DbServer.getSessionFactory());
        userDao = new UserDaoImpl(sessionManager);
        userService = new UserServiceImpl(userDao);
    }

    @After
    public void after(){
        sessionManager = null;
        userDao = null;
        userService = null;
    }


    @Test
    @DisplayName("1. Проверка сохранения юзера и вложенных сущностей")
    public void checkCreateMethod() {
        User user = new User();

        user.setName(name);
        user.setAge(age);

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(street);

        PhoneDataSet phoneDataSet1 = new PhoneDataSet();
        phoneDataSet1.setNumber(number1);
        phoneDataSet1.setUser(user);
        PhoneDataSet phoneDataSet2 = new PhoneDataSet();
        phoneDataSet2.setNumber(number2);
        phoneDataSet2.setUser(user);
        List<PhoneDataSet> listPhone = new ArrayList<>();
        listPhone.add(phoneDataSet1);
        listPhone.add(phoneDataSet2);

        user.setAddressDataSet(addressDataSet);
        user.setPhoneDataSet(listPhone);


        id = userService.saveUser(user);

        //check create User
        assertNotEquals(id , 0);
    }

    @Test
    @DisplayName("2. Проверка загрузки сохраненного юзера и вложенных сущностей")
    public void checkLoadMethod() {
        checkCreateMethod();
        User loadedUser = userService.getUserWithAllInfo(id);

        //check loadedUser
        assertTrue(loadedUser.getId() == id && loadedUser.getAge() == age && loadedUser.getName().equals(name));
        //check loadedAddressDataSet
        assertTrue(loadedUser.getAddressDataSet().getId() != 0 &&
                loadedUser.getAddressDataSet().getStreet().equals(street));
        //check loadedPhoneDataSet
        assertTrue(loadedUser.getPhoneDataSet().size() == 2 &&
                loadedUser.getPhoneDataSet().get(0).getNumber().equals(number1) &&
                loadedUser.getPhoneDataSet().get(1).getNumber().equals(number2));
    }

    @Test
    @DisplayName("3. Проверка обновления юзера и вложенных сущностей")
    public void checkUpdateMethod(){
        checkCreateMethod();

        User updatedUser = new User();

        updatedUser.setId(id);
        int newAge = 900;
        updatedUser.setAge(newAge);
        String newName = "Super Georgy";
        updatedUser.setName(newName);

        String newStreet = "Super Moscow";
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet(newStreet);


        List<PhoneDataSet> listPhone = new ArrayList<>();
        String newNumber ="999";
        PhoneDataSet phoneDataSet1 = new PhoneDataSet();
        phoneDataSet1.setNumber(newNumber);
        phoneDataSet1.setUser(updatedUser);
        listPhone.add(phoneDataSet1);

        updatedUser.setAddressDataSet(addressDataSet);
        updatedUser.setPhoneDataSet(listPhone);

        userService.updateUser(updatedUser);

        updatedUser = userService.getUserWithAllInfo(id);

        //check updatedUser
        assertTrue(updatedUser.getId() == id && updatedUser.getAge() == newAge && updatedUser.getName().equals(newName));
        //check updatedAddressDataSet
        assertTrue(updatedUser.getAddressDataSet().getId() != 0 &&
                updatedUser.getAddressDataSet().getStreet().equals(newStreet));
        //check updatedPhoneDataSet
        assertTrue(updatedUser.getPhoneDataSet().size() == 3 &&
                updatedUser.getPhoneDataSet().get(2).getNumber().equals(newNumber));

    }

}