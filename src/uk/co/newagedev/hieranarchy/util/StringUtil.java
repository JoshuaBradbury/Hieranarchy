package uk.co.newagedev.hieranarchy.util;

public class StringUtil {
	
	public static int countOccurences(String line, String sub) {
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			String sect = line.substring(i, i + sub.length());
			if (sect.equals(sub)) {
				count += 1;
			}
		}
		return count;
	}
	
}
