package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.state.State;

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
		Main.getScreen().renderQuad(getLocation().clone().subtract(0, 30), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Colour.DARK_GREY);
		Main.getScreen().renderQuad(getLocation().clone().add(5, 0), (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 35, Colour.DARK_DARK_GREY);
		super.render();
	}

	@Override
	public void update() {
		super.update();
		if (Mouse.isButtonPressing(Mouse.LEFT_BUTTON) && !moving) {
			if (Mouse.getMouseX() > getLocation().getX() && Mouse.getMouseX() < getLocation().getX() + getDimensions().getWidth()) {
				if (Mouse.getMouseY() > getLocation().getY()  - 30 && Mouse.getMouseY() < getLocation().getY()) {
					moving = true;
				}
			}
		}
		if (moving) {
			if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON)) {
				moving = false;
			}
			if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
				for (Component component : getComponents()) {
					component.setLocation((int) (component.getLocation().getX() + Mouse.getChangeInMouseX()), (int) (component.getLocation().getY() - Mouse.getChangeInMouseY()));
				}
				setLocation((int) (getLocation().getX() + Mouse.getChangeInMouseX()), (int) (getLocation().getY() - Mouse.getChangeInMouseY()));
			}
		}
	}
}
