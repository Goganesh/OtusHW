package ru.otus.homework.dao;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.homework.api.dao.DaoException;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.User;
import ru.otus.homework.sessionmanager.DatabaseSessionHibernate;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private final SessionManagerHibernate sessionManager;

    public UserDaoImpl(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public List<User> list() {
        return listByCriteria( null);
    }

    @Override
    public List<User> listByCriteria( Criterion criterion) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        List<User> usersList;
        try {
            Criteria criteria = currentSession.getHibernateSession().createCriteria(User.class);
            if (criterion != null) {
                criteria.add(criterion);
            }
            usersList = criteria.list();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        sessionManager.close();
        return usersList;
    }

    @Override
    public User findByCriteria(Criterion criterion) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        User user;
        try {
            Criteria criteria = currentSession.getHibernateSession().createCriteria(User.class);
            if (criterion != null) {
                criteria.add(criterion);
            }
            user = findByCriteria(criteria);

        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        sessionManager.close();
        return user;
    }

    @Override
    public User findByCriteria(Criteria executableCriteria) {
        sessionManager.beginSession();
        List<User> results = executableCriteria.list();
        if (results.isEmpty()) {
            return null;
        } else {
            if (results.size() > 1) {
                throw new NonUniqueObjectException("Found more than one result through query", results.get(0).toString(), "");
            }
            sessionManager.close();
            return results.get(0);
        }
    }

    @Override
    public User findById(long id) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        User user;
        try {
            user = currentSession.getHibernateSession().find(User.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        sessionManager.close();
        return user;
    }

    @Override
    public long saveUser(User user) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        long id;
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(user);
            sessionManager.commitSession();
            id = user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new DaoException(e);
        }
        sessionManager.close();
        return id;
    }

    @Override
    public void updateUser(User user) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().update(user);
            sessionManager.commitSession();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new DaoException(e);
        }
        sessionManager.close();
    }

    @Override
    public void deleteUser(User user) {
        sessionManager.beginSession();
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().delete(user);
            sessionManager.commitSession();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sessionManager.rollbackSession();
            throw new DaoException(e);
        }
        sessionManager.close();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
