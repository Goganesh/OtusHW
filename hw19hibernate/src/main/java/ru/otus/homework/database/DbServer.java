package ru.otus.homework.database;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;


public class DbServer {
    private static SessionFactory sessionFactory = null;
    private static Logger logger = LoggerFactory.getLogger(DbServer.class);


    public DbServer() {
        sessionFactory = this.createSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //create session factory
    private SessionFactory createSessionFactory() {
        logger.info("Server run");
        return HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, AddressDataSet.class, PhoneDataSet.class);
    }
}
