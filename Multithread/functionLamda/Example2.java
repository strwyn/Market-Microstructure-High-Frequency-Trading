package class7;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eran on 12/6/17.
 */
public class Example2 {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()){
            Integer i = iter.next();
            System.out.println(i);
        }

        list.forEach((i) -> System.out.println(i));
    }

}
