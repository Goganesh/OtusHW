package ru.otus.homework.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.api.dao.DaoException;
import ru.otus.homework.api.dao.PhoneDataSetDao;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.sessionmanager.DatabaseSessionHibernate;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;

public class PhoneDataSetDaoImpl implements PhoneDataSetDao {

    private static Logger logger = LoggerFactory.getLogger(PhoneDataSetDaoImpl.class);

    private final SessionManagerHibernate sessionManager;

    public PhoneDataSetDaoImpl(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public PhoneDataSet findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return currentSession.getHibernateSession().find(PhoneDataSet.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public long savePhoneDataSet(PhoneDataSet phoneDataSet) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(phoneDataSet);
            return phoneDataSet.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updatePhoneDataSet(PhoneDataSet phoneDataSet) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().update(phoneDataSet);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void deletePhoneDataSet(PhoneDataSet phoneDataSet) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().delete(phoneDataSet);
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
