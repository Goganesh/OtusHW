package ru.otus.homework.service;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.example.Example;
import ru.otus.homework.example.ExampleExample;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TraverseTest {

    private Gson gson;
    private JsonService jsonService;
    private Traverse traverse;

    @Before
    public void beforeTest(){
        gson = new Gson();
        jsonService = new JsonService();
        traverse = new Traverse();
    }

    @After
    public  void afterTest(){
        gson = null;
        jsonService = null;
        traverse = null;
    }


    @Test
    public void traversTestObjectTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //ex1
        ExampleExample exampleExample = new ExampleExample("lalala", 500);
        Example example = new Example("Georgy", 29, true, exampleExample);
        String resultGson = gson.toJson(example);
        traverse.traverse(null, example, jsonService);
        String resultJsonService = jsonService.stringBuilder.toString();

        assertTrue(resultGson.equals(resultJsonService));
    }

    @Test
    public void traversTestArrayOfObject() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ExampleExample exampleExample = new ExampleExample("lalala", 500);
        Example example = new Example("Georgy", 29, true, exampleExample);
        ExampleExample exampleExample1 = new ExampleExample("papapa", 1000);
        Example example1 = new Example("Lubov", 24, false, exampleExample);
        Example[] examples = new Example[2];
        examples[0] = example;
        examples[1] = example1;

        String resultGson = gson.toJson(examples);
        traverse.traverse(null, examples, jsonService);
        String resultJsonService = jsonService.stringBuilder.toString();

        assertTrue(resultGson.equals(resultJsonService));
    }

    @Test
    public void traversTestArrayOfPrimitiveType() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int[] ints = new int[2];
        ints[0] = 2;
        ints[1] = 10;

        String resultGson = gson.toJson(ints);
        traverse.traverse(null, ints, jsonService);
        String resultJsonService = jsonService.stringBuilder.toString();

        assertTrue(resultGson.equals(resultJsonService));
    }

    @Test
    public void traversTestCollectionType() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ExampleExample exampleExample = new ExampleExample("lalala", 500);
        Example example = new Example("Georgy", 29, true, exampleExample);
        ExampleExample exampleExample1 = new ExampleExample("papapa", 1000);
        Example example1 = new Example("Lubov", 24, false, exampleExample);
        List<Example> examples = new ArrayList<>();
        examples.add(example);
        examples.add(example1);

        String resultGson = gson.toJson(examples);
        traverse.traverse(null, examples, jsonService);
        String resultJsonService = jsonService.stringBuilder.toString();

        assertTrue(resultGson.equals(resultJsonService));
    }

}