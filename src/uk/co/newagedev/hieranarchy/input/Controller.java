package uk.co.newagedev.hieranarchy.input;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;

public class Controller {
	
	public static void update() {
		for (int i = GLFW.GLFW_JOYSTICK_1; i < GLFW.GLFW_JOYSTICK_LAST; i++) {
			if (GLFW.glfwGetJoystickName(i) == null) break;
			FloatBuffer axis = GLFW.glfwGetJoystickAxes(i);
			ByteBuffer buttons = GLFW.glfwGetJoystickButtons(i);
			float x = axis.get(0), y = axis.get(1);
			x *= Math.abs(x) > 0.3 ? 1 : 0;
			y *= Math.abs(y) > 0.3 ? 1 : 0;
			Mouse.simulateLocation(Mouse.getMouseX() + (int) (x * 10.0f), Mouse.getMouseY() + (int) (y * 10.0f));
			
			if (buttons.get(0) == 1) {
				Mouse.simulatePress(Mouse.BUTTON_1);
			}
		}
	}
}
