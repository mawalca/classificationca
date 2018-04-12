package pl.edu.ug.caclassification.rule;

import pl.edu.ug.caclassification.util.*;

public abstract class RuleCCA extends Rule {
	
	private ValuesOfColors colors = new ValuesOfColorsCCA();
	
	public ValuesOfColors getColors() {
		return colors;
	}	
}
