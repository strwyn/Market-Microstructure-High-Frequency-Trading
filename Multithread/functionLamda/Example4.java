package class7;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eran on 12/6/17.
 */
public class Example4 {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        int sum = list.stream().reduce(0, (a,b) -> a+b).intValue();
        System.out.println(sum);
    }

}
