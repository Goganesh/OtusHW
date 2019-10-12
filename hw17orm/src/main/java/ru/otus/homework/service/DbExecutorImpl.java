package ru.otus.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.dao.Dao;
import ru.otus.homework.api.service.DbExecutor;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.dao.DaoImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public class DbExecutorImpl<T> implements DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutorImpl.class);
    private final SessionManager sessionManager;

    public DbExecutorImpl(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public int create(T objectData) {
        if(objectData == null)
            logger.info("Error - null exception");
        long id = 0;
        sessionManager.beginSession();
        Dao<T> dao = new DaoImpl<>(sessionManager.getCurrentSession().getConnection());
        try {
            id = dao.saveEntity(objectData);
        } catch (SQLException | NoAnnotationException e) {
            logger.error(e.getMessage());
        }
        sessionManager.close();
        return (int) id;
    }

    @Override
    public void update(T objectData) {
        if (objectData == null)
            logger.info("Error - null exception");
        sessionManager.beginSession();
        Dao<T> dao = new DaoImpl<>(sessionManager.getCurrentSession().getConnection());
        try {
            dao.updateEntity(objectData);
        } catch (NoAnnotationException | SQLException ex) {
            logger.error(ex.getMessage());
        }
        sessionManager.close();
    }

    @Override
    public <T>T Load (long id, Class<T> clazz) {
        if(id == 0 || clazz == null)
            logger.info("Error - null exception");
        sessionManager.beginSession();
        Dao<T> dao = new DaoImpl<>(sessionManager.getCurrentSession().getConnection());
        try {
            Optional<T> optional = null;
            optional =  dao.findById(id, clazz);
            sessionManager.close();
            return optional.get();
        } catch (NoAnnotationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            logger.error(e.getMessage());
        }
        sessionManager.close();
        return null;
    }

    @Override
    public void createOrUpdate(T objectData) {

    }
}
