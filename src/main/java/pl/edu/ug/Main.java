package pl.edu.ug;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {


        Rule fpRule = new FullProbRule();
        Rule ppRule = new PartiallyProbRule();
        Rule knnRule1 = new KNNRule(new ManhattanDistance(), 5);
        Rule knnRule2 = new KNNRule(new ManhattanDistance(), 10);
        Rule knnRule3 = new KNNRule(new EuclideanDistance(), 3);

        byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);

        List<Rule> rules = new ArrayList<>();
        //rules.add(fpRule);
        rules.add(ppRule);
        rules.add(knnRule1);
        rules.add(knnRule2);
        rules.add(knnRule3);

        Simulation simulation = new Simulation(diagonalImg, rules, 1, 99);
        simulation.run();

    }
}
