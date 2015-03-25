package uk.co.newagedev.hieranarchy.entities;

import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.CollisionBox;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.LocationContainer;

public class Entity implements LocationContainer {
	/**
	 * The location variable of the entity.
	 */
	private Location loc;
	
	/**
	 * The collision box variable of the entity.
	 */
	private CollisionBox box;
	
	/**
	 * The map variable that the entity is a part of.
	 */
	private Map map;
	
	/**
	 * The sprite variable of the entity.
	 */
	private String sprite;
	
	/**
	 * The constructor of Entity.
	 * @param sprite - The sprite of the entity.
	 * @param loc - The initial location for the entity.
	 */
	public Entity(String sprite, Location loc) {
		this.loc = loc;
		this.sprite = sprite;
		Sprite sp = SpriteRegistry.getSprite(sprite);
		box = new CollisionBox(this, sp.getWidth(), sp.getHeight());
	}
	
	/**
	 * Sets the Map of the Entity.
	 * @param map - the Map the Entity is a part of.
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	public boolean isCollidingWithTiles() {
		List<CollisionBox> collisionBoxes = map.getTileCollisionBoxesWithinRadius(loc, 3);
		boolean colliding = false;
		
		for (CollisionBox colBox : collisionBoxes) {
			if (colBox.isColliding(box)) {
				colliding = true;
			}
		}
		
		return colliding;
	}
	
	/**
	 * Returns the entity's location.
	 * @return loc - The entity's location.
	 */
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * Sets the Entity's location
	 * @param location - The new location for the entity.
	 */
	public void setLocation(Location location) {
		loc = location;
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
	 * @return sprite - the sprite of the entity.
	 */
	public String getSprite() {
		return sprite;
	}
}
