package uk.co.newagedev.hieranarchy.events.types.screen;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class ScreenResizeEvent extends Event {

	private int width, height;

	public ScreenResizeEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
