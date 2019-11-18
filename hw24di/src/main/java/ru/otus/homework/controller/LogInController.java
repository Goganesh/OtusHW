package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserServiceImpl;
import java.util.ArrayList;

@Controller
public class LogInController {
    private static final String USER_NOT_FOUND_TEXT = "User is not found";
    private static final String TEMPLATE_VARIABLE_USERS = "users";
    private static final String ADMIN_FILE_NAME = "Admin";
    private static final String INDEX_FILE_NAME = "Index";
    private static final String TEMPLATE_VARIABLE_STATUS = "status";

    private final UserServiceImpl userService;

    public LogInController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("status", "");
        return "Index";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String logIn(@RequestParam(value = "login") String login, @RequestParam(value = "password") String password, Model model) {
        if (userService.authenticate(login, password)) {
            model.addAttribute(TEMPLATE_VARIABLE_STATUS,"");
            model.addAttribute(TEMPLATE_VARIABLE_USERS, new ArrayList<User>());
            return ADMIN_FILE_NAME;
        } else {
            model.addAttribute(TEMPLATE_VARIABLE_STATUS,USER_NOT_FOUND_TEXT);
            return INDEX_FILE_NAME;
        }
    }
}