package uk.co.newagedev.hieranarchy.map.objects;

import java.util.List;

import uk.co.newagedev.hieranarchy.util.CollisionBox;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 */
public class Entity extends MapObject {

	/**
	 * Instantiates a new entity.
	 */
	public Entity() {
		setProperty("type", "entity");
	}

	/**
	 * Checks if is colliding with tiles.
	 *
	 * @return true, if is colliding with tiles
	 */
	public boolean isCollidingWithTiles() {
		List<CollisionBox> collisionBoxes = getMap().getObjectCollisionBoxesWithinRadius("tile", getLocation(), 3);
		boolean colliding = false;
		
		for (CollisionBox colBox : collisionBoxes) {
			if (colBox.isColliding(getCollisionBox())) {
				colliding = true;
			}
		}
		
		return colliding;
	}
}
