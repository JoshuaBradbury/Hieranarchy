package uk.co.newagedev.hieranarchy.util;

import uk.co.newagedev.hieranarchy.testing.Main;

public class Mouse {
	
	private static boolean[] pressing = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	private static boolean[] down = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	private static boolean[] releasing = new boolean[org.lwjgl.input.Mouse.getButtonCount()];
	
	public static final int LEFT_BUTTON = 0, RIGHT_BUTTON = 1, MIDDLE_BUTTON = 2;
	private static int mx = 0, my = 0, mdx = 0, mdy = 0;
	private static long lastTimeUpdate = 0, millisSinceLastMovement = 0, millisSinceLastButton = 0;
	
	public static int getMouseX() {
		return mx;
	}
	
	public static int getMouseY() {
		return Main.HEIGHT - my;
	}
	
	public static int getChangeInMouseX() {
		return mdx;
	}
	
	public static int getChangeInMouseY() {
		return mdy;
	}
	
	public static long getMillisSinceLastMovement() {
		return millisSinceLastMovement;
	}
	
	public static long getMillisSinceLastButton() {
		return millisSinceLastButton;
	}
	
	public static void update() {
		boolean[] tpressing = pressing;
		boolean[] tdown = down;
		boolean[] treleasing = releasing;
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
		
		long currentTimeMillis = System.currentTimeMillis();
		
		if (tpressing == pressing && tdown == down && treleasing == releasing) {
			millisSinceLastButton = (int) (currentTimeMillis - lastTimeUpdate);
		} else {
			millisSinceLastButton = 0;
		}
		
		int oldmx = mx;
		int oldmy = my;
		mx = org.lwjgl.input.Mouse.getX();
		my = org.lwjgl.input.Mouse.getY();
		mdx = mx - oldmx;
		mdy = my - oldmy;
		if (mdx == 0 && mdy == 0) {
			millisSinceLastMovement = (int) (currentTimeMillis - lastTimeUpdate);
		} else {
			millisSinceLastMovement = 0;
		}
		lastTimeUpdate = currentTimeMillis;
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
