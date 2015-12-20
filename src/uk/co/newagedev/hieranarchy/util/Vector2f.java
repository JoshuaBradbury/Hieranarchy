package uk.co.newagedev.hieranarchy.util;

public class Vector2f {
	
	private float x, y;
	
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2f(float x, float y) {
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
	
	public Vector2f add(Vector2f loc) {
		return add(loc.x, loc.y);
	}
	
	public Vector2f add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2f subtract(Vector2f loc) {
		return subtract(loc.x, loc.y);
	}
	
	public Vector2f subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2f multiply(Vector2f loc) {
		return multiply(loc.x, loc.y);
	}
	
	public Vector2f multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Vector2f divide(Vector2f loc) {
		return divide(loc.x, loc.y);
	}
	
	public Vector2f divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public float distance(Vector2f loc) {
		return distance(loc.x, loc.y);
	}
	
	public float distance(float x, float y) {
		return (float) Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
	}

	public Vector2f clone() {
		return new Vector2f(x, y);
	}
	
	public boolean equals(Vector2f loc) {
		return this.x == loc.x && this.y == loc.y;
	}
	
	public Vector2f getRelative(float x, float y) {
		return new Vector2f(this.x + x, this.y + y);
	}
	
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}
}
