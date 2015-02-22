package uk.co.newagedev.hieranarchy.util;

import uk.co.newagedev.hieranarchy.testing.Main;

public class Mouse {
	
	private static boolean[] pressing = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	private static boolean[] down = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	private static boolean[] releasing = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	
	public static final int LEFT_BUTTON = 0, RIGHT_BUTTON = 1, MIDDLE_BUTTON = 2;
	
	public static int getMouseX() {
		return org.lwjgl.input.Mouse.getX();
	}
	
	public static int getMouseY() {
		return Main.HEIGHT - org.lwjgl.input.Mouse.getY();
	}
	
	public static void update() {
		for (int i = 0; i < org.lwjgl.input.Mouse.getButtonCount(); i++) {
			boolean bd = org.lwjgl.input.Mouse.isButtonDown(i);
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
			}
			
		}
	}
	
	public static boolean isMousePressing(int index) {
		return pressing[index];
	}
	
	public static boolean isMouseDown(int index) {
		return down[index];
	}
	
	public static boolean isMouseReleasing(int index) {
		return releasing[index];
	}
}
