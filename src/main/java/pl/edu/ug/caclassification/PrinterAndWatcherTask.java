package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.simulation.SimResult;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public class PrinterAndWatcherTask implements Runnable {

    private BlockingQueue<List<SimResult>> resultBlockingQueue;
    private int expected;
    private int actual = 0;
    private ExecutorService executor;

    private BufferedWriter writer;

    public PrinterAndWatcherTask(BlockingQueue<List<SimResult>> resultBlockingQueue, int expected, ExecutorService executor) {
        this.resultBlockingQueue = resultBlockingQueue;
        this.expected = expected;
        this.executor = executor;
    }

    @Override
    public void run() {

        System.out.println("Printer and Watcher Task started");

        //Prepare Results file
        String filename = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss'.csv'").format(new Date());
        Path path = Paths.get("./results/" + filename);

        try {
            writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);

            while (actual < expected) {

                List<SimResult> simResults = resultBlockingQueue.take();

                if (actual == 0) {
                    StringBuilder header = new StringBuilder();
                    header.append("#");
                    // all results have the same data, prepare header
                    simResults.forEach(simResult -> {
                        header.append(",Mean").append(simResult.getRule());
                        header.append(",Stat").append(simResult.getRule());
                        header.append(",Max").append(simResult.getRule());

                    });
                    writer.write(header.toString());
                    writer.newLine();

                }
                actual++;
                StringBuilder rSb = new StringBuilder();
                rSb.append(actual);

                simResults.forEach(simResult -> {
                    rSb.append(",").append(simResult.getMean());
                    rSb.append(",").append(simResult.getStatMethodDiff());
                    rSb.append(",").append(simResult.getMax());
                });
                writer.write(rSb.toString());
                writer.newLine();

                // Not optimal but we want to have something on disk
                writer.flush();

                System.out.println("\n******* Completed: " + new Double(100.0 * actual / expected).intValue() + "%\n");
            }

            writer.close();

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
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
