package ru.otus.homework.servlet;

import ru.otus.homework.api.service.DbServiceException;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {

    private UserService userService;
    private final String CONTENT_TYPE = "text/html;charset=utf-8";
    private final TemplateProcessor templateProcessor;

    public UserServlet(UserService userService) {
        this.userService = userService;
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Map<String, Object> pageVariables = new HashMap<>();
        resp.setContentType(CONTENT_TYPE);
        pageVariables.put("users", String.valueOf(""));
        try {
            User user = userService.getUserByLogin(login);
            if (user == null) {
                User newUser = new User();
                newUser.setPassword(password);
                newUser.setLogin(login);
                userService.saveUser(newUser);
                pageVariables.put("createStatus", String.valueOf("User is created"));

            } else {
                pageVariables.put("createStatus", String.valueOf("User is already exist"));
            }
        } catch (DbServiceException e) {
            throw new DbServiceException(e);
        }
        resp.getWriter().println(templateProcessor.getPage("Admin.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> users = userService.getAllUsers();

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("createStatus", String.valueOf(""));
        StringBuilder stringBuilder = new StringBuilder();
        for(User user : users){
            stringBuilder.append("id is - ").append(user.getId()).append(", login is - ").append(user.getLogin()).append(";");
        }
        pageVariables.put("users", stringBuilder.toString());

        resp.setContentType(CONTENT_TYPE);
        resp.getWriter().println(templateProcessor.getPage("Admin.html", pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
