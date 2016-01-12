package uk.co.newagedev.hieranarchy.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

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
			parts = StringUtil.split(filePath, "\\");
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
				List<File> toCheck = new ArrayList<File>();
				for (File f : filesToCheck) {
					if (f.isDirectory()) {
						remove.add(f);
						for (File fi : f.listFiles()) {
							toCheck.add(fi);
						}
					}
				}
				for (File f : toCheck) {
					filesToCheck.add(f);
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

	public static ByteBuffer readToByteBuffer(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[4096];

		try {
			while (true) {
				int n = inputStream.read(buffer);

				if (n < 0)
					break;

				outputStream.write(buffer, 0, n);
			}

			inputStream.close();
		} catch (Exception e) {
			Logger.error((Object[]) e.getStackTrace());
		}

		byte[] bytes = outputStream.toByteArray();

		ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bytes.length);
		byteBuffer.put(bytes).flip();

		return byteBuffer;
	}
}
