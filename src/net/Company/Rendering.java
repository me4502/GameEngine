package net.Company;

import org.lwjgl.opengl.GL11;

public class Rendering {

	public static void drawPixelBlend(int x, int y, float r, float g, float b,
			float a) {
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPointSize(1f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
	}

	public static void drawBlob(int x, int y, float r, float g, float b, float a) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPointSize(4f);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glPopMatrix();
	}

	public static void drawFire(int x, int y, float r, float g, float b, float a) {
		GL11.glPointSize(3f);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor4f(r, g, b, a);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
		GL11.glPointSize(1f);
	}

	public static void drawRect(int x1, int y1, int x2, int y2, float r, float g, float b) {
		drawRect(x1,y1,x2,y2,r,g,b,1f);
	}

	public static void drawRect(int x1, int y1, int x2, int y2, float r,
			float g, float b, float a) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(r, g, b, a);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x1, y2);
		GL11.glEnd();
	}

	public static void drawRectLine(int x1, int y1, int x2, int y2, double r, double g, double b) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor3d(r, g, b);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x1, y2);
		GL11.glEnd();
	}

	/* Utility Functions */
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

	public static void setProperBlending() {

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	}

	public static void rotate(double deg, int x, int y, int z) {
		GL11.glRotated(deg, x, y, z);
	}

	public static void drawQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, float r, float g, float b) {
		GL11.glPushMatrix();
		GL11.glColor4f(r, g, b, 1f);
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		GL11.glVertex2i(x1, y1);
		GL11.glVertex2i(x2, y2);
		GL11.glVertex2i(x3, y3);
		GL11.glVertex2i(x4, y4);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}