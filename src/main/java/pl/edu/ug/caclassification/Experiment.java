package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.simulation.*;
import pl.edu.ug.caclassification.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Experiment {

    private int simulations;
    private List<SimRule> rules;
    private List<FullImage> fullImages;
    private int percentToShow;

    private int threadPoolSize;
    
    private BlockingQueue<Simulation> simulationBlockingQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<List<SimResult>> resultBlockingQueue = new LinkedBlockingQueue<>();

    ExecutorService watcherExecutor = Executors.newSingleThreadExecutor();

    public Experiment()
    {
    	simulations = 1;
    	rules = new ArrayList<>();
    	fullImages = new ArrayList<>();
    	threadPoolSize = 1;
    }
    
    public static ExperimentBuilder newBuilder()
    {
    	return new ExperimentBuilder();
    }
    
    public void setSimulations(int simulations) {
		this.simulations = simulations;
	}

	public void addRule(SimRule rule) {
		this.rules.add(rule);
	}

	public void addFullImage(FullImage fullImage) {
		this.fullImages.add(fullImage);
	}

	public void setPercentToShow(int percentToShow) {
		this.percentToShow = percentToShow;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}


    public ExecutorService getWatcherExecutor() {
        return watcherExecutor;
    }
	
	public boolean isCorrect()
	{
		return !fullImages.isEmpty() && !rules.isEmpty() 
				&& percentToShow != 0;
	}
	
	
	public void start() {

        ExecutorService executor = getExecutorWithSimulationTasks();

        // One experiment - many simulations
        for (int i = 0; i < simulations; i++) {

            // One simulation - one random image
            List<Simulation> simulations = getSimulations();
            try {
            	for(Simulation simulation : simulations) {
            		simulationBlockingQueue.put(simulation);
            	}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        watcherExecutor.submit(new PrinterAndWatcherTask(resultBlockingQueue, simulations * fullImages.size(), executor));
    }

	private ExecutorService getExecutorWithSimulationTasks() {
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		for (int i = 0; i < threadPoolSize; i++) {
			executor.submit(new SimulationTask(simulationBlockingQueue));
		}
		return executor;
	}
	
	private List<Simulation> getSimulations() {
		List<Simulation> result = new ArrayList<>();
		
		for(FullImage fullImage : fullImages) {
			
			float[][] startImage = getStartImage(fullImage.getImage());
			Simulation simulation = new Simulation(fullImage, startImage, rules, resultBlockingQueue);
			result.add(simulation);
		}
		return result;
	}

	private float[][] getStartImage(float[][] fullImage) {		
		int cellsToShow = new Double(percentToShow / 100.0 * fullImage[0].length * fullImage.length).intValue();
        return Utils.hide(fullImage, cellsToShow);
	}
}
