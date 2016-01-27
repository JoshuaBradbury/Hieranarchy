package uk.co.newagedev.hieranarchy.input;

import uk.co.newagedev.hieranarchy.events.EventHub;
import uk.co.newagedev.hieranarchy.events.Listener;
import uk.co.newagedev.hieranarchy.events.types.input.ControllerMoveEvent;
import uk.co.newagedev.hieranarchy.events.types.input.CursorClickEvent;
import uk.co.newagedev.hieranarchy.events.types.input.CursorMoveEvent;
import uk.co.newagedev.hieranarchy.events.types.input.MouseButtonEvent;
import uk.co.newagedev.hieranarchy.events.types.input.MouseMoveEvent;
import uk.co.newagedev.hieranarchy.main.Main;

public class Cursor implements Listener {

	private int x, y, offX, offY, updatesSinceLastMovement, updatesSinceLastPress;
	private boolean pressing, down, releasing;

	public Cursor(int x, int y) {
		this.x = x;
		this.y = y;
		EventHub.registerListener(this);
	}

	public void update() {
		if (KeyBinding.isBindingReleasing("Unhide Mouse") && Main.getScreen().isCursorHidden()) {
			Main.getScreen().hideCursor(false);
		}

		if (pressing && releasing) {
			releasing = false;
		} else if (releasing && down) {
			down = false;
		} else if (pressing && down) {
			down = false;
		} else {
			updatesSinceLastPress++;
		}
		updatesSinceLastMovement++;
	}

	public void updateCursorPosition(MouseMoveEvent event) {
		updatesSinceLastMovement = 0;
		int oldx = getX(), oldy = getY();
		x = (int) event.getX();
		y = (int) event.getY();
		EventHub.pushEvent(new CursorMoveEvent(getX(), getY(), getX() - oldx, getY() - oldy, updatesSinceLastMovement));
	}
	
	public void updateCursorPosition(ControllerMoveEvent event) {
		updatesSinceLastMovement = 0;
		x += event.getDX();
		y += event.getDY();
		EventHub.pushEvent(new CursorMoveEvent(getX(), getY(), (int) event.getDX(), (int) event.getDY(), updatesSinceLastMovement));
	}
	
	public void setOffset(int x, int y) {
		offX = x;
		offY = y;
	}
	
	public int getX() {
		return x + offX;
	}
	
	public int getY() {
		return y + offY;
	}
	
	public int getUpdatesSinceLastMovement() {
		return updatesSinceLastMovement;
	}

	public void updateCursorState(MouseButtonEvent event) {
		updatesSinceLastPress = 0;
		if (event.getMouseButton() == Mouse.BUTTON_LEFT) {
			if (!pressing && !down && event.isDown()) {
				pressing = true;
			} else if (pressing && !down && event.isDown()) {
				pressing = false;
				down = true;
			} else if (down && !releasing && !event.isDown()) {
				down = false;
				releasing = true;
			} else if (!down && releasing && !event.isDown()) {
				releasing = false;
			}
		}
		EventHub.pushEvent(new CursorClickEvent(pressing, down, releasing, getX(), getY(), updatesSinceLastPress, event.getMouseButton()));
	}
}
