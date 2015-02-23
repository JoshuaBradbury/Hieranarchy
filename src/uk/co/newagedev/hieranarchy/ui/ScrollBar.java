package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;

public class ScrollBar {
	private ScrollPane parent;
	private int display;
	private int y, height;
	
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;
	
	public ScrollBar(int display, ScrollPane parent) {
		this.display = display;
		this.parent = parent;
		y = 0;
		height = (int) parent.getDimensions().getHeight() - 4;
	}
	
	public void update() {
		
	}
	
	public void render() {
		if (display == ScrollBar.VERTICAL) {
			Screen.renderQuad((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 10, (int) parent.getLocation().getY(), 10, (int) parent.getDimensions().getHeight(), Component.VERY_LIGHT);
			Screen.renderLine(new int[] {(int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 5, y + (int) parent.getLocation().getY() + 2}, new int[]{(int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 5, (int) parent.getLocation().getY() + 2 + y + height}, 6.0f, Component.DARK);
		}
	}
}
