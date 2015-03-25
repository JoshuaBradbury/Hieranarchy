package uk.co.newagedev.hieranarchy.map;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Background;
import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.tile.Tile;
import uk.co.newagedev.hieranarchy.tile.TileMap;
import uk.co.newagedev.hieranarchy.util.CollisionBox;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.KeyBinding;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Map {
	private TileMap tileMap;
	private Background bg;
	private List<Tile> tiles;
	private String state;
	private String mapPath;
	private int width, height;

	public Map(String mapPath, String state) {
		tileMap = new TileMap();

		tileMap.registerTile("ice");
		tileMap.setProperty("ice", "sprite", "icetile");
		tileMap.setProperty("ice", "connected-textures", true);
		tileMap.setProperty("ice", "colour", new Color(0, 0x88, 0x88));

		tileMap.registerTile("flooring");
		tileMap.setProperty("flooring", "sprite", "flooring");
		tileMap.setProperty("flooring", "colour", new Color(0xaa, 0x44, 0));

		loadMap(mapPath);
		this.mapPath = mapPath;
		this.state = state;
	}
	
	public TileMap getTileMap() {
		return tileMap;
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
			camera.move((int) (-5 * camera.getZoom()), 0);
		}
		if (KeyBinding.isKeyDown("Right")) {
			camera.move((int) (5 * camera.getZoom()), 0);
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
					for (String tileName : tileMap.getTilesWithProperty("colour")) {
						if (((Color) tileMap.getTileProperty(tileName, "colour")).equals(colour)) {
							Tile tile = new Tile(new Location(x, y));
							java.util.Map<String, Object> props = tileMap.getTileProperties(tileName);
							for (String prop : props.keySet()) {
								tile.setProperty(prop, props.get(prop));
							}
							tiles.add(tile);
							tile.setMap(this);
						}
					}
				}
			}
			Logger.info("\"" + mapPath + "\"", "loaded as", "\"" + FileUtil.getFileNameWithoutExtension(mapPath) + "\"", "Width:", width, "Height:", height, "tileCount:", tiles.size());
		}
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public void removeTile(Tile tile) {
		tiles.remove(tile);
	}
	
	public List<Tile> getPlacedTilesWithProperty(String name) {
		List<Tile> tilesWithProps = new ArrayList<Tile>();
		for (Tile tile : tiles) {
			if (tile.doesPropertyExist(name)) {
				tilesWithProps.add(tile);
			}
		}
		return tilesWithProps;
	}

	public List<Tile> getTilesWithinRadius(Location loc, float radius) {
		List<Tile> tilesWithinRadius = new ArrayList<Tile>();
		for (Tile tile : tiles) {
			if (tile.getLocation().distance(loc) < radius) {
				tilesWithinRadius.add(tile);
			}
		}
		return tilesWithinRadius;
	}
	
	public List<CollisionBox> getTileCollisionBoxesWithinRadius(Location loc, float radius) {
		List<CollisionBox> boxes = new ArrayList<CollisionBox>();
		for (Tile tile : getTilesWithinRadius(loc, radius)) {
			boxes.add(tile.getCollisionBox());
		}
		return boxes;
	}
}
