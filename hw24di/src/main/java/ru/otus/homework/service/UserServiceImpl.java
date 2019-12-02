package ru.otus.homework.service;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.DbServiceException;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private final UserDao dao;


    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public User getUserByLogin(String login) {
        try {
            User user = dao.findByCriteria(Restrictions.eq("login", login));
            logger.info("loaded user: {}", user.getId());
            return user;
        } catch (NullPointerException e){
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DbServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            List<User> userList = dao.list();
            logger.info("loaded users: {}", userList.size());
            return userList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DbServiceException(e);
        }
    }

    @Override
    public boolean authenticate(String login, String password) {
        try {
            User user = dao.findByCriteria(Restrictions.eq("login", login));
            logger.info("loaded user: {}", user.getId());
            return password.equals(user.getPassword());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public long saveUser(User user) {
        try {
            long userId = dao.saveUser(user);
            logger.info("created user: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DbServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            dao.updateUser(user);
            logger.info("updated user: {}", user.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DbServiceException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            dao.deleteUser(user);
            logger.info("deleted user: {}", user.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DbServiceException(e);
        }
    }

    @Override
    public User getUser(long id) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User user = dao.findById(id);

                logger.info("loaded user: {}", user.getId());
                return user;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }

    @Override
    public User getUserWithAllInfo(long id) {
        try {
            User user = dao.findById(id);
            //Hibernate.initialize(entity); //add entity
            logger.info("loaded user: {}", user.getId());
            return user;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
