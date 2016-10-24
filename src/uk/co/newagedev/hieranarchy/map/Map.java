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
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.map.objects.MapObject;
import uk.co.newagedev.hieranarchy.map.objects.Tile;
import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.project.ProjectManager;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.util.CollisionBox;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Vector2f;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.StringUtil;

public class Map {

	public static final String MAP_FILE = "data.json";

	private Project project;
	private MapStore store = new MapStore();
	private Background bg;
	private List<MapObject> objects;
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
		store.storeObjects(objects);
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

	public Tile getTileAt(Vector2f loc) {
		Tile tile = null;
		for (MapObject object : objects) {
			if (object != null) {
				if (object instanceof Tile) {
					if (object.getLocation().equals(loc)) {
						tile = (Tile) object;
						break;
					}
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
		if (KeyBinding.isBindingDown("Left")) {
			camera.move((int) (-5 * camera.getZoom()), 0);
		}
		if (KeyBinding.isBindingDown("Right")) {
			camera.move((int) (5 * camera.getZoom()), 0);
		}
	}

	public void update() {
		updateCamera();
		for (String object : store.getObjects().keySet()) {
			String sprite = (String) store.getObjectProperties(object).get("sprite");
			if (!SpriteRegistry.doesSpriteExist(sprite)) {
				String file = "";
				for (String fileName : FileUtil.getAllFilesInFolder(ProjectManager.getCurrentProject().getProjectFolder() + Project.TEXTURES_DIRECTORY)) {
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
		for (MapObject object : objects) {
			object.update();
		}
	}

	public void render(Camera camera) {
		if (bg != null) {
			bg.render();
		}
		for (MapObject object : objects) {
			if (object != null) {
				object.render(camera);
			}
		}
	}

	public void loadMap() {
		bg = new Background(store.getBGSprite(), store.getBGLocation()[0], store.getBGLocation()[1], store.getBGLocation()[2]);
		bg.setScrollDirections(store.getBGScrollDirections()[0], store.getBGScrollDirections()[1]);
		bg.setMap(this);
		width = store.getWidth();
		height = store.getHeight();
		objects = new ArrayList<MapObject>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				String objectName = store.getObjectAtLocation(x, y);
				if (objectName != "") {
					if (((String) store.getObjectProperty(objectName, "type")).equalsIgnoreCase("tile")) {
						Tile tile = new Tile();
						tile.setLocation(new Vector2f(x, y));
						java.util.Map<String, Object> props = store.getObjects().get(objectName);
						for (String prop : props.keySet()) {
							tile.setProperty(prop, props.get(prop));
						}
						objects.add(tile);
						tile.setMap(this);
					}
				}
			}
		}
		Logger.info(StringUtil.surroundWith(name, "\""), "loaded with", "Width:", width, "Height:", height, "Object Count:", objects.size());
	}

	public void addObject(MapObject object) {
		List<MapObject> objs = new ArrayList<MapObject>();
		for (MapObject obj : objects) {
			if (obj.getLocation().equals(object.getLocation())) {
				objs.add(obj);
			}
		}
		for (Object obj : objs) {
			removeObject(obj);
		}
		objects.add(object);
		object.setMap(this);
	}

	public void removeObject(Object object) {
		if (object != null) {
			objects.remove(object);
		}
	}

	public List<MapObject> getObjectsWithProperty(String... props) {
		List<MapObject> objectsWithProps = new ArrayList<MapObject>();
		for (MapObject object : objects) {
			if (object != null) {
				int propCount = 0;

				for (String prop : props) {
					if (prop.contains(":")) {
						if (object.doesPropertyExist(prop.split(":")[0]) && object.getProperty(prop.split(":")[0]).toString().equalsIgnoreCase(prop.split(":")[1])) {
							propCount += 1;
						}
					} else {
						if (object.doesPropertyExist(prop)) {
							propCount += 1;
						}
					}
				}
				
				if (propCount == props.length) {
					objectsWithProps.add(object);
				}
			}
		}
		return objectsWithProps;
	}

	public int getObjectCount() {
		return objects.size();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public List<MapObject> getObjectsWithinRadius(String type, Vector2f loc, float radius) {
		List<MapObject> objectsWithinRadius = new ArrayList<MapObject>();
		for (MapObject object : getObjectsWithProperty(type)) {
			if (object.getLocation().distance(loc) < radius) {
				objectsWithinRadius.add(object);
			}
		}
		return objectsWithinRadius;
	}

	public List<CollisionBox> getObjectCollisionBoxesWithinRadius(String type, Vector2f loc, float radius) {
		List<CollisionBox> boxes = new ArrayList<CollisionBox>();
		for (MapObject object : getObjectsWithinRadius(type, loc, radius)) {
			boxes.add(object.getCollisionBox());
		}
		return boxes;
	}
}
