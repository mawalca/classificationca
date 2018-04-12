package pl.edu.ug.caclassification.rule;

import java.util.List;

public class AverageRule extends RuleCCA {

	public AverageRule(String name) {
        this.name = name;
    }
	
	public float step(float[][] img, int row, int col) {
		if (img[row][col] == 1.0)
			return 1;
		if (img[row][col] == 0.0)
			return 0;
		
		List<Float> neigh = getNeighbours(img, row, col);
		return calculateAverage(neigh);
	}
	
	private float calculateAverage(List<Float> cells) {
		int len = cells.size();
		if (len == 0) {
			return 0;
		}
		float sum = 0.0f;
		for (int i = 0; i < len; i++) {
            sum += cells.get(i);
		}
		return sum / len;
	}
}
