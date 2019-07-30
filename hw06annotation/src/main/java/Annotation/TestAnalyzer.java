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

        fillMethodList(beforeList, allMethods, Before.class);
        fillMethodList(afterList, allMethods, After.class);
        fillMethodList(testList, allMethods, Test.class);

        allTest = testList.size();
        for (Method method : testList){
            Object testClassInstance = clazz.newInstance();
            try{
                for(Method method1 :beforeList){
                    method1.invoke(testClassInstance);
                }
                method.invoke(testClassInstance);
                testSuccess++;
            } catch (Exception e){
                excepTest++;
            } finally {
                try{
                    for(Method method1 : afterList){
                        method1.invoke(testClassInstance);
                    }
                } catch (Exception e) {}
            }
        }

        System.out.println("all test - "+ allTest + "\n"+"success test - "+ testSuccess+ "\n"+"exception test - "+ excepTest + "\n");
    }

    public static void fillMethodList(List<Method> methodList, Method[] methods,Class clazz){
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

}
