package pl.edu.ug.caclassification.simulation.finalcondition;

import java.util.List;

public class NrIterationsCondition extends FinalCondition {

	private int nrIterations;
	
	public NrIterationsCondition(int nrIterations)
	{
		this.nrIterations = nrIterations;
	}
	
	@Override
	public boolean isFinished(List<float[][]> iterations, int iter) {
		return iter >= nrIterations;
	}

	@Override
	public String toString(){
		return nrIterations + "iters";
	}
}
