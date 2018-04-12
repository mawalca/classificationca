package pl.edu.ug.caclassification.rule;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.util.Map;

public class PartiallyProbRule extends RuleBCA {

    public PartiallyProbRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != 0) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int all = countedClasses.get((float) 1) + countedClasses.get((float) 2);
        double p1 = countedClasses.get((float) 1) * 1.0 / all;
        double p2 = countedClasses.get((float) 2) * 1.0 / all;


        if (countedClasses.get((float) 1) + countedClasses.get((float) 2) == 0) return 0;

        int[] sinletons = new int[]{1, 2};
        double[] probs = new double[]{p1, p2};

        float result = (float) new EnumeratedIntegerDistribution(sinletons, probs).sample();

        return result;
    }
}
