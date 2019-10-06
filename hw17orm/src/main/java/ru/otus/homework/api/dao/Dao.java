package ru.otus.homework.api.dao;

import ru.otus.homework.annotation.NoAnnotationException;
import ru.otus.homework.api.sessionmanager.SessionManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public interface Dao<T> {
        Optional<T> findById(long id, Class<T> clazz) throws NoAnnotationException, SQLException, InvocationTargetException, IllegalAccessException;
        long saveEntity(T entity) throws SQLException, NoAnnotationException;
        void updateEntity(T entity) throws NoAnnotationException, SQLException;
        SessionManager getSessionManager();
}
