package uk.co.newagedev.hieranarchy.events.types.input;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class MouseButtonEvent extends Event {

	private int mouseButton;
	private boolean down;
	
	public MouseButtonEvent(int mouseButton, boolean down) {
		this.mouseButton = mouseButton;
		this.down = down;
	}
	
	public int getMouseButton() {
		return mouseButton;
	}
	
	public boolean isDown() {
		return down;
	}
}
