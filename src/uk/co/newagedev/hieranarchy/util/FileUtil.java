package uk.co.newagedev.hieranarchy.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {

	public static String getExtension(String filePath) {
		String fileName = getFileName(filePath);
		String[] parts = fileName.split("\\.");
		return parts[parts.length - 1];
	}

	public static String getFileName(String filePath) {
		String[] parts;
		if (filePath.contains("/")) {
			parts = filePath.split("/");
		} else if (filePath.contains("\\")) {
			parts = filePath.split("\\");
		} else {
			parts = new String[] {filePath};
		}
		return parts[parts.length - 1];
	}

	public static String getFileNameWithoutExtension(String filePath) {
		String fileName = getFileName(filePath);
		return fileName.substring(0, fileName.length() - getExtension(fileName).length() - 1);
	}

	public static boolean doesFileExist(String filePath) {
		return load(filePath) != null;
	}
	
	public static boolean isDirectory(String filePath) {
		File file = load(filePath);
		if (file != null) {
			return file.isDirectory();
		}
		return false;
	}

	public static File load(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return file;
		}
		return null;
	}
	
	public static File create(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				Logger.error((Object[]) e.getStackTrace());
			}
		}
		return file;
	}
}
