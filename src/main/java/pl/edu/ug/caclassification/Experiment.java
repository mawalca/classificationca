package pl.edu.ug.caclassification;

import pl.edu.ug.caclassification.simulation.*;
import pl.edu.ug.caclassification.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Experiment {

    private int simulations;
    private List<SimRule> rules;
    private float[][] fullImg;
    private float[][] startImg;
    private int percentToShow;

    private int threadPoolSize;
    
    private BlockingQueue<Simulation> simulationBlockingQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<List<SimResult>> resultBlockingQueue = new LinkedBlockingQueue<>();

    ExecutorService watcherExecutor = Executors.newSingleThreadExecutor();

    public Experiment()
    {
    	simulations = 1;
    	rules = new ArrayList<>();
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

	public void setFullImg(float[][] fullImg) {
		this.fullImg = fullImg;
	}

	public void setStartImg(float[][] startImg) {
		this.startImg = startImg;
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
		return fullImg != null && !rules.isEmpty() 
				&& (startImg != null || percentToShow != 0);
	}
	
	
	public void start() {

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < threadPoolSize; i++) {
            executor.submit(new SimulationTask(simulationBlockingQueue));
        }

        // One experiment - many simulations
        for (int i = 0; i < simulations; i++) {

            // One simulation - one random image for all triesInSimulation
            Simulation simulation;
            if (startImg == null) {
            	int cellsToShow = new Double(percentToShow / 100.0 * fullImg[0].length * fullImg.length).intValue();
            	float[][] img = Utils.hide(fullImg, cellsToShow);
                simulation = new Simulation(fullImg, img, rules, resultBlockingQueue);
            } else {
                simulation = new Simulation(fullImg, startImg, rules, resultBlockingQueue);
            }
            
            try {
                simulationBlockingQueue.put(simulation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        watcherExecutor.submit(new PrinterAndWatcherTask(resultBlockingQueue, simulations, executor));
    }
}
