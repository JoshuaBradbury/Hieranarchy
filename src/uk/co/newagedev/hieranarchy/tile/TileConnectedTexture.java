package uk.co.newagedev.hieranarchy.tile;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.util.Location;

public class TileConnectedTexture extends Tile {

	public TileConnectedTexture(Location loc, String texture) {
		super(loc, texture);
	}

	public void render() {
		float txmi = 0.0f, tymi = 0.0f, txma = 1.0f, tyma = 1.0f;
		boolean top = false, bottom = false, left = false, right = false;
		
		Tile up = getMap().getTileAt(getLocation().getRelative(0, 1));
		if (up != null) {
			if (up.getClass() == this.getClass()) {
				top = true;
			}
		}
		
		Tile down = getMap().getTileAt(getLocation().getRelative(0, -1));
		if (down != null) {
			if (down.getClass() == this.getClass()) {
				bottom = true;
			}
		}
		
		Tile leftSide = getMap().getTileAt(getLocation().getRelative(-1, 0));
		if (leftSide != null) {
			if (leftSide.getClass() == this.getClass()) {
				left = true;
			}
		}
		
		Tile rightSide = getMap().getTileAt(getLocation().getRelative(1, 0));
		if (rightSide != null) {
			if (rightSide.getClass() == this.getClass()) {
				right = true;
			}
		}
		
		if (top && !bottom) {
			tyma = 0.8f;
		}
		
		if (bottom && !top) {
			tymi = 0.2f;
		}
		
		if (top && bottom) {
			tymi = 0.2f;
			tyma = 0.8f;
		}
		
		if (left && !right) {
			txmi = 0.2f;
		}
		
		if (right && !left) {
			txma = 0.8f;
		}
		
		if (left && right) {
			txmi = 0.2f;
			txma = 0.8f;
		}
		
		if (!left && !right && !top && !bottom) {
			txmi = 0.0f;
			txma = 1.0f;
			tymi = 0.0f;
			tyma = 1.0f;
		}
		
		Screen.renderSprite(sprite, getLocation(), getMap().getState().getCurrentCamera(), new float[] {txmi, txma, tymi, tyma});
	}
}
