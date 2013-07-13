package cs;

import java.util.concurrent.CountDownLatch;

/**
 * @author Chris Steinhoff
 * @since 7/13/13
 */
public class TestHarness {

	public static void main(String[] args) {
		int threadCount = 5;

		CountDownLatch startingGate = new CountDownLatch(1);
		CountDownLatch finishLine = new CountDownLatch(threadCount);

		for(int i = 0 ; i < threadCount ; i++) {
			new Thread(new Test(startingGate, finishLine)).start();
		}

		System.out.println("Starting tests");
		startingGate.countDown();
		try {
			finishLine.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finished");
	}
}
