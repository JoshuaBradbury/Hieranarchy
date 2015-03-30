package uk.co.newagedev.hieranarchy.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.tile.Tile;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.TextBox;
import uk.co.newagedev.hieranarchy.ui.TickBox;
import uk.co.newagedev.hieranarchy.ui.Window;
import uk.co.newagedev.hieranarchy.util.Location;

public class EditorState extends State {
	private Map currentMap;
	private boolean playing = false, editing = false, placing = false, deleting = false;
	private Location selectionLocation = new Location(0, 0);
	private Tile selection = new Tile(selectionLocation);
	private Button playButton;
	private String currentTileName;
	private Container toolbar = new Container(0, 0);
	private List<Window> windows = new ArrayList<Window>();

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
				Window window = getCreateNewTileWindow();
				addWindow(window);
			}
		});
		newTileButton.setImage("new tile");
		toolbar.addComponent(newTileButton);

		currentTileName = currentMap.getTileMap().getNextTile(currentTileName);
	}

	public void addWindow(Window window) {
		windows.add(window);
	}

	public void removeWindow(Window window) {
		windows.remove(window);
	}

	public Window getCreateNewTileWindow() {
		Window window = new Window(this, Main.WIDTH - 250, 70, 250, 300);

		Label name = new Label("name", 10, 10);

		TextBox box = new TextBox(10, 30, 10);

		TickBox tick = new TickBox(10, 100, false);

		Label conn = new Label("connected textures", 10, 80);

		window.addComponent(name);
		window.addComponent(box);
		window.addComponent(tick);
		window.addComponent(conn);

		return window;
	}

	@Override
	public void render() {
		currentMap.render();
		if (editing) {
			if (selection != null) {
				selection.render(getCurrentCamera());
			}
		}
		Screen.renderQuad(0, 0, Main.WIDTH, 40, Component.VERY_LIGHT);
		toolbar.render();
		if (editing) {
			Screen.renderText("Edit Mode", 10 + Screen.getTextWidth("Edit Mode") / 2, 50 + Screen.getTextHeight("Edit Mode") / 2, new Color(0xff, 0xff, 0xff));
			for (Window window : windows) {
				window.render();
			}
		}
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
		currentMap.removeTile(selection);
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
			try {
				for (Window window : windows) {
					window.update();
				}
			} catch (Exception e) {

			}
			boolean mouseOverWindow = false;
			for (Window window : windows) {
				if (Mouse.getMouseX() > window.getLocation().getX() && Mouse.getMouseX() < window.getLocation().getX() + window.getDimensions().getWidth()) {
					if (Mouse.getMouseY() > window.getLocation().getY() - 30 && Mouse.getMouseY() < window.getLocation().getY() + window.getDimensions().getHeight() - 30) {
						mouseOverWindow = true;
					}
				}
			}
			if (Mouse.getMouseY() > toolbar.getDimensions().getHeight() + toolbar.getLocation().getY()) {
				selectionLocation = new Location((int) ((Mouse.getMouseX() + getCurrentCamera().getX()) / (Main.SPRITE_WIDTH * getCurrentCamera().getZoom())), (int) ((Mouse.getMouseY() - getCurrentCamera().getY()) / (Main.SPRITE_HEIGHT * getCurrentCamera().getZoom())));

				currentMap.removeTile(selection);
				
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
					List<Tile> tiles = currentMap.getPlacedTilesWithProperty("delete");
					for (Tile tile : tiles) {
						currentMap.removeTile(tile);
					}
				}

				if (Mouse.isButtonDown(Mouse.LEFT_BUTTON) && !deleting && !mouseOverWindow) {
					placing = true;
					if (currentMap.getTileAt(selectionLocation) != null) {
						currentMap.removeTile(currentMap.getTileAt(selectionLocation));
					}
					if (selection != null) {
						selection.removeProperty("selection");
					}

					currentMap.addTile(selection);
				}

				if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON) && !deleting) {
					placing = false;
				}

				if (KeyBinding.isKeyReleasing("SelectNextTile")) {
					currentTileName = currentMap.getTileMap().getNextTile(currentTileName);
				}

				if (KeyBinding.isKeyReleasing("SelectPrevTile")) {
					currentTileName = currentMap.getTileMap().getPrevTile(currentTileName);
				}

				selection = new Tile(selectionLocation);

				java.util.Map<String, Object> props = currentMap.getTileMap().getTileProperties(currentTileName);
				for (String prop : props.keySet()) {
					selection.setProperty(prop, props.get(prop));
				}

				for (Tile tile : currentMap.getPlacedTilesWithProperty("selection")) {
					currentMap.removeTile(tile);
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
	}
}
