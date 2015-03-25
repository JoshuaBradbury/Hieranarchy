package uk.co.newagedev.hieranarchy.editor;

import java.util.List;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.tile.Tile;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.util.KeyBinding;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.Mouse;

public class EditorState extends State {
	private Map currentMap;
	private boolean playing = false, editing = false;
	private Location selectionLocation = new Location(0, 0);
	private Tile selection = new Tile(selectionLocation);
	private Button playButton;
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
	}

	@Override
	public void render() {
		currentMap.render();
		selection.render(getCurrentCamera());
		Screen.renderQuad(0, 0, Main.WIDTH, 40, Component.VERY_LIGHT);
		toolbar.render();
	}

	public void changePlaying() {
		playing = !playing;
		if (playing) {
			playButton.changeText("Pause");
			playButton.setImage("pause");
		} else {
			playButton.changeText("Play");
			playButton.setImage("play");
		}
	}

	public void restartMap() {
		currentMap.reload();
		for (Camera camera : getCameras().values()) {
			camera.reset();
		}
	}

	public void enableEditing() {
		editing = true;
	}

	public void disableEditing() {
		editing = false;
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
			selectionLocation = new Location((int) ((Mouse.getMouseX() + getCurrentCamera().getX()) / (Main.SPRITE_WIDTH * getCurrentCamera().getZoom())), (int) ((Mouse.getMouseY() + getCurrentCamera().getY()) / (Main.SPRITE_HEIGHT * getCurrentCamera().getZoom())));

			currentMap.removeTile(selection);

			if (Mouse.isButtonDown(Mouse.RIGHT_BUTTON)) {
				Tile tile = currentMap.getTileAt(selectionLocation);
				if (tile != null) {
					tile.setProperty("delete", null);
				}
			}

			if (Mouse.isButtonReleasing(Mouse.RIGHT_BUTTON)) {
				List<Tile> tiles = currentMap.getPlacedTilesWithProperty("delete");
				for (Tile tile : tiles) {
					currentMap.removeTile(tile);
				}
			}

			if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON)) {
				if (currentMap.getTileAt(selectionLocation) != null) {
					currentMap.removeTile(currentMap.getTileAt(selectionLocation));
				}
				selection.removeProperty("selection");
				currentMap.addTile(selection);
			}

			selection = new Tile(selectionLocation);
			selection.setMap(currentMap);
			selection.setProperty("sprite", "icetile");
			selection.setProperty("connected-textures", true);

			for (Tile tile : currentMap.getPlacedTilesWithProperty("selection")) {
				currentMap.removeTile(tile);
			}

			selection.setProperty("selection", null);
			currentMap.addTile(selection);
		}
		if (playing) {
			currentMap.update();
		}
	}
}
