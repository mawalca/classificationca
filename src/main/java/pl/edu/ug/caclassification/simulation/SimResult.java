package pl.edu.ug.caclassification.simulation;

import pl.edu.ug.caclassification.rule.Rule;

import java.util.List;

public class SimResult {

    private Rule rule;
    private float[][] img;
    private float[][] hiddenImg;
    private List<float[][]> samples;
    private List<float[][]> midIterSamples;
    private float[][] avgImage;

    private int statMethodDiff;
    private double mean;
    private double std;
    private int max;

    public SimResult(Rule rule, float[][] img, float[][] hiddenImg, List<float[][]> samples, List<float[][]> midIterSamples, float[][] avgImage, double mean, double std, int max, int statMethodDiff) {
        this.rule = rule;
        this.img = img;
        this.hiddenImg = hiddenImg;
        this.samples = samples;
        this.avgImage = avgImage;
        this.mean = mean;
        this.std = std;
        this.statMethodDiff = statMethodDiff;
        this.max = max;
        this.midIterSamples = midIterSamples;
    }

    public List<float[][]> getMidIterSamples() {
        return midIterSamples;
    }

    public int getMax() {
        return max;
    }

    public float[][] getImg() {
        return img;
    }

    public float[][] getHiddenImg() {
        return hiddenImg;
    }

    public List<float[][]> getSamples() {
        return samples;
    }

    public float[][] getAvgImage() {
        return avgImage;
    }

    public double getMean() {
        return mean;
    }

    public double getStd() {
        return std;
    }

    public int getStatMethodDiff() {
        return statMethodDiff;
    }

    public Rule getRule() {
        return rule;
    }

    public String getStatsString(){
        StringBuilder sb = new StringBuilder();

        sb.append(" Mean: ");
        sb.append(String.format("%.2f", mean));

        sb.append(" Std: ");
        sb.append(String.format("%.2f", std));

        sb.append(" StatMethodDiff: ");
        sb.append(statMethodDiff);

        sb.append(" MaxDiff: ");
        sb.append(max);

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n" + rule);
        sb.append(getStatsString());
        return sb.toString();
    }
}
