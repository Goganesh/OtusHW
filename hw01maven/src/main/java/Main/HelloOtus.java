package Main;

import java.util.List;
import com.google.common.collect.Lists;

public class HelloOtus {
    public static void main(String[] args) {
        Integer[] array = new Integer[3];
        array[0] = 10;
        array[2] = 20;
        List<Integer> list =  Lists.asList(0, array);
        for(Integer num : list) {
            System.out.println(num);
        }

    }
}
