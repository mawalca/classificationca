package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.simulation.SimResult;
import pl.edu.ug.caclassification.util.Utils;

import java.io.BufferedWriter;
import java.io.File;
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

        //Prepare Results directory
        String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
        new File("./results/" + dateName).mkdir();

        Path resultPath = Paths.get("./results/" + dateName + "/" + dateName + ".csv");


        try {

            writer = Files.newBufferedWriter(resultPath, StandardOpenOption.CREATE);

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


                File dir = new File("./results/" + dateName + "/" + actual);
                dir.mkdir();

                for (int i = 0; i < simResults.size(); i++) {

                    SimResult simResult = simResults.get(i);
                    String ruleName = simResult.getRule().toString();

                    // img, hiddenImg, samples, avgImage,
                    Path img = Paths.get(dir.getAbsolutePath() + "/" + ruleName + "_orgImg.csv");
                    Path hiddenImg = Paths.get(dir.getAbsolutePath() + "/" + ruleName + "_hiddenImg.csv");
                    Path avgImg = Paths.get(dir.getAbsolutePath() + "/" + ruleName + "_avgImg.csv");

                    // No of samples - 3 (hardcoded)
                    Path sample1Img = Paths.get(dir.getAbsolutePath() + "/" + ruleName + "_sample1Img.csv");
                    Path sample2Img = Paths.get(dir.getAbsolutePath() + "/" + ruleName + "_sample2Img.csv");
                    Path sample3Img = Paths.get(dir.getAbsolutePath() + "/" + ruleName + "_sample3Img.csv");


                    saveImg(img, simResult.getImg());
                    saveImg(hiddenImg, simResult.getHiddenImg());
                    saveImg(avgImg, simResult.getAvgImage());
                    saveImg(sample1Img, simResult.getSamples().get(0));
                    saveImg(sample2Img, simResult.getSamples().get(1));
                    saveImg(sample3Img, simResult.getSamples().get(2));

                }

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

    private void saveImg(Path path, byte[][] img) {

        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);

            for (byte[] line : img) {
                StringBuilder textLine = new StringBuilder();
                for (int i = 0; i < line.length; i++) {
                    textLine.append(line[i]);
                    if (i < line.length - 1) {
                        textLine.append(",");
                    }
                }
                writer.write(textLine.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
