package pl.edu.ug;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;

public class Simulation {

    byte[][] img;
    private List<Rule> rules;
    private int showPercent;

    Map<Rule, List<byte[][]>> results;
    int tries;

    public Simulation(byte[][] img, List<Rule> rules, int showPercent, int tries) {
        this.img = img;
        this.rules = rules;
        this.showPercent = showPercent;
        this.results = new HashMap<>();
        this.tries = tries;
    }

    public void run() {
        int cols = img[0].length;
        int rows = img.length;
        int cellsToShow = new Double(showPercent / 100.0 * cols * rows).intValue();
        byte[][] hiddenImg = hide(img, cellsToShow);

        rules.stream().forEach(rule -> {

            System.out.println("\nRule: " + rule);

            List<byte[][]> finalImages = new ArrayList<>();
            int[] diffs = new int[tries];


            for (int t = 0; t < tries; t++) {

                List<byte[][]> iterations = new ArrayList<>();
                iterations.add(img); // Full img
                // System.out.println("\nInitial img: ");
                // System.out.println(byteMatrixToString(img));

                iterations.add(hiddenImg);
                //System.out.println("\nHidden img: ");
                //System.out.println(byteMatrixToString(hiddenImg));

                for (int iter = 2; !isFullyShown(iterations.get(iter - 1)); iter++) {

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


                System.out.println("\nPROBA: " + t);
                System.out.println("Iterations: " + (iterations.size() - 2));

                int diff = imgDiff(img,finalImage );
                System.out.println("Diff: " + diff);
                diffs[t] = diff;

                byte[][] avgImage = avgImg(finalImages);
                System.out.println("Avg img: " + imgDiff(img, avgImage ));

                //System.out.println(byteMatrixToString(avgImage));
            }


            DescriptiveStatistics stats = new DescriptiveStatistics();

            // Add the data from the array
            for( int i = 0; i < diffs.length; i++) {
                stats.addValue(diffs[i]);
            }

            // Compute some statistics
            double mean = stats.getMean();
            double std = stats.getStandardDeviation();
            //double median = stats.getPercentile(50);

            System.out.println("Mean " + mean + " " + " Std: " + std);
            //System.out.println(byteMatrixToString(hiddenImg));

        });
    }

    private byte[][] avgImg(List<byte[][]> images){
        int cols = images.get(0)[0].length;
        int rows = images.get(0).length;

        byte[][] result = new byte[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int blacks = 0;
                int whites = 0;

                for (int k = 0; k < images.size(); k++) {
                    byte value = images.get(k)[i][j];
                    if (value == 1) whites++;
                    else if (value == 2) blacks++;
                }

                if (blacks > whites) result[i][j] = 2;
                else result[i][j] = 1;
            }
        }
        return result;
    }

    private static byte[][] hide(byte[][] img, int cellsToShow) {
        int cols = img[0].length;
        int rows = img.length;

        byte[][] result = new byte[rows][cols];

        Random random = new Random();

        //hide all
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = 0;

        //show only cellsToShow number of cells
        random.ints(cellsToShow, 0, rows * cols).forEach(cellNumber -> {
            int col = cellNumber % cols;
            int row = cellNumber / rows;
            result[row][col] = img[row][col];
        });
        return result;
    }

    private static boolean isFullyShown(byte[][] img) {
        int cols = img[0].length;
        int rows = img.length;
        boolean result = true;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (img[i][j] == 0) return false;

        return result;
    }

    public static String byteMatrixToString(byte[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == 0) sb.append("-");
                else if (array[i][j] == 1) sb.append("B");
                else if (array[i][j] == 2) sb.append("C");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static byte[][] buildDiagonalImg(int rows, int cols) {
        byte[][] diagonalImg = new byte[100][100];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i < j) diagonalImg[i][j] = 1;
                else diagonalImg[i][j] = 2;
            }
        }
        return diagonalImg;
    }


    public static int imgDiff(byte[][] img1, byte[][] img2) {
        // assume the same shape
        int cols = img1[0].length;
        int rows = img1.length;

        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (img1[i][j] != img2[i][j]) result++;
            }
        }
        return result;
    }
}
