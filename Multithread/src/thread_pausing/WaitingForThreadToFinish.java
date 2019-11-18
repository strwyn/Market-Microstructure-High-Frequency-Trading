package thread_pausing;

public class WaitingForThreadToFinish implements Runnable {

	public void run() {
		// Do something here
	}

	public static void main( String[] args ) {
		WaitingForThreadToFinish thread = new WaitingForThreadToFinish();
		Thread t = new Thread( thread );
		t.start();
		
		// Do something else here
		
		try {
			t.join(); // Wait for thread to finish
		} catch (InterruptedException e) {
			
		}
		
	}
	
}
