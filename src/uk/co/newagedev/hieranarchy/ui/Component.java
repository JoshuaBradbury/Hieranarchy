package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;
import java.awt.Font;

import uk.co.newagedev.hieranarchy.graphics.FontSheet;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public abstract class Component {
	
	public static FontSheet componentFont = new FontSheet(new Font("Segoe UI", Font.PLAIN, 20));
	private float x, y;
	private int width, height, offsetX, offsetY;
	private Component parent;
	
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
	
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public abstract void render();
	
	public abstract void update();
}
