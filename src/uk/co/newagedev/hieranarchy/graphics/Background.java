package uk.co.newagedev.hieranarchy.graphics;

import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.Location;

public class Background {
	private String spriteName;
	private Map map;
	private int startX, x, y, z;
	
	public Background(String spriteName, int startX, int startY, int z) {
		this.spriteName = spriteName;
		this.startX = startX;
		this.y = startY;
		this.z = z;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void update() {
		x = (int) ((map.getCurrentCamera().getX() / z) + startX);
	}
	
	public void render() {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			int width = SpriteRegistry.getSprite(spriteName).getWidth();
			Screen.renderSpriteIgnoringCamera(spriteName, new Location(((x + map.getCurrentCamera().getX()) % width) - width, y));
			Screen.renderSpriteIgnoringCamera(spriteName, new Location(((x + map.getCurrentCamera().getX()) % width), y));
			Screen.renderSpriteIgnoringCamera(spriteName, new Location(((x + map.getCurrentCamera().getX()) % width) + width, y));
		}
	}
}
