package net.Company;

import java.io.File;

public class ResourceLoader {

	public static String loadResource(String location, String resource) {
		File f = new File(location + "/" + resource);
		if (f.exists()) {
			return location + "/" + resource;
		}
		else
			return resource;
	}
}