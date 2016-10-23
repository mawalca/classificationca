package pl.edu.ug;

import pl.edu.ug.rule.Rule;

import java.util.ArrayList;
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

    public void start(){

        // One experiment - many random images, one for simulation
        for (int i = 0; i < simulations; i++) {

            List<SimResult> simResults = new ArrayList<>();

            // One simulation - one random image for all triesInSimulation
            Simulation simulation = new Simulation(fullImg, rules, percentToShow, triesInSimulation, simResults);
            simulation.run();
            experimentResults.add(simResults);

            System.out.println("Completed: " + new Double(100.0*(i + 1)/simulations).intValue() + "%");
        }
    }

    public void analyze(){
        experimentResults.stream().forEach(listSimResults -> {



        });
    }
}
