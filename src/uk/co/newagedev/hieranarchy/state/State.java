package uk.co.newagedev.hieranarchy.state;

import java.util.HashMap;
import java.util.Map;

import uk.co.newagedev.hieranarchy.graphics.Camera;

public abstract class State {
	private Map<String, Camera> cameras = new HashMap<String, Camera>();
	private Camera currentCamera;
	
	public void registerCamera(String name, Camera camera) {
		cameras.put(name, camera);
	}
	
	public void removeCamera(String name) {
		cameras.remove(name);
	}
	
	public Camera getCamera(String name) {
		return cameras.get(name);
	}
	
	public void switchCamera(String name) {
		Camera tempCamera = getCamera(name);
		currentCamera = tempCamera;
	}
	
	public Camera getCurrentCamera() {
		return currentCamera;
	}
	
	public abstract void render();
	
	public abstract void update();
}
