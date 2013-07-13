package cs;

import java.util.concurrent.CountDownLatch;

/**
 * @author Chris Steinhoff
 * @since 7/13/13
 */
public class Test implements Runnable {
	private final CountDownLatch startingGate;
	private final CountDownLatch finishLine;

	public Test(CountDownLatch startingGate, CountDownLatch finishLine) {
		this.startingGate = startingGate;
		this.finishLine = finishLine;
	}

	@Override
	public void run() {
		try {
			startingGate.await();
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		System.out.println(Thread.currentThread().getName());
		finishLine.countDown();
	}
}
