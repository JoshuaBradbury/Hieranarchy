package uk.co.newagedev.hieranarchy.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Background;
import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.tile.Tile;
import uk.co.newagedev.hieranarchy.tile.TileType;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.KeyBinding;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Map {

	private Background bg;
	private List<Tile> tiles;
	private String state;
	private String mapPath;
	private int width, height;

	public Map(String mapPath, String state) {
		loadMap(mapPath);
		this.mapPath = mapPath;
		this.state = state;
	}

	public Tile getTileAt(Location loc) {
		Tile tile = null;
		for (Tile t : tiles) {
			if (t != null) {
				if (t.getLocation().equals(loc)) {
					tile = t;
					break;
				}
			}
		}
		return tile;
	}
	
	public State getState() {
		return StateManager.getState(state);
	}

	public void setBackground(Background background) {
		bg = background;
		bg.setMap(this);
	}

	public void reload() {
		loadMap(mapPath);
	}
	
	public void update() {
		bg.update();
		Camera camera = getState().getCurrentCamera();
		if (KeyBinding.isKeyDown("Left")) {
			camera.move((int) (5 * camera.getZoom()), 0);
		}
		if (KeyBinding.isKeyDown("Right")) {
			camera.move((int) (-5 * camera.getZoom()), 0);
		}
		for (Tile tile : tiles) {
			if (tile != null) {
				tile.update();
			}
		}
	}

	public void render() {
		bg.render();
		for (Tile tile : tiles) {
			if (tile != null) {
				tile.render(StateManager.getState(state).getCurrentCamera());
			}
		}
	}

	public void loadMap(String mapPath) {
		BufferedImage image = Main.screen.loadImage(mapPath);
		if (image != null) {
			width = image.getWidth();
			height = image.getHeight();
			tiles = new ArrayList<Tile>();
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int pix = image.getRGB(x, y);
					Color colour = new Color(pix);
					TileType type = TileType.getTileTypeByColour(colour);
					if (type != null) {
						try {
							Tile tile = type.getTileClass().getConstructor(Location.class).newInstance(new Location(x, y));
							tiles.add(tile);
							tile.setMap(this);
						} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException e) {
							Logger.error(e.getMessage());
						}
					}
				}
			}
			Logger.info("\"" + mapPath + "\"", "loaded as", "\"" + FileUtil.getFileNameWithoutExtension(mapPath) + "\"", "Width:", width, "Height:", height, "tileCount:", tiles.size());
		}
	}
}
