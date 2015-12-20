package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.ProjectCreationState;
import uk.co.newagedev.hieranarchy.state.ProjectLoaderState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("Create New Project", 50, 200, 200, 50, false,  new ButtonRunnable() {
			public void run() {
				ProjectCreationState state = new ProjectCreationState();
				StateManager.registerState("project creator", state);
				Main.setCurrentState("project creator");
			}
		}));
		registerComponent(new Button("Load Project", 450, 200, 200, 50, false, new ButtonRunnable() {
			public void run() {
				ProjectLoaderState state = new ProjectLoaderState();
				StateManager.registerState("project loader", state);
				Main.setCurrentState("project loader");
			}
		}));
		registerComponent(new Button("Quit Game", 50, 300, 200, 50, false, new ButtonRunnable() {
			public void run() {
				Main.getScreen().close();
			}
		}));
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(new Vector2f(), Main.WIDTH, Main.HEIGHT, Colour.DARK_GREY);
		for (int i = -200; i < Main.WIDTH; i += 20) {
			Main.getScreen().renderLine(new Vector2f(200 + i + offset, -10), new Vector2f(i + offset, Main.HEIGHT + 10), 10.0f, Colour.LIGHT_GREY);
		}
		super.render();
	}
	
	@Override
	public void update() {
		super.update();
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
