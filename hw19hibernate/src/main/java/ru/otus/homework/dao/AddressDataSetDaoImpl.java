package ru.otus.homework.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.api.dao.AddressDataSetDao;
import ru.otus.homework.api.dao.DaoException;
import ru.otus.homework.api.dao.PhoneDataSetDao;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.sessionmanager.DatabaseSessionHibernate;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;

public class AddressDataSetDaoImpl implements AddressDataSetDao {

    private static Logger logger = LoggerFactory.getLogger(AddressDataSetDaoImpl.class);

    private final SessionManagerHibernate sessionManager;

    public AddressDataSetDaoImpl(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public AddressDataSet findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return currentSession.getHibernateSession().find(AddressDataSet.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public long saveAddressDataSet(AddressDataSet addressDataSet) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.save(addressDataSet);
            return addressDataSet.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updateAddressDataSet(AddressDataSet addressDataSet) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().update(addressDataSet);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteAddressDataSet(AddressDataSet addressDataSet) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            currentSession.getHibernateSession().delete(addressDataSet);
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
