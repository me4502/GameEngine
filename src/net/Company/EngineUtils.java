package net.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;

public class EngineUtils {

	Engine engine;

	public EngineUtils(Engine engine) {

		this.engine = engine;
	}

	public boolean hasGotNatives = false;
	public File appDir;

	protected String getOs() {
		String s = System.getProperty("os.name").toLowerCase();

		if (s.contains("win")) {
			return "windows";
		}

		if (s.contains("mac")) {
			return "macosx";
		}

		if (s.contains("solaris")) {
			return "solaris";
		}

		if (s.contains("sunos")) {
			return "solaris";
		}

		if (s.contains("linux")) {
			return "linux";
		}

		if (s.contains("unix")) {
			return "linux";
		} else {
			return "linux";
		}
	}

	public File getAppDir() {
		if (appDir == null) {
			appDir = getAppDir(engine.title.replace(" ", ""));
		}

		return appDir;
	}

	public boolean isInstalling() {
		if (appDir == null) {
			appDir = getAppDir(engine.title.replace(" ", ""));
		}

		File natives = new File(appDir, "natives/");
		if (!natives.exists())
			natives.mkdir();
		File os = new File(natives, getOs());
		return !os.exists();
	}

	public void downloadNatives(boolean force) {
		File natives = new File(appDir, "natives/");
		if (!natives.exists())
			natives.mkdir();
		File os = new File(natives, getOs());
		if (!os.exists()) {
			os.mkdir();
		}
		if(new File(os, "natives.done").exists() && !force) {
			hasGotNatives = true;
			return;
		}
		downloadFiles("http://me4502.com/downloads/natives/" + getOs() + "/files.txt", os);
	}

	public File getAppDir(String par0Str) {
		String s = System.getProperty("user.home", ".");
		File file = null;

		if (getOs().equalsIgnoreCase("windows")) {
			String s1 = System.getenv("APPDATA");

			if (s1 != null) {
				file = new File(s1, new StringBuilder().append(".")
						.append(par0Str).append('/').toString());
			} else {
				file = new File(s, new StringBuilder().append('.')
						.append(par0Str).append('/').toString());
			}
		} else if (getOs().equalsIgnoreCase("macosx")) {
			file = new File(s, new StringBuilder()
			.append("Library/Application Support/").append(par0Str)
			.toString());
		} else if (getOs().equalsIgnoreCase("solaris")) {
			file = new File(s, new StringBuilder().append('.').append(par0Str)
					.append('/').toString());
		} else if (getOs().equalsIgnoreCase("linux")) {
			file = new File(s, new StringBuilder().append(par0Str).append('/')
					.toString());
		} else {
			file = new File(s, new StringBuilder().append(par0Str).append('/')
					.toString());
		}

		if (!file.exists() && !file.mkdirs()) {
			throw new RuntimeException(new StringBuilder()
			.append("The working directory could not be created: ")
			.append(file).toString());
		} else {
			return file;
		}
	}

	public void downloadFiles(String list, File outputDir) {
		try {
			URL url = new URL(list);
			URLConnection urlconnection = url.openConnection();
			//urlconnection.setDoOutput(true);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(urlconnection.getInputStream()));
			String s;
			while ((s = bufferedreader.readLine()) != null) {
				downloadFile(list.replace("files.txt", s), new File(outputDir,
						s));
			}
			bufferedreader.close();
			if (hasGotNatives == false) {
				new File(outputDir, "natives.done").createNewFile();
				hasGotNatives = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadFile(final String url, final File out) {
		if (out.exists())
			out.delete();
		try {
			URL url1 = new URL(url);
			java.nio.channels.ReadableByteChannel readablebytechannel = Channels
					.newChannel(url1.openStream());
			FileOutputStream fileoutputstream = new FileOutputStream(out);
			fileoutputstream.getChannel().transferFrom(readablebytechannel, 0L,
					0x1000000L);
			fileoutputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
