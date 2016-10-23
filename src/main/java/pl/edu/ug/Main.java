package pl.edu.ug;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import pl.edu.ug.rule.FullProbRule;
import pl.edu.ug.rule.KNNRule;
import pl.edu.ug.rule.PartiallyProbRule;
import pl.edu.ug.rule.Rule;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule();
        Rule ppRule = new PartiallyProbRule();
        Rule knn5Rule = new KNNRule(new EuclideanDistance(), 5);

        List<Rule> rules = new ArrayList<>();
        rules.add(fpRule);
        rules.add(ppRule);
        rules.add(knn5Rule);

        byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);

        Experiment experiment = new Experiment(5, rules, diagonalImg, 50, 1);

        experiment.start();
        //Utils.awtPrintSResults(simResults);

    }
}
