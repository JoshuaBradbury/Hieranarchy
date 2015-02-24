package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.Logger;

public class ScrollPane extends Component {
	private ScrollBar[] scrollBars = new ScrollBar[2];
	private Container pane;
	
	public ScrollPane(int x, int y, int width, int height, int scrollBarDisplays) {
		super(x, y, width, height);
		if ((scrollBarDisplays & ScrollBar.HORIZONTAL) == 1) {
			scrollBars[0] = new ScrollBar(ScrollBar.HORIZONTAL, this);
		}
		if ((scrollBarDisplays & ScrollBar.VERTICAL) == 1) {
			scrollBars[1] = new ScrollBar(ScrollBar.VERTICAL, this);
		}
		pane = new Container(0, 0);
	}
	
	public Container getPane() {
		return pane;
	}
	
	public void update() {
		for (ScrollBar bar : scrollBars) {
			if (bar != null) {
				bar.update();
			}
		}
		pane.update();
	}
	
	public void render(Rectangle view) {
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.LIGHT);
		for (ScrollBar bar : scrollBars) {
			if (bar != null) {
				bar.render();
			}
		}
		Location location = new Location(0, 0);
		if (scrollBars[1] != null) {
			Logger.info(scrollBars[1].calculateYOffset());
			location.setY(getPane().getHeight() * scrollBars[1].calculateYOffset());
		}
		pane.setLocation((int) location.getX(), (int) location.getY());
		pane.render(getAsRectangle());
	}
}
