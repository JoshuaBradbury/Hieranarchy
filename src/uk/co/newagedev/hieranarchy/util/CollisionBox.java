package uk.co.newagedev.hieranarchy.util;

public class CollisionBox {

	private LocationContainer locationContainer;
	private int width, height;
	private boolean collidable;

	public CollisionBox(LocationContainer locationContainer, int width, int height) {
		this(locationContainer, width, height, true);
	}

	public CollisionBox(LocationContainer locationContainer, int width, int height, boolean collidable) {
		this.locationContainer = locationContainer;
		this.width = width;
		this.height = height;
		this.collidable = collidable;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isColliding(CollisionBox box) {
		if (collidable) {
			Location loc = locationContainer.getLocation();
			Location boxLoc = box.locationContainer.getLocation();

			int[][] boxPoints = new int[][] { { (int) boxLoc.getX(), (int) boxLoc.getY() }, { (int) boxLoc.getX() + box.getWidth(), (int) boxLoc.getY() }, { (int) boxLoc.getX() + box.getWidth(), (int) boxLoc.getY() + box.getHeight() }, { (int) boxLoc.getX(), (int) boxLoc.getY() + box.getHeight() } };
			boolean colliding = false;

			for (int[] point : boxPoints) {
				if (point[0] > loc.getX() && point[0] < loc.getX() + width) {
					if (point[1] > loc.getY() && point[1] < loc.getY() + height) {
						colliding = true;
					}
				}
			}
			return colliding;
		} else {
			return false;
		}
	}
}
