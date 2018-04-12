package pl.edu.ug.caclassification.rule.ruleBCA;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import pl.edu.ug.caclassification.util.Colors;

import java.util.Map;

public class PartiallyProbRule extends RuleBCA {

    public PartiallyProbRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != 0) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int all = countedClasses.get(Colors.white) + countedClasses.get(Colors.black);
        double p1 = countedClasses.get(Colors.white) * 1.0 / all;
        double p2 = countedClasses.get(Colors.black) * 1.0 / all;


        if (countedClasses.get(Colors.white) + countedClasses.get(Colors.black) == 0) return 0;

        int[] sinletons = new int[]{(int) Colors.white, (int) Colors.black};
        double[] probs = new double[]{p1, p2};

        float result = (float) new EnumeratedIntegerDistribution(sinletons, probs).sample();

        return result;
    }
}
