package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

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

	public void render(Rectangle view) {
		Rectangle rect = view.intersection(getAsRectangle());
		Screen.renderQuad((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), Component.DARK);
		Screen.renderQuad((int) rect.getX() + 5, (int) rect.getY() + 5, (int) rect.getWidth() - 10, (int) rect.getHeight() - 10, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		Screen.renderText(text, (int) (rect.getX() + (rect.getWidth() / 2)), (int) (rect.getY() + (rect.getHeight() / 2)));
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
