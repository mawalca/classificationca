package pl.edu.ug;

import pl.edu.ug.rule.Rule;

import java.util.List;

public class SimResult {

    private Rule rule;
    private byte[][] img;
    private byte[][] hiddenImg;
    private List<byte[][]> samples;
    private byte[][] avgImage;

    private int statMethodDiff;
    private double mean;
    private double std;
    private int max;

    public SimResult(Rule rule, byte[][] img, byte[][] hiddenImg, List<byte[][]> samples, byte[][] avgImage, double mean, double std, int max, int statMethodDiff) {
        this.rule = rule;
        this.img = img;
        this.hiddenImg = hiddenImg;
        this.samples = samples;
        this.avgImage = avgImage;
        this.mean = mean;
        this.std = std;
        this.statMethodDiff = statMethodDiff;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public byte[][] getImg() {
        return img;
    }

    public byte[][] getHiddenImg() {
        return hiddenImg;
    }

    public List<byte[][]> getSamples() {
        return samples;
    }

    public byte[][] getAvgImage() {
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
