package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.rule.FawcettRule;
import pl.edu.ug.caclassification.rule.FullProbRule;
import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.util.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;
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

        byte[][] diagonalImg = Utils.buildDiagonalImg(100, 100);
        //byte[][] diagonalImg = Utils.buildDiagonalImg(50, 50);
        //byte[][] parabolicImg = Utils.buildParabolicImg(100, 100);
        byte[][] parabolicImg = Utils.buildParabolicImg(100);

        //Path path = Paths.get("./images/PFvsFawcetImgsDiagonal50x50_03/4/Faw_hiddenImg.csv");
        //byte[][] startImg = Utils.buildImageFromFile(path, null);

        //System.out.printf(Utils.byteMatrixToString(diagonalImg));

        Experiment experiment = new Experiment(1, rules, parabolicImg, 99, 1, 3);
//        Experiment experiment = new Experiment(diagonalImg, startImg, rules, 99);

        experiment.start();
        //Utils.awtPrintSResults(experiment.getExperimentResults().get(0));
    }
}
