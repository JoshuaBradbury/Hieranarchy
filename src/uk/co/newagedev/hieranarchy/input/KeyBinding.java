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
	
	private static boolean capsLock = false, shift = false;
	
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
		for (int i = 0; i < Keyboard.KEYBOARD_SIZE; i++) {
			boolean kd = Keyboard.isKeyDown(i);
			if (!pressing[i] && !down[i] && kd) {
				pressing[i] = true;
			} else if (pressing[i] && !down[i] && kd) {
				pressing[i] = false;
				down[i] = true;
				if (i == Keyboard.KEY_LSHIFT || i == Keyboard.KEY_RSHIFT) shift = true;
				if (i == Keyboard.KEY_CAPITAL) capsLock = !capsLock;
			} else if (down[i] && !releasing[i] && !kd) {
				down[i] = false;
				releasing[i] = true;
			} else if (!down[i] && releasing[i] && !kd) {
				releasing[i] = false;
				if (i == Keyboard.KEY_LSHIFT || i == Keyboard.KEY_RSHIFT) shift = false;
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
	
	public static char getKeyChar(int key) {
		switch (key) {
		case Keyboard.KEY_0:          return shift ? ')' : '0';
		case Keyboard.KEY_1:          return shift ? '!' : '1';
		case Keyboard.KEY_2:          return shift ? '"' : '2';
		case Keyboard.KEY_3:          return shift ? '£' : '3';
		case Keyboard.KEY_4:          return shift ? '$' : '4';
		case Keyboard.KEY_5:          return shift ? '%' : '5';
		case Keyboard.KEY_6:          return shift ? '^' : '6';
		case Keyboard.KEY_7:          return shift ? '&' : '7';
		case Keyboard.KEY_8:          return shift ? '*' : '8';
		case Keyboard.KEY_9:          return shift ? '(' : '9';
		case Keyboard.KEY_A:          return capsLock || shift ? 'A' : 'a';
		case Keyboard.KEY_ADD:        return '+';
		case Keyboard.KEY_APOSTROPHE: return shift ? '@' : '\'';
		case Keyboard.KEY_B:          return capsLock || shift ? 'B' : 'b';
		case Keyboard.KEY_BACKSLASH:  return shift ? '|' : '\\';
		case Keyboard.KEY_C:          return capsLock || shift ? 'C' : 'c';
		case Keyboard.KEY_CIRCUMFLEX: return '^';
		case Keyboard.KEY_COLON:      return ':';
		case Keyboard.KEY_COMMA:      return shift ? '<' : ',';
		case Keyboard.KEY_D:          return capsLock || shift ? 'D' : 'd';
		case Keyboard.KEY_DECIMAL:    return '.';
		case Keyboard.KEY_DIVIDE:     return '/';
		case Keyboard.KEY_E:          return capsLock || shift ? 'E' : 'e';
		case Keyboard.KEY_EQUALS:     return shift ? '+' : '=';
		case Keyboard.KEY_F:          return capsLock || shift ? 'F' : 'f';
		case Keyboard.KEY_G:          return capsLock || shift ? 'G' : 'g';
		case Keyboard.KEY_GRAVE:      return shift ? '¬' : '`';
		case Keyboard.KEY_H:          return capsLock || shift ? 'H' : 'h';
		case Keyboard.KEY_I:          return capsLock || shift ? 'I' : 'i';
		case Keyboard.KEY_J:          return capsLock || shift ? 'J' : 'j';
		case Keyboard.KEY_K:          return capsLock || shift ? 'K' : 'k';
		case Keyboard.KEY_L:          return capsLock || shift ? 'L' : 'l';
		case Keyboard.KEY_LBRACKET:   return shift ? '{' : '[';
		case Keyboard.KEY_M:          return capsLock || shift ? 'M' : 'm';
		case Keyboard.KEY_MINUS:      return shift ? '_' : '-';
		case Keyboard.KEY_MULTIPLY:   return '*';
		case Keyboard.KEY_N:          return capsLock || shift ? 'N' : 'n';
		case Keyboard.KEY_NUMPAD0:    return '0';
		case Keyboard.KEY_NUMPAD1:    return '1';
		case Keyboard.KEY_NUMPAD2:    return '2';
		case Keyboard.KEY_NUMPAD3:    return '3';
		case Keyboard.KEY_NUMPAD4:    return '4';
		case Keyboard.KEY_NUMPAD5:    return '5';
		case Keyboard.KEY_NUMPAD6:    return '6';
		case Keyboard.KEY_NUMPAD7:    return '7';
		case Keyboard.KEY_NUMPAD8:    return '8';
		case Keyboard.KEY_NUMPAD9:    return '9';
		case Keyboard.KEY_O:          return capsLock || shift ? 'O' : 'o';
		case Keyboard.KEY_P:          return capsLock || shift ? 'P' : 'p';
		case Keyboard.KEY_PERIOD:     return shift ? '>' : '.';
		case Keyboard.KEY_Q:          return capsLock || shift ? 'Q' : 'q';
		case Keyboard.KEY_R:          return capsLock || shift ? 'R' : 'r';
		case Keyboard.KEY_RBRACKET:   return shift ? '}' : ']';
		case Keyboard.KEY_S:          return capsLock || shift ? 'S' : 's';
		case Keyboard.KEY_SEMICOLON:  return shift ? ':' : ';';
		case Keyboard.KEY_SLASH:      return shift ? '?' : '/';
		case Keyboard.KEY_SPACE:      return ' ';
		case Keyboard.KEY_SUBTRACT:   return '-';
		case Keyboard.KEY_T:          return capsLock || shift ? 'T' : 't';
		case Keyboard.KEY_TAB:        return '	';
		case Keyboard.KEY_U:          return capsLock || shift ? 'U' : 'u';
		case Keyboard.KEY_UNDERLINE:  return '_';
		case Keyboard.KEY_V:          return capsLock || shift ? 'V' : 'v';
		case Keyboard.KEY_W:          return capsLock || shift ? 'W' : 'w';
		case Keyboard.KEY_X:          return capsLock || shift ? 'X' : 'x';
		case Keyboard.KEY_Y:          return capsLock || shift ? 'Y' : 'y';
		case Keyboard.KEY_Z:          return capsLock || shift ? 'Z' : 'z';
		}
		return '¥';
	}
}
