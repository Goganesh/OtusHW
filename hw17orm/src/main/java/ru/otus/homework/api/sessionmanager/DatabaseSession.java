package ru.otus.homework.api.sessionmanager;

import java.sql.Connection;

public interface DatabaseSession {
    Connection getConnection();
}
