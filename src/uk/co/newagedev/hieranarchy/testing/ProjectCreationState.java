package uk.co.newagedev.hieranarchy.testing;

import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.TextBox;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class ProjectCreationState extends MenuState {

	private String projectName;
	private TextBox nameBox;
	
	public ProjectCreationState() {
		Label label = new Label("Project Name:", 100, 100);
		nameBox = new TextBox(100 + (int) label.getDimensions().getWidth(), 100, Main.WIDTH - 200 - (int) label.getDimensions().getWidth(), (int) label.getDimensions().getHeight());
		
		registerComponent(label);
		registerComponent(nameBox);
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(new Vector2f(), Main.WIDTH, Main.HEIGHT, Colour.LIGHT_LIGHT_GREY);
		super.render();
	}
	
	@Override
	public void update() {
		super.update();
		projectName = nameBox.getText();
	}
}