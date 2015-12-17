package uk.co.newagedev.hieranarchy.map.objects;

import java.util.List;

import uk.co.newagedev.hieranarchy.util.CollisionBox;

public class Entity extends MapObject {

	public Entity() {
		setProperty("type", "entity");
	}

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
