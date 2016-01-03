package uk.co.newagedev.hieranarchy.map.objects;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.main.Main;

public class Tile extends MapObject {

	public Tile() {
		setProperty("type", "tile");
	}
	
	public void render(Camera camera) {
		float[] col = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		if (doesPropertyExist("selection"))
			col[3] = 0.6f;
		if (doesPropertyExist("delete")) {
			col[1] = 0.4f;
			col[2] = 0.4f;
		}
		if (doesPropertyExist("connected-textures")) {
			if ((boolean) getProperty("connected-textures")) {
				if (getMap() != null) {
					float txmi = 0.0f, tymi = 0.0f, txma = 1.0f, tyma = 1.0f;
					boolean top = false, bottom = false, left = false, right = false;

					Tile up = getMap().getTileAt(getLocation().getRelative(0, 1));
					if (up != null) {
						if (up.doesPropertyExist("connected-textures") && (boolean) up.getProperty("connected-textures")) {
							top = true;
						}
					}

					Tile down = getMap().getTileAt(getLocation().getRelative(0, -1));
					if (down != null) {
						if (down.doesPropertyExist("connected-textures") && (boolean) down.getProperty("connected-textures")) {
							bottom = true;
						}
					}

					Tile leftSide = getMap().getTileAt(getLocation().getRelative(-1, 0));
					if (leftSide != null) {
						if (leftSide.doesPropertyExist("connected-textures") && (boolean) leftSide.getProperty("connected-textures")) {
							left = true;
						}
					}

					Tile rightSide = getMap().getTileAt(getLocation().getRelative(1, 0));
					if (rightSide != null) {
						if (rightSide.doesPropertyExist("connected-textures") && (boolean) rightSide.getProperty("connected-textures")) {
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

					Main.getScreen().renderSprite(getSprite(), getLocation(), camera, new float[] { txmi, txma, tymi, tyma }, col);
					return;
				}
			}
		}
		Main.getScreen().renderSprite(getSprite(), getLocation(), camera, new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, col);
	}
}
