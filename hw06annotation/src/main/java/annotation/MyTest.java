package annotation;

import test.Before;

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
    void beforeTest() throws Exception {
        x = 5;
        y = 130;
    }

    @After
    void afterTest() throws Exception {
        x = null;
        y = null;
    }

}