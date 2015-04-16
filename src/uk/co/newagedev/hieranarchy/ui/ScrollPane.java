package uk.co.newagedev.hieranarchy.ui;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glScissor;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.input.Mouse;

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
		boolean hovering = (scrollBars[0] != null ? scrollBars[0].hoveringOverBar() : false) || (scrollBars[1] != null ? scrollBars[1].hoveringOverBar() : false);
		if (hovering || (new Rectangle((int) getLocation().getX(), (int) getLocation().getY(), getWidth(), getHeight())).contains(Mouse.getMouseX(), Mouse.getMouseY())) {
			pane.update();
		}
	}

	public void render() {
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.LIGHT);
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

		glEnable(GL_SCISSOR_TEST);
		int yOff = scrollBars[1] != null ? 15 : 0;
		glScissor((int) getLocation().getX(), (int) getLocation().getY() + yOff, getWidth(), getHeight());
		pane.render();
		glDisable(GL_SCISSOR_TEST);
	}
}