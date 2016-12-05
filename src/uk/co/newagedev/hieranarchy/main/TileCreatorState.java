package uk.co.newagedev.hieranarchy.main;

import java.awt.Rectangle;
import java.util.HashMap;

import uk.co.newagedev.hieranarchy.project.ProjectManager;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.TextBox;
import uk.co.newagedev.hieranarchy.ui.TickBox;
import uk.co.newagedev.hieranarchy.util.Colour;

public class TileCreatorState extends State {

	private Container container;
	private TextBox tileName, sprite;
	private TickBox connectedTextures;

	public TileCreatorState() {
		container = new Container(0, 0, Main.WIDTH, Main.HEIGHT);

		tileName = new TextBox(150, 20, 200, 50);
		container.addComponent(tileName);

		Label tileNameLabel = new Label("Tile Name", 20, 25);
		container.addComponent(tileNameLabel);

		connectedTextures = new TickBox(524, 50, false);
		container.addComponent(connectedTextures);

		Label connectedTexturesLabel = new Label("Connected Textures", 20, 55);
		container.addComponent(connectedTexturesLabel);

		sprite = new TextBox(150, 80, 200, 50);
		container.addComponent(sprite);

		Label spriteLabel = new Label("Sprite", 20, 85);
		container.addComponent(spriteLabel);

		Button createTile = new Button("Create Tile", (Main.WIDTH - 150) / 2, Main.HEIGHT - 140, 150, 50, false, new ButtonRunnable() {
			@Override
			public void run() {
				closeTileCreator();
				EditorState editor = (EditorState) StateManager.getCurrentStates().get(1);
				HashMap<String, Object> props = getProps();

				ProjectManager.getCurrentProject().addObjectToMap(editor.getCurrentMap().getMapStore().getName(), props);
			}
		});
		container.addComponent(createTile);

		Button cancel = new Button("Cancel", (Main.WIDTH - 150) / 2, Main.HEIGHT - 70, 150, 50, false, new ButtonRunnable() {
			@Override
			public void run() {
				closeTileCreator();
			}
		});
		container.addComponent(cancel);
	}

	public void closeTileCreator() {
		StateManager.popCurrentState();
	}

	public HashMap<String, Object> getProps() {
		HashMap<String, Object> props = new HashMap<String, Object>();

		if (connectedTextures.isTicked()) {
			props.put("connected-textures", true);
		}

		props.put("type", "tile");
		props.put("sprite", sprite.getText().toLowerCase());
		props.put("name", tileName.getText().toLowerCase());
		return props;
	}

	@Override
	public void onLoad() {

	}
	
	@Override
	public void onDestroy() {

	}

	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(Main.WIDTH, Main.HEIGHT), Colour.LIGHT_GREY);
		container.render();
	}

	@Override
	public void update() {
		container.update();
	}

	@Override
	public void hide() {
		if (isShown()) {
			super.hide();
			container.hide();
			connectedTextures.hide();
			sprite.hide();
			tileName.hide();
		}
	}

	@Override
	public void show() {
		if (!isShown()) {
			super.show();
			container.show();
			connectedTextures.show();
			sprite.show();
			tileName.show();
		}
	}
}
