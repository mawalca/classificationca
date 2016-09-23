package pl.edu.ug;

import java.util.*;
import java.util.stream.IntStream;

public abstract class Rule {

    Random random = new Random();

    abstract public byte step(byte[][] img, int row, int col);

    // returns Map 0 -> # class 0, 1 -> #class 1, itp.
    public static Map<Byte, Byte> countClasses(byte[][] img, int row, int col) {
        int cols = img[0].length;
        int rows = img.length;

        List<Byte> neigh = new ArrayList<>();

        // 3 cells in neighborhood
        if (row == 0 && col == 0) {
            neigh.add(img[0][1]);
            neigh.add(img[1][1]);
            neigh.add(img[1][0]);
            return count(neigh);
        }
        else if (row == 0 && col == cols -1 ) {
            neigh.add(img[0][col - 1]);
            neigh.add(img[1][col - 1]);
            neigh.add(img[1][col]);
            return count(neigh);
        }
        else if (row == rows - 1 && col == cols - 1) {
            neigh.add(img[row - 1][col - 1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row][col - 1]);
            return count(neigh);
        }
        else if (row == rows - 1 && col == 0) {
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col + 1]);
            return count(neigh);
        }
        // 5 cells in neighborhood
        else if (row == 0 ){
            neigh.add(img[row][col - 1 ]);
            neigh.add(img[row][col + 1]);
            neigh.add(img[row + 1][col - 1]);
            neigh.add(img[row + 1][col]);
            neigh.add(img[row + 1][col + 1]);
            return count(neigh);
        }

        else if (row == rows - 1 ){
            neigh.add(img[row - 1][col - 1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col - 1]);
            neigh.add(img[row][col + 1]);
            return count(neigh);
        }

        else if (col == 0 ){
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col + 1]);
            neigh.add(img[row + 1][col + 1 ]);
            neigh.add(img[row + 1][col]);
            return count(neigh);
        }

        else if (col == cols - 1 ){
            neigh.add(img[row - 1][col -1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row][col - 1]);
            neigh.add(img[row + 1][col - 1]);
            neigh.add(img[row + 1][col]);
            return count(neigh);
        }

        // 8 cells in neighborhood
        neigh.add(img[row - 1][col - 1]);
        neigh.add(img[row - 1][col]);
        neigh.add(img[row - 1][col + 1]);
        neigh.add(img[row][col - 1]);
        neigh.add(img[row][col + 1]);
        neigh.add(img[row + 1][col - 1]);
        neigh.add(img[row + 1][col]);
        neigh.add(img[row + 1][col + 1]);

        return count(neigh);
    }

    private static Map<Byte, Byte> count(List<Byte> neigh){
        Map<Byte, Byte> result = new HashMap<>();
        result.put((byte)0, (byte)0);
        result.put((byte)1, (byte)0);
        result.put((byte)2, (byte)0);
        neigh.forEach(value -> result.put(value, (byte)(result.get(value) + 1)));
        return result;
    }
}
