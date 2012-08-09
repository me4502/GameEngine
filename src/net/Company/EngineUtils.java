package net.Company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;

class EngineUtils {

	public static boolean hasGotNatives = false;
	public static File appDir;

	static String getOs() {
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

	public static File getDirectory() {
		if (appDir == null) {
			appDir = getAppDir(Engine.title.replace(" ", ""));
		}

		File natives = new File(appDir, "natives/");
		if (!natives.exists())
			natives.mkdir();
		File os = new File(natives, getOs());
		if (!os.exists()) {
			os.mkdir();
		}
		downloadFiles("http://dl.dropbox.com/u/20806998/PS/natives/" + getOs()
				+ "/files.txt", os);

		return appDir;
	}

	public static File getAppDir(String par0Str) {
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

	public static void downloadFiles(String list, File outputDir) {
		try {
			URL url = new URL(list);
			URLConnection urlconnection = url.openConnection();
			urlconnection.setReadTimeout(5000);
			urlconnection.setDoOutput(true);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(urlconnection.getInputStream()));
			String s;
			while ((s = bufferedreader.readLine()) != null) {
				downloadFile(list.replace("files.txt", s), new File(outputDir,
						s));
			}
			bufferedreader.close();
			if (hasGotNatives == false)
				hasGotNatives = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadFile(final String url, final File out) {
		if (out.exists())
			return;
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
