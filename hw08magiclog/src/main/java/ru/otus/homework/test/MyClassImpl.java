package ru.otus.homework.test;

import ru.otus.homework.annotation.Log;

public class MyClassImpl {

    @Log
    public void calculation(int param) {
    }

    @Override
    public String toString() {
        return "MyClassImpl";
    }
}
