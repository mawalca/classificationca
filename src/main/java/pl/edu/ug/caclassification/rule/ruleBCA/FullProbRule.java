package pl.edu.ug.caclassification.rule.ruleBCA;

import org.apache.commons.math3.distribution.EnumeratedRealDistribution;

import pl.edu.ug.caclassification.util.Colors;

import java.util.Map;

public class FullProbRule extends RuleBCA {

    public FullProbRule(String name) {
        this.name = name;
    }

    public float step(float[][] img, int row, int col) {

        if (img[row][col] != Colors.unknown) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int all = countedClasses.get(Colors.unknown) + countedClasses.get(Colors.white) + countedClasses.get(Colors.black);
        double p0 = countedClasses.get(Colors.unknown) * 1.0 / all;
        double p1 = countedClasses.get(Colors.white) * 1.0 / all;
        double p2 = countedClasses.get(Colors.black) * 1.0 / all;

        double[] singletons = new double[]{Colors.unknown, Colors.white, Colors.black};
        double[] probs = new double[]{p0, p1, p2};

        float result = (float) new EnumeratedRealDistribution(singletons, probs).sample();


//        if ((p0 != 0 && p0 != 1) ||
//                (p1 != 0 && p1 != 1) ||
//                (p2 != 0 && p2 != 1)) {
//            System.out.println("\nProbs:");
//            System.out.println("p0 " + p0);
//            System.out.println("p1 " + p1);
//            System.out.println("p2 " + p2);
//
//            System.out.println("result " + result);
//        }
        return result;
    }
}