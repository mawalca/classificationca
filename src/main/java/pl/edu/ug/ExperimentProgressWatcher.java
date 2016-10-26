package pl.edu.ug;

import pl.edu.ug.simulation.SimResult;

import java.util.List;


public class ExperimentProgressWatcher extends Thread {

    private List<List<SimResult>> simResults;
    private int expected;

    public ExperimentProgressWatcher(List<List<SimResult>> simResults, int expected) {
        this.simResults = simResults;
        this.expected = expected;
    }

    @Override
    public void run() {

        boolean pending = true;

        while (pending) {

            int actual = simResults.size();
            if (actual < expected) {
            } else {
                pending = false;
            }
            System.out.println("\n************** Completed: " + new Double(100.0 * actual / expected).intValue() + "%\n");

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Experiment.printOut(simResults);
    }
}
