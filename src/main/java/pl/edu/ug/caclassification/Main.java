package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.rule.AverageRule;
import pl.edu.ug.caclassification.rule.FawcettRule;
import pl.edu.ug.caclassification.rule.FullProbRule;
import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.util.Utils;

public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule("FPr");
        //Rule fawcettRule = new FawcettRule("Faw");
    	Rule averRule = new AverageRule("average");

        float[][] parabolicImg = Utils.buildParabolicImg(100);

        // Experiment experiment = new Experiment(1, rules, parabolicImg, 30, 1, 3);
        Experiment experiment = Experiment.newBuilder()
			// .setSimulations(1)
        	.addRule(fpRule)
			.addRule(averRule)
			.setFullImg(parabolicImg)
			.setTriesInSimulation(30)
			// .setThreadPoolSize(1)
			.setPercentToShow(3)
			.build();
        		
        experiment.start();
    }
}
