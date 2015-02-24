package uk.co.newagedev.hieranarchy.ui;

import org.lwjgl.util.Rectangle;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.Mouse;

public class ScrollBar {
	private ScrollPane parent;
	private int display, oldmy, x, y, width, maxWidth, height, maxHeight;
	private boolean barHover = false, topHover = false, bottomHover = false;

	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;

	public ScrollBar(int display, ScrollPane parent) {
		this.display = display;
		this.parent = parent;
		y = 0;
		x = 0;
		maxHeight = (int) parent.getDimensions().getHeight() - 30;
		height = 0;
		maxWidth = (int) parent.getDimensions().getWidth() - 30;
		width = 13;
	}

	public void update() {
		height = (int) (((parent.getDimensions().getHeight() - 15) / parent.getPane().getDimensions().getHeight()) * maxHeight);
		if (barHover) {
			if (Mouse.isMousePressing(Mouse.LEFT_BUTTON)) {
				oldmy = Mouse.getMouseY();
			}
			if (Mouse.isMouseDown(Mouse.LEFT_BUTTON)) {
				y += (Mouse.getMouseY() - oldmy);
				oldmy = Mouse.getMouseY();
			}
		}
		if (!Mouse.isMouseDown(Mouse.LEFT_BUTTON)) {
			barHover = getBar().contains(Mouse.getMouseX(), Mouse.getMouseY());
		}
		topHover = (new Rectangle((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 13, (int) (parent.getLocation().getY()) + 2, 11, 11)).contains(Mouse.getMouseX(), Mouse.getMouseY());
		bottomHover = (new Rectangle((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 13, (int) (parent.getLocation().getY()) + maxHeight + 17, 11, 11)).contains(Mouse.getMouseX(), Mouse.getMouseY());
		if (Mouse.isMouseDown(Mouse.LEFT_BUTTON)) {
			if (topHover) {
				y -= 3;
			}
			if (bottomHover) {
				y += 3;
			}
		}
		if (y < 0) {
			y = 0;
		}
		if (y + height > maxHeight) {
			y = maxHeight - height;
		}
	}
	
	public Rectangle getBar() {
		return new Rectangle((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 13, (int) (parent.getLocation().getY()) + y + 15, width, height);
	}
	
	public float calculateYOffset() {
		return (float) y / (float) (parent.getPane().getHeight() - parent.getDimensions().getHeight());
	}

	public void render() {
		if (display == ScrollBar.VERTICAL) {
			Screen.renderQuad((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 15, (int) parent.getLocation().getY(), 15, (int) parent.getDimensions().getHeight(), Component.VERY_LIGHT);
			Screen.renderLine(new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 7, y + (int) parent.getLocation().getY() + 15 }, new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 7, (int) parent.getLocation().getY() + 15 + y + height }, 11.0f, barHover ? Component.DARK : Component.LIGHT);
			Screen.renderQuad((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 13, (int) (parent.getLocation().getY()) + 2, 11, 11, topHover ? Component.DARK : Component.LIGHT);
			Screen.renderLine(new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 7, (int) (parent.getLocation().getY()) + 4 }, new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 3, (int) (parent.getLocation().getY()) + 12 }, 2, Component.DARK);
			Screen.renderLine(new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 7, (int) (parent.getLocation().getY()) + 4 }, new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 11, (int) (parent.getLocation().getY()) + 12 }, 2, Component.DARK);
			Screen.renderQuad((int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 13, (int) (parent.getLocation().getY()) + maxHeight + 17, 11, 11, bottomHover ? Component.DARK : Component.LIGHT);
			Screen.renderLine(new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 3, (int) (parent.getLocation().getY() + parent.getDimensions().getHeight()) - 12 }, new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 7, (int) (parent.getLocation().getY() + parent.getDimensions().getHeight()) - 4 }, 2, Component.DARK);
			Screen.renderLine(new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 11, (int) (parent.getLocation().getY() + parent.getDimensions().getHeight()) - 12 }, new int[] { (int) (parent.getLocation().getX() + parent.getDimensions().getWidth()) - 7, (int) (parent.getLocation().getY() + parent.getDimensions().getHeight()) - 4 }, 2, Component.DARK);
		}
	}
}
