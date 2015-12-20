package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;

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
		int mx = Mouse.getMouseX(), my = Mouse.getMouseY();
		boolean hovering = (scrollBars[0] != null ? (my > getDisplayLocation().getY() + getHeight() - 15 && my < getDisplayLocation().getY() + getHeight()) || scrollBars[0].isHeld() : false) || (scrollBars[1] != null ? (mx > getDisplayLocation().getX() + getWidth() - 15 && mx < getDisplayLocation().getX() + getWidth()) || scrollBars[1].isHeld() : false);
		if ((new Rectangle((int) getLocation().getX(), (int) getLocation().getY(), getWidth(), getHeight())).contains(Mouse.getMouseX(), Mouse.getMouseY())) {
			pane.update();
		} else if (hovering) {
			Mouse.simulateLocation(-1, -1);
			pane.update();
			Mouse.simulateLocation(mx, my);
		}
	}

	public void render() {
		Main.getScreen().renderQuad(getLocation(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.LIGHT);
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
		Main.getScreen().startScissor(getLocation().clone().add(0, yOff), getWidth(), getHeight());
		pane.render();
		Main.getScreen().stopScissor();
	}
}