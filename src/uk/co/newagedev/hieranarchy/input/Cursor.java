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
	private boolean pressing, down, releasing, mouseDown;

	public Cursor(int x, int y) {
		this.x = x;
		this.y = y;
		EventHub.registerListener(this);
	}

	public void update() {
		if (KeyBinding.isBindingReleasing("Unhide Mouse") && Main.getScreen().isCursorHidden()) {
			Main.getScreen().hideCursor(false);
		}

		if (mouseDown && !pressing && !down) {
			pressing = true;
			down = false;
			releasing = false;
		} else if (mouseDown && pressing) {
			pressing = false;
			down = true;
		} else if (!mouseDown && (pressing || down)) {
			pressing = false;
			down = false;
			releasing = true;
		} else if (!mouseDown && releasing) {
			releasing = false;
		}
		
		if (updatesSinceLastPress == 0) {
			EventHub.pushEvent(new CursorClickEvent(pressing, down, releasing, getX(), getY(), updatesSinceLastPress, Mouse.BUTTON_1));
		}
		
		if (!pressing && !down && !releasing) {
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
			mouseDown = event.isDown();
		}
	}
}
