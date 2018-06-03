package pl.edu.ug.caclassification.util;

import pl.edu.ug.caclassification.simulation.SimResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

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
                    if (value == BaseColors.WHITE) whites++;
                    else if (value == BaseColors.BLACK) blacks++;
                }

                if (blacks > whites) result[i][j] = BaseColors.BLACK;
                else result[i][j] = BaseColors.WHITE;
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
                result[i][j] = BaseColors.UNKNOWN;

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
                if (img[i][j] != BaseColors.WHITE && img[i][j] != BaseColors.BLACK)
                	return false;

        return result;
    }

    public static String byteMatrixToString(float[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] == BaseColors.UNKNOWN) sb.append("-");
                else if (array[i][j] == BaseColors.WHITE) sb.append("B");
                else if (array[i][j] == BaseColors.BLACK) sb.append("C");
            }
            sb.append("\n");
        }
        return sb.toString();
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
    
    public static float imgError(float[][] originalImage, float[][] resultImage) {
        // assume the same shape
        int cols = originalImage[0].length;
        int rows = originalImage.length;

        float result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result += computeError(originalImage[i][j], resultImage[i][j]);
            }
        }
        return result;
    }
    
    public static float[][] getDiscretization(float[][] image) {
        int cols = image[0].length;
        int rows = image.length;

        float[][] result = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = (image[i][j] < 0.5) ? 0 : 1;
            }
        }
        return result;
    }

	public static void awtPrintSResults(List<SimResult> results) {

        results.stream().forEach(simResult -> {
            new AwtViewerAll(simResult);
        });
    }
	
    private static float computeError(float orig, float result) {
		return Math.abs(orig - result);
	}
    
    public static void cosTam(int[] diffs) {

		DescriptiveStatistics stats = new DescriptiveStatistics();

		// Add the data from the array
		for (int i = 0; i < diffs.length; i++) {
			stats.addValue(diffs[i]);
		}

		// Compute some statistics
		double mean = stats.getMean();
		double std = stats.getStandardDeviation();
		int max = new Double(stats.getMax()).intValue();
		
		//int avgMethodDiff = Utils.imgDiff(image.getImage(), avgImage);
    }

}
