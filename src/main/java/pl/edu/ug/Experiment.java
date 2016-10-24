package pl.edu.ug;

import pl.edu.ug.rule.Rule;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Experiment {

    private int simulations;

    private List<Rule> rules;
    private byte[][] fullImg;
    private int triesInSimulation;
    private int percentToShow;

    private List<List<SimResult>> experimentResults = new ArrayList<>();

    public Experiment(int simulations, List<Rule> rules, byte[][] fullImg, int triesInSimulation, int percentToShow) {
        this.simulations = simulations;
        this.rules = rules;
        this.fullImg = fullImg;
        this.triesInSimulation = triesInSimulation;
        this.percentToShow = percentToShow;
    }

    public List<List<SimResult>> getExperimentResults() {
        return experimentResults;
    }

    public void start() {

        // One experiment - many random images, one for simulation
        for (int i = 0; i < simulations; i++) {

            List<SimResult> simResults = new ArrayList<>();

            // One simulation - one random image for all triesInSimulation
            Simulation simulation = new Simulation(fullImg, rules, percentToShow, triesInSimulation, simResults);
            simulation.run();
            experimentResults.add(simResults);

            System.out.println("\n************** Completed: " + new Double(100.0 * (i + 1) / simulations).intValue() + "%\n");
        }
    }

    public void printOut() {

        String filename = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss'.csv'").format(new Date());
        Path path = Paths.get("./results/" + filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE_NEW)) {

            StringBuilder sb = new StringBuilder();
            sb.append("#");

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
            writer.close();

        } catch (IOException ioe) {
            System.err.format("IOException: %s%n", ioe);
        }
    }
}
