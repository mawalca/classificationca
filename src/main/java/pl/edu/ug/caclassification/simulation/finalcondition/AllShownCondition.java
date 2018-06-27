package pl.edu.ug.caclassification.simulation.finalcondition;

import java.util.List;

import pl.edu.ug.caclassification.util.Utils;

public class AllShownCondition extends FinalCondition {

	@Override
	public boolean isFinished(List<float[][]> iterations, int iter) {
		return Utils.isFullyShown(iterations.get(iter - 1));
	}

	@Override
	public String toString(){
		return "allShown";
	}
}
