package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.input.Mouse;

public class Window extends Container {

	private boolean moving = false;
	
	public Window(int x, int y, int width, int height) {
		super(x, y, width, height);
		Button closeButton = new Button("X", x + width - 30, y - 25, 20, 20, false, new ButtonRunnable() {
			public void run() {
				((Container) getParent()).removeComponent(button.getParent());
			}
		});
		addComponent(closeButton);
	}

	@Override
	public void render() {
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY() - 30, (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Screen.renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY(), (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 35, Component.LIGHT);
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
