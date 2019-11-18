package class7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by eran on 12/7/17.
 */
public class Example6 {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Stream<Integer> s1 = list.stream();
        Stream<Integer> s2 = s1.filter(x -> x >=2);
        Stream<Integer> s3 = s2.map(x-> x *34);
        int sum = s3.reduce(0, (a, b) -> a+b).intValue();
        //System.out.println(sum);
    }

}
