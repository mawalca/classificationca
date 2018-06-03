package pl.edu.ug.caclassification.rule.ruleCCA;

import java.util.*;

import pl.edu.ug.caclassification.util.BaseColors;

public class WeightedMeanRule extends RuleCCA {

	int weightOne;
	int weightTwo;
	int weightThree;
		
	public WeightedMeanRule(String name, int weightOne, int weightTwo, int weightThree) {
		this.name = name;
		this.weightOne = weightOne;
		this.weightTwo = weightTwo;
		this.weightThree = weightThree;
	}
	
	public WeightedMeanRule(String name, int weightOne, int weightTwo, int weightThree, boolean ifDiscret) {
		this(name, weightOne, weightTwo, weightThree);
		this.ifDiscret = ifDiscret;
	}
	
	@Override
	public float step(float[][] img, int row, int col) {
		if (img[row][col] == BaseColors.WHITE || img[row][col] == BaseColors.BLACK)
			return img[row][col];
		
		List<Float> neighOne = getNeighOne(img, row, col);
		List<Float> neighTwo = getNeighTwo(img, row, col);
		List<Float> neighThree = getNeighThree(img, row, col);
		
		float sum = weightOne * sumList(neighOne) + weightTwo * sumList(neighTwo) + weightThree * sumList(neighThree);
		float result = sum / countDivisor(neighOne, neighTwo, neighThree);
		
		return result;
	}

	private List<Float> getNeighOne(float[][] img, int row, int col) {
		List<Float> neigh = new ArrayList<>();
		neigh.add(img[row][col]);
		return neigh;
	}

	private List<Float> getNeighTwo(float[][] img, int row, int col) {
		List<Float> neigh = new ArrayList<>();
		if (row != 0)
			neigh.add(img[row - 1][col]);
		if (col != img[0].length - 1)
			neigh.add(img[row][col + 1]);
		if (row != img.length - 1)
			neigh.add(img[row + 1][col]);
		if (col != 0)
			neigh.add(img[row][col - 1]);
		return neigh;
	}

	private List<Float> getNeighThree(float[][] img, int row, int col) {
		List<Float> neigh = new ArrayList<>();
		if (row != 0) {
			if (col != 0)
				neigh.add(img[row - 1][col - 1]);
		
			if (col != img[0].length - 1)
				neigh.add(img[row - 1][col + 1]);
		}
		if (row != img.length - 1) { 
			if (col != img[0].length - 1)
				neigh.add(img[row + 1][col + 1]);
		
			if (col != 0)
				neigh.add(img[row + 1][col - 1]);
		}
		return neigh;
	}

	private float sumList(List<Float> list) {
		float sum = 0;
		for(Float element : list) {
			sum += element;
		}
		return sum;
	}
	
	private float countDivisor(List<Float> neighOne, List<Float> neighTwo, List<Float> neighThree) {
		return weightOne * neighOne.size() + weightTwo * neighTwo.size() + weightThree * neighThree.size();
	}
}
