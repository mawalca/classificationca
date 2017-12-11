package pl.edu.ug.caclassification.rule;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.util.Map;

public class FullProbRule extends Rule {

    public FullProbRule(String name) {
        this.name = name;
    }

    public byte step(byte[][] img, int row, int col) {

        if (img[row][col] != 0) return img[row][col];

        Map<Byte, Integer> countedClasses = countClasses(img, row, col);

        int all = countedClasses.get((byte) 0) + countedClasses.get((byte) 1) + countedClasses.get((byte) 2);
        double p0 = countedClasses.get((byte) 0) * 1.0 / all;
        double p1 = countedClasses.get((byte) 1) * 1.0 / all;
        double p2 = countedClasses.get((byte) 2) * 1.0 / all;

        int[] sinletons = new int[]{0, 1, 2};
        double[] probs = new double[]{p0, p1, p2};

        byte result = (byte) new EnumeratedIntegerDistribution(sinletons, probs).sample();


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