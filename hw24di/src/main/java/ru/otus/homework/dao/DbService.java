package ru.otus.homework.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import ru.otus.homework.model.User;

import java.util.List;

import java.util.List;

public interface DbService {

    <T> List<T> listAll(Class<T> clazz);

    void saveOrUpdate(Object obj);

    <T> T get(Class<T> dataClass, long id);
}
