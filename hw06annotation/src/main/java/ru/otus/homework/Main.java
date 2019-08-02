package ru.otus.homework;

import ru.otus.homework.test.MyTest;
import ru.otus.homework.testrunner.TestAnalyzer;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        TestAnalyzer.testLauncher(MyTest.class);
    }
}
