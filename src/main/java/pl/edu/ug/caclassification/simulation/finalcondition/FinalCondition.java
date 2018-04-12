package pl.edu.ug.caclassification.simulation.finalcondition;

import java.util.List;

public abstract class FinalCondition {
	
	public abstract boolean isFinished(List<float[][]> iterations, int iter);
}
