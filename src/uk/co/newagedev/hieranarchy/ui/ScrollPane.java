package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.Colour;

public class ScrollPane extends Component {
	private ScrollBar[] scrollBars = new ScrollBar[2];
	private Container pane;

	public ScrollPane(int x, int y, int width, int height, int scrollBarDisplays) {
		super(x, y, width, height);
		if ((scrollBarDisplays & ScrollBar.HORIZONTAL) == ScrollBar.HORIZONTAL) {
			scrollBars[0] = new ScrollBar(ScrollBar.HORIZONTAL, this);
		}
		if ((scrollBarDisplays & ScrollBar.VERTICAL) == ScrollBar.VERTICAL) {
			scrollBars[1] = new ScrollBar(ScrollBar.VERTICAL, this);
		}
		pane = new Container(0, 0);
	}

	public Container getPane() {
		return pane;
	}

	public int getHeight() {
		return (int) getDimensions().getHeight() - (scrollBars[1] != null ? 15 : 0);
	}

	public int getWidth() {
		return (int) getDimensions().getWidth() - (scrollBars[0] != null ? 15 : 0);
	}

	public void update() {
		for (ScrollBar bar : scrollBars) {
			if (bar != null) {
				bar.update();
			}
		}
		int mx = (int) Mouse.getCursorX(), my = (int) Mouse.getCursorY();
		boolean hovering = (scrollBars[0] != null ? (my > getDisplayLocation().getY() + getHeight() && my < getDisplayLocation().getY() + getHeight() + 15) || scrollBars[0].isHeld() : false) || (scrollBars[1] != null ? (mx > getDisplayLocation().getX() + getWidth() && mx < getDisplayLocation().getX() + getWidth() + 15) || scrollBars[1].isHeld() : false);
		if ((new Rectangle((int) getDisplayLocation().getX(), (int) getDisplayLocation().getY(), getWidth(), getHeight())).contains(Mouse.getCursorX(), Mouse.getCursorY())) {
			pane.update();
		} else if (hovering) {
			Mouse.simulateLocation(-1, -1);
			pane.update();
			Mouse.simulateLocation(mx, my);
		}
	}

	public void render() {
		Main.getScreen().renderQuad(getDisplayLocation(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Colour.LIGHT_LIGHT_GREY);
		for (ScrollBar bar : scrollBars) {
			if (bar != null) {
				bar.render();
			}
		}

		int xOffset = 0, yOffset = 0;

		if (scrollBars[0] != null) {
			xOffset = (int) -scrollBars[0].calculateXOffset();
		}
		if (scrollBars[1] != null) {
			yOffset = (int) -scrollBars[1].calculateYOffset();
		}

		pane.setOffset(xOffset, yOffset);

		int yOff = scrollBars[1] != null ? 15 : 0;
		Main.getScreen().startScissor(getDisplayLocation().clone().add(0, yOff), getWidth(), getHeight());
		pane.render();
		Main.getScreen().stopScissor();
	}
}