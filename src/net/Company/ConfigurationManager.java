package net.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class ConfigurationManager {

	File configFile;

	public ConfigurationManager() {
		configFile = new File(EngineUtils.getAppDir(), "config.txt");
	}

	public void load() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] args = line.split(":");
				if (args[0].equalsIgnoreCase("AA"))
					Engine.AA = Integer.parseInt(args[1]);
				else if (args[0].equalsIgnoreCase("VSync"))
					Engine.VSync = Boolean.parseBoolean(args[1]);
				else if (args[0].equalsIgnoreCase("Debug"))
					Engine.Debug = Boolean.parseBoolean(args[1]);
				else if (args[0].equalsIgnoreCase("targetFrames"))
					Engine.targetFrames = Integer.parseInt(args[1]);
				else if (args[0].equalsIgnoreCase("Fullscreen"))
					Engine.Fullscreen = Boolean.parseBoolean(args[1]);
				else
					Engine.game.configLoad(args);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			PrintWriter p = new PrintWriter(new FileOutputStream(configFile));
			p.println("AA:" + Engine.AA);
			p.println("VSync:" + Engine.VSync);
			p.println("Debug:" + Engine.Debug);
			p.println("targetFrames:" + Engine.targetFrames);
			p.println("Fullscreen:" + Engine.Fullscreen);
			String[] extra = Engine.game.configSave();
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
