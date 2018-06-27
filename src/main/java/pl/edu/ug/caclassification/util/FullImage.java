package pl.edu.ug.caclassification.util;

public class FullImage {

	private String name;
	
	private float[][] image;
	
	public FullImage(String name, float[][] image) {
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public float[][] getImage() {
		return image;
	}
}
