package uk.co.newagedev.hieranarchy.input;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;

import uk.co.newagedev.hieranarchy.events.EventHub;
import uk.co.newagedev.hieranarchy.events.types.input.ControllerMoveEvent;
import uk.co.newagedev.hieranarchy.events.types.input.MouseButtonEvent;

public class Controller {

	private static boolean button1;
	
	public static void update() {
		for (int i = GLFW.GLFW_JOYSTICK_1; i < GLFW.GLFW_JOYSTICK_LAST; i++) {
			if (GLFW.glfwGetJoystickName(i) == null) break;
			FloatBuffer axis = GLFW.glfwGetJoystickAxes(i);
			ByteBuffer buttons = GLFW.glfwGetJoystickButtons(i);
			float x = axis.get(0), y = axis.get(1);
			x *= Math.abs(x) > 0.3 ? 1 : 0;
			y *= Math.abs(y) > 0.3 ? 1 : 0;
			EventHub.pushEvent(new ControllerMoveEvent(x * 10.0f, y * 10.0f));
			
			if (buttons.get(0) == 1 && !button1) {
				EventHub.pushEvent(new MouseButtonEvent(Mouse.BUTTON_1, true));
				button1 = true;
			} else if (buttons.get(0) == 0 && button1) {
				EventHub.pushEvent(new MouseButtonEvent(Mouse.BUTTON_1, false));
				button1 = false;
			}
		}
	}
}
