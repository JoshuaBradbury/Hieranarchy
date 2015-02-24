package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;
import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.util.Location;

public abstract class Component {
	
	private int x, y, width, height, offsetX, offsetY;
	private boolean visible;
	
	public static final float[] VERY_LIGHT = new float[] { 0.7f, 0.7f, 0.85f }, LIGHT = new float[] { 0.6f, 0.6f, 0.75f }, DARK = new float[] { 0.4f, 0.4f, 0.55f };
	
	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = true;
	}
	
	public Component(int x, int y) {
		this.x = x;
		this.y = y;
		this.visible = true;
	}
	
	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Dimension getDimensions() {
		return new Dimension(width, height);
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Location getLocation() {
		return new Location(x, y);
	}
	
	public Location getOffset() {
		return new Location(offsetX, offsetY);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getAsRectangle() {
		return new Rectangle((int) getLocation().getX() + offsetX, (int) getLocation().getY() + offsetY, (int) getDimensions().getWidth(), (int) getDimensions().getHeight());
	}
	
	public abstract void render(Rectangle view);
	
	public abstract void update();
}
