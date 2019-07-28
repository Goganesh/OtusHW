package Annotation;

import Annotation.After;
import Annotation.Before;
import Annotation.Test;

@Test()
public class MyTest {
    Integer x;
    Integer y;

    @Test
    void test1() throws Exception {
        if(y.equals(Math.max(x, y))){

        }else{
            throw new Exception();
        }
    }

    @Test
    void test2() throws Exception {
        if(x.equals(Math.max(x, y))){

        }else{
            throw new Exception();
        }
    }

    @Test
    void test3() throws Exception {
        if(x.equals(Math.max(x, y))){

        }else{
            throw new Exception();
        }
    }

    @Before
    void beforeTest(){
        x = 5;
        y = 130;
    }

    @After
    void afterTest(){
        x = null;
        y = null;
    }

}