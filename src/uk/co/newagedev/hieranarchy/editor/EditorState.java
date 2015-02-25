package uk.co.newagedev.hieranarchy.editor;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.state.State;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.util.KeyBinding;

public class EditorState extends State {
	private Map currentMap;
	private boolean playing = false;

	public EditorState(Map map) {
		currentMap = map;
	}
	
	@Override
	public void render() {
		currentMap.render();
		if (!playing) {
			Screen.renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, Component.DARK_ALPHA);
		}
		Screen.renderQuad(0, 0, Main.WIDTH, 40, Component.DARK);
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
		if (KeyBinding.isKeyReleasing("editmapplay")) {
			if (playing) {
				playing = false;
			} else {
				playing = true;
			}
		}
		if (playing) {
			currentMap.update();
		}
	}
}
