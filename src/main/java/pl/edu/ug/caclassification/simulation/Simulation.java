package pl.edu.ug.caclassification.simulation;

import pl.edu.ug.caclassification.util.FullImage;
import pl.edu.ug.caclassification.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Simulation {

	private FullImage image;
	private float[][] startImg;
	private List<SimRule> simRules;

	private int cols;
	private int rows;

	private BlockingQueue<List<SimResult>> resultBlockingQueue;

	private Random rand = new Random();
	private int amountOfSamples = 4;
	private int amountOfTrySamples = 3;
	
	private int lastIter;

	public Simulation(FullImage image, float[][] startImg, List<SimRule> rules,
			BlockingQueue<List<SimResult>> resultBlockingQueue) {
		this.image = image;
		this.startImg = startImg;
		this.simRules = rules;

		// all images are the same size
		this.cols = this.image.getImage()[0].length;
		this.rows = this.image.getImage().length;

		this.resultBlockingQueue = resultBlockingQueue;
	}

	public void run() {

		List<SimResult> simResults = new ArrayList<>();

		simRules.stream().forEach(simRule -> {

			SimResult simResult;
			
			if (simRule.getTries() == 0) {				
				simResult = getSimResultOneTry(simRule);
			} else {
				simResult = getSimResultManyTries(simRule);
				System.out.println(simResult);
			}
			simResults.add(simResult);
		});

		try {
			resultBlockingQueue.put(simResults);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private SimResult getSimResultManyTries(SimRule simRule) {
		
		List<float[][]> finalImages = new ArrayList<>();
		int[] diffs = new int[simRule.getTries()];
		float[][] avgImage = startImg;

		// Gather some mid iteration samples from first try
		List<float[][]> midIterImgs = new ArrayList<>();
		
		for (int nrTry = 0; nrTry < simRule.getTries(); nrTry++) {
			float[][] finalImage;
			
			if (nrTry == 0) {
				midIterImgs = runIterationsAndGetMidIters(simRule);
				finalImage = midIterImgs.get(midIterImgs.size() - 1);
				midIterImgs.remove(midIterImgs.size() - 1);
			} else {
				finalImage = runIterationsAndGetFinalImage(simRule);
			}
			finalImages.add(finalImage);

			int diff = Utils.imgDiff(image.getImage(), finalImage);
			diffs[nrTry] = diff;
		}

		avgImage = Utils.avgImg(finalImages);
		
		// Gather some samples
		List<float[][]> samples = new ArrayList<>();

		if (amountOfTrySamples < simRule.getTries()) {
			rand.ints(amountOfTrySamples, 0, simRule.getTries())
				.forEach(i -> samples.add(finalImages.get(i)));
		}
		
		SimResultBuilder simResultBuilder = SimResult.newBuilder()
				.setRule(simRule)
				.setFullImage(image)
				.setStartImage(startImg)
				.setFinalImage(avgImage)
				.setNrIters(lastIter)
				.setMidIterImages(midIterImgs)
				.setSamples(samples);
		
		if (simRule.ifDiscret()) {
			simResultBuilder.setDiscretImage(Utils.getDiscretization(avgImage));
		}
		
		return simResultBuilder.build();
	}

	private SimResult getSimResultOneTry(SimRule simRule) {
		List<float[][]> midIterImgs = runIterationsAndGetMidIters(simRule);
		
		int size = midIterImgs.size();
		float[][] finalImg = midIterImgs.get(size - 1);
		midIterImgs.remove(size - 1);
		
		SimResultBuilder simResultBuilder =
				SimResult.newBuilder()
				.setRule(simRule)
				.setFullImage(image)
				.setStartImage(startImg)
				.setMidIterImages(midIterImgs)
				.setFinalImage(finalImg)
				.setNrIters(lastIter);
		
		if(simRule.ifDiscret()) {
			simResultBuilder.setDiscretImage(Utils.getDiscretization(finalImg));
		}
		
		return simResultBuilder.build();
	}

	private float[][] runIterationsAndGetFinalImage(SimRule simRule) {
		List<float[][]> iterations = getIterations(simRule);
		return iterations.get(iterations.size() - 1);
	}

	private List<float[][]> runIterationsAndGetMidIters(SimRule simRule) {
		List<float[][]> iterations = getIterations(simRule);
		return getSamples(iterations);
	}

	private List<float[][]> getIterations(SimRule simRule) {
		List<float[][]> iterations = new ArrayList<>();

		iterations.add(image.getImage()); // Full image
		iterations.add(startImg);

		for (int iter = 2; !simRule.isFinished(iterations, iter); iter++) {

			float[][] iterResult = new float[rows][cols]; // uninitialized
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					iterResult[i][j] = simRule.ruleStep(iterations.get(iter - 1), i, j);
				}
			}
			iterations.add(iterResult);
			lastIter = iter;
		}
		return iterations;
	}

	private List<float[][]> getSamples(List<float[][]> iterations) {
		List<float[][]> samples = new ArrayList<>();

		List<Integer> samplesIndex = getSampleIndexes(iterations.size());

		for (int i = 0; i < samplesIndex.size(); i++) {
			samples.add(iterations.get(samplesIndex.get(i)));
		}

		return samples;
	}

	private List<Integer> getSampleIndexes(int size) {
		List<Integer> samplesIndex = new ArrayList<>();
		
		if (size < amountOfSamples) {
			for (int i = 0; i < size; i++) {
				samplesIndex.add(i);
			}
			return samplesIndex;
		}
		
		int step = (int)Math.ceil((double)size / amountOfSamples);
		for (int i = 1; i < amountOfSamples; i++) {
			samplesIndex.add(i * step - 1);
		}
		samplesIndex.add(size - 1);

		return samplesIndex;
	}
}
