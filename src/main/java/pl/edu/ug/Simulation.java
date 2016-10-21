package pl.edu.ug;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;

public class Simulation {

    byte[][] img;
    private List<Rule> rules;
    private int showPercent;
    final AwtViewer viewer;

    int cols;
    int rows;

    Map<Rule, List<byte[][]>> results;
    int tries;

    public Simulation(byte[][] img, List<Rule> rules, int showPercent, int tries) {
        this.img = img;
        this.rules = rules;
        this.showPercent = showPercent;
        this.results = new HashMap<>();
        this.tries = tries;

        // all images are the same size
        this.cols = img[0].length;
        this.rows = img.length;

        this.viewer = new AwtViewer(rows, cols);

    }

    public void run() {
        int cellsToShow = new Double(showPercent / 100.0 * cols * rows).intValue();
        byte[][] hiddenImg = Utils.hide(img, cellsToShow);

        rules.stream().forEach(rule -> {

            System.out.println("\nRule: " + rule);

            List<byte[][]> finalImages = new ArrayList<>();
            int[] diffs = new int[tries];
            byte[][] avgImage = hiddenImg;


            for (int t = 0; t < tries; t++) {

                List<byte[][]> iterations = new ArrayList<>();
                iterations.add(img); // Full img
                // System.out.println("\nInitial img: ");
                // System.out.println(byteMatrixToString(img));

                iterations.add(hiddenImg);
                //System.out.println("\nHidden img: ");
                //System.out.println(byteMatrixToString(hiddenImg));

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

              //  System.out.println("\nPROBA: " + t);
              //  System.out.println("Iterations: " + (iterations.size() - 2));

                int diff = Utils.imgDiff(img, finalImage);
             //   System.out.println("Diff: " + diff);
                diffs[t] = diff;

                avgImage = Utils.avgImg(finalImages);
             //   System.out.println("Avg img: " + Utils.imgDiff(img, avgImage));

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
            //double median = stats.getPercentile(50);

            System.out.println("Mean " + mean + " " + " Std: " + std);
            //System.out.println(byteMatrixToString(hiddenImg));


            viewer.drawArray(avgImage);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
    }

}
