package pl.edu.ug.caclassification.rule.ruleBCA;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import pl.edu.ug.caclassification.util.BaseColors;

import java.util.Map;

public class PartiallyProbRule extends RuleBCA {

    public PartiallyProbRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != 0) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int all = countedClasses.get(BaseColors.WHITE) + countedClasses.get(BaseColors.BLACK);
        double p1 = countedClasses.get(BaseColors.WHITE) * 1.0 / all;
        double p2 = countedClasses.get(BaseColors.BLACK) * 1.0 / all;


        if (countedClasses.get(BaseColors.WHITE) + countedClasses.get(BaseColors.BLACK) == 0) return 0;

        int[] sinletons = new int[]{(int) BaseColors.WHITE, (int) BaseColors.BLACK};
        double[] probs = new double[]{p1, p2};

        float result = (float) new EnumeratedIntegerDistribution(sinletons, probs).sample();

        return result;
    }
}
