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
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Criteria criteria = currentSession.getHibernateSession().createCriteria(User.class);
            if (criterion != null) {
                criteria.add(criterion);
            }
            List<User> usersList = criteria.list();
            return usersList;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public User findByCriteria(Criterion criterion) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Criteria criteria = currentSession.getHibernateSession().createCriteria(User.class);
            if (criterion != null) {
                criteria.add(criterion);
            }
            User user = findByCriteria(criteria);
            return user;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public User findByCriteria(Criteria executableCriteria) {
        List<User> results = executableCriteria.list();
        if (results.isEmpty()) {
            return null;
        } else {
            if (results.size() > 1) {
                throw new NonUniqueObjectException("Found more than one result through query", results.get(0).toString(), "");
            }
            return results.get(0);
        }
    }

    @Override
    public User findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return currentSession.getHibernateSession().find(User.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public long saveUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(user);
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().update(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().delete(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
