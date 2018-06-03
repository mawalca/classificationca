package pl.edu.ug.caclassification.rule.ruleBCA;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;

import pl.edu.ug.caclassification.util.BaseColors;

import java.util.Map;

public class FawcettRule extends RuleBCA {

    public FawcettRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != BaseColors.UNKNOWN) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int c1 = countedClasses.get(BaseColors.WHITE);
        int c2 = countedClasses.get(BaseColors.BLACK);

        if (c1 + c2 == 0) return BaseColors.UNKNOWN;
        if (c1 > c2) return BaseColors.WHITE;
        if (c2 > c1) return BaseColors.BLACK;

        double[] singletons = new double[]{BaseColors.WHITE, BaseColors.BLACK};
        double[] probs = new double[]{0.5, 0.5};

        float result = (float) new EnumeratedRealDistribution(singletons, probs).sample();
        return result;
    }
}
