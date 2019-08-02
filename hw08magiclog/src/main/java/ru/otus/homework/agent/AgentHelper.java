package ru.otus.homework.agent;

import ru.otus.homework.annotation.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AgentHelper {
    public static List<Method> fillAnnotatedMethods(Class clazz) {
        List<Method> annotatedMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if(checkMethodAnnotations(annotations, Log.class)){
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    public static boolean checkMethodAnnotations(Annotation[] annotations, Class clazz){
        for(Annotation annotation: annotations) {
            if(annotation.annotationType().equals(clazz)){
                return true;
            }
        }
        return false;
    }
}
