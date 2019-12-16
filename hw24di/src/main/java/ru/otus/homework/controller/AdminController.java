package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.api.service.DbServiceException;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController{
    private final String TEMPLATE_VARIABLE_USERS = "users";
    private final String TEMPLATE_VARIABLE_STATUS = "status";
    private final String ADMIN_FILE_NAME = "Admin";
    private final String USER_CREATED_TEXT = "User is created";
    private final String USER_EXISTS_TEXT = "User is already exist";

    private final UserService userService;

    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute(TEMPLATE_VARIABLE_USERS, users);
        model.addAttribute(TEMPLATE_VARIABLE_STATUS, "");

        return ADMIN_FILE_NAME;
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String logIn(@RequestParam(value = "login") String login, @RequestParam(value = "password") String password, Model model) {
        model.addAttribute(TEMPLATE_VARIABLE_USERS, new ArrayList<User>());
        try {
            User user = userService.getUserByLogin(login);
            if (user == null) {
                User newUser = new User(login, password);
                userService.saveUser(newUser);
                model.addAttribute(TEMPLATE_VARIABLE_STATUS,USER_CREATED_TEXT);
            } else {
                model.addAttribute(TEMPLATE_VARIABLE_STATUS,USER_EXISTS_TEXT);
            }
        } catch (DbServiceException e) {
            throw new DbServiceException(e);
        }
        return ADMIN_FILE_NAME;
    }
}
