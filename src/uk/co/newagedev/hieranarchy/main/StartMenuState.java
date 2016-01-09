package uk.co.newagedev.hieranarchy.main;

import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.TextBox;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class StartMenuState extends MenuState {

	private int count, offset;
	
	public StartMenuState() {
		registerComponent(new Button("Create New Project", Main.WIDTH / 8 * 3 - 100, Main.HEIGHT / 8 * 3 - 25, 200, 50, false,  new ButtonRunnable() {
			public void run() {
				Container cont = new Container(0, 0);
				Label label = new Label("Project Name:", 0, 100);
				TextBox nameBox = new TextBox((int) label.getDimensions().getWidth() + 20, 100, Main.WIDTH - 200 - (int) label.getDimensions().getWidth(), (int) label.getDimensions().getHeight());
				Label spacing = new Label(" ", 0, 200);
				cont.addComponent(label);
				cont.addComponent(nameBox);
				cont.addComponent(spacing);
				Main.popup("Create New Project", cont, new ButtonRunnable() {
					@Override
					public void run() {
						Logger.info("YOLO");
					}
				});
			}
		}));
		registerComponent(new Button("Load Project", Main.WIDTH / 8 * 5 - 100, Main.HEIGHT / 8 * 3 - 25, 200, 50, false, new ButtonRunnable() {
			public void run() {
				ProjectLoaderState state = new ProjectLoaderState();
				StateManager.registerState("project loader", state);
				Main.setCurrentState("project loader");
			}
		}));
		registerComponent(new Button("Quit Game", Main.WIDTH / 8 * 5 - 100, Main.HEIGHT / 8 * 5 - 25, 200, 50, false, new ButtonRunnable() {
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
