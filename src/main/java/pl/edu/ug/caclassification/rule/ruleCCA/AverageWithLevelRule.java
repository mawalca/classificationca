package pl.edu.ug.caclassification.rule.ruleCCA;

import java.util.List;

import pl.edu.ug.caclassification.util.BaseColors;

public class AverageWithLevelRule extends RuleCCA {

	private float level;
	
	public AverageWithLevelRule(String name, float level) {
        this.name = name;
        this.level = level;
    }
	
	public AverageWithLevelRule(String name, float level, boolean ifDiscret) {
        this(name, level);
        this.ifDiscret = ifDiscret;
    }
	
	@Override
	public float step(float[][] img, int row, int col) {
		if (img[row][col] == BaseColors.WHITE || img[row][col] == BaseColors.BLACK)
			return img[row][col];
		
		List<Float> neigh = getNeighbours(img, row, col);
		float nextValue = calculateAverage(neigh);
		
		if (closeTo(nextValue, BaseColors.BLACK)) return BaseColors.BLACK;
		if (closeTo(nextValue, BaseColors.WHITE)) return BaseColors.WHITE;
		return nextValue;
	}
	
    @Override
    public String toString() {
        return name + "-" + level;
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

	private boolean closeTo(float nextValue, float color) {
		return Math.abs(nextValue - color) < level;
	}
}
