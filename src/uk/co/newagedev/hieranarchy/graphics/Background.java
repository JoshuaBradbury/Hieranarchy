package uk.co.newagedev.hieranarchy.graphics;

import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.Location;

public class Background {
	
	/** The sprite variable for the background. */
	private String sprite;
	
	/** The map the background is a part of. */
	private Map map;
	
	/** The location variables for the background. */
	private int startX, startY, x, y, z;
	
	/** Booleans to determine which directions it will scroll. */
	private boolean scrollsX = true, scrollsY = true;
	
	/**
	 * The constructor of Background.
	 * @param sprite - the sprite of the background.
	 * @param startX - the starting x of the background.
	 * @param startY - the starting y of the background.
	 * @param z - the factor the cameras offset is divided by.
	 */
	public Background(String sprite, int startX, int startY, int z) {
		this.sprite = sprite;
		this.startX = startX;
		this.startY = startY;
		this.z = z;
	}
	
	/**
	 * Sets the scroll directions.
	 * @param x - If the background scrolls in the x direction.
	 * @param y - If the background scrolls in the y direction.
	 */
	public void setScrollDirections(boolean x, boolean y) {
		scrollsX = x;
		scrollsY = y;
	}
	
	/**
	 * Gets if the background will scroll in the x direction.
	 * @return scrollsX
	 */
	public boolean getScrollsX() {
		return scrollsX;
	}
	
	/**
	 * Gets if the background will scroll in the y direction.
	 * @return scrollsY
	 */
	public boolean getScrollsY() {
		return scrollsY;
	}
	
	/**
	 * Sets the current map.
	 * @param map - the map to set as the current map.
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void update() {
		if (scrollsX) x = (int) ((-map.getState().getCurrentCamera().getX() / z) + startX);
		if (scrollsY) y = (int) (startY + (map.getState().getCurrentCamera().getY() / z));
	}
	
	public void render() {
		if (SpriteRegistry.doesSpriteExist(sprite)) {
			int width = SpriteRegistry.getSprite(sprite).getWidth();
			Main.getScreen().renderSpriteIgnoringCamera(sprite, new Location(((x - map.getState().getCurrentCamera().getX()) % width) - width, y));
			Main.getScreen().renderSpriteIgnoringCamera(sprite, new Location(((x - map.getState().getCurrentCamera().getX()) % width), y));
			Main.getScreen().renderSpriteIgnoringCamera(sprite, new Location(((x - map.getState().getCurrentCamera().getX()) % width) + width, y));
		}
	}
}
