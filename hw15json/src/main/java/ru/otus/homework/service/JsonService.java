package ru.otus.homework.service;

import java.lang.reflect.InvocationTargetException;

public class JsonService {
    private JsonAccumulatorImpl jsonAccumulatorImpl;

    public JsonService() {
        this.jsonAccumulatorImpl = new JsonAccumulatorImpl();
    }

    public String toJsonString(Object object) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException{
        Traverse traverse = new Traverse();
        traverse.traverse(null, object, this.jsonAccumulatorImpl);
        return jsonAccumulatorImpl.getStringBuilder().toString();
    }
}
