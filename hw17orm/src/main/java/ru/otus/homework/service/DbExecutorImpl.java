package ru.otus.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.dao.Dao;
import ru.otus.homework.api.service.DbExecutor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public class DbExecutorImpl<T> implements DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutorImpl.class);
    private Dao dao;

    public DbExecutorImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public int create(T objectData) {
        if(objectData == null)
            logger.info("Error - null exception");
        long id = 0;
        try {
            id = dao.saveEntity(objectData);
        } catch (SQLException | NoAnnotationException e) {
            logger.error(e.getMessage());
        }
        return (int) id;
    }

    @Override
    public void update(T objectData) {
        if (objectData == null)
            logger.info("Error - null exception");
        try {
            dao.updateEntity(objectData);
        } catch (NoAnnotationException | SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public <T>T Load (long id, Class<T> clazz) {
        if(id == 0 || clazz == null)
            logger.info("Error - null exception");
        try {
            Optional<T> optional = null;
            optional =  dao.findById(id, clazz);
            return optional.get();
        } catch (NoAnnotationException | IllegalAccessException | InvocationTargetException | SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void createOrUpdate(T objectData) {

    }
}
