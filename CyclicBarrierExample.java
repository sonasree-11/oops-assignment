package assignment3;
import java.util.concurrent.CyclicBarrier;
class WorkerThread extends Thread {
    private CyclicBarrier barrier;
    private int id;
    public WorkerThread(int id, CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }
    @Override
    public void run() {
        try {
            System.out.println("Thread " + id + " is working...");
            Thread.sleep(1000 + id * 500); // simulate work

            System.out.println("Thread " + id + " reached the barrier.");
            barrier.await();  // wait for others

            // Once all reach the barrier, all continue
            System.out.println("Thread " + id + " continues after barrier.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class CyclicBarrierExample {
    public static void main(String[] args) {
		final int THREAD_COUNT = 3;
                // CyclicBarrier with 3 parties and a barrier action
		        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, 
		            () -> System.out.println("All threads reached the barrier. Moving ahead...\n"));
		        // start 3 threads
		        for (int i = 1; i <= THREAD_COUNT; i++) {
		            new WorkerThread(i, barrier).start();
		        }
		   
	}

}
