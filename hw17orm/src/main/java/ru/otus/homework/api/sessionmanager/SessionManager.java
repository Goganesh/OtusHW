package ru.otus.homework.api.sessionmanager;

import ru.otus.homework.api.sessionmanager.DatabaseSession;

public interface SessionManager extends AutoCloseable {
    void beginSession();
    void commitSession();
    void rollbackSession();
    void close();
    DatabaseSession getCurrentSession();
}