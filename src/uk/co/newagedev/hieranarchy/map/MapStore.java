package uk.co.newagedev.hieranarchy.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.newagedev.hieranarchy.tile.Tile;
import uk.co.newagedev.hieranarchy.util.Logger;

public class MapStore {
	private int width, height;
	private String bgSprite, name;
	private int[] bgLocation = new int[3];
	private boolean[] bgScrollDirs = new boolean[2];
	private java.util.Map<String, java.util.Map<String, Object>> tiles = new HashMap<String, java.util.Map<String, Object>>();
	private java.util.Map<String, List<int[]>> tileLocations = new HashMap<String, List<int[]>>();

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBGScrollDirections(boolean scrollX, boolean scrollY) {
		bgScrollDirs[0] = scrollX;
		bgScrollDirs[1] = scrollY;
	}

	public void setBGLocation(int x, int y, int z) {
		bgLocation[0] = x;
		bgLocation[1] = y;
		bgLocation[2] = z;
	}

	public int[] getBGLocation() {
		return bgLocation;
	}

	public boolean[] getBGScrollDirections() {
		return bgScrollDirs;
	}

	public void setbgSprite(String sprite) {
		bgSprite = sprite;
	}

	public String getBGSprite() {
		return bgSprite;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public java.util.Map<String, java.util.Map<String, Object>> getTiles() {
		return tiles;
	}

	public String getTileAtLocation(int x, int y) {
		for (String tile : tileLocations.keySet()) {
			for (int[] loc : tileLocations.get(tile)) {
				if (loc[0] == x && loc[1] == y) {
					return tile;
				}
			}
		}
		return "";
	}

	public boolean doesTileExist(String name) {
		return tiles.containsKey(name);
	}

	public boolean doesPropertyExistForTile(String name, String property) {
		if (doesTileExist(name)) {
			return tiles.get(name).containsKey(property);
		}
		return false;
	}

	public void writeTile(String name, Map<String, Object> props) {
		props.put("name", name);
		tiles.put(name, props);
	}

	public void removeTile(String name) {
		tiles.remove(name);
	}

	public Map<String, Object> getTileProperties(String name) {
		return tiles.get(name);
	}

	public void setProperty(String name, String property, Object value) {
		Map<String, Object> props = getTileProperties(name);
		if (props != null) {
			props.put(property, value);
			tiles.put(name, props);
		} else {
			Logger.error("Unable to set property", "\"" + property + "\"", "for the tile", "\"" + name + "\"");
		}
	}

	public List<String> getTilesWithProperty(String property) {
		List<String> t = new ArrayList<String>();
		for (String tile : tiles.keySet()) {
			if (doesPropertyExistForTile(tile, property)) {
				t.add(tile);
			}
		}
		return t;
	}

	public Object getTileProperty(String name, String property) {
		if (doesPropertyExistForTile(name, property)) {
			return tiles.get(name).get(property);
		}
		return null;
	}

	public String getNextTile(String prev) {
		if (tiles.containsKey(prev)) {
			Iterator<String> iter = tiles.keySet().iterator();
			while (iter.hasNext()) {
				if (iter.next().equalsIgnoreCase(prev)) {
					if (iter.hasNext()) {
						return iter.next();
					} else {
						break;
					}
				}
			}
		}
		if (tiles.size() > 0) {
			return (String) tiles.keySet().toArray()[0];
		}
		return prev;
	}

	public String getPrevTile(String next) {
		if (((String) tiles.keySet().toArray()[0]).equalsIgnoreCase(next)) {
			return (String) tiles.keySet().toArray()[tiles.size() - 1];
		}
		if (tiles.containsKey(next)) {
			Iterator<String> iter = tiles.keySet().iterator();
			String prev = (String) tiles.keySet().toArray()[0];
			while (iter.hasNext()) {
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

	public void storeTiles(List<Tile> mapTiles) {
		tileLocations.clear();
		for (Tile tile : mapTiles) {
			String tileName = (String) tile.getProperty("name");
			if (!tileLocations.containsKey(tileName)) {
				tileLocations.put(tileName, new ArrayList<int[]>());
			}
			List<int[]> locations = tileLocations.get(tileName);
			locations.add(new int[] {(int) tile.getLocation().getX(), (int) tile.getLocation().getY()});
			tileLocations.put(tileName, locations);
		}
	}
}
