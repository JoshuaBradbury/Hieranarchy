package uk.co.newagedev.hieranarchy.tile;

import java.util.HashMap;

import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.Location;

public class Tile {
	private Location loc;
	private Map map;
	protected String sprite;
	private java.util.Map<String, Object> properties = new HashMap<String, Object>();
	
	public Tile(Location loc, String sprite) {
		this.loc = loc;
		this.sprite = sprite;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void setProperty(String name, Object value) {
		properties.put(name, value);
	}
	
	public Object getProperty(String name) {
		return properties.get(name);
	}
	
	public void update() {
		
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
	
	public String getSprite() {
		return sprite;
	}
}
