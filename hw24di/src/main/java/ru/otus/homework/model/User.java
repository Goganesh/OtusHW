package ru.otus.homework.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Users")
@Data
public final class User {

    @Id
    @GeneratedValue
    private long id;

    private String login;

    private String password;
}