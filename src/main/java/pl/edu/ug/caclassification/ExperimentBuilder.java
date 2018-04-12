package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.simulation.SimRule;

public class ExperimentBuilder {

	private Experiment experiment;
		
	public ExperimentBuilder()
	{
		experiment = new Experiment();
	}
	
	public ExperimentBuilder setSimulations(int simulations)
	{
		experiment.setSimulations(simulations);
		return this;
	}
	
	public ExperimentBuilder setFullImg(float[][] fullImg)
	{
		experiment.setFullImg(fullImg);
		return this;
	}
	
	public ExperimentBuilder setStartImg(float[][] startImg)
	{
		experiment.setStartImg(startImg);
		return this;
	}
	
	public ExperimentBuilder setPercentToShow(int percentToShow)
	{
		experiment.setPercentToShow(percentToShow);
		return this;
	}
	
	public ExperimentBuilder setThreadPoolSize(int threadPoolSize) {
		experiment.setThreadPoolSize(threadPoolSize);
		return this;
	}
	
	public ExperimentBuilder addRule(SimRule rule) {
		experiment.addRule(rule);
		return this;
	}
	
	public Experiment build()
	{
		if (experiment.isCorrect())
		{	
			return experiment;
		}
		throw new IllegalStateException("Experiment does not have all needed data");
	}
}
