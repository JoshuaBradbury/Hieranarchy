package uk.co.newagedev.hieranarchy.tile;

import java.util.HashMap;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.Location;

public class Tile {
	private Location loc;
	private Map map;
	private java.util.Map<String, Object> properties = new HashMap<String, Object>();

	public Tile(Location loc) {
		this.loc = loc;
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
		return (String) getProperty("sprite");
	}
	
	public boolean doesPropertyExist(String name) {
		return properties.containsKey(name);
	}

	public void render(Camera camera) {
		if (doesPropertyExist("connected-textures")) {
			if ((boolean) getProperty("connected-textures")) {
				float txmi = 0.0f, tymi = 0.0f, txma = 1.0f, tyma = 1.0f;
				boolean top = false, bottom = false, left = false, right = false;

				Tile up = getMap().getTileAt(getLocation().getRelative(0, 1));
				if (up != null) {
					if (up.getClass() == this.getClass()) {
						top = true;
					}
				}

				Tile down = getMap().getTileAt(getLocation().getRelative(0, -1));
				if (down != null) {
					if (down.getClass() == this.getClass()) {
						bottom = true;
					}
				}

				Tile leftSide = getMap().getTileAt(getLocation().getRelative(-1, 0));
				if (leftSide != null) {
					if (leftSide.getClass() == this.getClass()) {
						left = true;
					}
				}

				Tile rightSide = getMap().getTileAt(getLocation().getRelative(1, 0));
				if (rightSide != null) {
					if (rightSide.getClass() == this.getClass()) {
						right = true;
					}
				}

				if (top && !bottom) {
					tyma = 0.8f;
				}

				if (bottom && !top) {
					tymi = 0.2f;
				}

				if (top && bottom) {
					tymi = 0.2f;
					tyma = 0.8f;
				}

				if (left && !right) {
					txmi = 0.2f;
				}

				if (right && !left) {
					txma = 0.8f;
				}

				if (left && right) {
					txmi = 0.2f;
					txma = 0.8f;
				}

				if (!left && !right && !top && !bottom) {
					txmi = 0.0f;
					txma = 1.0f;
					tymi = 0.0f;
					tyma = 1.0f;
				}

				Screen.renderSprite(getSprite(), getLocation(), getMap().getState().getCurrentCamera(), new float[] { txmi, txma, tymi, tyma });
				return;
			}
		}
		Screen.renderSprite(getSprite(), getLocation(), camera);
	}
}
