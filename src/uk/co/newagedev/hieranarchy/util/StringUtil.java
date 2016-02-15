package uk.co.newagedev.hieranarchy.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	public static String surroundWith(String line, String surrounding) {
		return surrounding + line + surrounding;
	}

	public static String[] split(String line, String regex) {
		List<String> strings = new ArrayList<String>();
		String part = "";
		for (int i = 0; i < line.length() - (regex.length() - 1); i++) {
			if (line.substring(i, i + regex.length()).equalsIgnoreCase(regex)) {
				strings.add(part);
				part = "";
				i = i + regex.length() - 1;
			} else {
				part += line.charAt(i);
			}
		}
		strings.add(part);
		String[] parts = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			parts[i] = strings.get(i);
		}
		return parts;
	}

	public static boolean doesArrayContainString(String[] array, String str) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(str))
				return true;
		}
		return false;
	}
}
