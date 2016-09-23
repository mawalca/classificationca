package pl.edu.ug;

import java.util.List;
import java.util.Random;

public class Utils {

    public static byte[][] avgImg(List<byte[][]> images){
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

    public static byte[][] hide(byte[][] img, int cellsToShow) {
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

    public static boolean isFullyShown(byte[][] img) {
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
