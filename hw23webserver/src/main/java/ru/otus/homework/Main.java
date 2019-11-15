package ru.otus.homework;

import org.eclipse.jetty.server.*;
import ru.otus.homework.app.WebApp;

public class Main {

    public static void main(String[] args) throws Exception {
        Server webServer = new Server(8080);

        WebApp webApp = new WebApp(webServer);
        webApp.appConfig();
        webApp.run();
    }
}
