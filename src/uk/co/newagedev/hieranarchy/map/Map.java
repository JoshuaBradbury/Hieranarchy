package uk.co.newagedev.hieranarchy.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Background;
import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.tile.Tile;
import uk.co.newagedev.hieranarchy.util.CollisionBox;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Map {

	public static final String MAP_FILE = "data.json";

	private Project project;
	private MapStore store = new MapStore();
	private Background bg;
	private List<Tile> tiles;
	private String state, name, mapFolder;
	private int width, height;

	public Map(String name, Project project) {
		this.project = project;
		this.name = name;
		mapFolder = name + "/";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(FileUtil.create(getMapFile())));

			store = Main.GSON.fromJson(reader, MapStore.class);

			reader.close();
		} catch (IOException e) {
			Logger.error(e.getMessage());
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}

		if (store == null) {
			store = new MapStore();
			store.setName(name);
		}
		
		loadMap();
	}

	public MapStore getMapStore() {
		return store;
	}

	public String getMapFile() {
		if (!FileUtil.doesFileExist(project.getProjectFolder() + Project.MAPS_DIRECTORY))
			FileUtil.create(project.getProjectFolder() + Project.MAPS_DIRECTORY);
		if (!FileUtil.doesFileExist(project.getProjectFolder() + Project.MAPS_DIRECTORY + mapFolder))
			FileUtil.create(project.getProjectFolder() + Project.MAPS_DIRECTORY + mapFolder);
		return project.getProjectFolder() + Project.MAPS_DIRECTORY + mapFolder + MAP_FILE;
	}

	public void save() {
		store.storeTiles(tiles);
		try {
			FileWriter writer = new FileWriter(getMapFile());
			String json = Main.GSON.toJson(store);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			Logger.error(e.getMessage());
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}
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
	
	public void setState(String state) {
		this.state = state;
	}

	public State getState() {
		return StateManager.getState(state);
	}

	public void setBackground(Background background) {
		bg = background;
		bg.setMap(this);
	}

	public void reload() {
		loadMap();
	}

	public void updateCamera() {
		bg.update();
		Camera camera = getState().getCurrentCamera();
		if (KeyBinding.isKeyDown("Left")) {
			camera.move((int) (-5 * camera.getZoom()), 0);
		}
		if (KeyBinding.isKeyDown("Right")) {
			camera.move((int) (5 * camera.getZoom()), 0);
		}
	}

	public void update() {
		updateCamera();
		for (String tile : store.getTiles().keySet()) {
			String sprite = (String) store.getTileProperties(tile).get("sprite");
			if (!SpriteRegistry.doesSpriteExist(sprite)) {
				String file = "";
				Logger.info("");
				for (String fileName : FileUtil.getAllFilesInFolder(Main.project.getProjectFolder() + Project.TEXTURES_DIRECTORY)) {
					String temp = FileUtil.getFileNameWithoutExtension(fileName);
					if (temp.equalsIgnoreCase(sprite)) {
						file = fileName;
						break;
					}
				}
				if (file != "") {
					if (FileUtil.doesFileExist(file) && !FileUtil.isDirectory(file)) {
						SpriteRegistry.registerSprite(sprite, file);
					}
				}
			}
		}
		for (Tile tile : tiles) {
			if (tile != null) {
				tile.update();
			}
		}
	}

	public void render() {
		if (bg != null) {
			bg.render();
		}
		for (Tile tile : tiles) {
			if (tile != null) {
				tile.render(getState().getCurrentCamera());
			}
		}
	}

	public void loadMap() {
		bg = new Background(store.getBGSprite(), store.getBGLocation()[0], store.getBGLocation()[1], store.getBGLocation()[2]);
		bg.setScrollDirections(store.getBGScrollDirections()[0], store.getBGScrollDirections()[1]);
		bg.setMap(this);
		width = store.getWidth();
		height = store.getHeight();
		tiles = new ArrayList<Tile>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				String tileName = store.getTileAtLocation(x, y);
				if (tileName != "") {
					Tile tile = new Tile(new Location(x, y));
					java.util.Map<String, Object> props = store.getTiles().get(tileName);
					for (String prop : props.keySet()) {
						tile.setProperty(prop, props.get(prop));
					}
					tiles.add(tile);
					tile.setMap(this);
				}
			}
		}
		Logger.info("\"" + name + "\"", "loaded with \"", "Width:", width, "Height:", height, "tileCount:", tiles.size());
	}

	public void addTile(Tile tile) {
		List<Tile> ts = new ArrayList<Tile>();
		for (Tile t : tiles) {
			if (t.getLocation().equals(tile.getLocation())) {
				ts.add(t);
			}
		}
		for (Tile t : ts) {
			removeTile(t);
		}
		tiles.add(tile);
		tile.setMap(this);
	}

	public void removeTile(Tile tile) {
		if (tile != null) {
			tiles.remove(tile);
		}
	}

	public List<Tile> getPlacedTilesWithProperty(String name) {
		List<Tile> tilesWithProps = new ArrayList<Tile>();
		for (Tile tile : tiles) {
			if (tile != null) {
				if (tile.doesPropertyExist(name)) {
					tilesWithProps.add(tile);
				}
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
