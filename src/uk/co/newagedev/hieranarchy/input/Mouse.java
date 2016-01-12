package uk.co.newagedev.hieranarchy.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import uk.co.newagedev.hieranarchy.main.Main;

public class Mouse {

	public static final int BUTTON_COUNT = 8, BUTTON_1 = 0x0, BUTTON_2 = 0x1, BUTTON_3 = 0x2, BUTTON_4 = 0x3, BUTTON_5 = 0x4, BUTTON_6 = 0x5, BUTTON_7 = 0x6, BUTTON_8 = 0x7, BUTTON_LEFT = BUTTON_1, BUTTON_RIGHT = BUTTON_2, BUTTON_MIDDLE = BUTTON_3;

	private static boolean[] pressing = new boolean[BUTTON_COUNT];
	private static boolean[] down = new boolean[BUTTON_COUNT];
	private static boolean[] releasing = new boolean[BUTTON_COUNT];

	private static int updatesSinceLastMovement = 0, updatesSinceLastButton = 0;
	private static double mx = 0, my = 0, mdx = 0, mdy = 0, mdw = 0;

	@SuppressWarnings("unused")
	private static GLFWCursorPosCallback cursorPosCallback;

	public static void init() {
		GLFW.glfwSetCursorPosCallback(Main.getScreen().getWindowID(), (cursorPosCallback = new GLFWCursorPosCallback() {

			@Override
			public void invoke(long window, double xpos, double ypos) {
				double oldmx = mx, oldmy = my;
				mx = xpos;
				my = ypos;
				mdx = mx - oldmx;
				mdy = my - oldmy;
			}

		}));
	}

	public static double getMouseX() {
		return mx;
	}

	public static double getMouseY() {
		return my;
	}

	public static void simulateLocation(int x, int y) {
		mx = x;
		my = y;
	}

	public static double getChangeInMouseX() {
		return mdx;
	}

	public static double getChangeInMouseY() {
		return mdy;
	}

	public static double getChangeInMouseWheel() {
		return mdw;
	}

	public static long getMillisSinceLastMovement() {
		return updatesSinceLastMovement;
	}

	public static long getMillisSinceLastButton() {
		return updatesSinceLastButton;
	}

	public static void update() {
		int t = 0;
		for (int i = 0; i < BUTTON_COUNT; i++) {
			boolean bd = GLFW.glfwGetMouseButton(Main.getScreen().getWindowID(), i) == GLFW.GLFW_PRESS;
			if (!pressing[i] && !down[i] && bd) {
				pressing[i] = true;
			} else if (pressing[i] && !down[i] && bd) {
				pressing[i] = false;
				down[i] = true;
			} else if (down[i] && !releasing[i] && !bd) {
				down[i] = false;
				releasing[i] = true;
			} else if (!down[i] && releasing[i] && !bd) {
				releasing[i] = false;
			} else {
				t += 1;
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

		if (t == BUTTON_COUNT) {
			updatesSinceLastButton += 1;
		} else {
			updatesSinceLastButton = 0;
		}

		if (mdx == 0 && mdy == 0) {
			updatesSinceLastMovement += 1;
		} else {
			updatesSinceLastMovement = 0;
		}
	}

	public static boolean isButtonPressing(int index) {
		return pressing[index];
	}

	public static boolean isButtonDown(int index) {
		return down[index];
	}

	public static boolean isButtonReleasing(int index) {
		return releasing[index];
	}

	public static String getButtonStates(int index) {
		return "Pressing: " + String.valueOf(pressing[index]) + ", Down: " + String.valueOf(down[index]) + ", Releasing: " + String.valueOf(releasing[index]);
	}
}
