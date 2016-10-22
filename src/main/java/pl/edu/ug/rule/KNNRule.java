package pl.edu.ug.rule;

import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KNNRule extends Rule {

    private DistanceMeasure dm;
    private int k;

    public KNNRule(DistanceMeasure dm, int k) {
        this.dm = dm;
        this.k = k;
    }

    @Override
    public byte step(byte[][] img, int row, int col) {

        if (img[row][col] != 0) return img[row][col];

        Map<Byte, Integer> countedClasses = countClasses(img, row, col);

        int num1s = 0;
        int num2s = 0;

        if (countedClasses.get((byte)1) != null) {
            num1s = countedClasses.get((byte)1);
        }

        if (countedClasses.get((byte)2) != null) {
            num2s = countedClasses.get((byte)2);
        }

        if (num1s > num2s) return 1;
        if (num2s > num1s) return 2;

        if (random.nextInt(2) < 1) return 1;
        return 2;
    }

    @Override
    public Map<Byte, Integer> countClasses(byte[][] img, int row, int col) {

        int cols = img[0].length;
        int rows = img.length;

        List<Neighbour> allNeighbours = new ArrayList<>();

        //TODO: optimization needed here (e.g. Set of "known" points instead of full img)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // current is always class 0 (if !=0 we return immediately, see above)
                if (img[i][j] != 0) {
                    double[] current = new double[]{row, col};
                    double[] neighbour = new double[]{i, j};
                    allNeighbours.add(new Neighbour(i, j, dm.compute(current, neighbour)));
                }
            }
        }

        Collections.sort(allNeighbours);
        List<Neighbour> kNeighbours = allNeighbours.subList(0, k);


        List<Byte> neighClasses = new ArrayList<>();
        for (int i = 0; i < kNeighbours.size(); i++) {
            int r = kNeighbours.get(i).getRow();
            int c = kNeighbours.get(i).getCol();
            neighClasses.add(img[r][c]);
        }

        return count(neighClasses);
    }

    @Override
    public String toString() {
        String className = dm.toString().split("@")[0];
        String[] parts = className.split("\\.");
        return "Rule: kNN k = " + k + " Metric: " + parts[parts.length - 1];
    }
}

class Neighbour implements Comparable<Neighbour> {

    private int row;
    private int col;
    private double distance;

    public Neighbour(int row, int col, double distance) {
        this.row = row;
        this.col = col;
        this.distance = distance;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int compareTo(Neighbour o) {
        return Double.compare(distance, o.distance);
    }
}
