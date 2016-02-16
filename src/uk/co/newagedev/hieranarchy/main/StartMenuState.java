package uk.co.newagedev.hieranarchy.main;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.events.types.screen.ScreenResizeEvent;
import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.TextBox;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.StringUtil;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class StartMenuState extends MenuState {

	private int count, offset;
	private Button newProjectButton, loadProjectButton, quitButton;
	
	public StartMenuState() {
		newProjectButton = new Button("Create New Project", Main.WIDTH / 2 - 225, Main.HEIGHT / 2 - 100, 200, 50, false,  new ButtonRunnable() {
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
						if (nameBox.getText().length() > 0) {
							Logger.info("Loading project " + StringUtil.surroundWith(nameBox.getText(), "\""));
							ProjectManagementState state = new ProjectManagementState(new Project(nameBox.getText()));
							StateManager.registerState(nameBox.getText() + " management", state);
							Main.setCurrentState(nameBox.getText() + " management");
						}
					}
				});
			}
		});
		registerComponent(newProjectButton);
		
		loadProjectButton = new Button("Load Project", Main.WIDTH / 2 + 25, Main.HEIGHT / 2 - 100, 200, 50, false, new ButtonRunnable() {
			public void run() {
				ProjectLoaderState state = new ProjectLoaderState();
				StateManager.registerState("project loader", state);
				Main.setCurrentState("project loader");
			}
		});
		registerComponent(loadProjectButton);
		
		quitButton = new Button("Quit Game", Main.WIDTH / 2 - 100, Main.HEIGHT / 2 + 50, 200, 50, false, new ButtonRunnable() {
			public void run() {
				Main.getScreen().close();
			}
		});
		registerComponent(quitButton);
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(Main.WIDTH, Main.HEIGHT), Colour.DARK_GREY);
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
	
	public void screenResize(ScreenResizeEvent event) {
		newProjectButton.setLocation(Main.WIDTH / 2 - 225, Main.HEIGHT / 2 - 50);
		loadProjectButton.setLocation(Main.WIDTH / 2 + 25, Main.HEIGHT / 2 - 50);
		quitButton.setLocation(Main.WIDTH / 2 - 100, Main.HEIGHT / 2 + 50);
	}
}
