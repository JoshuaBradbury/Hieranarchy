package uk.co.newagedev.hieranarchy.events.types.input;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class CursorMoveEvent extends Event {

	private int cx, cy, dx, dy, updatesSinceLastMovement;
	
	public CursorMoveEvent(int cx, int cy, int dx, int dy, int updatesSinceLastMovement) {
		this.cx = cx;
		this.cy = cy;
		this.updatesSinceLastMovement = updatesSinceLastMovement;
	}

	public int getUpdatesSinceLastMovement() {
		return updatesSinceLastMovement;
	}
	
	public int getDX() {
		return dx;
	}
	
	public int getDY() {
		return dy;
	}
	
	public int getX() {
		return cx;
	}

	public int getY() {
		return cy;
	}
}
