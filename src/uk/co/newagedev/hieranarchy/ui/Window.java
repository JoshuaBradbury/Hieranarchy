package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.events.types.input.CursorClickEvent;
import uk.co.newagedev.hieranarchy.events.types.input.CursorMoveEvent;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.util.Colour;

public class Window extends Container {

	private boolean moving = false;

	public Window(State state, int x, int y, int width, int height) {
		super(x, y, width, height);
		Button closeButton = new Button("X", width - 30, -25, 20, 20, false, new ButtonRunnable() {
			public void run() {
				state.removeWindow((Window) button.getParent());
			}
		});
		addComponent(closeButton);
	}

	public void addComponent(Component component) {
		component.setLocation((int) (component.getLocation().getX() + getLocation().getX()), (int) (component.getLocation().getY() + getLocation().getY()));
		super.addComponent(component);
	}

	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle((int) getLocation().getX(), (int) getLocation().getY() - 30, (int) getDimensions().getWidth(), (int) getDimensions().getHeight()), Colour.DARK_GREY);
		Main.getScreen().renderQuad(new Rectangle((int) getLocation().getX() + 5, (int) getLocation().getY(), (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 35), Colour.DARK_DARK_GREY);
		super.render();
	}

	@Override
	public void update() {
		super.update();
	}

	public void cursorClick(CursorClickEvent event) {
		if (event.isButtonPressing(Mouse.BUTTON_LEFT) && !moving) {
			if (event.getX() > getLocation().getX() && event.getX() < getLocation().getX() + getDimensions().getWidth()) {
				if (event.getY() > getLocation().getY() - 30 && event.getY() < getLocation().getY()) {
					moving = true;
				}
			}
		}
		if (moving) {
			if (event.isButtonReleasing(Mouse.BUTTON_LEFT)) {
				moving = false;
			}
		}
	}

	public void cursorMove(CursorMoveEvent event) {
		if (moving) {
			for (Component component : getComponents()) {
				component.setLocation((int) (component.getLocation().getX() + event.getDX()), (int) (component.getLocation().getY() - event.getDY()));
			}
			setLocation((int) (getLocation().getX() + event.getDX()), (int) (getLocation().getY() - event.getDY()));
		}
	}
}
