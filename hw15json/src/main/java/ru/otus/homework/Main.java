package ru.otus.homework;

import com.google.gson.Gson;
import ru.otus.homework.example.Example;
import ru.otus.homework.example.ExampleExample;
import ru.otus.homework.service.JsonService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
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

        //ex5
        String exampleString = "example";


        //ex6
        List list = Collections.singletonList(1);


        //ex7
        List list2 = Arrays.asList(1, 2, 3, 4, 5, 6);


        //ex1
        System.out.println(gson.toJson(example));
        System.out.println(new JsonService().toJsonString(example));

        //ex2
        System.out.println(gson.toJson(examples));
        System.out.println(new JsonService().toJsonString(examples));


        //ex3
        System.out.println(gson.toJson(ints));
        System.out.println(new JsonService().toJsonString(ints));


        //ex4
        System.out.println(gson.toJson(exampleList));
        System.out.println(new JsonService().toJsonString(exampleList));

        //ex5
        System.out.println(gson.toJson(exampleString));
        System.out.println(new JsonService().toJsonString(exampleString));

        //ex6
        System.out.println(gson.toJson(list));
        System.out.println(new JsonService().toJsonString(list));


        //ex7
        System.out.println(gson.toJson(list2));
        System.out.println(new JsonService().toJsonString(list2));

        //ex8
        System.out.println(gson.toJson(null));
        System.out.println(new JsonService().toJsonString(null));

    }

}
