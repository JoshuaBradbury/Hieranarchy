package uk.co.newagedev.hieranarchy.main;

import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.ScrollBar;
import uk.co.newagedev.hieranarchy.ui.ScrollPane;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class ProjectLoaderState extends MenuState {

	private int count, offset;
	
	public ProjectLoaderState() {
		ScrollPane pane = new ScrollPane(50, 50, Main.WIDTH - 100, Main.HEIGHT - 100, ScrollBar.VERTICAL | ScrollBar.HORIZONTAL);
		Container container = pane.getPane();
		container.addComponent(new Button("Project A", 100, 100, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		registerComponent(pane);
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(new Vector2f(), Main.WIDTH, Main.HEIGHT, Colour.DARK_DARK_GREY);
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