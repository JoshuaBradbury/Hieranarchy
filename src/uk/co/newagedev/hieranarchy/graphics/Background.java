package uk.co.newagedev.hieranarchy.graphics;

import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.Location;

public class Background {
	/**
	 * The sprite variable for the background;
	 */
	private String sprite;
	
	/**
	 * The map the background is a part of.
	 */
	private Map map;
	
	/**
	 * The location variables for the background.
	 */
	private int startX, startY, x, y, z;
	
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
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void update() {
		x = (int) ((map.getState().getCurrentCamera().getX() / z) + startX);
		y = (int) (startY + (map.getState().getCurrentCamera().getY() / z));
	}
	
	public void render() {
		if (SpriteRegistry.doesSpriteExist(sprite)) {
			int width = SpriteRegistry.getSprite(sprite).getWidth();
			Screen.renderSpriteIgnoringCamera(sprite, new Location(((x + map.getState().getCurrentCamera().getX()) % width) - width, y));
			Screen.renderSpriteIgnoringCamera(sprite, new Location(((x + map.getState().getCurrentCamera().getX()) % width), y));
			Screen.renderSpriteIgnoringCamera(sprite, new Location(((x + map.getState().getCurrentCamera().getX()) % width) + width, y));
		}
	}
}
