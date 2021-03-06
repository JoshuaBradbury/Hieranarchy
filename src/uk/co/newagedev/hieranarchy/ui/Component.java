package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;

import uk.co.newagedev.hieranarchy.events.EventHub;
import uk.co.newagedev.hieranarchy.events.Listener;
import uk.co.newagedev.hieranarchy.graphics.FontSheet;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public abstract class Component implements Listener {
	
	public static FontSheet componentFont = new FontSheet("segoeui", 25);
	private float x, y;
	private int width, height, offsetX, offsetY;
	private Component parent;
	
	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		parent = null;
		EventHub.registerListener(this);
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
	
	public void hide() {
		EventHub.ignoreListener(this);
	}
	
	public void show() {
		EventHub.listenTo(this);
	}
	
	public abstract void render();
	
	public abstract void update();
}
