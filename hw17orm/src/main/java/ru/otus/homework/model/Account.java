package ru.otus.homework.model;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.annotation.Id;

public class Account {
    @Id
    @Getter
    @Setter
    int no;
    @Getter
    @Setter
    String type;
    @Getter
    @Setter
    int number;

    public Account() {
    }

    public Account(String type, int number) {
        this.type = type;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", number=" + number +
                '}';
    }
}
