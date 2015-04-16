package uk.co.newagedev.hieranarchy.ui;

import org.lwjgl.util.Rectangle;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.input.Mouse;

public class ScrollBar {
	private ScrollPane parent;
	private int display, x, y, startY, startX, startDragX, startDragY, width, maxWidth, height, maxHeight;
	private boolean barHover = false, topHover = false, bottomHover = false, leftHover = false, rightHover = false;

	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;

	public ScrollBar(int display, ScrollPane parent) {
		this.display = display;
		this.parent = parent;
		y = 0;
		x = 0;
		maxHeight = (int) getParentHeight() - 30;
		height = 0;
		maxWidth = (int) getParentWidth() - 30;
		width = 0;
	}
	
	private int getParentHeight() {
		int offset = 0;
		if (display == ScrollBar.VERTICAL)
			offset = 15;
		return (int) parent.getDimensions().getHeight() - offset;
	}
	
	private int getParentWidth() {
		int offset = 0;
		if (display == ScrollBar.HORIZONTAL)
			offset = 15;
		return (int) parent.getDimensions().getWidth() - offset;
	}

	public boolean hoveringOverBar() {
		return barHover;
	}
	
	public void update() {
		if (display == ScrollBar.VERTICAL) {
			int paneHeight = (int) (parent.getPane().getDimensions().getHeight() > getParentHeight() ? parent.getPane().getDimensions().getHeight() : getParentHeight());
			height = (int) (((float) getParentHeight() / (float) paneHeight) * (float) maxHeight);
			if (barHover) {
				if (Mouse.isButtonPressing(Mouse.LEFT_BUTTON)) {
					startY = y;
					startDragY = Mouse.getMouseY();
				}
				if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
					y = startY + (Mouse.getMouseY() - startDragY);
				}
			}
			if (!Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
				barHover = getBar().contains(Mouse.getMouseX(), Mouse.getMouseY());
			}
			topHover = (new Rectangle((int) (parent.getLocation().getX() + getParentWidth()) - 13, (int) (parent.getLocation().getY()) + 2, 11, 11)).contains(Mouse.getMouseX(), Mouse.getMouseY());
			bottomHover = (new Rectangle((int) (parent.getLocation().getX() + getParentWidth()) - 13, (int) (parent.getLocation().getY()) + maxHeight + 17, 11, 11)).contains(Mouse.getMouseX(), Mouse.getMouseY());
			if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
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
		} else if (display == ScrollBar.HORIZONTAL) {
			int paneWidth = (int) (parent.getPane().getDimensions().getWidth() > getParentWidth() ? parent.getPane().getDimensions().getWidth() : getParentWidth());
			width = (int) (((float) getParentWidth() / (float) paneWidth) * (float) maxWidth);
			if (barHover) {
				if (Mouse.isButtonPressing(Mouse.LEFT_BUTTON)) {
					startX = x;
					startDragX = Mouse.getMouseX();
				}
				if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
					x = startX + (Mouse.getMouseX() - startDragX);
				}
			}
			if (!Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
				barHover = getBar().contains(Mouse.getMouseX(), Mouse.getMouseY());
			}
			leftHover = (new Rectangle((int) parent.getLocation().getX() + 2, (int) (parent.getLocation().getY() + getParentHeight()) + 2, 11, 11)).contains(Mouse.getMouseX(), Mouse.getMouseY());
			rightHover = (new Rectangle((int) (parent.getLocation().getX()) + 2, (int) (parent.getLocation().getY() + getParentHeight()) - 2, 11, 11)).contains(Mouse.getMouseX(), Mouse.getMouseY());
			if (Mouse.isButtonDown(Mouse.LEFT_BUTTON)) {
				if (leftHover) {
					x -= 3;
				}
				if (rightHover) {
					x += 3;
				}
			}
			if (x < 0) {
				x = 0;
			}
			if (x + width > maxWidth) {
				x = maxWidth - width;
			}
		}
	}

	public Rectangle getBar() {
		if (display == ScrollBar.HORIZONTAL) {
			return new Rectangle((int) (parent.getLocation().getX()) + 15 + x, y + (int) (parent.getLocation().getY() + getParentHeight()) - 13, width, 11);
		} else if (display == ScrollBar.VERTICAL) {
			return new Rectangle((int) (parent.getLocation().getX() + getParentWidth()) - 13, y + (int) parent.getLocation().getY() + 15, 11, height);
		}
		return null;
	}

	public float calculateYOffset() {
		return ((float) y / (float) (maxHeight - height)) * (float) getParentHeight();
	}

	public float calculateXOffset() {
		return ((float) x / (float) (maxWidth - width)) * (float) getParentWidth();
	}

	public void render() {
		if (display == ScrollBar.VERTICAL) {
			Screen.renderQuad((int) (parent.getLocation().getX() + getParentWidth()) - 15, (int) parent.getLocation().getY(), 15, (int) getParentHeight(), Component.VERY_LIGHT);
			Screen.renderQuad((int) (parent.getLocation().getX() + getParentWidth()) - 13, y + (int) parent.getLocation().getY() + 15, 11, height, barHover ? Component.DARK : Component.LIGHT);
			Screen.renderSprite(topHover ? "arrow hover" : "arrow", (float) (parent.getLocation().getX() + getParentWidth()) - 13, parent.getLocation().getY() + 2, 11.0f, 11.0f, new float[] { 0.0f, 0.0f, 0.0f });
			Screen.renderSprite(bottomHover ? "arrow hover" : "arrow", (float) (parent.getLocation().getX() + getParentWidth()) - 2, parent.getLocation().getY() + maxHeight + 28, 11.0f, 11.0f, new float[] { 0.0f, 0.0f, 180.0f });
		} else if (display == ScrollBar.HORIZONTAL) {
			Screen.renderQuad((int) parent.getLocation().getX(), (int) (parent.getLocation().getY() + getParentHeight()) - 15, (int) getParentWidth(), 15, Component.VERY_LIGHT);
			Screen.renderQuad((int) (parent.getLocation().getX()) + 15 + x, y + (int) (parent.getLocation().getY() + getParentHeight()) - 13, width, 11, barHover ? Component.DARK : Component.LIGHT);
			Screen.renderSprite(rightHover ? "arrow hover" : "arrow", (int) (parent.getLocation().getX() + getParentWidth()) - 2, (int) (parent.getLocation().getY() + getParentHeight()) -13, 11, 11, new float[] { 0.0f, 0.0f, 90.0f });
			Screen.renderSprite(leftHover ? "arrow hover" : "arrow", (int) (parent.getLocation().getX()) + 2, (int) (parent.getLocation().getY() + getParentHeight()) - 2, 11, 11, new float[] { 0.0f, 0.0f, 270.0f });
		}
	}
}