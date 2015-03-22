package uk.co.newagedev.hieranarchy.tile;

import java.util.HashMap;
import java.util.Map;

import uk.co.newagedev.hieranarchy.util.Logger;

public class TileMap {
	private Map<String, Map<String, Object>> map;
	
	public TileMap() {
		map = new HashMap<String, Map<String, Object>>();
	}
	
	public void registerTile(String name) {
		Map<String, Object> props = new HashMap<String, Object>();
		map.put(name, props);
	}
	
	public void removeTile(String name) {
		map.remove(name);
	}
	
	public Map<String, Object> getTileProperties(String name) {
		return map.get(name);
	}
	
	public void setProperty(String name, String property, Object value) {
		Map<String, Object> props = getTileProperties(name);
		if (props != null) {
			props.put(property, value);
			map.put(name, props);
		} else {
			Logger.error("Unable to set property", "\"" + property + "\"", "for the tile", "\"" + name + "\"");
		}
	}
}
