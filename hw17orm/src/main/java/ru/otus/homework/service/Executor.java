package ru.otus.homework.service;

import ru.otus.homework.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Executor<T> {
    void create(T objectData){
        checkAnnotation(objectData.getClass());
    }
    void update(T objectData){

    }
    <T> T Load(long id, Class<T> clazz){
        return (T) new Object();
    }

    private boolean checkAnnotation(Class clazz){
        for(Field field : clazz.getFields()){
            field.setAccessible(true);
            if(Modifier.isStatic(field.getModifiers()))
               continue;
            for(Annotation annotation : field.getAnnotations()){
                if(annotation.annotationType().equals(Id.class)){
                    return true;
                }
            }
        }
        return false;
    }
}
