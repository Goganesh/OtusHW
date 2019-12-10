package ru.otus.homework.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.model.User;

public class UserDaoImpl implements UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findById(long id) {
        User user;
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                user = session.get(User.class, id);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                logger.error(ex.getMessage(), ex);
                throw new DaoException(ex);
            }
        }
        return user;
    }

    @Override
    public long saveUser(User user) {
        long id;
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.save(user);
                id = user.getId();
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                logger.error(ex.getMessage(), ex);
                throw new DaoException(ex);
            }
        }
        return id;
    }

    @Override
    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.update(user);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                logger.error(ex.getMessage(), ex);
                throw new DaoException(ex);
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.delete(user);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                logger.error(ex.getMessage(), ex);
                throw new DaoException(ex);
            }
        }
    }
}
