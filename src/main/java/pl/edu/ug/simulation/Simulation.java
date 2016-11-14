package pl.edu.ug.simulation;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import pl.edu.ug.util.Utils;
import pl.edu.ug.rule.Rule;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Simulation {

    private byte[][] img;
    private List<Rule> rules;
    private int showPercent;

    private int cols;
    private int rows;

    private BlockingQueue<List<SimResult>> resultBlockingQueue;
    int tries;

    private Random rand = new Random();

    public Simulation(byte[][] img, List<Rule> rules, int showPercent, int tries, BlockingQueue<List<SimResult>> resultBlockingQueue) {
        this.img = img;
        this.rules = rules;
        this.showPercent = showPercent;
        this.tries = tries;

        // all images are the same size
        this.cols = img[0].length;
        this.rows = img.length;

        this.resultBlockingQueue = resultBlockingQueue;
    }

    public void run() {
        int cellsToShow = new Double(showPercent / 100.0 * cols * rows).intValue();
        byte[][] hiddenImg = Utils.hide(img, cellsToShow);

        List<SimResult> simResults = new ArrayList<>();

        rules.stream().forEach(rule -> {

            List<byte[][]> finalImages = new ArrayList<>();

            int[] diffs = new int[tries];
            byte[][] avgImage = hiddenImg;

            for (int t = 0; t < tries; t++) {

                List<byte[][]> iterations = new ArrayList<>();
                iterations.add(img); // Full img

                iterations.add(hiddenImg);

                //TODO: optimization for "Deterministic" rules
                for (int iter = 2; !Utils.isFullyShown(iterations.get(iter - 1)); iter++) {

                    byte[][] iterResult = new byte[rows][cols];  //uninitialized

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            iterResult[i][j] = rule.step(iterations.get(iter - 1), i, j);
                        }
                    }
                    iterations.add(iterResult);
                }

                byte[][] finalImage = iterations.get(iterations.size() - 1);
                finalImages.add(finalImage);

                int diff = Utils.imgDiff(img, finalImage);
                diffs[t] = diff;

                //System.out.println(byteMatrixToString(avgImage));
            }

            DescriptiveStatistics stats = new DescriptiveStatistics();

            // Add the data from the array
            for (int i = 0; i < diffs.length; i++) {
                stats.addValue(diffs[i]);
            }

            // Compute some statistics
            double mean = stats.getMean();
            double std = stats.getStandardDeviation();
            int max = new Double(stats.getMax()).intValue();

            // Apply Stats Method
            avgImage = Utils.avgImg(finalImages);
            int avgMethodDiff = Utils.imgDiff(img, avgImage);

            // Gather some (3 - hardcoded) samples
            List<byte[][]> samples = new ArrayList<>();

            if (3 < tries) {
                rand.ints(3, 0, tries).forEach(i -> samples.add(finalImages.get(i)));
            }

            SimResult simResult = new SimResult(rule, img, hiddenImg, samples, avgImage, mean, std, max, avgMethodDiff);
            simResults.add(simResult);
            System.out.println(simResult);
        });

        try {
            resultBlockingQueue.put(simResults);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
