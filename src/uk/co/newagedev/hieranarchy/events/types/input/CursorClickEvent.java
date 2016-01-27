package uk.co.newagedev.hieranarchy.events.types.input;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class CursorClickEvent extends Event {

	private boolean pressing, down, releasing;
	private int x, y, updatesSinceLastPress, button;
	
	public CursorClickEvent(boolean pressing, boolean down, boolean releasing, int x, int y, int updatesSinceLastPress, int button) {
		this.pressing = pressing;
		this.down = down;
		this.releasing = releasing;
		this.x = x;
		this.y = y;
		this.button = button;
		this.updatesSinceLastPress = updatesSinceLastPress;
	}

	public int getButton() {
		return button;
	}
	
	public int getUpdatesSinceLastPress() {
		return updatesSinceLastPress;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isPressing() {
		return pressing;
	}
	
	public boolean isDown() {
		return down;
	}
	
	public boolean isReleasing() {
		return releasing;
	}

	public boolean isButtonPressing(int button) {
		return pressing && this.button == button;
	}
	
	public boolean isButtonDown(int button) {
		return down && this.button == button;
	}
	
	public boolean isButtonReleasing(int button) {
		return releasing && this.button == button;
	}
}
