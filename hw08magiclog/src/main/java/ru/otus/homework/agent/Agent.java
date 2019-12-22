package ru.otus.homework.agent;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    public static List<Class> classes = new ArrayList<>();

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new MyClassFileTransformer());
    }
}

