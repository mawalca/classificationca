package pl.edu.ug;

import pl.edu.ug.simulation.SimResult;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public class ProgressWatcherTask implements Runnable {

    private List<List<SimResult>> simResults;
    private int expected;
    private ExecutorService executor;

    public ProgressWatcherTask(List<List<SimResult>> simResults, int expected, ExecutorService executor) {
        this.simResults = simResults;
        this.expected = expected;
        this.executor = executor;
    }

    @Override
    public void run() {

        System.out.println("Progress Watcher Task started");

        boolean pending = true;

        while (pending) {

            int actual = simResults.size();
            if (actual < expected) {
            } else {
                pending = false;
            }
            System.out.println("\n************** Completed: " + new Double(100.0 * actual / expected).intValue() + "%\n");

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                System.out.println("Progress Watcher interrupted");
            }
        }

        executor.shutdownNow();

        Experiment.printOut(simResults);

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
