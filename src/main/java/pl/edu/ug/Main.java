package pl.edu.ug;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import pl.edu.ug.rule.*;
import pl.edu.ug.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule("FPr");
        Rule ppRule = new PartiallyProbRule("PPr");
        Rule knn5Rule = new KNNRule(new EuclideanDistance(), 5, "K5nn");
        Rule fawcettRule = new FawcettRule("Faw");

        List<Rule> rules = new ArrayList<>();
        rules.add(fpRule);
        rules.add(ppRule);
        rules.add(fawcettRule);
        rules.add(knn5Rule);

        //byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);
        byte[][] parabolicImg = Utils.buildParabolicImg(100, 100);

        Experiment experiment = new Experiment(100, rules, parabolicImg, 99, 1, 4);

        experiment.start();

        //Utils.awtPrintSResults(experiment.getExperimentResults().get(0));

    }
}
