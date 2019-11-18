package class7;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eran on 12/7/17.
 */
public class Example7 {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        list.stream().forEach(System.out::println);
        list.stream().forEach(i -> System.out.println(i));
    }

}
