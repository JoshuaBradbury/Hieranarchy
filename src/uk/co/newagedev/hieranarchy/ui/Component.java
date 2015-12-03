package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;

import uk.co.newagedev.hieranarchy.graphics.Font;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public abstract class Component {
	
	public static Font componentFont = new Font("Projects/testing/Assets/Textures/font.png", 10, 2);
	private int x, y, width, height, offsetX, offsetY;
	private Component parent;
	
	public static final float[] VERY_LIGHT = new float[] { 0.7f, 0.7f, 0.85f }, LIGHT = new float[] { 0.6f, 0.6f, 0.75f }, DARK = new float[] { 0.4f, 0.4f, 0.55f }, DARK_ALPHA = new float[] { 0.2f, 0.2f, 0.35f, 0.75f };
	
	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		parent = null;
	}
	
	public void setParent(Component parent) {
		this.parent = parent;
	}
	
	public Component getParent() {
		return parent;
	}
	
	public Component(int x, int y) {
		this.x = x;
		this.y = y;
		parent = null;
	}
	
	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
	
	public Dimension getDimensions() {
		return new Dimension(width, height);
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Vector2f getLocation() {
		return new Vector2f(x, y);
	}
	
	public Vector2f getDisplayLocation() {
		return getLocation().add(getOffset());
	}
	
	public Vector2f getOffset() {
		return new Vector2f(offsetX, offsetY);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render();
	
	public abstract void update();
}
