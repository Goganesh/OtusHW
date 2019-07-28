package Annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestAnalyzer {

    public static void testLauncher(Class clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        int allTest;
        int testSuccess = 0;
        int excepTest = 0;

        Method[] allMethods = clazz.getDeclaredMethods();

        List<Method> beforeList = new ArrayList<>();
        List<Method> testList = new ArrayList<>();
        List<Method> afterList = new ArrayList<>();

        for(Method method : allMethods){
            Annotation[] annotations = method.getDeclaredAnnotations();
            if(checkMethodAnnotation(annotations,  Before.class)){
                beforeList.add(method);
            }
            if(checkMethodAnnotation(annotations, Test.class)){
                testList.add(method);
            }
            if(checkMethodAnnotation(annotations, After.class)){
                afterList.add(method);
            }
        }

        allTest = testList.size();
        for (Method method : testList){
            Object forExec = clazz.newInstance();
            for(Method method1 :beforeList){
                method1.invoke(forExec);
            }
            try{
                method.invoke(forExec);
                testSuccess++;
            } catch (Exception e){
                excepTest++;
                for(Method method1 : afterList){
                    method1.invoke(forExec);
                }
            }
            for(Method method1 : afterList){
                method1.invoke(forExec);
            }
        }

        System.out.println("all test - "+ allTest + "\n"+"success test - "+ testSuccess+ "\n"+"exception test - "+ excepTest + "\n");
    }

    public static boolean checkMethodAnnotation(Annotation[] annotations, Class clazz){
        for(Annotation annotation: annotations) {
            if(annotation.annotationType().equals(clazz)){
                return true;
            }
        }
        return false;
    }
}
