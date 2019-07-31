import test.MyTest;
import testrunner.TestAnalyzer;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        TestAnalyzer.testLauncher(MyTest.class);

    }
}
