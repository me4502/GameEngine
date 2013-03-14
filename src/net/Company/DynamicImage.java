package net.Company;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class DynamicImage {

	private int resolution;
	Image image;

	public DynamicImage(Image image) {
		this.image = image;
		if(image.getWidth() <= image.getHeight())
			resolution = image.getWidth();
		else
			resolution = image.getHeight();
	}

	public DynamicImage(String absolutePath) throws SlickException {
		image = new Image(absolutePath, Image.FILTER_NEAREST);
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
