package ru.otus.homework;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.api.dao.UserDao;
import ru.otus.homework.api.service.UserService;
import ru.otus.homework.dao.UserDaoImpl;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserServiceImpl;

import javax.annotation.PostConstruct;

@Service
public class ServiceCheck {

    private UserDaoImpl e;

    public ServiceCheck(UserDaoImpl e) {
        this.e = e;
    }

    /*@PostConstruct
    public void init(){
        User user = new User("dfdf","dfdf");
        UserServiceImpl userService = new UserServiceImpl(e);

        System.out.println(userService.saveUser(user));
    }*/
}
