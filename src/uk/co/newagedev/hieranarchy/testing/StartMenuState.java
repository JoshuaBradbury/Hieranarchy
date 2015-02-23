package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.Component;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("Load Map", 50, 200, 200, 50, new Runnable() {
			public void run() {
				MapLoaderState state = new MapLoaderState();
				StateManager.registerState("load map", state);
				Main.setCurrentState("load map");
			}
		}));
		registerComponent(new Button("Quit Game", 50, 300, 200, 50, new Runnable() {
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
