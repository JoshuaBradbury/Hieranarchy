package uk.co.newagedev.hieranarchy.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import uk.co.newagedev.hieranarchy.events.EventHub;
import uk.co.newagedev.hieranarchy.events.types.input.MouseButtonEvent;
import uk.co.newagedev.hieranarchy.events.types.input.MouseMoveEvent;
import uk.co.newagedev.hieranarchy.graphics.OpenGLScreen;
import uk.co.newagedev.hieranarchy.main.Main;

public class Mouse {

	public static final int BUTTON_COUNT = 8, BUTTON_1 = 0x0, BUTTON_2 = 0x1, BUTTON_3 = 0x2, BUTTON_4 = 0x3, BUTTON_5 = 0x4, BUTTON_6 = 0x5, BUTTON_7 = 0x6, BUTTON_8 = 0x7, BUTTON_LEFT = BUTTON_1, BUTTON_RIGHT = BUTTON_2, BUTTON_MIDDLE = BUTTON_3;

	private static boolean[] buttonStates = new boolean[BUTTON_COUNT];

	private static double mx = 0, my = 0;

	@SuppressWarnings("unused")
	private static GLFWCursorPosCallback cursorPosCallback;

	public static void init() {
		if (Main.getScreen() instanceof OpenGLScreen) {
			GLFW.glfwSetCursorPosCallback(((OpenGLScreen) Main.getScreen()).getWindowID(), (cursorPosCallback = new GLFWCursorPosCallback() {

				@Override
				public void invoke(long window, double xpos, double ypos) {
					if (mx == 0 && my == 0) {
						mx = xpos;
						my = ypos;
						return;
					}
					double oldmx = mx, oldmy = my;
					mx = xpos;
					my = ypos;
					EventHub.pushEvent(new MouseMoveEvent(mx, my, mx - oldmx, my - oldmy));
				}
			}));
		}
	}

	public static void update() {
		if (Main.getScreen() instanceof OpenGLScreen) {
			for (int i = 0; i < BUTTON_COUNT; i++) {
				boolean bd = (GLFW.glfwGetMouseButton(((OpenGLScreen) Main.getScreen()).getWindowID(), i) == GLFW.GLFW_PRESS);
				if (bd != buttonStates[i]) {
					EventHub.pushEvent(new MouseButtonEvent(i, bd));
					buttonStates[i] = bd;
				}
			}
		}
	}
}
