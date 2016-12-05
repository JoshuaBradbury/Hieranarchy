package uk.co.newagedev.hieranarchy.util;

public final class ErrorUtil {

	private ErrorUtil() {}
	
	public static void throwError(Object... output) {
		Logger.error(output);
		throw new RuntimeException();
	}
	
}
