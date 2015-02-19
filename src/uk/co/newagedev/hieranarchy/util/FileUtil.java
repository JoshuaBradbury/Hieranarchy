package uk.co.newagedev.hieranarchy.util;

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
}
