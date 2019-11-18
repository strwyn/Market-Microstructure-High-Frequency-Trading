package class3;

import java.util.Random;

/**
 * Created by eran on 10/18/2017.
 */
public class UniformRandomVeriableGenerator implements RandomVariableGenerator {

    private Random rand;
    public UniformRandomVeriableGenerator(){
        rand = new Random();
    }

    public double getNextNumber(){
        return rand.nextDouble();
    }
}