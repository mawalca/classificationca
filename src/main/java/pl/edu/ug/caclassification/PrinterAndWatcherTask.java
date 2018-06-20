package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.simulation.SimResult;
import pl.edu.ug.caclassification.util.ResultFilesWriter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class PrinterAndWatcherTask implements Runnable {

	private BlockingQueue<List<SimResult>> resultBlockingQueue;
	private int expected;
	private int actual = 0;
	private int percent;
	private ExecutorService executor;

	public PrinterAndWatcherTask(BlockingQueue<List<SimResult>> resultBlockingQueue, int expected,
			int percent, ExecutorService executor) {
		this.resultBlockingQueue = resultBlockingQueue;
		this.expected = expected;
		this.percent = percent;
		this.executor = executor;
	}

	@Override
	public void run() {

		System.out.println("Printer and Watcher Task started");

		ResultFilesWriter resultWriter = new ResultFilesWriter(percent);

		try {
			while (actual < expected) {

				List<SimResult> simResults = resultBlockingQueue.take();

				actual++;
				
				resultWriter.saveErrors(simResults, actual);
				
				simResults.forEach(simResult -> {
					resultWriter.saveToFiles(simResult, actual);
				});
				
				System.out.println("\n******* Completed: " + new Double(100.0 * actual / expected).intValue() + "%\n");
			}
		} catch (InterruptedException e) {
			System.out.println("Watcher Task interrupted");
		}

		executor.shutdownNow();

		try {
			if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
				System.out.println("Still waiting...");
				System.exit(0);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
