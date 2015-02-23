package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;

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
		super.update();
		for (ScrollBar bar : scrollBars) {
			if (bar != null) {
				bar.update();
			}
		}
		pane.update();
	}
	
	public void render() {
		super.render();
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.LIGHT);
		for (ScrollBar bar : scrollBars) {
			if (bar != null) {
				bar.render();
			}
		}
		pane.render();
	}
}
