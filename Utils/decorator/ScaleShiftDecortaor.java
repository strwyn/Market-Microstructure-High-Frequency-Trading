package class3;

/**
 * Created by eran on 10/18/2017.
 */
public class ScaleShiftDecortaor implements RandomVariableGenerator {

    private double shift, scale;
    private RandomVariableGenerator inner;
    public ScaleShiftDecortaor(RandomVariableGenerator inner, double shift, double scale){
        this.shift = shift;
        this.scale = scale;
        this.inner = inner;
    }

    public double getNextNumber(){
        return scale * inner.getNextNumber() + shift;
    }


}
