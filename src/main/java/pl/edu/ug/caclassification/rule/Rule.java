package pl.edu.ug.caclassification.rule;

import java.util.*;

public abstract class Rule {

    protected String name = "";

    abstract public float step(float[][] img, int row, int col);
    
    public List<Float> getNeighbours(float[][] img, int row, int col) {
    	int cols = img[0].length;
        int rows = img.length;

        List<Float> neigh = new ArrayList<>();

        // vertex - 4 cells in neighborhood
        if (row == 0 && col == 0) {
        	neigh.add(img[0][0]);
            neigh.add(img[0][1]);
            neigh.add(img[1][1]);
            neigh.add(img[1][0]);
            return neigh;
        }
        if (row == 0 && col == cols - 1) {
            neigh.add(img[0][col - 1]);
            neigh.add(img[0][col]);
            neigh.add(img[1][col - 1]);
            neigh.add(img[1][col]);
            return neigh;
        }
        if (row == rows - 1 && col == cols - 1) {
            neigh.add(img[row - 1][col - 1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col - 1]);
            return neigh;
        }
        if (row == rows - 1 && col == 0) {
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col + 1]);
            return neigh;
        }
        // side - 6 cells in neighborhood
        if (row == 0) {
            neigh.add(img[row][col - 1]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col + 1]);
            neigh.add(img[row + 1][col - 1]);
            neigh.add(img[row + 1][col]);
            neigh.add(img[row + 1][col + 1]);
            return neigh;
        }
        if (row == rows - 1) {
            neigh.add(img[row - 1][col - 1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col - 1]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col + 1]);
            return neigh;
        }
        if (col == 0) {
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col + 1]);
            neigh.add(img[row + 1][col + 1 ]);
            neigh.add(img[row + 1][col]);
            return neigh;
        }
        if (col == cols - 1) {
            neigh.add(img[row - 1][col -1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row][col - 1]);
            neigh.add(img[row][col]);
            neigh.add(img[row + 1][col - 1]);
            neigh.add(img[row + 1][col]);
            return neigh;
        }

        // 9 cells in neighborhood
        neigh.add(img[row - 1][col - 1]);
        neigh.add(img[row - 1][col]);
        neigh.add(img[row - 1][col + 1]);
        neigh.add(img[row][col - 1]);
        neigh.add(img[row][col]);
        neigh.add(img[row][col + 1]);
        neigh.add(img[row + 1][col - 1]);
        neigh.add(img[row + 1][col]);
        neigh.add(img[row + 1][col + 1]);
        
        return neigh;
    }

    @Override
    public String toString() {
        return name;
    }
}
