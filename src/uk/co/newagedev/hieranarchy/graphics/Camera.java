package uk.co.newagedev.hieranarchy.graphics;

public class Camera {
	
	/** The current location of the camera and the home location of the camera. */
	private int x, y, homeX, homeY;
	
	/** The zoom. */
	private float zoom = 1.0f;
	
	/**
	 * Instantiates a new camera.
	 * @param x - the x value to set the camera to.
	 * @param y - the y value to set the camera to.
	 */
	public Camera(int x, int y) {
		homeX = x;
		homeY = y;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the x value of the camera.
	 * @return x - The current value of x.
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Gets the y value of the camera.
	 * @return y - The current value of y.
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Resets the location of the camera to the home location.
	 */
	public void reset() {
		this.x = homeX;
		this.y = homeY;
	}
	
	/**
	 * Gets the current zoom of the camera.
	 * @return zoom.
	 */
	public float getZoom() {
		return zoom;
	}
	
	/**
	 * Moves the camera to the specified location.
	 * @param x - the new x value.
	 * @param y - the new y value.
	 */
	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
}
