package pl.edu.ug.caclassification.util;

import pl.edu.ug.caclassification.simulation.SimResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Utils {

    public static float[][] avgImg(List<float[][]> images) {
        int cols = images.get(0)[0].length;
        int rows = images.get(0).length;

        float[][] result = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int blacks = 0;
                int whites = 0;

                for (int k = 0; k < images.size(); k++) {
                    float value = images.get(k)[i][j];
                    if (value == Colors.white) whites++;
                    else if (value == Colors.black) blacks++;
                }

                if (blacks > whites) result[i][j] = Colors.black;
                else result[i][j] = Colors.white;
            }
        }
        return result;
    }

    public static float[][] hide(float[][] img, int cellsToShow) {
        int cols = img[0].length;
        int rows = img.length;

        float[][] result = new float[rows][cols];

        Random random = new Random();

        //hide all
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = Colors.unknown;

        //show only cellsToShow number of cells
        random.ints(cellsToShow, 0, rows * cols).forEach(cellNumber -> {
            int col = cellNumber % cols;
            int row = cellNumber / rows;
            result[row][col] = img[row][col];
        });
        return result;
    }
    
    public static boolean isFullyShown(float[][] img) {
        int cols = img[0].length;
        int rows = img.length;
        boolean result = true;

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (img[i][j] == Colors.unknown) return false;

        return result;
    }

    public static String byteMatrixToString(float[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == Colors.unknown) sb.append("-");
                else if (array[i][j] == Colors.white) sb.append("B");
                else if (array[i][j] == Colors.black) sb.append("C");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static float[][] buildDiagonalImg(int rows, int cols) {
        float[][] diagonalImg = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i < j) diagonalImg[i][j] = Colors.white;
                else diagonalImg[i][j] = Colors.black;
            }
        }
        return diagonalImg;
    }

//
//    public static float[][] buildParabolicImg(int rows, int cols) {
//        float[][] parabolaImg = new float[rows][cols];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                parabolaImg[i][j] = computeParabolic(i, j);
//            }
//        }
//        return parabolaImg;
//    }

    public static float[][] buildImageFromFile(Path path, List<Coordinates> coordinates) {

        List<float[]> lines = new LinkedList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            reader.lines().forEach(line -> {
                String[] strNumbers = line.split(",");
                float[] numbers = new float[strNumbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = new Float(strNumbers[i]);
                }
                lines.add(numbers);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        float[][] result = new float[lines.size()][];
        lines.toArray(result);
        return result;
    }

    public static float[][] buildParabolicImg(int size) {

        float[][] parabolaImg = new float[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                parabolaImg[i][j] = computeParabolic(i, j, size);
            }
        }
        return parabolaImg;
    }

//    private static byte computeParabolic(int i, int j) {
//
//        if (-1 * j < (-1 * Math.pow((0.2 * i - 10), 2) - 20)) return 1;
//
//        //  if (-1 * j < (-1 * Math.pow((0.1 * i - 15), 2) - 80)) return 1;
//        // -(0.1*x - 15)^2 - 80
//        return 2;
//    }

    private static float computeParabolic(int x, int y, int size) {

        int k = new Double(0.2 * size).intValue();
        int h = size / 2;

        double m = Math.pow(size - h, 2);
        double p = (size - k) / m;

        if ((y - k) < p * Math.pow(x - h, 2)) return Colors.white;
        return Colors.black;
    }


    public static int imgDiff(float[][] img1, float[][] img2) {
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

    public static void awtPrintSResults(List<SimResult> results) {

        results.stream().forEach(simResult -> {
            new AwtViewerAll(simResult);
        });

    }

}
