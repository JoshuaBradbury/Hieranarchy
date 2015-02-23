package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.util.Mouse;

public class Button extends Component {

	private String text;
	private boolean hover = false;
	private Runnable task;
	
	public Button(String text, int x, int y, int width, int height, Runnable task) {
		super(x, y, width, height);
		this.text = text;
		this.task = task;
	}

	public void render() {
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Screen.renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY() + 5, (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 10, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		Screen.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
	}
	
	public void update() {
		hover = false;
		if (Mouse.getMouseX() > getLocation().getX() && Mouse.getMouseX() < getLocation().getX() + getDimensions().getWidth()) {
			if (Mouse.getMouseY() > getLocation().getY() && Mouse.getMouseY() < getLocation().getY() + getDimensions().getHeight()) {
				hover = true;
			}
		}
		if (Mouse.isMouseReleasing(Mouse.LEFT_BUTTON)) {
			if (hover) {
				task.run();
			}
		}
	}
}
