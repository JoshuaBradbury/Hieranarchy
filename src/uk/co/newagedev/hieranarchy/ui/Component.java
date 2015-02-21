package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;

import uk.co.newagedev.hieranarchy.util.Location;

public class Component {
	
	private int x, y, width, height;
	private boolean visible;
	
	
	public Component(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = true;
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
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render() {
		
	}
	
	public void update() {
		
	}
}
