package uk.co.newagedev.hieranarchy.util;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Logger {
	
	public static void info(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			builder.append(objects[i].toString() + " ");
		}
		System.out.println("[" + getTimeStamp() + "] [INFO] " + builder.toString());
	}
	
	public static void error(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			builder.append(objects[i].toString() + " ");
		}
		System.err.println("[" + getTimeStamp() + "] [ERROR] " + builder.toString());
	}
	
	private static String getTimeStamp() {
		return (new Timestamp((new Date()).getTime())).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")).toString();
	}
}
