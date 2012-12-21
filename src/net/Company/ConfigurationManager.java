package net.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class ConfigurationManager {

	Engine engine;
	File configFile;

	public ConfigurationManager(Engine engine) {
		this.engine = engine;
		configFile = new File(engine.utilities.getAppDir(), "config.txt");
	}

	public void load() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] args = line.split(":");
				if (args[0].equalsIgnoreCase("AA"))
					engine.AA = Integer.parseInt(args[1]);
				else if (args[0].equalsIgnoreCase("VSync"))
					engine.VSync = Boolean.parseBoolean(args[1]);
				else if (args[0].equalsIgnoreCase("Debug"))
					engine.Debug = Boolean.parseBoolean(args[1]);
				else if (args[0].equalsIgnoreCase("targetFrames"))
					engine.targetFrames = Integer.parseInt(args[1]);
				else if (args[0].equalsIgnoreCase("Fullscreen"))
					engine.Fullscreen = Boolean.parseBoolean(args[1]);
				else
					engine.game.configLoad(args);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			PrintWriter p = new PrintWriter(new FileOutputStream(configFile));
			p.println("AA:" + engine.AA);
			p.println("VSync:" + engine.VSync);
			p.println("Debug:" + engine.Debug);
			p.println("targetFrames:" + engine.targetFrames);
			p.println("Fullscreen:" + engine.Fullscreen);
			String[] extra = engine.game.configSave();
			if (extra != null)
				for (String s : extra) {
					p.println(s);
				}
			p.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
