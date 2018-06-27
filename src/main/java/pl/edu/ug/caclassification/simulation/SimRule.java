package pl.edu.ug.caclassification.simulation;

import java.util.List;

import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.simulation.finalcondition.FinalCondition;

public class SimRule {

	private Rule rule;
	private FinalCondition condition;
	private int tries;
	
	public SimRule(Rule rule, FinalCondition condition) {
		this.rule = rule;
		this.condition = condition;
	}
	
	public SimRule(Rule rule, FinalCondition condition, int tries) {
		this(rule, condition);
		this.tries = tries;
	}

	public Rule getRule() {
		return rule;
	}
	
	public int getTries() {
		return tries;
	}
	
	public float ruleStep(float[][] img, int row, int col) {
		return rule.step(img, row, col);
	}
	
	public boolean ifDiscret() {
		return rule.ifDiscret();
	}

	public boolean isFinished(List<float[][]> iterations, int iter) {
		return condition.isFinished(iterations, iter);
	}
	
	@Override
    public String toString() {
		if (tries == 0)
			return rule.toString() + "-" + condition.toString();
		return rule.toString() + "-" + condition.toString() + "-" + tries + "tries";
    }
}
