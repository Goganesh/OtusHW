package ru.otus.homework.sessionmanager;

import org.hibernate.Session;
import ru.otus.homework.api.sessionmanager.DatabaseSession;

public class DatabaseSessionHibernate implements DatabaseSession {
    private final Session session;

    DatabaseSessionHibernate(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }
}
