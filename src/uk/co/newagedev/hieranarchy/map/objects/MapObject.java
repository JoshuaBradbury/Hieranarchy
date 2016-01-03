package uk.co.newagedev.hieranarchy.map.objects;

import java.util.HashMap;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.CollisionBox;
import uk.co.newagedev.hieranarchy.util.Vector2f;
import uk.co.newagedev.hieranarchy.map.Map;

public class MapObject {

	private Map map;

	private Vector2f location;

	private CollisionBox box;	

	private HashMap<String, Object> properties = new HashMap<String, Object>();
	
	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}

	public void removeProperty(String name) {
		properties.remove(name);
	}

	public Object getProperty(String name) {
		return properties.get(name);
	}

	public String getSprite() {
		return (String) getProperty("sprite");
	}

	public boolean doesPropertyExist(String name) {
		return properties.containsKey(name);
	}

	public void setCollisionBox(CollisionBox box) {
		this.box = box;
	}
	
	public CollisionBox getCollisionBox() {
		return box;
	}
	
	public void update() {
		
	}
	
	public void render(Camera camera) {
		Main.getScreen().renderSprite(getSprite(), location, camera);
	}
	
	public void setLocation(Vector2f location) {
		this.location = location;
	}
	
	public Vector2f getLocation() {
		return location;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
}
