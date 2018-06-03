package pl.edu.ug.caclassification.rule.ruleCCA;

import java.util.List;

import pl.edu.ug.caclassification.util.BaseColors;

public class AverageRule extends RuleCCA {

	public AverageRule(String name) {
        this.name = name;
    }
	
	public AverageRule(String name, boolean ifDiscret) {
		this(name);
        this.ifDiscret = ifDiscret;
    }
	
	public float step(float[][] img, int row, int col) {
		
		if (img[row][col] == BaseColors.WHITE || img[row][col] == BaseColors.BLACK)
			return img[row][col];
		
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
