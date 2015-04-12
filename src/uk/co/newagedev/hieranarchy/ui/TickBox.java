package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.input.Mouse;

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
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Screen.renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY() + 5, (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 10, Component.LIGHT);
		if (ticked) {
			Screen.renderLine(new int[] {(int) getLocation().getX() + 15, (int) getLocation().getY() + 5}, new int[] {(int) getLocation().getX() + 5, (int) getLocation().getY() + 15}, 2, Component.DARK);
			Screen.renderLine(new int[] {(int) getLocation().getX() + 5, (int) getLocation().getY() + 5}, new int[] {(int) getLocation().getX() + 15, (int) getLocation().getY() + 15}, 2, Component.DARK);
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
