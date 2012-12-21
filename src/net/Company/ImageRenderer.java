package net.Company;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class ImageRenderer {

	private HashMap<String, Image> images = new HashMap<String, Image>();

	Engine engine;

	public ImageRenderer(Engine engine) {
		this.engine = engine;
	}

	public void addImage(String name, Image image) {
		images.put(name, image);
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
			images.put(name, new Image(new File(engine.utilities.getAppDir(), path).getAbsolutePath()));
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
		return draw(name,x,y,1f,1f,rotation);
	}

	public int getGlTexture(String name) {
		return images.get(name).getTexture().getTextureID();
	}

	public void bindGlTexture(String name) {
		images.get(name).getTexture().bind();
	}

	public boolean draw(String name, int x, int y, float scaleX, float scaleY, int rotation) {
		return draw(name,x,y,scaleX,scaleY,rotation,1f);
	}

	public boolean draw(String name, int x, int y, int rotation, float alpha) {
		return draw(name,x,y,1f,1f,rotation,alpha);
	}

	public boolean draw(String name, int x, int y, float scaleX, float scaleY, int rotation, float alpha) {
		try {
			images.get(name).setCenterOfRotation(8,8);
			images.get(name).setAlpha(alpha);
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

	public SpriteSheet getContainedSpriteSheet(String path, int x, int y) {

		try {
			try {
				URL url = this.getClass().getResource(path);
				File f = new File(url.getFile());
				System.err.println(f.getPath());
				return new SpriteSheet(new Image(f.getPath()), x, y);
			}
			catch(Exception e) {
				return new SpriteSheet(new Image(path), x, y);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}