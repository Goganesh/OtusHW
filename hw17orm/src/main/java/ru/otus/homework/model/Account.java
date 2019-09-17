package ru.otus.homework.model;

import ru.otus.homework.annotation.Id;

public class Account {
    @Id
    int no;
    String type;
    int number;
}
