package pl.edu.ug.caclassification.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ImageGetter {

	public static float[][] buildDiagonalImg(int rows, int cols) {
        float[][] diagonalImg = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i < j) diagonalImg[i][j] = BaseColors.WHITE;
                else diagonalImg[i][j] = BaseColors.BLACK;
            }
        }
        return diagonalImg;
    }
	
	public static float[][] buildDiagonalImg(int size) {
    	return buildDiagonalImg(size, size);
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

    public static float[][] buildEmojiImg(int rows, int cols) {
        float[][] emojiImg = new float[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isEmoji((double)i/rows, (double)j/cols)) emojiImg[i][j] = BaseColors.BLACK;
                else emojiImg[i][j] = BaseColors.WHITE;
            }
        }
        return emojiImg;
    }

    public static float[][] buildEmojiImg(int size) {
    	return buildEmojiImg(size, size);
    }

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
    
    private static float computeParabolic(int x, int y, int size) {

        int k = new Double(0.2 * size).intValue();
        int h = size / 2;

        double m = Math.pow(size - h, 2);
        double p = (size - k) / m;

        if ((y - k) < p * Math.pow(x - h, 2)) return BaseColors.WHITE;
        return BaseColors.BLACK;
    }
    
    private static boolean isEmoji(double row, double col) {
		boolean isLeftEye = Math.pow(row - 0.3, 2) + Math.pow(col - 0.3, 2) <= 0.01;
		if (isLeftEye) return true;
		boolean isRightEye = Math.pow(row - 0.3, 2) + Math.pow(col - 0.7, 2) <= 0.01;
		if (isRightEye) return true;
		boolean isMouth = 0.6 <= row && row <= 0.8 && 0.1 <= col && col <= 0.9;
		return isMouth;
	}
	
}
