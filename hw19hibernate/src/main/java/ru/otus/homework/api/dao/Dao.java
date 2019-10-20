package ru.otus.homework.api.dao;

import ru.otus.homework.api.sessionmanager.SessionManager;

import java.io.Serializable;
import java.util.Optional;

public interface Dao<T> {
    T findById(long id, Class<T> clazz) ;
    Long saveEntity(T entity) ;
    void updateEntity(T entity);
    void saveOrUpdate(T entity);
    void delete(T entity);
    SessionManager getSessionManager();
}
