package ru.otus.homework.service;

import java.lang.reflect.InvocationTargetException;

public class JsonService {
    private JsonAccumulator jsonAccumulator;

    public JsonService() {
        this.jsonAccumulator = new JsonAccumulator();
    }

    public String toJsonString(Object object) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException{
        Traverse traverse = new Traverse();
        traverse.traverse(null, object, this.jsonAccumulator);
        return jsonAccumulator.getStringBuilder().toString();
    }
}
