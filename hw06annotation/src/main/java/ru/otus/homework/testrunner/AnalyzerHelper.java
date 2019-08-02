package ru.otus.homework.testrunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AnalyzerHelper {
    public static void fillMethodList(List<Method> methodList, Method[] methods, Class clazz){
        for(Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (checkMethodAnnotation(annotations, clazz)) {
                methodList.add(method);
            }
        }
    }

    public static boolean checkMethodAnnotation(Annotation[] annotations, Class clazz){
        for(Annotation annotation: annotations) {
            if(annotation.annotationType().equals(clazz)){
                return true;
            }
        }
        return false;
    }

    public static void runMethods(List<Method> methodList, Object testClassInstance) throws InvocationTargetException, IllegalAccessException {
        for(Method method1 :methodList){
            method1.invoke(testClassInstance);
        }
    }
}
