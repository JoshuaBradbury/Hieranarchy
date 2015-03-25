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
		return add(loc.x, loc.y);
	}
	
	public Location add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Location subtract(Location loc) {
		return subtract(loc.x, loc.y);
	}
	
	public Location subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Location multiply(Location loc) {
		return multiply(loc.x, loc.y);
	}
	
	public Location multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Location divide(Location loc) {
		return divide(loc.x, loc.y);
	}
	
	public Location divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public float distance(Location loc) {
		return distance(loc.x, loc.y);
	}
	
	public float distance(float x, float y) {
		float xDiff = Math.abs(x - this.x);
		float yDiff = Math.abs(y - this.y);
		return (float) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
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
