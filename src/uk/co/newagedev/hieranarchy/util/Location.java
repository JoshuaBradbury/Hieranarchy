package uk.co.newagedev.hieranarchy.util;

public class Location {
	
	private float x, y;
	
	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public Location add(Location loc) {
		x += loc.x;
		y += loc.y;
		return this;
	}
	
	public Location add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Location subtract(Location loc) {
		x -= loc.x;
		y -= loc.y;
		return this;
	}
	
	public Location subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Location multiply(Location loc) {
		x *= loc.x;
		y *= loc.y;
		return this;
	}
	
	public Location multiply(int x, int y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Location divide(Location loc) {
		x /= loc.x;
		y /= loc.y;
		return this;
	}
	
	public Location divide(int x, int y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public Location clone() {
		return new Location(x, y);
	}
	
	public boolean equals(Location loc) {
		return this.x == loc.x && this.y == loc.y;
	}
	
	public Location getRelative(float x, float y) {
		return new Location(this.x + x, this.y + y);
	}
	
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}
}
