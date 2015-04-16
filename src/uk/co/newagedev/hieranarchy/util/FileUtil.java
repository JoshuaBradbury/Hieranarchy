package uk.co.newagedev.hieranarchy.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
			parts = new String[] { filePath };
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

	public static String[] getAllFilesInFolder(String folder) {
		List<File> filesToCheck = new ArrayList<File>();
		File file = load(folder);
		if (file != null) {
			if (file.exists()) {
				if (file.isDirectory()) {
					for (File f : file.listFiles()) {
						filesToCheck.add(f);
					}
				}
			}
			boolean foldersInList = true;
			while (foldersInList) {
				List<File> remove = new ArrayList<File>();
				for (File f : filesToCheck) {
					if (f.isDirectory()) {
						remove.add(f);
						for (File fi : f.listFiles()) {
							filesToCheck.add(fi);
						}
					}
				}
				for (File f : remove) {
					filesToCheck.remove(f);
				}
				if (remove.size() == 0) {
					foldersInList = false;
				}
			}
			String[] files = new String[filesToCheck.size()];
			for (int i = 0; i < files.length; i++) {
				files[i] = filesToCheck.get(i).getPath();
			}
			return files;
		}
		return new String[] {};
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
				if (filePath.endsWith("/") || filePath.endsWith("\\")) {
					file.mkdirs();
				} else {
					file.createNewFile();
				}
			} catch (IOException e) {
				Logger.error(e.getMessage());
				for (Object obj : e.getStackTrace()) {
					Logger.error(obj);
				}
			}
		}
		return file;
	}
}
