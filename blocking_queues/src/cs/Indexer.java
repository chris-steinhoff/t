package cs;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * @author Chris Steinhoff
 * @since 7/9/13
 */
public class Indexer implements Runnable {
	private final BlockingQueue<File> queue;

	public Indexer(BlockingQueue<File> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			for(
					File file = queue.take();
					file != FileCrawler.poison;
					file = queue.take()
					) {
				System.out.println("< " + file.getAbsolutePath());
			}
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
