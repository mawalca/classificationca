package pl.edu.ug.caclassification.simulation;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import pl.edu.ug.caclassification.rule.Rule;
import pl.edu.ug.caclassification.simulation.finalcondition.FinalCondition;
import pl.edu.ug.caclassification.util.Utils;
import pl.edu.ug.caclassification.util.ValuesOfColorsCCA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Simulation {

    private float[][] image;
    private float[][] startImg;
    private List<SimRule> simRules;

    private int cols;
    private int rows;

    private BlockingQueue<List<SimResult>> resultBlockingQueue;

    private Random rand = new Random();

    public Simulation(float[][] img, float[][] startImg, List<SimRule> rules, BlockingQueue<List<SimResult>> resultBlockingQueue) {
        this.image = img;
        this.startImg = startImg;
        this.simRules = rules;

        // all images are the same size
        this.cols = img[0].length;
        this.rows = img.length;

        this.resultBlockingQueue = resultBlockingQueue;
    }

    public void run() {

        List<SimResult> simResults = new ArrayList<>();

        simRules.stream().forEach(simRule -> {
        	
        	List<float[][]> finalImages = new ArrayList<>();
            int[] diffs = new int[simRule.getTries()];
            float[][] avgImage = startImg;

            // Gather some (2 - hardcoded) mid iteration samples
            List<float[][]> midIterSamples = new ArrayList<>();

            for (int t = 0; t < simRule.getTries(); t++) {

                List<float[][]> iterations = new ArrayList<>();
                iterations.add(image); // Full image

                iterations.add(startImg);

                //TODO: optimization for "Deterministic" rules
                
                for (int iter = 2; simRule.getCondition().isFinished(iterations, iter); iter++) {
                	
                    float[][] iterResult = new float[rows][cols];  //uninitialized

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            iterResult[i][j] = simRule.getRule().step(iterations.get(iter - 1), i, j);
                        }
                    }
                    
                    iterations.add(iterResult);

                    if (t == 0 && (iter == 5 || iter == 7)) {
                        midIterSamples.add(iterResult);
                    }

                }

                float[][] finalImage = iterations.get(iterations.size() - 1);
                finalImages.add(finalImage);

                int diff = Utils.imgDiff(image, finalImage);
                diffs[t] = diff;

                //System.out.println(Utils.floatMatrixToString(finalImage));
            }

            DescriptiveStatistics stats = new DescriptiveStatistics();

            // Add the data from the array
            for (int i = 0; i < diffs.length; i++) {
                stats.addValue(diffs[i]);
            }

            // Compute some statistics
            double mean = stats.getMean();
            double std = stats.getStandardDeviation();
            int max = new Double(stats.getMax()).intValue();

            // Apply Stats Method
            avgImage = Utils.avgImg(finalImages);
            int avgMethodDiff = Utils.imgDiff(image, avgImage);

            // Gather some (3 - hardcoded) samples
            List<float[][]> samples = new ArrayList<>();

            if (3 < simRule.getTries()) {
                rand.ints(3, 0, simRule.getTries()).forEach(i -> samples.add(finalImages.get(i)));
            }

            //SimResult simResult = new SimResult(rule, img, hiddenImg, samples, avgImage, mean, std, max, avgMethodDiff);
            SimResult simResult = new SimResult(simRule.getRule(), image, startImg, samples, midIterSamples, avgImage, mean, std, max, avgMethodDiff);
            simResults.add(simResult);
            System.out.println(simResult);
        });

        try {
            resultBlockingQueue.put(simResults);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
