package uk.co.newagedev.hieranarchy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class KeyBinding {

	private static Map<String, Integer> keyBindings = new HashMap<String, Integer>();
	private static List<Object[]> events = new ArrayList<Object[]>();
	
	public static void bindKey(String function, int key) {
		keyBindings.put(function, key);
		Logger.info(function, "bound as key", Keyboard.getKeyName(key));
	}
	
	public static void removeBinding(String function) {
		Logger.info(function, "unbound from key", Keyboard.getKeyName(keyBindings.get(function)));
		keyBindings.remove(function);
	}
	
	public static void changeBinding(String function, int newKey) {
		keyBindings.put(function, newKey);
		Logger.info(function, "rebound to key", Keyboard.getKeyName(newKey));
	}
	
	public static boolean isKeyDuplicated(String function) {
		int count = 0;
		for (String key : keyBindings.keySet()) {
			if (function.equalsIgnoreCase(key)) {
				count += 1;
			}
		}
		return count > 1;
	}
	
	public static int getBinding(String function) {
		return keyBindings.get(function);
	}
	
	public static void update() {
		events = new ArrayList<Object[]>();
		Keyboard.poll();
		while (Keyboard.next()) {
			events.add(new Object[] { Keyboard.getEventKey(), Keyboard.getEventKeyState() });
		}
	}
	
	public static void cleanup() {
		Keyboard.destroy();
	}
	
	public static boolean isKeyDown(String function) {
		int id = getBinding(function);
		return Keyboard.isKeyDown(id);
	}
	
	public static boolean isKeyPressing(String function) {
		int id = getBinding(function);
		for(Object[] event : events) {
			if((int) event[0] == id) {
				return (boolean) event[1];
			}
		}
		return false;
	}
	
	public static boolean isKeyReleasing(String function) {
		int id = getBinding(function);
		for(Object[] event : events) {
			if((int) event[0] == id) {
				return !((boolean) event[1]);
			}
		}
		return false;
	}
}
