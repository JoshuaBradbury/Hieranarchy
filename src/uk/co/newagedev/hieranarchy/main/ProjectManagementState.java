package uk.co.newagedev.hieranarchy.main;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.events.types.screen.ScreenResizeEvent;
import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.ScrollBar;
import uk.co.newagedev.hieranarchy.ui.ScrollPane;
import uk.co.newagedev.hieranarchy.util.Colour;

public class ProjectManagementState extends MenuState {

	private Project project;
	private ScrollPane pane;
	private Container toolbar;

	public ProjectManagementState(Project project) {
		this.project = project;
		
		pane = new ScrollPane(0, 90, Main.WIDTH, Main.HEIGHT - 90, ScrollBar.VERTICAL);
		toolbar = new Container(0, 50);
		
		registerComponent(toolbar);
		registerComponent(pane);
	}

	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(0, 0, Main.WIDTH, 50), Colour.GREY);
		Component.componentFont.renderText(project.getName(), Main.WIDTH / 2, 25);
		Main.getScreen().renderQuad(new Rectangle(0, 50, Main.WIDTH, 40), Colour.LIGHT_GREY);
		super.render();
	}
	
	public void screenResize(ScreenResizeEvent event) {
		pane.setDimensions(event.getWidth(), event.getHeight() - 90);
	}
}
