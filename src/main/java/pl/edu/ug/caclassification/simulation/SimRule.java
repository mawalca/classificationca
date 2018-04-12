package pl.edu.ug.caclassification.simulation;

import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.simulation.finalcondition.FinalCondition;

public class SimRule {

	private Rule rule;
	private FinalCondition condition;
	private int tries;
	
	public SimRule(Rule rule, FinalCondition condition, int tries) {
		this.rule = rule;
		this.condition = condition;
		this.tries = tries;
	}

	public Rule getRule() {
		return rule;
	}

	public FinalCondition getCondition() {
		return condition;
	}

	public int getTries() {
		return tries;
	}
}
