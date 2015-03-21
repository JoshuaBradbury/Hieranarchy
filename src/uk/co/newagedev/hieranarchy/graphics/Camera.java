package uk.co.newagedev.hieranarchy.graphics;

public class Camera {
	private int x, y, homeX, homeY;
	private float zoom = 1.0f;
	
	public Camera(int x, int y) {
		homeX = x;
		homeY = y;
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void reset() {
		this.x = homeX;
		this.y = homeY;
	}
	
	public float getZoom() {
		return zoom;
	}
	
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	public Camera(int x, int y, float zoom) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
	}
}
