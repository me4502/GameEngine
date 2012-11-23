package net.Company;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import org.newdawn.slick.Image;

public class ImageRenderer {

	private HashMap<String, Image> images = new HashMap<String, Image>();

	public ImageRenderer() {
	}

	/**
	 * Registers an image to render.
	 * 
	 * @param name
	 * @param path
	 * @return if successful
	 */
	public boolean registerImage(String name, String path) {
		try {
			System.out.println("Registering image: " + name + " with ID " + images.size());
			images.put(name, new Image(new File(EngineUtils.getAppDir(), path).getAbsolutePath()));
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Registers an image to render.
	 * 
	 * @param name
	 * @param path
	 * @return if successful
	 */
	public boolean registerContainedImage(String name, String path) {
		try {
			System.out.println("Registering image: " + name + " at " + path + " with ID " + images.size());
			try {
				URL url = this.getClass().getResource(path);
				File f = new File(url.getFile());
				System.err.println(f.getPath());
				images.put(name, new Image(f.getPath()));
			}
			catch(Exception e) {
				images.put(name, new Image(path));
			}
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param scale
	 * @return if image existed.
	 */
	public boolean draw(String name, int x, int y, int rotation) {
		try {
			images.get(name).setCenterOfRotation(8,8);
			if(rotation != 0)
				images.get(name).setRotation(rotation);
			images.get(name).draw(x,y);
			images.get(name).setRotation(0);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getGlTexture(String name) {
		return images.get(name).getTexture().getTextureID();
	}

	public void bindGlTexture(String name) {
		images.get(name).getTexture().bind();
	}

	public boolean draw(String name, int x, int y, float scaleX, float scaleY, int rotation) {
		try {
			images.get(name).setCenterOfRotation(8,8);
			if(rotation != 0)
				images.get(name).setRotation(rotation);
			images.get(name).draw(x, y, scaleX * 16.1f, scaleY * 16.1f);
			images.get(name).setRotation(0);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}