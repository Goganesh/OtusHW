package ru.otus.homework.service;

import org.hibernate.NonUniqueObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.api.dao.Dao;
import ru.otus.homework.api.service.DbService;
import ru.otus.homework.dao.DaoImpl;

public class DbServiceImpl<T> implements DbService<T> {
    private Dao dao = new DaoImpl<T>();
    private static Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);


    @Override
    public Long create(T entity) {
        dao.getSessionManager().beginSession();
        Long saved = null;
        dao.getSessionManager().getCurrentSession().getSession().beginTransaction();
        try {
            saved = (Long) dao.getSessionManager().getCurrentSession().getSession().save(entity);
            dao.getSessionManager().commitSession();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            dao.getSessionManager().getCurrentSession().getSession().getTransaction().rollback();
        }
        dao.getSessionManager().getCurrentSession().getSession().close();
        logger.info("created id = {}", saved);
        return saved;
    }

    @Override
    public void update(T entity) {
        dao.getSessionManager().beginSession();
        dao.getSessionManager().getCurrentSession().getSession().beginTransaction();
        try {
            dao.updateEntity(entity);
            dao.getSessionManager().commitSession();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            dao.getSessionManager().getCurrentSession().getSession().getTransaction().rollback();
        }
        dao.getSessionManager().close();
        logger.info("updated");
    }

    @Override
    public T Load(long id, Class<T> clazz) {
        dao.getSessionManager().beginSession();
        T entity = (T) dao.findById(id, clazz);
        dao.getSessionManager().close();
        logger.info("loaded");
        return entity;
    }

    @Override
    public void createOrUpdate(T entity) {
        dao.getSessionManager().beginSession();
        dao.getSessionManager().getCurrentSession().getSession().beginTransaction();
        try {
            dao.saveOrUpdate(entity);
            dao.getSessionManager().commitSession();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            dao.getSessionManager().getCurrentSession().getSession().getTransaction().rollback();
        }
        dao.getSessionManager().close();
        logger.info("created or updated");
    }

    @Override
    public void delete(T entity) {
        dao.getSessionManager().beginSession();
        dao.getSessionManager().getCurrentSession().getSession().beginTransaction();
        try {
            dao.delete(entity);
            dao.getSessionManager().commitSession();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            dao.getSessionManager().getCurrentSession().getSession().getTransaction().rollback();
        }
        dao.getSessionManager().close();
        logger.info("deleted");
    }
}
