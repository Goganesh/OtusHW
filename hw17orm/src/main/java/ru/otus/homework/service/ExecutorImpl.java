package ru.otus.homework.service;

import ru.otus.homework.model.User;

public class ExecutorImpl implements DBExecutor<User> {

    @Override
    public void create(User objectData) {

    }

    @Override
    public void update(User objectData) {

    }

    @Override
    public <T> T Load(long id, Class<T> clazz) {
        return null;
    }
}
