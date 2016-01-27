package uk.co.newagedev.hieranarchy.events.types.input;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class ControllerMoveEvent extends Event {

	private float dx, dy;

	public ControllerMoveEvent(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return dy;
	}
}
