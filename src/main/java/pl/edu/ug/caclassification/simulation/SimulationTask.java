package pl.edu.ug.caclassification.simulation;

import java.util.concurrent.BlockingQueue;

public class SimulationTask implements Runnable {

    private final BlockingQueue<Simulation> queue;

    public SimulationTask(BlockingQueue<Simulation> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Simulation Task started");
        try {
            while (true) {
                Simulation simulation = queue.take();
                simulation.run();

            }
        } catch (InterruptedException e) {
            System.out.println("Simulation Task interrupted");
        }
    }
}
