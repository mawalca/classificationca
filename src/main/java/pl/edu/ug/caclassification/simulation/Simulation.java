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
    private List<Rule> rules;
    private int showPercent;

    private int cols;
    private int rows;

    private FinalCondition finalCondition;
    private BlockingQueue<List<SimResult>> resultBlockingQueue;
    int tries;

    private Random rand = new Random();

    public Simulation(float[][] img, List<Rule> rules, BlockingQueue<List<SimResult>> resultBlockingQueue)
    {
    	this.image = img;
        this.rules = rules;
        
        this.resultBlockingQueue = resultBlockingQueue;
        
        // all images are the same size
        this.cols = img[0].length;
        this.rows = img.length;
    }
    
    public Simulation(float[][] img, List<Rule> rules, int showPercent, int tries, BlockingQueue<List<SimResult>> resultBlockingQueue) {
        this.image = img;
        this.rules = rules;
        this.showPercent = showPercent;
        this.tries = tries;

        // all images are the same size
        this.cols = img[0].length;
        this.rows = img.length;

        this.resultBlockingQueue = resultBlockingQueue;
    }

    public Simulation(float[][] img, float[][] startImg, List<Rule> rules, int tries, BlockingQueue<List<SimResult>> resultBlockingQueue) {
        this.image = img;
        this.startImg = startImg;
        this.rules = rules;
        this.tries = tries;

        // all images are the same size
        this.cols = img[0].length;
        this.rows = img.length;

        this.resultBlockingQueue = resultBlockingQueue;
    }
    
    public void SetCondition(FinalCondition condition)
    {
    	this.finalCondition = condition;
    }

    public void run() {

        List<SimResult> simResults = new ArrayList<>();

        rules.stream().forEach(rule -> {
	
        	if (rule.getColors().getClass() == ValuesOfColorsCCA.class) {
        		startImg = Utils.imageColorToCCA(image);
        	}
        	else {
        		startImg = image;
        	}
        	
        	float[][] hiddenImg = getHiddenImage(rule);
        	
            List<float[][]> finalImages = new ArrayList<>();
            int[] diffs = new int[tries];
            float[][] avgImage = hiddenImg;

            // Gather some (2 - hardcoded) mid iteration samples
            List<float[][]> midIterSamples = new ArrayList<>();

            for (int t = 0; t < tries; t++) {

                List<float[][]> iterations = new ArrayList<>();
                iterations.add(startImg); // Full img

                iterations.add(hiddenImg);

                //TODO: optimization for "Deterministic" rules
                
                for (int iter = 2; finalCondition.isFinished(iterations, iter); iter++) {
                	
                    float[][] iterResult = new float[rows][cols];  //uninitialized

                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            iterResult[i][j] = rule.step(iterations.get(iter - 1), i, j);
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
            avgImage = Utils.avgImg(finalImages, rule.getColors());
            int avgMethodDiff = Utils.imgDiff(image, avgImage);

            // Gather some (3 - hardcoded) samples
            List<float[][]> samples = new ArrayList<>();

            if (3 < tries) {
                rand.ints(3, 0, tries).forEach(i -> samples.add(finalImages.get(i)));
            }

            //SimResult simResult = new SimResult(rule, img, hiddenImg, samples, avgImage, mean, std, max, avgMethodDiff);
            SimResult simResult = new SimResult(rule, image, hiddenImg, samples, midIterSamples, avgImage, mean, std, max, avgMethodDiff);
            simResults.add(simResult);
            System.out.println(simResult);
        });

        try {
            resultBlockingQueue.put(simResults);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private float[][] getHiddenImage(Rule rule)
    {
        int cellsToShow = new Double(showPercent / 100.0 * cols * rows).intValue();
        return Utils.hide(startImg, rule.getColors(), cellsToShow);
    }
}
