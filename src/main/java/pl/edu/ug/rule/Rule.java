package pl.edu.ug.rule;

import java.util.*;

public abstract class Rule {

    Random random = new Random();

    abstract public byte step(byte[][] img, int row, int col);

    // returns Map 0 -> # class 0, 1 -> #class 1, itp.
    public Map<Byte, Integer> countClasses(byte[][] img, int row, int col) {
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

    protected static Map<Byte, Integer> count(List<Byte> neigh){
        Map<Byte, Integer> result = new HashMap<>();
        result.put((byte)0, 0);
        result.put((byte)1, 0);
        result.put((byte)2, 0);
        neigh.forEach(value -> result.put(value, result.get(value) + 1));
        return result;
    }
}
