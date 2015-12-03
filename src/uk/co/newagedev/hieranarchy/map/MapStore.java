package uk.co.newagedev.hieranarchy.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.co.newagedev.hieranarchy.map.objects.MapObject;
import uk.co.newagedev.hieranarchy.util.Logger;

public class MapStore {
	private int width, height;
	private String bgSprite, name;
	private int[] bgLocation = new int[3];
	private boolean[] bgScrollDirs = new boolean[2];

	private java.util.Map<String, java.util.Map<String, Object>> objects = new HashMap<String, java.util.Map<String, Object>>();

	private java.util.Map<String, List<int[]>> objectLocations = new HashMap<String, List<int[]>>();

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

	public java.util.Map<String, java.util.Map<String, Object>> getObjects() {
		return objects;
	}

	public String getObjectAtLocation(int x, int y) {
		for (String object : objectLocations.keySet()) {
			for (int[] loc : objectLocations.get(object)) {
				if (loc[0] == x && loc[1] == y) {
					return object;
				}
			}
		}
		return "";
	}

	public boolean doesObjectExist(String name) {
		return objects.containsKey(name);
	}

	public boolean doesPropertyExistForObject(String name, String property) {
		if (doesObjectExist(name)) {
			return objects.get(name).containsKey(property);
		}
		return false;
	}

	public void writeObject(String name, Map<String, Object> props) {
		props.put("name", name);
		objects.put(name, props);
	}

	public void removeObject(String name) {
		objects.remove(name);
	}

	public Map<String, Object> getObjectProperties(String name) {
		return objects.get(name);
	}

	public void setProperty(String name, String property, Object value) {
		Map<String, Object> props = getObjectProperties(name);
		if (props != null) {
			props.put(property, value);
			objects.put(name, props);
		} else {
			Logger.error("Unable to set property", "\"" + property + "\"", "for the object", "\"" + name + "\"");
		}
	}

	public List<String> getObjectsWithProperty(String property) {
		List<String> t = new ArrayList<String>();
		for (String object : objects.keySet()) {
			if (doesPropertyExistForObject(object, property)) {
				t.add(object);
			}
		}
		return t;
	}

	public Object getObjectProperty(String name, String property) {
		if (doesPropertyExistForObject(name, property)) {
			return objects.get(name).get(property);
		}
		return null;
	}

	public String getNextTile(String prev) {
		if (objects.containsKey(prev)) {
			Iterator<String> iter = getObjectsWithProperty("type:tile").iterator();
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
		if (objects.size() > 0) {
			return (String) objects.keySet().toArray()[0];
		}
		return prev;
	}

	public String getPrevTile(String next) {
		List<String> objs = getObjectsWithProperty("type:tile");
		if (objs.size() > 0) {
			if (((String) objs.toArray()[0]).equalsIgnoreCase(next)) {
				return (String) objects.keySet().toArray()[objects.size() - 1];
			}
			if (objs.contains(next)) {
				Iterator<String> iter = objs.iterator();
				String prev = (String) objs.toArray()[0];
				while (iter.hasNext()) {
					String n = iter.next();
					if (n.equalsIgnoreCase(next)) {
						return prev;
					} else {
						prev = n;
					}
				}
			}
		}
		return next;
	}

	public void storeObjects(List<MapObject> mapobjects) {
		objectLocations.clear();
		for (MapObject object : mapobjects) {
			String objectName = (String) object.getProperty("name");
			if (!objectLocations.containsKey(objectName)) {
				objectLocations.put(objectName, new ArrayList<int[]>());
			}
			List<int[]> locations = objectLocations.get(objectName);
			locations.add(new int[] { (int) object.getLocation().getX(), (int) object.getLocation().getY() });
			objectLocations.put(objectName, locations);
		}
	}
}
