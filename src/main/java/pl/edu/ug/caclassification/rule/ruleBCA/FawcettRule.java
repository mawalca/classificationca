package pl.edu.ug.caclassification.rule.ruleBCA;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;

import pl.edu.ug.caclassification.util.Colors;

import java.util.Map;

public class FawcettRule extends RuleBCA {

    public FawcettRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != Colors.unknown) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int c1 = countedClasses.get(Colors.white);
        int c2 = countedClasses.get(Colors.black);

        if (c1 + c2 == 0) return Colors.unknown;
        if (c1 > c2) return Colors.white;
        if (c2 > c1) return Colors.black;

        double[] singletons = new double[]{Colors.white, Colors.black};
        double[] probs = new double[]{0.5, 0.5};

        float result = (float) new EnumeratedRealDistribution(singletons, probs).sample();
        return result;
    }
}
