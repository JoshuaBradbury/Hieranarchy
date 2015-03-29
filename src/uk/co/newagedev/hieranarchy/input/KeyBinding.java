package uk.co.newagedev.hieranarchy.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.util.Logger;

public class KeyBinding {

	private static Map<String, Integer> keyBindings = new HashMap<String, Integer>();

	private static boolean[] pressing = new boolean[Keyboard.KEYBOARD_SIZE];
	private static boolean[] down = new boolean[Keyboard.KEYBOARD_SIZE];
	private static boolean[] releasing = new boolean[Keyboard.KEYBOARD_SIZE];

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
		Keyboard.poll();
		for (int i = 0; i < Keyboard.getKeyCount(); i++) {
			boolean kd = Keyboard.isKeyDown(i);
			if (!pressing[i] && !down[i] && kd) {
				pressing[i] = true;
			} else if (pressing[i] && !down[i] && kd) {
				pressing[i] = false;
				down[i] = true;
			} else if (down[i] && !releasing[i] && !kd) {
				down[i] = false;
				releasing[i] = true;
			} else if (!down[i] && releasing[i] && !kd) {
				releasing[i] = false;
			}

			if (pressing[i] && releasing[i]) {
				releasing[i] = false;
			}
			if (releasing[i] && down[i]) {
				down[i] = false;
			}
			if (pressing[i] && down[i]) {
				down[i] = false;
			}
		}
	}

	public static void cleanup() {
		Keyboard.destroy();
	}

	public static boolean isKeyDown(String function) {
		int id = getBinding(function);
		return down[id];
	}

	public static boolean isKeyPressing(String function) {
		int id = getBinding(function);
		return pressing[id];
	}

	public static boolean isKeyReleasing(String function) {
		int id = getBinding(function);
		return releasing[id];
	}
	
	public static List<Integer> getKeysPressing() {
		List<Integer> keys = new ArrayList<Integer>();
		for (int i = 0; i < pressing.length; i++) {
			if (pressing[i]) {
				keys.add(i);
			}
		}
		return keys;
	}
	
	public static List<Integer> getKeysDown() {
		List<Integer> keys = new ArrayList<Integer>();
		for (int i = 0; i < down.length; i++) {
			if (down[i]) {
				keys.add(i);
			}
		}
		return keys;
	}
	
	public static List<Integer> getKeysReleasing() {
		List<Integer> keys = new ArrayList<Integer>();
		for (int i = 0; i < releasing.length; i++) {
			if (releasing[i]) {
				keys.add(i);
			}
		}
		return keys;
	}
}
