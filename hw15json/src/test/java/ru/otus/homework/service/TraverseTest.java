package ru.otus.homework.service;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.example.Example;
import ru.otus.homework.example.ExampleExample;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

public class TraverseTest {

    private Gson gson;
    private JsonService jsonService;

    @Before
    public void beforeTest(){
        gson = new Gson();
        jsonService = new JsonService();
    }

    @After
    public  void afterTest(){
        gson = null;
        jsonService = null;
    }


    @Test
    public void traversTestObjectTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //ex1
        ExampleExample exampleExample = new ExampleExample("lalala", 500);
        Example example = new Example("Georgy", 29, true, exampleExample);
        String resultGson = gson.toJson(example);
        String resultJsonService = jsonService.toJsonString(example);

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
        String resultJsonService = jsonService.toJsonString(examples);

        assertTrue(resultGson.equals(resultJsonService));
    }

    @Test
    public void traversTestArrayOfPrimitiveType() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int[] ints = new int[2];
        ints[0] = 2;
        ints[1] = 10;

        String resultGson = gson.toJson(ints);
        String resultJsonService = jsonService.toJsonString(ints);

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
        String resultJsonService = jsonService.toJsonString(examples);

        assertTrue(resultGson.equals(resultJsonService));
    }

    @Test
    public void customTest() {

        Function<Object, String> toJson = (o) -> {
            try {
                jsonService = new JsonService();
                return jsonService.toJsonString(o);
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        };
        assertEquals(gson.toJson(null), toJson.apply(null));
        assertEquals(gson.toJson(1f), toJson.apply(1f));
        assertEquals(gson.toJson(1d), toJson.apply(1d));
        assertEquals(gson.toJson("aaa"), toJson.apply("aaa"));
        assertEquals(gson.toJson('a'), toJson.apply('a'));
        assertEquals(gson.toJson(Collections.singletonList(1)), toJson.apply(Collections.singletonList(1)));
        assertEquals(gson.toJson(Arrays.asList(1, 2, 3, 4, 5, 6)), toJson.apply(Arrays.asList(1, 2, 3, 4, 5, 6)));
    }

}