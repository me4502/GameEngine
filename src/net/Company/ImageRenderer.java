package net.Company;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class ImageRenderer {

	private HashMap<String, DynamicImage> images = new HashMap<String, DynamicImage>();

	Engine engine;

	public static int baseImageScale = 16;

	public ImageRenderer(Engine engine) {
		this.engine = engine;
	}

	public void addImage(String name, DynamicImage image) {
		System.out.println("Registering image: " + name + " with ID " + images.size());
		images.put(name, image);
	}

	public DynamicImage getImage(String name) {

		return images.get(name);
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
			images.put(name, new DynamicImage(new File(engine.utilities.getAppDir(), path).getAbsolutePath()));
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
				images.put(name, new DynamicImage(f.getPath()));
			}
			catch(Exception e) {
				images.put(name, new DynamicImage(path));
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
		return draw(name,x,y,1f,1f,rotation);
	}

	public int getGlTexture(String name) {
		return images.get(name).getImage().getTexture().getTextureID();
	}

	public void bindGlTexture(String name) {
		images.get(name).getImage().getTexture().bind();
	}

	public boolean draw(String name, int x, int y, float scaleX, float scaleY, int rotation) {
		return draw(name,x,y,scaleX,scaleY,rotation,1f);
	}

	public boolean draw(String name, int x, int y, int rotation, float alpha) {
		return draw(name,x,y,1f,1f,rotation,alpha);
	}

	public boolean draw(String name, int x, int y, float scaleX, float scaleY, int rotation, float alpha) {
		try {
			Rendering.setTextureState(true);
			images.get(name).getImage().setAlpha(alpha);
			if(rotation != 0)
				images.get(name).getImage().setRotation(rotation);
			int res = baseImageScale;
			if(baseImageScale != images.get(name).getResolution())
				res = images.get(name).getResolution() / (images.get(name).getResolution() / baseImageScale);
			images.get(name).getImage().setCenterOfRotation(images.get(name).getResolution()/2,images.get(name).getResolution()/2);
			images.get(name).getImage().draw(x, y, scaleX * res, scaleY * res);
			images.get(name).getImage().setRotation(0);
			Rendering.setTextureState(false);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns a SpriteSheet with a dynamic resolution. (All items need to be the same resolution).
	 * 
	 * @param path
	 * @param width The amount of images across
	 * @param height The amount of images vertically
	 * @return
	 */
	public SpriteSheet getContainedSpriteSheet(String path, int width, int height) {

		try {
			try {
				URL url = this.getClass().getResource(path);
				File f = new File(url.getFile());
				System.err.println(f.getPath());
				Image i = new Image(f.getPath(), Image.FILTER_NEAREST);
				return new SpriteSheet(i, i.getWidth() / width, i.getHeight() / height);
			}
			catch(Exception e) {
				Image i = new Image(path, Image.FILTER_NEAREST);
				return new SpriteSheet(i, i.getWidth() / width, i.getHeight() / height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}