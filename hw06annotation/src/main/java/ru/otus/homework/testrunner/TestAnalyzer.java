package ru.otus.homework.testrunner;

import ru.otus.homework.annotation.After;
import ru.otus.homework.annotation.Test;
import ru.otus.homework.annotation.Before;

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

        AnalyzerHelper.fillMethodList(beforeList, allMethods, Before.class);
        AnalyzerHelper.fillMethodList(afterList, allMethods, After.class);
        AnalyzerHelper.fillMethodList(testList, allMethods, Test.class);

        allTest = testList.size();
        for (Method method : testList){
            Object testClassInstance = clazz.newInstance();
            try{
                AnalyzerHelper.runMethods(beforeList, testClassInstance);
                method.invoke(testClassInstance);
                testSuccess++;
            } catch (Exception e){
                excepTest++;
            } finally {
                for(Method method1 :afterList){
                    try {
                        method1.invoke(testClassInstance);
                    } catch (Exception e) {}
                }
            }
        }
        System.out.println("all test - "+ allTest + "\n"+"success test - "+ testSuccess+ "\n"+"exception test - "+ excepTest + "\n");
    }
}
