package pl.edu.ug;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;
import pl.edu.ug.rule.FullProbRule;
import pl.edu.ug.rule.KNNRule;
import pl.edu.ug.rule.PartiallyProbRule;
import pl.edu.ug.rule.Rule;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        List<SimResult> simResults = new ArrayList<>();

        List<List<SimResult>> experiment = new ArrayList<>();

        Rule fpRule = new FullProbRule();
        Rule ppRule = new PartiallyProbRule();
        Rule knnRule3 = new KNNRule(new EuclideanDistance(), 3);
        Rule knnRule5 = new KNNRule(new EuclideanDistance(), 5);
        Rule knnRule7 = new KNNRule(new EuclideanDistance(), 7);

        byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);

        List<Rule> rules = new ArrayList<>();
        rules.add(fpRule);
        rules.add(ppRule);
        //rules.add(knnRule1);
        //rules.add(knnRule2);
        rules.add(knnRule3);
        rules.add(knnRule5);
        rules.add(knnRule7);

        Simulation simulation = new Simulation(diagonalImg, rules, 1, 100, simResults);
        simulation.run();

        Utils.awtPrintSResults(simResults);

//        simResults.stream().forEach(simResult -> {
//            System.out.println(simResult);
//        });

    }
}
