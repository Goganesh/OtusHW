package ru.otus.homework.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.api.dao.Dao;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;

import java.io.Serializable;

public class DaoImpl<T> implements Dao<T> {

    private final SessionManager sessionManager = new SessionManagerHibernate();
    private static Logger logger = LoggerFactory.getLogger(DaoImpl.class);

    @Override
    public T findById(long id, Class<T> clazz) {
        return clazz.cast(sessionManager.getCurrentSession().getSession().get(clazz, id));
    }

    @Override
    public Long saveEntity(T entity) {
        Serializable saved = sessionManager.getCurrentSession().getSession().save(entity);
        return (Long) saved;
    }

    @Override
    public void updateEntity(T entity) {
        sessionManager.getCurrentSession().getSession().update(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        sessionManager.getCurrentSession().getSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        sessionManager.getCurrentSession().getSession().delete(entity);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
