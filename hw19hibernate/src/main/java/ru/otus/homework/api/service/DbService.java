package ru.otus.homework.api.service;

import java.io.Serializable;

public interface DbService <T>{
    Long create(T entity);
    void update(T entity);
    T Load(long id, Class<T> clazz);
    void createOrUpdate(T entity);
    void delete(T entity);
}
