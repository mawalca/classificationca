package pl.edu.ug.caclassification.simulation;

import java.util.List;

import pl.edu.ug.caclassification.util.FullImage;

public class SimResultBuilder {

	private SimResult simResult;
	
	public SimResultBuilder() {
		this.simResult = new SimResult();
	}
	
	public SimResultBuilder setFullImage(FullImage image) {
		this.simResult.setOrigImg(image);
		return this;
	}
	
	public SimResultBuilder setStartImage(float[][] startImg) {
		this.simResult.setStartImg(startImg);
		return this;
	}
	
	public SimResultBuilder setFinalImage(float[][] finalImg) {
		this.simResult.setFinalImg(finalImg);
		return this;
	}
	
	public SimResultBuilder setRule(SimRule rule) {
		this.simResult.setRule(rule);
		return this;
	}
	
	public SimResultBuilder setMidIterImages(List<float[][]> midIterImgs) {
		this.simResult.setMidIterImgs(midIterImgs);
		return this;
	}
	
	public SimResultBuilder setSamples(List<float[][]> samples) {
		this.simResult.setSamples(samples);
		return this;
	}
	
	public SimResultBuilder setDiscretImage(float[][] discretImg) {
		this.simResult.setDiscretImg(discretImg);
		return this;
	}
	
	public SimResultBuilder setDiffs(int[] diffs) {
		this.simResult.setDiffs(diffs);
		return this;
	}
	
	public SimResultBuilder setNrIters(int nrIters) {
		this.simResult.setNrIters(nrIters);
		return this;
	}
	
	public SimResult build() {
		return simResult;
	}
}
