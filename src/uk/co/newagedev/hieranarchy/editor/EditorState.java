package uk.co.newagedev.hieranarchy.editor;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.util.KeyBinding;

public class EditorState extends State {
	private Map currentMap;
	private boolean playing = false;
	private Container toolbar = new Container(0, 0);

	public EditorState(Map map) {
		currentMap = map;
		Button button = new Button("", 5, 5, 30, 30, new Runnable() {
			public void run() {
				playing = !playing;
			}
		});
		Sprite play = SpriteRegistry.getSprite("play");
		play.setWidth(16);
		play.setHeight(16);
		button.setImage("play");
		toolbar.addComponent(button);
	}
	
	@Override
	public void render() {
		currentMap.render();
		if (!playing) {
			Screen.renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, Component.DARK_ALPHA);
		}
		Screen.renderQuad(0, 0, Main.WIDTH, 40, Component.VERY_LIGHT);
		toolbar.render();
	}
	
	public void restartMap() {
		currentMap.reload();
		playing = true;
	}
	
	public void continueMap() {
		playing = true;
	}

	@Override
	public void update() {
		toolbar.update();
		if (KeyBinding.isKeyReleasing("editmapplay")) {
			playing = !playing;
		}
		if (playing) {
			currentMap.update();
		}
	}
}
