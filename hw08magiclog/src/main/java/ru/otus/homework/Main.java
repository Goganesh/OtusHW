package ru.otus.homework;
//import jdk.internal.org.objectweb.asm.util.ASMifier;
import ru.otus.homework.test.MyClassImpl;

public class Main {
    public static void main(String[] args) throws Exception {

        //test Agent
        //java -javaagent:AopTest.jar -jar AopTest.jar
        MyClassImpl myClass = new MyClassImpl();
        myClass.calculation(6);

        //ASMifier.main(new String[]{MyClassImpl.class.getCanonicalName()});
    }
}
