package ru.otus.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.dao.UserDao;
import ru.otus.homework.cache.Cache;
import ru.otus.homework.model.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final UserDao dao;

    private final Cache<String, Object> cache;

    public UserServiceImpl(UserDao dao, Cache cache) {
        this.dao = dao;
        this.cache = cache;
    }

    private String getCacheKey(Class clazz, Long id) {
        return clazz.getName() + "_" + id;
    }

    private Long getId(Object obj) {
        try {
            Method method = obj.getClass().getMethod("getId");
            return (Long) method.invoke(obj);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(
                    String.format("Method getId doesn't defined for data class %s", obj.getClass()), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    String.format("Illegal access to getId for data class %s", obj.getClass()), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    String.format("Illegal object to invoke getId for data class %s", obj.getClass()), e);
        }
    }


    @Override
    public long saveUser(User user) {
        long id = dao.saveUser(user);
        String cacheKey = getCacheKey(user.getClass(), getId(user));
        cache.put(cacheKey, user);
        return id;
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
        String cacheKey = getCacheKey(user.getClass(), getId(user));
        cache.put(cacheKey, user);
    }

    @Override
    public void deleteUser(User user) {
        dao.deleteUser(user);
    }

    @Override
    public User getUser(long id) {
        String cacheKey = getCacheKey(User.class, id);
        Object o = cache.get(cacheKey);
        if (o != null) {
            if (o.getClass() != User.class) {
                throw new IllegalStateException("Сохраненный объект в кеше отличается по типу от ожидаемого");
            }
            return (User) o;
        }
        return dao.findById(id);
    }
}
