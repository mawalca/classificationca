package pl.edu.ug;

import pl.edu.ug.rule.Rule;
import pl.edu.ug.simulation.SimResult;
import pl.edu.ug.simulation.Simulation;
import pl.edu.ug.simulation.SimulationWorker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Experiment {

    private int simulations;

    private List<Rule> rules;
    private byte[][] fullImg;
    private int triesInSimulation;
    private int percentToShow;

    private int threadPoolSize;
    private List<List<SimResult>> experimentResults = Collections.synchronizedList(new ArrayList<>());
    private BlockingQueue<Simulation> blockingQueue = new LinkedBlockingQueue<>();

    public Experiment(int simulations, List<Rule> rules, byte[][] fullImg, int triesInSimulation, int percentToShow, int threadPoolSize) {
        this.simulations = simulations;
        this.rules = rules;
        this.fullImg = fullImg;
        this.triesInSimulation = triesInSimulation;
        this.percentToShow = percentToShow;
        this.threadPoolSize = threadPoolSize;
    }

    public void start() {
        try {
            // One experiment - many random images, one for simulation
            for (int i = 0; i < simulations; i++) {
                // One simulation - one random image for all triesInSimulation
                Simulation simulation = new Simulation(fullImg, rules, percentToShow, triesInSimulation, experimentResults);
                blockingQueue.put(simulation);
            }

            for (int i = 0; i < threadPoolSize; i++) {
                new SimulationWorker(blockingQueue).start();
            }

            new ExperimentProgressWatcher(experimentResults, simulations).start();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printOut(List<List<SimResult>> experimentResults) {

        String filename = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss'.csv'").format(new Date());
        Path path = Paths.get("./results/" + filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)) {

            StringBuilder sb = new StringBuilder();
            sb.append("#");

            synchronized (experimentResults) {

                // all results have the same data, prepare header
                experimentResults.get(0).forEach(simResult -> {
                    sb.append(",Mean").append(simResult.getRule());
                    sb.append(",Stat").append(simResult.getRule());
                    sb.append(",Max").append(simResult.getRule());
                });

                writer.write(sb.toString());
                writer.newLine();

                for (int i = 0; i < experimentResults.size(); i++) {
                    StringBuilder rSb = new StringBuilder();
                    rSb.append(i + 1);

                    experimentResults.get(i).stream().forEach(simResult -> {
                        rSb.append(",").append(simResult.getMean());
                        rSb.append(",").append(simResult.getStatMethodDiff());
                        rSb.append(",").append(simResult.getMax());
                    });
                    writer.write(rSb.toString());
                    writer.newLine();
                }
            }
            writer.close();

        } catch (IOException ioe) {
            System.err.format("IOException: %s%n", ioe);
        }
    }


}
