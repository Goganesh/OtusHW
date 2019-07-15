package Main;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class DIYarrayListTest {

    @Test
    public void testAddAll(){
        int countExample = 50;
        Integer[] integers = new Integer[countExample];
        for (int i = 0; i < countExample; i++) {
            integers[i] = (int) (Math.random() * 100);
        }

        List<Integer> trueList = new ArrayList<>();
        List<Integer> myList = new DIYarrayList<>();
        Collections.addAll(trueList, integers);
        Collections.addAll(myList, integers);

        boolean size = false;
        boolean count = true;

        for (int i = 0; i<countExample;i++) {
            if(!(trueList.get(i).equals(myList.get(i)))){
                count = false;
            }
        }
        if(trueList.size()== myList.size()){
            size = true;
        }
        assertTrue(size&count);
    }

    @Test
    public void testCopy() {
        int countExample = 50;
        List<Integer> myList1 = new DIYarrayList<>();
        for(int i=0;i<countExample;i++) {
            myList1.add(i);
        }

        List<Integer> myList2 = new DIYarrayList<>();
        for(int i=0;i<countExample;i++) {
            myList2.add(5);
        }

        List<Integer> trueList1 = new ArrayList<>();
        for(int i=0;i<countExample;i++) {
            trueList1.add(i);
        }

        List<Integer> trueList2 = new ArrayList<>();
        for(int i=0;i<countExample;i++) {
            trueList2.add(5);
        }

        Collections.copy(myList2, myList1);
        Collections.copy(trueList2, trueList1);

        boolean size = false;
        boolean count = true;

        for (int i = 0; i<countExample;i++) {
            if(!(trueList2.get(i).equals(myList2.get(i)))){
                count = false;
            }
        }
        if(trueList2.size()== myList2.size()){
            size = true;
        }
        assertTrue(size&count);
    }

    @Test
    public void testSort(){
        int countExample = 75;
        List<Integer> trueList = new ArrayList<>();
        List<Integer> myList = new DIYarrayList<>();
        for (int i = 0; i < countExample; i++) {
            int num = (int) (Math.random() * 100);
            trueList.add(num);
            myList.add(num);
        }

        Collections.sort(trueList);
        Collections.sort(myList);

        boolean size = false;
        boolean count = true;

        for (int i = 0; i<countExample;i++) {
            if(!(trueList.get(i).equals(myList.get(i)))){
                count = false;
            }
        }
        if(trueList.size()== myList.size()){
            size = true;
        }
        assertTrue(size&count);
    }

}