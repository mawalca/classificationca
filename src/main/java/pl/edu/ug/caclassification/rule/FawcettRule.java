package pl.edu.ug.caclassification.rule;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.util.Map;

public class FawcettRule extends RuleBCA {

    public FawcettRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != 0) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int c1 = countedClasses.get((float) 1);
        int c2 = countedClasses.get((float) 2);

        if (c1 + c2 == 0) return 0;
        if (c1 > c2) return 1;
        if (c2 > c1) return 2;

        int[] sinletons = new int[]{1, 2};
        double[] probs = new double[]{0.5, 0.5};

        byte result = (byte) new EnumeratedIntegerDistribution(sinletons, probs).sample();
        return result;
    }
}
