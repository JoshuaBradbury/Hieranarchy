package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.util.Logger;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("Start Game", 50, 200, 200, 50, new Runnable() {
			public void run() {
				Logger.info("Start Game!");
			}
		}));
		registerComponent(new Button("Quit Game", 50, 300, 200, 50, new Runnable() {
			public void run() {
				Logger.info("Quit Game!");
				Screen.close();
			}
		}));
	}
	
	@Override
	public void render() {
		Screen.renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, new float[] {0.6f, 0.6f, 0.75f});
		for (int i = -200; i < Main.WIDTH; i += 20) {
			Screen.renderLine(new int[] {200 + i + offset, -10}, new int[] {i + offset, Main.HEIGHT + 10}, 10.0f, new float[] {0.4f, 0.4f, 0.55f});
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
