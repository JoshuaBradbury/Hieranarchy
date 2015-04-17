package uk.co.newagedev.hieranarchy.state;

import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.ScrollBar;
import uk.co.newagedev.hieranarchy.ui.ScrollPane;
import uk.co.newagedev.hieranarchy.util.Logger;

public class ProjectLoaderState extends MenuState {

	private int count, offset;
	
	public ProjectLoaderState() {
		ScrollPane pane = new ScrollPane(50, 50, Main.WIDTH - 100, Main.HEIGHT - 100, ScrollBar.VERTICAL | ScrollBar.HORIZONTAL);
		Container container = pane.getPane();
		container.addComponent(new Button("Project A", 100, 100, 200, 50, false, new ButtonRunnable() {
			public void run() {
				Logger.info(button.getDisplayLocation());
			}
		}));
		container.addComponent(new Button("Project B", 200, 200, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		container.addComponent(new Button("Project C", 300, 300, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		container.addComponent(new Button("Project D", 400, 400, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		container.addComponent(new Button("Project E", 500, 500, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		container.addComponent(new Button("Project F", 600, 600, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		container.addComponent(new Button("Project G", 700, 700, 200, 50, false, new ButtonRunnable() {
			public void run() {
				
			}
		}));
		registerComponent(pane);
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(0, 0, Main.WIDTH, Main.HEIGHT, Component.DARK);
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