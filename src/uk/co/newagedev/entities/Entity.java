package uk.co.newagedev.entities;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.util.Location;

public class Entity {
	private Location loc;
	private String sprite;
	
	public Entity(Location loc) {
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void update() {
		
	}
	
	public void move(int x, int y) {
		loc.add(new Location(x, y));
	}
	
	public void move(Location loc) {
		this.loc.add(loc);
	}
}
