package pl.edu.ug.caclassification.simulation.finalcondition;

import java.util.List;

import pl.edu.ug.caclassification.util.Utils;

public class AllShownOrNoDiffCondition extends FinalCondition {

	@Override
	public boolean isFinished(List<float[][]> iterations, int iter) {
		
		if (iter == 10000)
		{
			return true;
		}
		
		boolean isShown = Utils.isFullyShown(iterations.get(iter - 1));
		if (isShown)
		{
			return true;
		}
		
		int diffs = Utils.imgDiff(iterations.get(iter - 1), iterations.get(iter - 2));
		return diffs == 0;
	}

	@Override
	public String toString(){
		return "allShownOrNoDiff";
	}
}
