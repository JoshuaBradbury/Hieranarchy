package uk.co.newagedev.hieranarchy.entities;

import uk.co.newagedev.hieranarchy.util.Location;

public class Entity {
	/**
	 * The location variable of the entity.
	 */
	private Location loc;
	/**
	 * The sprite variable of the entity.
	 */
	private String sprite;
	
	/**
	 * The constructor of Entity.
	 * @param loc - The initial location for the entity.
	 */
	public Entity(Location loc) {
		this.loc = loc;
	}
	
	/**
	 * Returns the entity's location.
	 * @return The entity's location.
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * Updates the entity.
	 */
	public void update() {
		
	}
	
	/**
	 * Moves the entity by the specified x and y values.
	 * @param x - how much to move in the x-direction.
	 * @param y - how much to move in the y-direction.
	 */
	public void move(int x, int y) {
		loc.add(new Location(x, y));
	}
	
	/**
	 * Moves the entity by the specified location.
	 * @param loc - the location of how much to move the entity by.
	 */
	public void move(Location loc) {
		this.loc.add(loc);
	}
	
	/**
	 * Returns the entity's current sprite.
	 * @return the sprite of the entity.
	 */
	public String getSprite() {
		return sprite;
	}
}
