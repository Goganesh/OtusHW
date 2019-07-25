package Annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestAnalyzer {

    public static void testLauncher(Class clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        int allTest = 0;
        int testSuccess = 0;
        int excepTest = 0;

        Class<MyTest> myTestClass = clazz;
        MyTest forExec = myTestClass.newInstance();

        Method[] methods = myTestClass.getDeclaredMethods();
        for(Method method : methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            if(checkMethodAnnotation(annotations,"@Annotation.Before()")){
                method.invoke(forExec);
            }
        }
        for(Method method : methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            if(checkMethodAnnotation(annotations,"@Annotation.Test()")){
                allTest++;
                try{
                    method.invoke(forExec);
                    testSuccess++;
                } catch (Exception e){
                    excepTest++;
                }
            }
        }
        for(Method method : methods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            if(checkMethodAnnotation(annotations,"@Annotation.After()")){
                method.invoke(forExec);
            }
        }

        System.out.println("all test - "+ allTest + "\n"+"success test - "+ testSuccess+ "\n"+"exception test - "+ excepTest + "\n");
    }

    public static boolean checkMethodAnnotation(Annotation[] annotations, String annotationName){
        for(Annotation annotation: annotations) {
            if(annotation.toString().equals(annotationName)){
                return true;
            }
        }
        return false;
    }
}
