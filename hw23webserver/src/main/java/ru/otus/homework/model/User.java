package ru.otus.homework.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Setter
@NoArgsConstructor
public class User {

    private Long id;
    private String login;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "login", unique = true)
    public String getLogin() {
        return login;
    }

    @Column(name = "Password")
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
