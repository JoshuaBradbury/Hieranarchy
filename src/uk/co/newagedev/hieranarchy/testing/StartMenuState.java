package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.ui.Button;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("test button", 100, 100, 200, 50));
	}
	
	@Override
	public void render() {
		Screen.renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, new float[] {0.8f, 0.8f, 0.8f});
		for (int i = -200; i < Main.WIDTH; i += 20) {
			Screen.renderLine(new int[] {200 + i + offset, 0}, new int[] {i + offset, Main.HEIGHT}, 10.0f, new float[] {0.6f, 0.6f, 0.6f});
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
	}
}
