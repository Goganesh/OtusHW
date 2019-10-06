package ru.otus.homework.server;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

public class DataSourceH2 implements DataSource {
    private static final String URL = "jdbc:h2:~/test";

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL,"sa", "" );
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password){
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {

    }

    @Override
    public void setLoginTimeout(int seconds) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public Logger getParentLogger(){
        return null;
    }

}
