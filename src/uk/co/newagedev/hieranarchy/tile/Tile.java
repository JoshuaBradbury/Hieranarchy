package uk.co.newagedev.hieranarchy.tile;

import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.Location;

public class Tile {
	private Location loc;
	private Map map;
	protected String sprite;
	
	public Tile(Location loc, String sprite) {
		this.loc = loc;
		this.sprite = sprite;
	}
	
	public Location getLocation() {
		return loc;
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
