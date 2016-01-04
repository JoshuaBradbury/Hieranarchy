package uk.co.newagedev.hieranarchy.state;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.util.Colour;

public class PopupState extends MenuState {
	
	private String state;
	
	public PopupState(String title, Container contents, String state, ButtonRunnable okayTask, ButtonRunnable cancelTask) {
		this.state = state;
		
		registerComponent(new Label(title, Main.WIDTH / 2 - Component.componentFont.getTextWidth(title) / 2, 0));
		
		Container cont = new Container((Main.WIDTH - contents.getWidth()) / 2, Component.componentFont.getTextHeight(title), contents.getWidth(), contents.getHeight());
		
		for (Component comp : contents.getComponents()) {
			cont.addComponent(comp);
		}
		
		registerComponent(cont);
		
		registerComponent(new Button("Okay", Main.WIDTH / 2 - 125, (int) (cont.getHeight() + cont.getLocation().getY()), 100, 25, false, okayTask));
		registerComponent(new Button("Cancel", Main.WIDTH / 2 + 125, (int) (cont.getHeight() + cont.getLocation().getY()), 100, 25, false, cancelTask));
	}
	
	@Override
	public void render() {
		StateManager.getState(state).render();
		Main.getScreen().renderQuad(new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT), Colour.vary(Colour.LIGHT_LIGHT_GREY, 0, 0, 0, -0.3f));
		super.render();
	}
	
	public String getState() {
		return state;
	}
}
