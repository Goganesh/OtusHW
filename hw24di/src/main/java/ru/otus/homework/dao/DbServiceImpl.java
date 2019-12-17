package ru.otus.homework.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.DbService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class DbServiceImpl implements DbService {
    private final SessionFactory sessionFactory;

    public DbServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> List<T> listAll(Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(clazz);
            Root<T> root = query.from(clazz);
            query.select(root);
            Query<T> q = session.createQuery(query);
            return q.getResultList();
        }
    }

    public void saveOrUpdate(Object obj) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(obj);
            tx.commit();
        }
    }

    @Override
    public <T> T get(Class<T> clazz, long id) {
        try (Session session = sessionFactory.openSession()) {

            return session.get(clazz, id);
        }
    }
}