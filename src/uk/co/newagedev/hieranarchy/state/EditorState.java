package uk.co.newagedev.hieranarchy.state;

import java.awt.Font;
import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.graphics.TextObject;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.map.objects.MapObject;
import uk.co.newagedev.hieranarchy.map.objects.Tile;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Window;
import uk.co.newagedev.hieranarchy.util.FontUtil;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class EditorState extends State {

	private static TextObject editorMode = FontUtil.getStringFromFont(new Font("Tahoma", java.awt.Font.PLAIN, 15), "Edit Mode");
	private Map currentMap;
	private boolean playing = false, editing = false, placing = false, deleting = false, mouseOverWindow = false, downOverWindow = false;

	private Vector2f selectionLocation;

	private Tile selection;
	private Button playButton;
	private String currentTileName;
	private Container toolbar = new Container(0, 0);

	public EditorState(Map map) {
		currentMap = map;
		Sprite play = SpriteRegistry.getSprite("play");
		play.setWidth(20);
		play.setHeight(20);

		Sprite pause = SpriteRegistry.getSprite("pause");
		pause.setWidth(20);
		pause.setHeight(20);

		Sprite reset = SpriteRegistry.getSprite("reset");
		reset.setWidth(20);
		reset.setHeight(20);

		Sprite edit = SpriteRegistry.getSprite("edit");
		edit.setWidth(20);
		edit.setHeight(20);

		Sprite newTile = SpriteRegistry.getSprite("new tile");
		newTile.setWidth(20);
		newTile.setHeight(20);

		playButton = new Button("Play", 5, 5, 30, 30, true, new ButtonRunnable() {
			public void run() {
				changePlaying();
			}
		});
		playButton.setImage("play");
		toolbar.addComponent(playButton);

		Button resetButton = new Button("Reset", 45, 5, 30, 30, true, new ButtonRunnable() {
			public void run() {
				restartMap();
			}
		});
		resetButton.setImage("reset");
		toolbar.addComponent(resetButton);

		Button editButton = new Button("Edit", 85, 5, 30, 30, true, new ButtonRunnable() {
			public void run() {
				if (!editing) {
					enableEditing();
				} else {
					disableEditing();
				}
			}
		});
		editButton.setImage("edit");
		toolbar.addComponent(editButton);

		Button newTileButton = new Button("Create New Tile", 205, 5, 30, 30, true, new ButtonRunnable() {
			public void run() {
				if (editing) {
					TileCreatorState state = new TileCreatorState(getName());
					StateManager.registerState("tile creator", state);
					Main.setCurrentState("tile creator");
				}
			}
		});
		newTileButton.setImage("new tile");
		toolbar.addComponent(newTileButton);

		currentTileName = currentMap.getMapStore().getNextTile(currentTileName);
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}

	@Override
	public void render() {
		currentMap.render(getCurrentCamera());
		if (editing) {
			if (selection != null) {
				selection.render(getCurrentCamera());
			}
		}
		Main.getScreen().renderQuad(0, 0, Main.WIDTH, 40, Component.VERY_LIGHT);
		toolbar.render();
		if (editing) {
			FontUtil.renderText(editorMode, 10 + editorMode.getWidth() / 2, 50 + editorMode.getHeight() / 2);
			for (Window window : getWindows()) {
				window.render();
			}
		}
		super.render();
	}

	public void changePlaying() {
		if (!editing) {
			playing = !playing;
			if (playing) {
				playButton.changeText("Pause");
				playButton.setImage("pause");
			} else {
				playButton.changeText("Play");
				playButton.setImage("play");
			}
		}
	}

	public void restartMap() {
		currentMap.reload();
		for (Camera camera : getCameras().values()) {
			camera.reset();
		}
	}

	public void enableEditing() {
		if (!playing) {
			editing = true;
		}
	}

	public void disableEditing() {
		editing = false;
		currentMap.removeObject(selection);
		selection = null;
	}

	public void continueMap() {
		playing = true;
	}

	@Override
	public void update() {
		toolbar.update();
		if (KeyBinding.isKeyReleasing("editmapplay")) {
			changePlaying();
		}
		if (editing) {
			for (Window window : getWindows()) {
				if (Mouse.getMouseX() > window.getLocation().getX() && Mouse.getMouseX() < window.getLocation().getX() + window.getDimensions().getWidth()) {
					if (Mouse.getMouseY() > window.getLocation().getY() - 30 && Mouse.getMouseY() < window.getLocation().getY() + window.getDimensions().getHeight() - 30) {
						mouseOverWindow = true;
					}
				}
			}
			if (Mouse.getMouseY() > toolbar.getDimensions().getHeight() + toolbar.getLocation().getY()) {
				selectionLocation = new Vector2f((int) ((Mouse.getMouseX() + getCurrentCamera().getX()) / (Main.SPRITE_WIDTH * getCurrentCamera().getZoom())), (int) ((Mouse.getMouseY() - getCurrentCamera().getY()) / (Main.SPRITE_HEIGHT * getCurrentCamera().getZoom())));

				currentMap.removeObject(selection);

				if (Mouse.isButtonDown(Mouse.LEFT_BUTTON) || Mouse.isButtonDown(Mouse.RIGHT_BUTTON) || Mouse.isButtonDown(Mouse.MIDDLE_BUTTON)) {
					if (mouseOverWindow) {
						downOverWindow = true;
					}
				}

				if (!Mouse.isButtonDown(Mouse.LEFT_BUTTON) && !Mouse.isButtonDown(Mouse.RIGHT_BUTTON) && !Mouse.isButtonDown(Mouse.MIDDLE_BUTTON)) {
					if (downOverWindow) {
						downOverWindow = false;
					}
				}

				if (Mouse.isButtonDown(Mouse.RIGHT_BUTTON) && !placing && !mouseOverWindow) {
					deleting = true;
					Tile tile = currentMap.getTileAt(selectionLocation);
					if (tile != null) {
						if (!tile.doesPropertyExist("delete")) {
							tile.setProperty("delete", null);
						}
					}
				}

				if (Mouse.isButtonReleasing(Mouse.RIGHT_BUTTON) && !placing) {
					deleting = false;
					List<MapObject> tiles = currentMap.getObjectsWithProperty("delete", "type:tile");
					for (MapObject tile : tiles) {
						currentMap.removeObject(tile);
					}
				}

				if (Mouse.isButtonDown(Mouse.LEFT_BUTTON) && !deleting && !mouseOverWindow) {
					placing = true;
					if (currentMap.getTileAt(selectionLocation) != null) {
						currentMap.removeObject(currentMap.getTileAt(selectionLocation));
					}
					if (selection != null) {
						selection.removeProperty("selection");
					}

					currentMap.addObject(selection);
				}

				if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON) && !deleting) {
					placing = false;
				}

				if (KeyBinding.isKeyReleasing("SelectNextTile")) {
					currentTileName = currentMap.getMapStore().getNextTile(currentTileName);
				}

				if (KeyBinding.isKeyReleasing("SelectPrevTile")) {
					currentTileName = currentMap.getMapStore().getPrevTile(currentTileName);
				}

				selection = new Tile();
				selection.setLocation(selectionLocation);
				
				java.util.Map<String, Object> props = currentMap.getMapStore().getObjectProperties(currentTileName);
				if (props != null) {
					for (String prop : props.keySet()) {
						selection.setProperty(prop, props.get(prop));
					}
				}

				for (MapObject object : currentMap.getObjectsWithProperty("selection")) {
					currentMap.removeObject(object);
				}

				selection.setProperty("selection", null);

				if (deleting) {
					selection.setProperty("delete", null);
				}
	
				Camera camera = getCurrentCamera();

				if (KeyBinding.isKeyDown("Up")) {
					camera.move(0, (int) (5 * camera.getZoom()));
				}

				if (KeyBinding.isKeyDown("Down")) {
					camera.move(0, (int) (-5 * camera.getZoom()));
				}

				currentMap.updateCamera();

			} else {
				selection = null;
			}
		}
		if (playing) {
			currentMap.update();
		}
		super.update();
	}
}
