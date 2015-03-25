package uk.co.newagedev.hieranarchy.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.newagedev.hieranarchy.util.Logger;

public class TileMap {
	private Map<String, Map<String, Object>> map;
	
	public TileMap() {
		map = new HashMap<String, Map<String, Object>>();
	}
	
	public boolean doesTileExist(String name) {
		return map.containsKey(name);
	}
	
	public boolean doesPropertyExistForTile(String name, String property) {
		if (doesTileExist(name)) {
			return map.get(name).containsKey(property);
		}
		return false;
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
	
	public List<String> getTilesWithProperty(String property) {
		List<String> tiles = new ArrayList<String>();
		for (String tile : map.keySet()) {
			if (doesPropertyExistForTile(tile, property)) {
				tiles.add(tile);
			}
		}
		return tiles;
	}
	
	public Object getTileProperty(String name, String property) {
		if (doesPropertyExistForTile(name, property)) {
			return map.get(name).get(property);
		}
		return null;
	}
	
	public String getNextTile(String prev) {
		if (map.containsKey(prev)) {
			Iterator<String> iter = map.keySet().iterator();
			while(iter.hasNext()) {
				if (iter.next().equalsIgnoreCase(prev)) {
					if (iter.hasNext()) {
						return iter.next();
					} else {
						break;
					}
				}
			}
		}
		if (map.size() > 0) {
			return (String) map.keySet().toArray()[0];
		}
		return prev;
	}
	
	public String getPrevTile(String next) {
		if (((String) map.keySet().toArray()[0]).equalsIgnoreCase(next)) {
			return (String) map.keySet().toArray()[map.size() - 1];
		}
		if (map.containsKey(next)) {
			Iterator<String> iter = map.keySet().iterator();
			String prev = (String) map.keySet().toArray()[0];
			while(iter.hasNext()) {
				String n = iter.next();
				if (n.equalsIgnoreCase(next)) {
					return prev;
				} else {
					prev = n;
				}
			}
		}
		return next;
	}
}
