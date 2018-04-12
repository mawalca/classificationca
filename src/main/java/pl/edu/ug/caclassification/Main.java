package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.rule.ruleBCA.FullProbRule;
import pl.edu.ug.caclassification.rule.ruleCCA.AverageRule;
import pl.edu.ug.caclassification.simulation.SimRule;
import pl.edu.ug.caclassification.simulation.finalcondition.*;
import pl.edu.ug.caclassification.util.Utils;

public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule("FPr");
    	Rule averRule = new AverageRule("Average");

        float[][] parabolicImg = Utils.buildParabolicImg(100);

        Experiment experiment = Experiment.newBuilder()
			// .setSimulations(1)
        	.addRule(new SimRule(fpRule, new AllShownCondition(), 4))
			.addRule(new SimRule(averRule, new NrIterationsCondition(100), 4))
			.setFullImg(parabolicImg)
			// .setThreadPoolSize(1)
			.setPercentToShow(3)
			.build();
        		
        experiment.start();
    }
}
