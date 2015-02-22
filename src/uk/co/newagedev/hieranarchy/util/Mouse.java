package uk.co.newagedev.hieranarchy.util;

import uk.co.newagedev.hieranarchy.testing.Main;

public class Mouse {
	
	public static int getMouseX() {
		return org.lwjgl.input.Mouse.getX();
	}
	
	public static int getMouseY() {
		return Main.HEIGHT - org.lwjgl.input.Mouse.getY();
	}
	
	public static boolean isMouseReleasing(int index) {
		if (org.lwjgl.input.Mouse.getEventButton() == index) {
			return org.lwjgl.input.Mouse.getEventButtonState();
		}
		return false;
	}
	
}
