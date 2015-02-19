package uk.co.newagedev.hieranarchy.graphics;

import uk.co.newagedev.hieranarchy.util.Location;

public class Camera {
	private Location location;
	private float zoom = 1.0f;
	
	public Camera(Location loc) {
		location = loc;
	}
	
	public float getX() {
		return location.getX();
	}
	
	public float getY() {
		return location.getY();
	}
	
	public float getZoom() {
		return zoom;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Camera(Location loc, float zoom) {
		location = loc;
		this.zoom = zoom;
	}
}
