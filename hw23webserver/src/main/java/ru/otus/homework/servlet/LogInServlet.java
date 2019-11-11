package ru.otus.homework.servlet;

import org.eclipse.jetty.http.HttpStatus;
import ru.otus.homework.api.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LogInServlet extends HttpServlet {

    private static final int EXPIRE_INTERVAL = 90; //seconds
    private static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final String USER_NOT_FOUND_TEXT = "User is not found";
    private static final String ADMIN_RESOURCE = "templates/Admin.html";
    private static final String INDEX_FILE_NAME = "LogIn.html";
    private static final String TEMPLATE_VARIABLE_STATUS = "logInStatus";

    private final TemplateProcessor templateProcessor;
    private final UserService userService;

    public LogInServlet(UserService userService, TemplateProcessor templateProcessor) {
        this.userService = userService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (userService.authenticate(login, password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(EXPIRE_INTERVAL);
            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(HttpStatus.OK_200);


            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(ADMIN_RESOURCE);
            byte[] buf = new byte[inputStream.available()];
            inputStream.read(buf);
            resp.getWriter().println(new String(buf));
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put(TEMPLATE_VARIABLE_STATUS, USER_NOT_FOUND_TEXT);

            resp.setContentType(CONTENT_TYPE);
            resp.getWriter().println(templateProcessor.getPage(INDEX_FILE_NAME, pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
