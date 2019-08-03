package ru.otus.homework.agent;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    public static List<Class> classes = new ArrayList<>();

    public static void premain(String agentArgs, Instrumentation inst) {
        Class[] classes = inst.getAllLoadedClasses();
        //почему могу найти только класс ru.otus.homework.agent.Agent  ?? где остальные написанные мной классы ??
        for (Class clazz : classes) {
            if (clazz.toString().contains("homework")) {
                System.out.println(clazz);
                Agent.classes.add(clazz);
            }
        }
        List<Method> methodList = AgentHelper.fillAnnotatedMethods(Agent.classes.get(0));
        inst.addTransformer(new ClassTR());
    }
}

