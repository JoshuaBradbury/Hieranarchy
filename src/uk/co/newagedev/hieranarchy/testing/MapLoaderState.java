package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.ScrollBar;
import uk.co.newagedev.hieranarchy.ui.ScrollPane;

public class MapLoaderState extends MenuState {

	private int count, offset;
	
	public MapLoaderState() {
		registerComponent(new ScrollPane(50, 50, Main.WIDTH - 100, Main.HEIGHT - 100, ScrollBar.VERTICAL));
	}
	
	@Override
	public void render() {
		Screen.renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, Component.DARK);
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
