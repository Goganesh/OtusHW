package ru.otus.homework;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.database.DbServer;
import ru.otus.homework.service.UserServiceImpl;
import ru.otus.homework.servlet.AuthorizationFilter;
import ru.otus.homework.servlet.LogInServlet;
import ru.otus.homework.servlet.UserServlet;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;


public class Main {

    public static void main(String[] args) throws Exception {
        DbServer dbServer = new DbServer();
        SessionManagerHibernate sessionManagerHibernate = new SessionManagerHibernate(DbServer.getSessionFactory());
        UserDao userDao = new UserDaoImpl(sessionManagerHibernate);
        UserService userService = new UserServiceImpl(userDao);

        /*User user = new User();
        user.setLogin("Admin");
        user.setPassword("123");
        userService.saveUser(user);

        System.out.println(userService.authenticate("Admin", "123"));
        //id 289
        */

        Server server = new Server(8080);

        //default page
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource("/Index.html");
        resourceHandler.setBaseResource(resource);
        //resourceHandler.setBaseResource(new PathResource(new File("hw23webserver/src/main/resources/Index.html")));

        //servlets
        ServletContextHandler servletContextHandlerhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandlerhandler.addServlet(new ServletHolder(new LogInServlet(userService)), "/signin");
        servletContextHandlerhandler.addServlet(new ServletHolder(new UserServlet(userService)), "/user");
        servletContextHandlerhandler.addFilter(new FilterHolder(new AuthorizationFilter()), "/user", null);


        //handler list
        HandlerList handlerList = new HandlerList();
        Handler[] handlers = new Handler[2];
        handlers[0] = resourceHandler;
        handlers[1] = servletContextHandlerhandler;
        handlerList.setHandlers(handlers);
        server.setHandler(handlerList);

        server.start();
        System.out.println("Web server start");
        server.join();
    }
}
