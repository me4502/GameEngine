package net.Company;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.GlyphPage;
import org.newdawn.slick.font.effects.ColorEffect;

public class Rendering {

	static UnicodeFont font = new UnicodeFont(new Font("Verdana", Font.BOLD, 8));

	@SuppressWarnings("unchecked")
	public static void init() {
		try {
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getFontWidth(String text) {
		return font.getWidth(text);
	}

	public static void drawFont(int x, int y, String text, float r, float g,
			float b) {
		Color c = new Color(r, g, b);
		font.drawString(x, y, text, c);
	}

	public static void setAntiAliasing(boolean antiAlias) {
		java.awt.Graphics g = GlyphPage.getScratchGraphics();
		if (g != null && g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON
							: RenderingHints.VALUE_ANTIALIAS_OFF);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON
							: RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}

	public static void drawPixelBlend(int x, int y, float r, float g, float b,
			float a) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPointSize(1f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void drawBlob(int x, int y, float r, float g, float b, float a) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPointSize(4f);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glPopMatrix();
	}

	public static void drawFire(int x, int y, float r, float g, float b, float a) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPointSize(3f);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glPointSize(1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void drawPixel(int x, int y, float r, float g, float b) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPointSize(1f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor3f(r, g, b);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void drawRect(int x1, int y1, int x2, int y2, float r,
			float g, float b) {
		//GL11.glDisable(3553 /* GL_TEXTURE_2D */);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor3f(r, g, b);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x1, y2);
		GL11.glEnd();
		//GL11.glEnable(3553 /* GL_TEXTURE_2D */);
	}

	public static void drawRectLine(int x1, int y1, int x2, int y2, double r,
			double g, double b) {
		//GL11.glDisable(3553 /* GL_TEXTURE_2D */);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor3d(r, g, b);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x1, y2);
		GL11.glEnd();
		//GL11.glEnable(3553 /* GL_TEXTURE_2D */);
	}

	public static int clamp_flt(float f, float min, float max) {
		if (f < min)
			return 0;
		if (f > max)
			return 255;
		return (int) (255.0f * (f - min) / (max - min));
	}

	public static int PIXRGB(int r, int g, int b) {
		return 0xFF000000 | r << 16 | g << 8 | b;
	}

	public static int PIXRGBA(int r, int g, int b, int a) {
		return a << 24 | r << 16 | g << 8 | b;
	}

	public static int PIXA(int x) {
		return x >> 24 & 0xFF;
	}

	public static int PIXR(int x) {
		return x >> 16 & 0xFF;
	}

	public static int PIXG(int x) {
		return x >> 8 & 0xFF;
	}

	public static int PIXB(int x) {
		return x & 0xFF;
	}

	public static int PIXPACK(int x) {
		return 0xFF000000 | x & 0xFFFFFF;
	}

	public static void drawLine(int i, int j, int k, int l, int i1, float r,
			float g, float b) {
		//GL11.glDisable(3553 /* GL_TEXTURE_2D */);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glColor4f(r, g, b, 1f);
		GL11.glLineWidth(i1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2i(i, j);
		GL11.glVertex2i(k, l);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		//GL11.glEnable(3553 /* GL_TEXTURE_2D */);
	}

	public static void rotate(double deg, int x, int y, int z) {
		GL11.glRotated(deg, x, y, z);
	}

	public static void drawQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, float r,
			float g, float b) {
		//GL11.glDisable(3553 /* GL_TEXTURE_2D */);
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glColor4f(r, g, b, 1f);
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		GL11.glVertex2i(x1, y1);
		GL11.glVertex2i(x2, y2);
		GL11.glVertex2i(x3, y3);
		GL11.glVertex2i(x4, y4);
		GL11.glEnd();
		GL11.glPopMatrix();
		//GL11.glEnable(3553 /* GL_TEXTURE_2D */);
	}

	public static void drawCircle(int x1, int y1, double radius) {
		int seg = 360;
		float theta = (float) (2 * 3.1415926 / seg);
		float tangetial_factor = (float) Math.tan(theta);//calculate the tangential factor

		float radial_factor = (float) Math.cos(theta);//calculate the radial factor

		float x = (float) radius;//we start at angle = 0

		float y = 0;

		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(int ii = 0; ii < seg; ii++)
		{
			GL11.glVertex2f(x + x1, y + y1);//output vertex

			//calculate the tangential vector
			//remember, the radial vector is (x, y)
			//to get the tangential vector we flip those coordinates and negate one of them

			float tx = -y;
			float ty = x;

			//add the tangential vector

			x += tx * tangetial_factor;
			y += ty * tangetial_factor;

			//correct using the radial factor

			x *= radial_factor;
			y *= radial_factor;
		}
		GL11.glEnd();
	}

	public static void drawFilledCircle(int x1, int y1, double radius) {
		while(radius > 0) {
			drawCircle(x1,y1,radius);
			radius -= 0.2;
		}
	}

	public static void drawThickLine(int startScreenX, int startScreenY, int endScreenX, int endScreenY, float width) {

		Vector2f start = new Vector2f(startScreenX, startScreenY);
		Vector2f end = new Vector2f(endScreenX, endScreenY);

		float dx = startScreenX - endScreenX;
		float dy = startScreenY - endScreenY;

		Vector2f rightSide = new Vector2f(dy, -dx);
		if (rightSide.length() > 0) {
			rightSide.normalise();
			rightSide.scale(width / 2);
		}
		Vector2f leftSide = new Vector2f(-dy, dx);
		if (leftSide.length() > 0) {
			leftSide.normalise();
			leftSide.scale(width / 2);
		}

		Vector2f one = new Vector2f();
		Vector2f.add(leftSide, start, one);

		Vector2f two = new Vector2f();
		Vector2f.add(rightSide, start, two);

		Vector2f three = new Vector2f();
		Vector2f.add(rightSide, end, three);

		Vector2f four = new Vector2f();
		Vector2f.add(leftSide, end, four);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(255f,255f,255f,255f);
		GL11.glVertex3f(one.x, one.y, 0);
		GL11.glVertex3f(two.x, two.y, 0);
		GL11.glVertex3f(three.x, three.y, 0);
		GL11.glVertex3f(four.x, four.y, 0);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnd();
	}
}