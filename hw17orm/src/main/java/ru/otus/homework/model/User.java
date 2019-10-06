package ru.otus.homework.model;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.annotation.Id;

public class User {

    @Id
    @Getter
    @Setter
    int id;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "id = " + id + ", name = " + name + ", age = " + age;
    }
}
