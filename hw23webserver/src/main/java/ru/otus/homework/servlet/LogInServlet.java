package ru.otus.homework.servlet;

import org.eclipse.jetty.http.HttpStatus;
import ru.otus.homework.api.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LogInServlet extends HttpServlet {

    private static final int EXPIRE_INTERVAL = 90; //seconds
    private final String CONTENT_TYPE = "text/html;charset=utf-8";
    private final TemplateProcessor templateProcessor;
    private UserService userService;

    public LogInServlet(UserService userService) {
        this.userService = userService;
        this.templateProcessor = new TemplateProcessor();
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

            File file = new File("hw23webserver/src/main/resources/Admin.html");
            byte[] bytes = Files.readAllBytes(file.toPath());
            resp.getWriter().println(new String(bytes));
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("logInStatus", String.valueOf("User is not found"));

            resp.setContentType(CONTENT_TYPE);
            resp.getWriter().println(templateProcessor.getPage("Index.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
            //resp.setStatus(403);
        }

    }
}
