package cs;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

/**
 * @author Chris Steinhoff
 * @since 7/9/13
 */
public class FileCrawler implements Runnable {
	public static final File poison = new File("poison");
	private final BlockingQueue<File> queue;
	private final File root;

	public FileCrawler(BlockingQueue<File> queue, File root) {
		this.queue = queue;
		this.root = root;
	}

	@Override
	public void run() {
		try {
			crawl(root);
			queue.put(poison);
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void crawl(File dir) throws InterruptedException {
		File[] files = dir.listFiles();
		if(files != null) {
			Arrays.sort(files);
			for(File file : files) {
				if(file.isDirectory()) {
					crawl(file);
				} else {
					System.out.println("> " + file.getAbsolutePath());
					queue.put(file);
				}
			}
		}
	}
}
