package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.rule.ruleBCA.FullProbRule;
import pl.edu.ug.caclassification.rule.ruleCCA.AverageRule;
import pl.edu.ug.caclassification.rule.ruleCCA.AverageWithLevelRule;
import pl.edu.ug.caclassification.simulation.SimRule;
import pl.edu.ug.caclassification.simulation.finalcondition.*;
import pl.edu.ug.caclassification.util.*;

public class Main {
    public static void main(String[] args) {

        Rule fpRule = new FullProbRule("FPr");
    	Rule avgRule = new AverageRule("Average");
    	Rule avgDiscRule = new AverageRule("AverageDisc", true);
    	Rule avgLvl05Rule = new AverageWithLevelRule("Level", 0.05f, true);
    	Rule avgLvl1Rule = new AverageWithLevelRule("Level", 0.1f, true);
    	Rule avgLvl2Rule = new AverageWithLevelRule("Level", 0.2f, true);

    	float[][] parabolicImg = ImageGetter.buildParabolicImg(100);
        float[][] emojiImg = ImageGetter.buildEmojiImg(100);

        Experiment experiment = Experiment.newBuilder()
			.setSimulations(5)
			
        	.addRule(new SimRule(fpRule, new AllShownCondition(), 10))
			.addRule(new SimRule(avgRule, new NrIterationsCondition(400)))
			.addRule(new SimRule(avgDiscRule, new AllShownOrNoDiffCondition()))
			.addRule(new SimRule(avgLvl05Rule, new AllShownOrNoDiffCondition()))
			.addRule(new SimRule(avgLvl1Rule, new AllShownOrNoDiffCondition()))
			.addRule(new SimRule(avgLvl2Rule, new AllShownOrNoDiffCondition()))
			
			.addFullImage(new FullImage("parabolic", parabolicImg))
			.addFullImage(new FullImage("emoji", emojiImg))
			.setPercentToShow(3)
			
			.setThreadPoolSize(4)
			.build();
        		
        experiment.start();
    }
}
