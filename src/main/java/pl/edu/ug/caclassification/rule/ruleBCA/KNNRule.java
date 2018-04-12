package pl.edu.ug.caclassification.rule.ruleBCA;

import org.apache.commons.math3.ml.distance.DistanceMeasure;

import pl.edu.ug.caclassification.util.Colors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KNNRule extends RuleBCA {

	private Random random = new Random();
    private DistanceMeasure dm;
    private int k;

    public KNNRule(DistanceMeasure dm, int k, String name) {
        this.dm = dm;
        this.k = k;
        this.name = name;
    }

    @Override
    public float step(float[][] img, int row, int col) {

        if (img[row][col] != Colors.unknown) return img[row][col];

        Map<Float, Integer> countedClasses = countClasses(img, row, col);

        int numWhites = 0;
        int numBlacks = 0;

        if (countedClasses.get(Colors.white) != null) {
        	numWhites = countedClasses.get(Colors.white);
        }

        if (countedClasses.get(Colors.black) != null) {
            numBlacks = countedClasses.get(Colors.black);
        }

        if (numWhites > numBlacks) return Colors.white;
        if (numBlacks > numWhites) return Colors.black;

        if (random.nextInt(2) < 1) return Colors.white;
        return Colors.black;
    }

    @Override
    public Map<Float, Integer> countClasses(float[][] img, int row, int col) {

        int cols = img[0].length;
        int rows = img.length;

        List<Neighbour> allNeighbours = new ArrayList<>();

        //TODO: optimization needed here (e.g. Set of "known" points instead of full img)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // current is always class 0 (if !=0 we return immediately, see above)
                if (img[i][j] != Colors.unknown) {
                    double[] current = new double[]{row, col};
                    double[] neighbour = new double[]{i, j};
                    allNeighbours.add(new Neighbour(i, j, dm.compute(current, neighbour)));
                }
            }
        }

        Collections.sort(allNeighbours);
        List<Neighbour> kNeighbours = allNeighbours.subList(0, k);


        List<Float> neighClasses = new ArrayList<>();
        for (int i = 0; i < kNeighbours.size(); i++) {
            int r = kNeighbours.get(i).getRow();
            int c = kNeighbours.get(i).getCol();
            neighClasses.add(img[r][c]);
        }

        return count(neighClasses);
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
