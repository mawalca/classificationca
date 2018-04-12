package pl.edu.ug.caclassification.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.ug.caclassification.util.*;

public abstract class RuleBCA extends Rule {

	private ValuesOfColors colors = new ValuesOfColorsBCA();
	
	public ValuesOfColors getColors() {
		return colors;
	}
	
	// neighbours without cell itself
	public List<Float> getNeighbours(float[][] img, int row, int col) {
    	int cols = img[0].length;
        int rows = img.length;

        List<Float> neigh = new ArrayList<>();

        // vertex - 3 cells in neighborhood
        if (row == 0 && col == 0) {
            neigh.add(img[0][1]);
            neigh.add(img[1][1]);
            neigh.add(img[1][0]);
            return neigh;
        }
        if (row == 0 && col == cols - 1) {
            neigh.add(img[0][col]);
            neigh.add(img[1][col - 1]);
            neigh.add(img[1][col]);
            return neigh;
        }
        if (row == rows - 1 && col == cols - 1) {
            neigh.add(img[row - 1][col]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col - 1]);
            return neigh;
        }
        if (row == rows - 1 && col == 0) {
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col]);
            neigh.add(img[row][col + 1]);
            return neigh;
        }
        // side - 5 cells in neighborhood
        if (row == 0) {
            neigh.add(img[row][col - 1]);
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
            neigh.add(img[row][col + 1]);
            return neigh;
        }
        if (col == 0) {
            neigh.add(img[row - 1][col]);
            neigh.add(img[row - 1][col + 1]);
            neigh.add(img[row][col + 1]);
            neigh.add(img[row + 1][col + 1 ]);
            neigh.add(img[row + 1][col]);
            return neigh;
        }
        if (col == cols - 1) {
            neigh.add(img[row - 1][col -1]);
            neigh.add(img[row - 1][col]);
            neigh.add(img[row][col - 1]);
            neigh.add(img[row + 1][col - 1]);
            neigh.add(img[row + 1][col]);
            return neigh;
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
        
        return neigh;
    }
	
    // returns Map 0 -> # class 0, 1 -> #class 1, itp.
    public Map<Float, Integer> countClasses(float[][] img, int row, int col) {
        List<Float> neigh = getNeighbours(img, row, col);
        return count(neigh);
    }

    protected static Map<Float, Integer> count(List<Float> neigh){
        Map<Float, Integer> result = new HashMap<>();
        result.put((float)0, 0);
        result.put((float)1, 0);
        result.put((float)2, 0);
        neigh.forEach(value -> result.put(value, result.get(value) + 1));
        return result;
    }
}
