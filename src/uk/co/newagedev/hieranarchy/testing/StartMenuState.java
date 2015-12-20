package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.state.EditorState;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.ProjectLoaderState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("Edit Map", 50, 200, 200, 50, false,  new ButtonRunnable() {
			public void run() {
				Main.project.loadMap("testing");
				EditorState state = new EditorState(Main.project.getMap("testing"));
				state.registerCamera("edit", new Camera(100, 0));
				state.registerCamera("play", new Camera(100, 0));
				state.switchCamera("edit");
				StateManager.registerState("edit map", state);
				Main.project.getMap("testing").setState("edit map");
				Main.setCurrentState("edit map");
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
		Main.getScreen().renderQuad(new Vector2f(), Main.WIDTH, Main.HEIGHT, Component.LIGHT);
		for (int i = -200; i < Main.WIDTH; i += 20) {
			Main.getScreen().renderLine(new Vector2f(200 + i + offset, -10), new Vector2f(i + offset, Main.HEIGHT + 10), 10.0f, Component.DARK);
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
