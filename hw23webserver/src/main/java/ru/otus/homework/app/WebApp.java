package ru.otus.homework.app;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.hibernate.SessionFactory;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.database.HibernateUtils;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserServiceImpl;
import ru.otus.homework.servlet.AuthorizationFilter;
import ru.otus.homework.servlet.LogInServlet;
import ru.otus.homework.servlet.TemplateProcessor;
import ru.otus.homework.servlet.UserServlet;
import ru.otus.homework.sessionmanager.SessionManagerHibernate;

public class WebApp {

    private final Server webServer;


    public WebApp(Server webServer) {
        this.webServer = webServer;
    }

    public void appConfig(){
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class);
        SessionManagerHibernate sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoImpl(sessionManagerHibernate);
        UserService userService = new UserServiceImpl(userDao);

        //create user
        /*User user = new User();
        user.setLogin("Admin");
        user.setPassword("123");
        userService.saveUser(user);

        System.out.println(userService.authenticate("Admin", "123"));
        */
        //id 289

        //create template

        TemplateProcessor templateProcessor = new TemplateProcessor();

        //default page
        //ResourceHandler resourceHandler = new ResourceHandler();
        //Resource resource = Resource.newClassPathResource("/static/Index.html");
        //resourceHandler.setBaseResource(resource);

        //servlets
        ServletContextHandler servletContextHandlerhandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandlerhandler.addServlet(new ServletHolder(new LogInServlet(userService, templateProcessor)), "/");
        servletContextHandlerhandler.addServlet(new ServletHolder(new LogInServlet(userService, templateProcessor)), "/signin");
        servletContextHandlerhandler.addServlet(new ServletHolder(new UserServlet(userService, templateProcessor)), "/user");
        servletContextHandlerhandler.addFilter(new FilterHolder(new AuthorizationFilter()), "/user", null);


        //handler list
        HandlerList handlerList = new HandlerList();
        Handler[] handlers = new Handler[1];
        handlers[0] = servletContextHandlerhandler;
        //handlers[1] = resourceHandler;
        handlerList.setHandlers(handlers);
        webServer.setHandler(handlerList);
    }
    public void run() throws Exception {
        webServer.start();
        System.out.println("Web server start");
        webServer.join();
    }
}
