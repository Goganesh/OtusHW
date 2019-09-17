package ru.otus.homework.service;

public interface DBExecutor <T>{
    void create(T objectData);
    void update(T objectData);
    <T> T Load(long id, Class<T> clazz);
}
