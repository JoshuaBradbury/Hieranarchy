package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class TickBox extends Component {

	private boolean ticked = false, hover = false;
	
	public TickBox(int x, int y, boolean ticked) {
		super(x, y, 20, 20);
		this.ticked = ticked;
	}

	public boolean isTicked() {
		return ticked;
	}
	
	public void setTicked(boolean ticked) {
		this.ticked = ticked;
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(getLocation(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Main.getScreen().renderQuad(getLocation().clone().add(1, 1), (int) getDimensions().getWidth() - 2, (int) getDimensions().getHeight() - 2, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		if (ticked) {
			Main.getScreen().renderLine(new Vector2f((int) getLocation().getX() + 15, (int) getLocation().getY() + 5), new Vector2f((int) getLocation().getX() + 5, (int) getLocation().getY() + 15), 2, Component.DARK);
			Main.getScreen().renderLine(new Vector2f((int) getLocation().getX() + 5, (int) getLocation().getY() + 5), new Vector2f((int) getLocation().getX() + 15, (int) getLocation().getY() + 15), 2, Component.DARK);
		}
	}

	@Override
	public void update() {
		hover = false;
		if (Mouse.getMouseX() > getLocation().getX() && Mouse.getMouseX() < getLocation().getX() + getDimensions().getWidth()) {
			if (Mouse.getMouseY() > getLocation().getY() && Mouse.getMouseY() < getLocation().getY() + getDimensions().getHeight()) {
				hover = true;
			}
		}
		if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON)) {
			if (hover) {
				ticked = !ticked;
			}
		}
	}

}
