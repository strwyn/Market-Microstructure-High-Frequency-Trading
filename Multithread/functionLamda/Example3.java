package class7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by eran on 12/6/17.
 */
public class Example3 {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        list.stream().filter((i) -> i >=4 ).map(i -> 2*i )
                .forEach(i -> System.out.println(i));
    }

}
