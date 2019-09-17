package ru.otus.homework.model;

import ru.otus.homework.annotation.Id;

public class User {
    @Id
    int id;
    String name;
    int age;
}
