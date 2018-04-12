package pl.edu.ug.caclassification.simulation.finalcondition;

import java.util.List;

import pl.edu.ug.caclassification.util.Utils;
import pl.edu.ug.caclassification.util.ValuesOfColorsBCA;

public class AllShownCondition extends FinalCondition {

	@Override
	public boolean isFinished(List<float[][]> iterations, int iter) {
		return !Utils.isFullyShown(iterations.get(iter - 1), new ValuesOfColorsBCA());
	}

}
