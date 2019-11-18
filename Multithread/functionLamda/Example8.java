package class7;

/**
 * Created by eran on 12/7/17.
 */
public class Example8 {

    public static void main(String[] args){
        for (int i = 1; i < 1000; ++i){
            int x = i;
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(x);
                }
            })).start();
        }

        for (int i = 0; i < 1000; ++i){
            int x = i;
            (new Thread(()->System.out.println(x))).start();
        }
    }


}
