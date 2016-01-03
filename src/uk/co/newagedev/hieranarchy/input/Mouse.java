package uk.co.newagedev.hieranarchy.input;

import uk.co.newagedev.hieranarchy.main.Main;

public class Mouse {
	
	private static boolean[] pressing = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	private static boolean[] down = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	private static boolean[] releasing = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	
	public static final int LEFT_BUTTON = 0, RIGHT_BUTTON = 1, MIDDLE_BUTTON = 2;
	private static int mx = 0, my = 0, mdx = 0, mdy = 0, mdw = 0, updatesSinceLastMovement = 0, updatesSinceLastButton = 0;
	
	public static int getMouseX() {
		return mx;
	}
	
	public static int getMouseY() {
		return Main.HEIGHT - my;
	}
	
	public static void simulateLocation(int x, int y) {
		mx = x;
		my = y;
	}
	
	public static int getChangeInMouseX() {
		return mdx;
	}
	
	public static int getChangeInMouseY() {
		return mdy;
	}
	
	public static int getChangeInMouseWheel() {
		return mdw;
	}
	
	public static long getMillisSinceLastMovement() {
		return updatesSinceLastMovement;
	}
	
	public static long getMillisSinceLastButton() {
		return updatesSinceLastButton;
	}
	
	public static void update() {
		mdw = org.lwjgl.input.Mouse.getDWheel();
		int t = 0;
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
			} else {
				t += 1;
			}
			
			if (pressing[i] && releasing[i]) {
				releasing[i] = false;
			}
			if(releasing[i] && down[i]) {
				down[i] = false;
			}
			if (pressing[i] && down[i]) {
				down[i] = false;
			}
		}
		
		if (t == org.lwjgl.input.Mouse.getButtonCount()) {
			updatesSinceLastButton += 1;
		} else {
			updatesSinceLastButton = 0;
		}
		
		int oldmx = mx;
		int oldmy = my;
		mx = org.lwjgl.input.Mouse.getX();
		my = org.lwjgl.input.Mouse.getY();
		mdx = mx - oldmx;
		mdy = my - oldmy;
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
