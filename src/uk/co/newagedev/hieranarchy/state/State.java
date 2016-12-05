package uk.co.newagedev.hieranarchy.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.newagedev.hieranarchy.events.EventHub;
import uk.co.newagedev.hieranarchy.events.Listener;
import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.ui.Window;

public abstract class State implements Listener {
	private Map<String, Camera> cameras;
	private List<Window> windows;
	private Camera currentCamera;
	private boolean loaded, transparent, shown;
	
	public State() {
		EventHub.registerListener(this);
		windows = new ArrayList<Window>();
		cameras = new HashMap<String, Camera>();
	}
	
	public List<Window> getWindows() {
		return windows;
	}
	
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
	
	public Map<String, Camera> getCameras() {
		return cameras;
	}
	
	public void addWindow(Window window) {
		windows.add(window);
	}

	public void removeWindow(Window window) {
		windows.remove(window);
	}
	
	protected void setShown(boolean shown) {
		this.shown = shown;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void render() {
		if (!loaded) {
			loaded = true;
			onLoad();
		}
		for (Window window : getWindows()) {
			window.update();
		}
	}
	
	public void update() {
		for (Window window : getWindows()) {
			window.update();
		}
	}
	
	public boolean isTransparent() {
		return transparent;
	}
	
	public void setIsTransparent(boolean transparent) {
		this.transparent = transparent;
	}
	
	public abstract void onLoad();
	public abstract void onDestroy();
	
	public void hide() {
		setShown(false);
	}
	
	public void show() {
		setShown(true);
	}
}
