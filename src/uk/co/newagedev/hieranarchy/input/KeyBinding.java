package uk.co.newagedev.hieranarchy.input;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.Logger;

public class KeyBinding {

	public static final int KEYBOARD_SIZE = 0x15c;
	
	public static final int KEY_SPACE         = 0x20,
							KEY_APOSTROPHE    = 0x27,
							KEY_COMMA         = 0x2C,
							KEY_MINUS         = 0x2D,
							KEY_PERIOD        = 0x2E,
							KEY_SLASH         = 0x2F,
							KEY_0             = 0x30,
							KEY_1             = 0x31,
							KEY_2             = 0x32,
							KEY_3             = 0x33,
							KEY_4             = 0x34,
							KEY_5             = 0x35,
							KEY_6             = 0x36,
							KEY_7             = 0x37,
							KEY_8             = 0x38,
							KEY_9             = 0x39,
							KEY_SEMICOLON     = 0x3B,
							KEY_EQUAL         = 0x3D,
							KEY_A             = 0x41,
							KEY_B             = 0x42,
							KEY_C             = 0x43,
							KEY_D             = 0x44,
							KEY_E             = 0x45,
							KEY_F             = 0x46,
							KEY_G             = 0x47,
							KEY_H             = 0x48,
							KEY_I             = 0x49,
							KEY_J             = 0x4A,
							KEY_K             = 0x4B,
							KEY_L             = 0x4C,
							KEY_M             = 0x4D,
							KEY_N             = 0x4E,
							KEY_O             = 0x4F,
							KEY_P             = 0x50,
							KEY_Q             = 0x51,
							KEY_R             = 0x52,
							KEY_S             = 0x53,
							KEY_T             = 0x54,
							KEY_U             = 0x55,
							KEY_V             = 0x56,
							KEY_W             = 0x57,
							KEY_X             = 0x58,
							KEY_Y             = 0x59,
							KEY_Z             = 0x5A,
							KEY_LEFT_BRACKET  = 0x5B,
							KEY_BACKSLASH     = 0x5C,
							KEY_RIGHT_BRACKET = 0x5D,
							KEY_GRAVE_ACCENT  = 0x60,
							KEY_WORLD_1       = 0xA1,
							KEY_WORLD_2       = 0xA2,
							KEY_ESCAPE        = 0x100,
							KEY_ENTER         = 0x101,
							KEY_TAB           = 0x102,
							KEY_BACKSPACE     = 0x103,
							KEY_INSERT        = 0x104,
							KEY_DELETE        = 0x105,
							KEY_RIGHT         = 0x106,
							KEY_LEFT          = 0x107,
							KEY_DOWN          = 0x108,
							KEY_UP            = 0x109,
							KEY_PAGE_UP       = 0x10A,
							KEY_PAGE_DOWN     = 0x10B,
							KEY_HOME          = 0x10C,
							KEY_END           = 0x10D,
							KEY_CAPS_LOCK     = 0x118,
							KEY_SCROLL_LOCK   = 0x119,
							KEY_NUM_LOCK      = 0x11A,
							KEY_PRINT_SCREEN  = 0x11B,
							KEY_PAUSE         = 0x11C,
							KEY_F1            = 0x122,
							KEY_F2            = 0x123,
							KEY_F3            = 0x124,
							KEY_F4            = 0x125,
							KEY_F5            = 0x126,
							KEY_F6            = 0x127,
							KEY_F7            = 0x128,
							KEY_F8            = 0x129,
							KEY_F9            = 0x12A,
							KEY_F10           = 0x12B,
							KEY_F11           = 0x12C,
							KEY_F12           = 0x12D,
							KEY_F13           = 0x12E,
							KEY_F14           = 0x12F,
							KEY_F15           = 0x130,
							KEY_F16           = 0x131,
							KEY_F17           = 0x132,
							KEY_F18           = 0x133,
							KEY_F19           = 0x134,
							KEY_F20           = 0x135,
							KEY_F21           = 0x136,
							KEY_F22           = 0x137,
							KEY_F23           = 0x138,
							KEY_F24           = 0x139,
							KEY_F25           = 0x13A,
							KEY_KP_0          = 0x140,
							KEY_KP_1          = 0x141,
							KEY_KP_2          = 0x142,
							KEY_KP_3          = 0x143,
							KEY_KP_4          = 0x144,
							KEY_KP_5          = 0x145,
							KEY_KP_6          = 0x146,
							KEY_KP_7          = 0x147,
							KEY_KP_8          = 0x148,
							KEY_KP_9          = 0x149,
							KEY_KP_DECIMAL    = 0x14A,
							KEY_KP_DIVIDE     = 0x14B,
							KEY_KP_MULTIPLY   = 0x14C,
							KEY_KP_SUBTRACT   = 0x14D,
							KEY_KP_ADD        = 0x14E,
							KEY_KP_ENTER      = 0x14F,
							KEY_KP_EQUAL      = 0x150,
							KEY_LEFT_SHIFT    = 0x154,
							KEY_LEFT_CONTROL  = 0x155,
							KEY_LEFT_ALT      = 0x156,
							KEY_LEFT_SUPER    = 0x157,
							KEY_RIGHT_SHIFT   = 0x158,
							KEY_RIGHT_CONTROL = 0x159,
							KEY_RIGHT_ALT     = 0x15A,
							KEY_RIGHT_SUPER   = 0x15B,
							KEY_MENU          = 0x15C;
	
	private static Map<String, Integer> keyBindings = new HashMap<String, Integer>();

	private static boolean[] pressing = new boolean[KEYBOARD_SIZE];
	private static boolean[] down = new boolean[KEYBOARD_SIZE];
	private static boolean[] releasing = new boolean[KEYBOARD_SIZE];
	
	private static boolean capsLock = false, shift = false;
	
	public static boolean isShift() {
		return shift;
	}
	
	public static void bindKey(String function, int key) {
		keyBindings.put(function, key);
		Logger.info(function, "bound as key", getKeyName(key));
	}

	public static void removeBinding(String function) {
		Logger.info(function, "unbound from key", getKeyName(keyBindings.get(function)));
		keyBindings.remove(function);
	}

	public static void changeBinding(String function, int newKey) {
		keyBindings.put(function, newKey);
		Logger.info(function, "rebound to key", getKeyName(newKey));
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
	
	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(Main.getScreen().getWindowID(), key) == GLFW.GLFW_PRESS;
	}

	public static int getBinding(String function) {
		return keyBindings.get(function);
	}

	public static void update() {
		for (int i = 0; i < KEYBOARD_SIZE; i++) {
			boolean kd = isKeyDown(i);
			if (!pressing[i] && !down[i] && kd) {
				pressing[i] = true;
			} else if (pressing[i] && !down[i] && kd) {
				pressing[i] = false;
				down[i] = true;
				if (i == KEY_LEFT_SHIFT || i == KEY_RIGHT_SHIFT) shift = true;
				if (i == KEY_CAPS_LOCK) capsLock = !capsLock;
			} else if (down[i] && !releasing[i] && !kd) {
				down[i] = false;
				releasing[i] = true;
			} else if (!down[i] && releasing[i] && !kd) {
				releasing[i] = false;
				if (i == KEY_LEFT_SHIFT || i == KEY_RIGHT_SHIFT) shift = false;
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
		
	}

	public static boolean isBindingDown(String function) {
		int id = getBinding(function);
		return down[id];
	}

	public static boolean isBindingPressing(String function) {
		int id = getBinding(function);
		return pressing[id];
	}

	public static boolean isBindingReleasing(String function) {
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
	
	public static String getKeyName(int key) {
		Field[] fields = KeyBinding.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().toLowerCase().contains("key_")) {
				try {
					if (((int)field.get(null)) == key) {
						return field.getName().substring(4);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	
	public static char getKeyChar(int key) {
		switch (key) {
		case KEY_0:             return shift ? ')' : '0';
		case KEY_1:             return shift ? '!' : '1';
		case KEY_2:             return shift ? '"' : '2';
		case KEY_3:             return shift ? '£' : '3';
		case KEY_4:             return shift ? '$' : '4';
		case KEY_5:             return shift ? '%' : '5';
		case KEY_6:             return shift ? '^' : '6';
		case KEY_7:             return shift ? '&' : '7';
		case KEY_8:             return shift ? '*' : '8';
		case KEY_9:             return shift ? '(' : '9';
		case KEY_A:             return capsLock || shift ? 'A' : 'a';
		case KEY_KP_ADD:        return '+';
		case KEY_APOSTROPHE:    return shift ? '@' : '\'';
		case KEY_B:             return capsLock || shift ? 'B' : 'b';
		case KEY_BACKSLASH:     return shift ? '|' : '\\';
		case KEY_C:             return capsLock || shift ? 'C' : 'c';
		case KEY_COMMA:         return shift ? '<' : ',';
		case KEY_D:             return capsLock || shift ? 'D' : 'd';
		case KEY_KP_DECIMAL:    return '.';
		case KEY_KP_DIVIDE:     return '/';
		case KEY_E:             return capsLock || shift ? 'E' : 'e';
		case KEY_EQUAL:         return shift ? '+' : '=';
		case KEY_F:             return capsLock || shift ? 'F' : 'f';
		case KEY_G:             return capsLock || shift ? 'G' : 'g';
		case KEY_GRAVE_ACCENT:  return shift ? '¬' : '`';
		case KEY_H:             return capsLock || shift ? 'H' : 'h';
		case KEY_I:             return capsLock || shift ? 'I' : 'i';
		case KEY_J:             return capsLock || shift ? 'J' : 'j';
		case KEY_K:             return capsLock || shift ? 'K' : 'k';
		case KEY_L:             return capsLock || shift ? 'L' : 'l';
		case KEY_LEFT_BRACKET:  return shift ? '{' : '[';
		case KEY_M:             return capsLock || shift ? 'M' : 'm';
		case KEY_MINUS:         return shift ? '_' : '-';
		case KEY_KP_MULTIPLY:   return '*';
		case KEY_N:             return capsLock || shift ? 'N' : 'n';
		case KEY_KP_0:          return '0';
		case KEY_KP_1:          return '1';
		case KEY_KP_2:          return '2';
		case KEY_KP_3:          return '3';
		case KEY_KP_4:          return '4';
		case KEY_KP_5:          return '5';
		case KEY_KP_6:          return '6';
		case KEY_KP_7:          return '7';
		case KEY_KP_8:          return '8';
		case KEY_KP_9:          return '9';
		case KEY_O:             return capsLock || shift ? 'O' : 'o';
		case KEY_P:             return capsLock || shift ? 'P' : 'p';
		case KEY_PERIOD:        return shift ? '>' : '.';
		case KEY_Q:             return capsLock || shift ? 'Q' : 'q';
		case KEY_R:             return capsLock || shift ? 'R' : 'r';
		case KEY_RIGHT_BRACKET: return shift ? '}' : ']';
		case KEY_S:             return capsLock || shift ? 'S' : 's';
		case KEY_SEMICOLON:     return shift ? ':' : ';';
		case KEY_SLASH:         return shift ? '?' : '/';
		case KEY_SPACE:         return ' ';
		case KEY_KP_SUBTRACT:   return '-';
		case KEY_T:             return capsLock || shift ? 'T' : 't';
		case KEY_TAB:           return '	';
		case KEY_U:             return capsLock || shift ? 'U' : 'u';
		case KEY_V:             return capsLock || shift ? 'V' : 'v';
		case KEY_W:             return capsLock || shift ? 'W' : 'w';
		case KEY_X:             return capsLock || shift ? 'X' : 'x';
		case KEY_Y:             return capsLock || shift ? 'Y' : 'y';
		case KEY_Z:             return capsLock || shift ? 'Z' : 'z';
		}
		return '¥';
	}
}
