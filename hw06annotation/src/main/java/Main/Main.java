package Main;

import Annotation.MyTest;
import Annotation.TestAnalyzer;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        TestAnalyzer.testLauncher(MyTest.class);
    }
}
