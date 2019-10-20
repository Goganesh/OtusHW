package ru.otus.homework.sessionmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.homework.api.sessionmanager.DatabaseSession;
import ru.otus.homework.api.sessionmanager.SessionManager;
import ru.otus.homework.database.DbServer;

public class SessionManagerHibernate implements SessionManager {

    private Session session;
    private DatabaseSessionHibernate databaseSession;
    private final SessionFactory sessionFactory = DbServer.getSessionFactory();

    public SessionManagerHibernate(){

    }

    @Override
    public void beginSession() {
        session = sessionFactory.openSession();
        databaseSession = new DatabaseSessionHibernate(session);
    }

    @Override
    public void commitSession() {
        session.getTransaction().commit();
    }

    @Override
    public void rollbackSession() {
        session.getTransaction().rollback();
    }

    @Override
    public void close() {
        session.close();
    }

    @Override
    public DatabaseSession getCurrentSession() {
        return databaseSession;
    }
}
