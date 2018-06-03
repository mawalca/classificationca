package pl.edu.ug.caclassification.simulation;

import java.util.List;

import pl.edu.ug.caclassification.util.FullImage;

public class SimResult {

	protected SimRule rule;
	protected FullImage origImg;
	protected float[][] startImg;
	protected List<float[][]> midIterImgs;
	protected float[][] finalImg;
	private List<float[][]> samples;
	private float[][] discretImg;
	private int[] diffs;
	private int nrIters;
	
	public SimRule getRule() {
		return rule;
	}
	
	public void setRule(SimRule rule) {
		this.rule = rule;
	}

	public float[][] getStartImg() {
		return startImg;
	}
	
	public void setStartImg(float[][] startImg) {
		this.startImg = startImg;
	}

	public List<float[][]> getMidIterImgs() {
		return midIterImgs;
	}


	public void setMidIterImgs(List<float[][]> midIterImgs) {
		this.midIterImgs = midIterImgs;
	}
	
	public FullImage getOrigImg() {
		return origImg;
	}

	public void setOrigImg(FullImage origImg) {
		this.origImg = origImg;
	}

	public float[][] getFinalImg() {
		return finalImg;
	}
	
	public void setFinalImg(float[][] finalImg) {
		this.finalImg = finalImg;
	}
	
	public List<float[][]> getSamples() {
		return samples;
	}

	public void setSamples(List<float[][]> samples) {
		this.samples = samples;
	}

	public float[][] getDiscretImg() {
		return discretImg;
	}

	public void setDiscretImg(float[][] discretImg) {
		this.discretImg = discretImg;
	}

	public int[] getDiffs() {
		return diffs;
	}

	public void setDiffs(int[] diffs) {
		this.diffs = diffs;
	}

	public int getNrIters() {
		return nrIters;
	}

	public void setNrIters(int nrIters) {
		this.nrIters = nrIters;
	}

	public float[][] getOriginalImage() {
		return origImg.getImage();
	}
	
	public String getImageName() {
		return origImg.getName();
	}
	
	public String getRuleName() {
		return rule.getRule().toString();
	}
	
	public static SimResultBuilder newBuilder() {
		return new SimResultBuilder();
	}
}
