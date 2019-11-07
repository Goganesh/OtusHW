package ru.otus.homework;

import org.eclipse.jetty.server.*;
import ru.otus.homework.app.WebApp;
import ru.otus.homework.database.DbServer;

public class Main {

    public static void main(String[] args) throws Exception {
        DbServer dbServer = new DbServer();
        Server webServer = new Server(8080);

        WebApp webApp = new WebApp(webServer, dbServer);
        webApp.appConfig();
        webApp.run();
    }
}
