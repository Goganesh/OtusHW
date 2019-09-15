package ru.otus.homework;

import com.google.gson.Gson;
import ru.otus.homework.example.Example;
import ru.otus.homework.example.ExampleExample;
import ru.otus.homework.service.JsonService;
import ru.otus.homework.service.Traverse;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Gson gson = new Gson();
        //ex1
        ExampleExample exampleExample = new ExampleExample("lalala", 500);
        Example example = new Example("Georgy", 29, true, exampleExample);
        //ex2
        Example[] examples = new Example[2];
        examples[0] = example;
        examples[1] = example;
        //ex3
        int[] ints = new int[2];
        ints[0] = 5;
        ints[1] = 6;

        //ex4
        List<Example> exampleList = new ArrayList<>();
        exampleList.add(example);
        exampleList.add(example);


        //ex1
        System.out.println(gson.toJson(example));

        JsonService jsonService = new JsonService();
        Traverse traverse = new Traverse();
        traverse.traverse(null, example, jsonService);
        System.out.println(jsonService.getStringBuilder());

        //ex2
        System.out.println(gson.toJson(examples));

        JsonService jsonService1 = new JsonService();
        Traverse traverse1 = new Traverse();
        traverse1.traverse(null, examples, jsonService1);
        System.out.println(jsonService1.getStringBuilder());

        //ex3
        System.out.println(gson.toJson(ints));

        JsonService jsonService2 = new JsonService();
        Traverse traverse2 = new Traverse();
        traverse2.traverse(null, ints, jsonService2);
        System.out.println(jsonService2.getStringBuilder());

        //ex4
        System.out.println(gson.toJson(exampleList));

        JsonService jsonService3 = new JsonService();
        Traverse traverse3 = new Traverse();
        traverse3.traverse(null, exampleList, jsonService3);
        System.out.println(jsonService3.getStringBuilder());
    }

}
