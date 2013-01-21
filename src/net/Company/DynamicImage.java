package net.Company;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class DynamicImage {

	private int resolution;
	Image image;

	public DynamicImage(Image image) {
		this.image = image;
		resolution = image.getWidth();
	}

	public DynamicImage(String absolutePath) throws SlickException {
		image = new Image(absolutePath);
		resolution = image.getWidth();
	}

	public void setResolution(int res) {

		resolution = res;
	}

	public int getResolution() {

		return resolution;
	}

	public Image getImage() {

		return image;
	}
}
