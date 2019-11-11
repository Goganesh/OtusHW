package ru.otus.homework.servlet;

import ru.otus.homework.api.service.DbServiceException;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String TEMPLATE_VARIABLE_USERS = "users";
    private static final String TEMPLATE_VARIABLE_STATUS = "createStatus";
    private static final String ADMIN_FILE_NAME = "Admin.html";
    private static final String USER_CREATED_TEXT = "User is created";
    private static final String USER_EXISTS_TEXT = "User is already exist";

    private final TemplateProcessor templateProcessor;
    private final UserService userService;

    public UserServlet(UserService userService, TemplateProcessor templateProcessor) {
        this.userService = userService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Map<String, Object> pageVariables = new HashMap<>();
        resp.setContentType(CONTENT_TYPE);
        pageVariables.put(TEMPLATE_VARIABLE_USERS, new ArrayList<User>());
        try {
            User user = userService.getUserByLogin(login);
            if (user == null) {
                User newUser = new User(login, password);
                userService.saveUser(newUser);
                pageVariables.put(TEMPLATE_VARIABLE_STATUS, USER_CREATED_TEXT);

            } else {
                pageVariables.put(TEMPLATE_VARIABLE_STATUS, USER_EXISTS_TEXT);
            }
        } catch (DbServiceException e) {
            throw new DbServiceException(e);
        }
        resp.getWriter().println(templateProcessor.getPage(ADMIN_FILE_NAME, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> users = userService.getAllUsers();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(TEMPLATE_VARIABLE_STATUS, "");
        pageVariables.put(TEMPLATE_VARIABLE_USERS, users);

        resp.setContentType(CONTENT_TYPE);
        resp.getWriter().println(templateProcessor.getPage(ADMIN_FILE_NAME, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
