package ru.otus.homework.test;

import ru.otus.homework.annotation.Log;

public class MyClassImpl {

    @Log
    public void calculation(int param) {
        //System.out.println("executed method: calculation, param: " + param);
    }
    @Log
    public int testLog(int param) {
        return param;

    }
    @Override
    public String toString() {
        return "MyClassImpl";
    }
}
