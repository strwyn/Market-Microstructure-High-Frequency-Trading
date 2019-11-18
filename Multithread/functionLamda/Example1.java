package class7;

/**
 * Created by eran on 12/6/17.
 */
public class Example1 {

    public static void main(String[] args){
        (new Thread(new Runnable() {
            public void run() {

                System.out.println("Inside a thread");
            }
        })).start();

        (new Thread(() -> System.out.println("Inside another thread"))).start();
    }

}
