package ru.otus.homework.api.service;

import ru.otus.homework.annotation.NoAnnotationException;

public interface DbExecutor<T>{
    int create(T objectData) throws NoAnnotationException;
    void update(T objectData);
    <T> T Load(long id, Class<T> clazz);
    void createOrUpdate(T objectData);
}
