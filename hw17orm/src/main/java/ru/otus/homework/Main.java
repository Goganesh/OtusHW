package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.service.DbExecutor;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.dao.DaoRepository;
import ru.otus.homework.model.Account;
import ru.otus.homework.model.User;
import ru.otus.homework.server.DataSourceH2;
import ru.otus.homework.service.DbExecutorImpl;
import ru.otus.homework.sessionmanager.SessionManagerJdbc;

import java.sql.SQLException;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)  {


        DataSourceH2 server = new DataSourceH2();

        SessionManager sessionManager = new SessionManagerJdbc(server);
        DaoRepository<User> daoRepository = new DaoRepository<>(sessionManager);
        DbExecutor<User> dbExecutor = new DbExecutorImpl<>(daoRepository);

        DaoRepository<Account> daoRepository1 = new DaoRepository<>(sessionManager);
        DbExecutor<Account> dbExecutor1 = new DbExecutorImpl<>(daoRepository);

        User user1 = new User("Georgy", 29);
        User user2 = new User("Lyubov", 26);
        User user3 = new User("David", 4);
        //System.out.println("id " + dbExecutor.create(user1));
        User loadUser = dbExecutor.Load(7, User.class);
        System.out.println(loadUser);
        loadUser.setAge(500);
        dbExecutor.update(loadUser);
        System.out.println(dbExecutor.Load(7, User.class));

        //Account account = new Account("debet", 150);
        //System.out.println("id " + dbExecutor1.create(account));
        //System.out.println(dbExecutor1.Load(1, Account.class));


        //System.out.println("id " + dbExecutor.create(user2));

    }
}
