package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.rule.ruleBCA.FullProbRule;
import pl.edu.ug.caclassification.rule.ruleCCA.AverageRule;
import pl.edu.ug.caclassification.rule.ruleCCA.AverageWithLevelRule;
import pl.edu.ug.caclassification.rule.ruleCCA.WeightedMeanRule;
import pl.edu.ug.caclassification.simulation.SimRule;
import pl.edu.ug.caclassification.simulation.finalcondition.*;
import pl.edu.ug.caclassification.util.*;

public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule("FPr");
    	Rule avgRule = new AverageRule("Average");
    	Rule avgDiscRule = new AverageRule("AverageDisc", true);
    	Rule avgLvl1Rule = new AverageWithLevelRule("Level", 0.1f, true);
    	Rule weightMeanRule = new WeightedMeanRule("Weight", 3, 2, 1, true);

    	float[][] parabolicImg = ImageGetter.buildParabolicImg(100);
        float[][] emojiImg = ImageGetter.buildEmojiImg(100);

        Experiment experiment = Experiment.newBuilder()
			.setSimulations(1)
			
        	//.addRule(new SimRule(fpRule, new AllShownCondition(), 10))
			//.addRule(new SimRule(avgRule, new NrIterationsCondition(400)))
			//.addRule(new SimRule(avgDiscRule, new AllShownOrNoDiffCondition()))
			//.addRule(new SimRule(avgLvl1Rule, new AllShownOrNoDiffCondition()))
			.addRule(new SimRule(weightMeanRule, new AllShownOrNoDiffCondition()))
			
			.addFullImage(new FullImage("parabolic", parabolicImg))
			.addFullImage(new FullImage("emoji", emojiImg))
			.setPercentToShow(3)
			
			.setThreadPoolSize(1)
			.build();
        		
        
        experiment.start();
    }
}
