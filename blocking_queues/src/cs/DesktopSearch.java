package cs;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Chris Steinhoff
 * @since 7/9/13
 */
public class DesktopSearch {
	public static void main(String[] args) throws Exception {
		File resources = new File("resources");
		if(!resources.exists()) {
			if(!resources.mkdir()) {
				System.err.println("Could not create the resources directory: " +
						resources.getAbsolutePath());
			}
		}
		createFiles(resources);
		BlockingQueue<File> queue = new ArrayBlockingQueue<File>(5);
		Thread crawler = new Thread(new FileCrawler(queue, resources));
		crawler.start();
		Thread indexer = new Thread(new Indexer(queue));
		indexer.start();
		crawler.join();
		indexer.join();
	}

	private static void createFiles(File root) throws IOException {
		for(char letter = 'a' ; letter <= 'z' ; ++letter) {
			File file = new File(root, letter + ".txt");
			file.createNewFile();
			file.deleteOnExit();
		}
	}
}
