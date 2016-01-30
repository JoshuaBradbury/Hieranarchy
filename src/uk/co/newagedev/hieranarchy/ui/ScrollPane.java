package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.events.types.input.CursorMoveEvent;
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
	
	}
	
	public void cursorMove(CursorMoveEvent event) {
		int mx = event.getX(), my = event.getY();
		boolean hovering = (scrollBars[0] != null ? (my > getDisplayLocation().getY() + getHeight() && my < getDisplayLocation().getY() + getHeight() + 15) || scrollBars[0].isHeld() : false) || (scrollBars[1] != null ? (mx > getDisplayLocation().getX() + getWidth() && mx < getDisplayLocation().getX() + getWidth() + 15) || scrollBars[1].isHeld() : false);
		if ((new Rectangle((int) getDisplayLocation().getX(), (int) getDisplayLocation().getY(), getWidth(), getHeight())).contains(event.getX(), event.getY())) {
			pane.update();
		} else if (hovering) {
			Main.getCursor().setOffset(-mx - 1, -my - 1);
			pane.update();
			Main.getCursor().setOffset(0, 0);
		}
	}

	public void render() {
		Main.getScreen().renderQuad(new Rectangle((int) getDisplayLocation().getX(), (int) getDisplayLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight()), Colour.LIGHT_LIGHT_GREY);
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