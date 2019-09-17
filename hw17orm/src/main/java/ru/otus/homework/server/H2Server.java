package ru.otus.homework.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Server {
    private static final String URL = "jdbc.h2:mem";
    private final Connection connection;

    private H2Server() throws SQLException {
        this.connection = DriverManager.getConnection(URL);
        this.connection.setAutoCommit(false);
    }

    private void close() throws SQLException {
        this.connection.close();
    }
}
