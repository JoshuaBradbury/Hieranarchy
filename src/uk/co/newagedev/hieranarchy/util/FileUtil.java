package uk.co.newagedev.hieranarchy.util;

import java.io.File;

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
		return (new File(filePath)).exists();
	}

	public static File load(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			return file;
		}
		return null;
	}
}
