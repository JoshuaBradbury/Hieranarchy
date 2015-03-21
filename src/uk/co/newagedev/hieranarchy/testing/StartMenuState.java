package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.editor.EditorState;
import uk.co.newagedev.hieranarchy.graphics.Background;
import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("Edit Map", 50, 200, 200, 50, false,  new ButtonRunnable() {
			public void run() {
				Map map = new Map("assets/maps/test.png", "edit map");
				map.setBackground(new Background("bg", 0, 0, 2));
				EditorState state = new EditorState(map);
				state.registerCamera("start", new Camera(100, 0));
				state.switchCamera("start");
				StateManager.registerState("edit map", state);
				Main.setCurrentState("edit map");
			}
		}));
		registerComponent(new Button("Quit Game", 50, 300, 200, 50, false, new ButtonRunnable() {
			public void run() {
				Screen.close();
			}
		}));
	}
	
	@Override
	public void render() {
		Screen.renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, Component.LIGHT);
		for (int i = -200; i < Main.WIDTH; i += 20) {
			Screen.renderLine(new int[] {200 + i + offset, -10}, new int[] {i + offset, Main.HEIGHT + 10}, 10.0f, Component.DARK);
		}
		super.render();
	}
	
	@Override
	public void update() {
		count++;
		if (count % 5 == 0) {
			count = 1;
			offset += 1;
			if (offset >= 20) {
				offset = 0;
			}
		}
		super.update();
	}
}
