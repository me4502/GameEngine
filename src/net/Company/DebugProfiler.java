package net.Company;

import java.util.ArrayList;

public class DebugProfiler {

	private int x = 0, y = 0;
	private String name = "";
	private long firstTime = 0L;
	private long lastTime = 0L;
	private ArrayList<String> extraTimes = new ArrayList<String>();
	private long sectionTime = 0L;
	private String sectionName = "";

	public DebugProfiler(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public void reset() {
		lastTime = 0L;
		firstTime = 0L;
		extraTimes.clear();
	}

	public void start() {
		long time = System.currentTimeMillis();
		firstTime = time;
	}

	public void stop() {
		long time = System.currentTimeMillis();
		lastTime = time;
	}

	public void startEndSection(String name) {
		long time = System.currentTimeMillis();
		long thisTime = time - sectionTime;
		extraTimes.add(sectionName + ": " + thisTime + "ms");
		sectionName = name;
		time = System.currentTimeMillis();
		sectionTime = time;
	}

	public void startSection(String name) {
		sectionName = name;
		long time = System.currentTimeMillis();
		sectionTime = time;
	}

	public void endSection() {
		long time = System.currentTimeMillis();
		long thisTime = time - sectionTime;
		extraTimes.add(sectionName + ": " + thisTime + "ms");
		sectionTime = 0L;
		sectionName = "";
	}

	public void drawTimes() {
		Rendering.drawFont(x, y, name + ": " + (lastTime - firstTime) + "ms", 255, 255, 255);
		int num = 1;
		for (String s : extraTimes) {
			Rendering.drawFont(x + 3, y + num * 10, s, 255, 255, 255);
			num++;
		}
	}
}