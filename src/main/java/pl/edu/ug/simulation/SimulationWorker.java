package pl.edu.ug.simulation;

import java.util.concurrent.BlockingQueue;

public class SimulationWorker extends Thread {

    private final BlockingQueue<Simulation> queue;

    public SimulationWorker(BlockingQueue<Simulation> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Simulation simulation = queue.take();
                simulation.run();
            }
        } catch (InterruptedException e) {

        }
    }
}
