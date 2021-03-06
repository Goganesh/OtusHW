package ru.otus.homework;

import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.database.DbServer;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserServiceImpl;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Main {
    public static void main(String[] args) {
        DbServer server = new DbServer();
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(DbServer.getSessionFactory());

        UserDao userDao = new UserDaoImpl(sessionManager);
        UserServiceImpl userService = new UserServiceImpl(userDao);

        User user = new User();
        String name = "Georgy";
        user.setName(name);
        int age = 29;
        user.setAge(age);

        AddressDataSet addressDataSet = new AddressDataSet();
        String street = "Moscow";
        addressDataSet.setStreet(street);

        PhoneDataSet phoneDataSet1 = new PhoneDataSet();
        String number1 = "150-93-97";
        phoneDataSet1.setNumber(number1);
        PhoneDataSet phoneDataSet2 = new PhoneDataSet();
        String number2 = "150-02-73";
        phoneDataSet2.setNumber(number2);
        List<PhoneDataSet> listPhone = new ArrayList<>();
        listPhone.add(phoneDataSet1);
        listPhone.add(phoneDataSet2);

        user.setAddressDataSet(addressDataSet);
        user.setPhoneDataSet(listPhone);


        long id = userService.saveUser(user);
        //check create
        System.out.println("id is " + id);

        //check load
        User loadedUser = userService.getUser(id);
        System.out.println("age is " + loadedUser.getAge() + " name is " + loadedUser.getName());
        System.out.println("street is " + loadedUser.getAddressDataSet().getStreet());

        //check loadedPhoneDataSet
        for(PhoneDataSet phoneDataSet : loadedUser.getPhoneDataSet()){
            System.out.println(phoneDataSet.getNumber());
        }
    }

}
