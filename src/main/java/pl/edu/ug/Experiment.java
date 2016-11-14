package pl.edu.ug;

import pl.edu.ug.rule.Rule;
import pl.edu.ug.simulation.SimResult;
import pl.edu.ug.simulation.Simulation;
import pl.edu.ug.simulation.SimulationTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Experiment {

    private int simulations;

    private List<Rule> rules;
    private byte[][] fullImg;
    private int triesInSimulation;
    private int percentToShow;

    private int threadPoolSize;
    //private List<List<SimResult>> experimentResults = Collections.synchronizedList(new ArrayList<>());
    private BlockingQueue<Simulation> simulationBlockingQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<List<SimResult>> resultBlockingQueue = new LinkedBlockingQueue<>();

    public Experiment(int simulations, List<Rule> rules, byte[][] fullImg, int triesInSimulation, int percentToShow, int threadPoolSize) {
        this.simulations = simulations;
        this.rules = rules;
        this.fullImg = fullImg;
        this.triesInSimulation = triesInSimulation;
        this.percentToShow = percentToShow;
        this.threadPoolSize = threadPoolSize;
    }

    public void start() {

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < simulations; i++) {
            executor.submit(new SimulationTask(simulationBlockingQueue));
        }

        // One experiment - many random images, one per simulation
        for (int i = 0; i < simulations; i++) {
            // One simulation - one random image for all triesInSimulation
            Simulation simulation = new Simulation(fullImg, rules, percentToShow, triesInSimulation, resultBlockingQueue);
            try {
                simulationBlockingQueue.put(simulation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ExecutorService watcherExecutor = Executors.newSingleThreadExecutor();
        watcherExecutor.submit(new PrinterAndWatcherTask(resultBlockingQueue, simulations, executor));
    }


}
