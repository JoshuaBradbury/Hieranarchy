package uk.co.newagedev.hieranarchy.graphics;

public class Camera {
	private int x, y;
	private float zoom = 1.0f;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
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
