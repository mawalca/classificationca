package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.rule.FawcettRule;
import pl.edu.ug.caclassification.rule.FullProbRule;
import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule("FPr");
        //Rule ppRule = new PartiallyProbRule("PPr");
        // Rule knn5Rule = new KNNRule(new EuclideanDistance(), 5, "K5nn");
        Rule fawcettRule = new FawcettRule("Faw");

        List<Rule> rules = new ArrayList<>();
        rules.add(fpRule);
        //rules.add(ppRule);
        rules.add(fawcettRule);
        //rules.add(knn5Rule);

        //byte[][] diagonalImg = Utils.buildDiagonalImg(300, 300);
        byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);
        //byte[][] parabolicImg = Utils.buildParabolicImg(100, 100);
        //byte[][] parabolicImg = Utils.buildParabolicImg(100);

           // System.out.printf(Utils.byteMatrixToString(parabolicImg));

      Experiment experiment = new Experiment(5, rules, diagonalImg, 99 , 1, 4);

      experiment.start();
       //Utils.awtPrintSResults(experiment.getExperimentResults().get(0));
    }
}
