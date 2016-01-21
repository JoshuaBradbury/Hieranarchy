package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.Colour;
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
		Main.getScreen().renderQuad(getDisplayLocation(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Colour.DARK_GREY);
		Main.getScreen().renderQuad(getDisplayLocation().clone().add(1, 1), (int) getDimensions().getWidth() - 2, (int) getDimensions().getHeight() - 2, (hover ? Colour.LIGHT_GREY : Colour.GREY));
		if (ticked) {
			Main.getScreen().renderLine(new Vector2f((int) getDisplayLocation().getX() + 15, (int) getDisplayLocation().getY() + 5), new Vector2f((int) getDisplayLocation().getX() + 5, (int) getDisplayLocation().getY() + 15), 2, Colour.DARK_DARK_GREY);
			Main.getScreen().renderLine(new Vector2f((int) getDisplayLocation().getX() + 5, (int) getDisplayLocation().getY() + 5), new Vector2f((int) getDisplayLocation().getX() + 15, (int) getDisplayLocation().getY() + 15), 2, Colour.DARK_DARK_GREY);
		}
	}

	@Override
	public void update() {
		hover = false;
		if (Mouse.getCursorX() > getDisplayLocation().getX() && Mouse.getCursorX() < getDisplayLocation().getX() + getDimensions().getWidth()) {
			if (Mouse.getCursorY() > getDisplayLocation().getY() && Mouse.getCursorY() < getDisplayLocation().getY() + getDimensions().getHeight()) {
				hover = true;
			}
		}
		if (Mouse.isButtonReleasing(Mouse.BUTTON_LEFT)) {
			if (hover) {
				ticked = !ticked;
			}
		}
	}

}
