package class7;

import java.util.Optional;

public class Example9 {

    @FunctionalInterface
    public interface FITest{
        public Optional<Integer> test(int x);
    }

    public interface FITest2{
        public Integer test(int x);
    }





    public static void main(String[] args){
        FITest test = (x) -> {
            if (x > 100){
                return Optional.empty();
            } else {
                return Optional.of(x);
            }
        };

        FITest2 test2 = (y) -> {
            if (y > 100){
                return null;
            } else {
                return y;
            }
        };

        Integer x = test2.test(10);
        if ( x == null){
            x = 2;
        }
        x = test2.test(x*10);
        if ( x == null ){
            x = 25;
            // return x;
        }

        Integer y = test.test(10).orElse(2).intValue();
        //est.test(y).

    }


}
