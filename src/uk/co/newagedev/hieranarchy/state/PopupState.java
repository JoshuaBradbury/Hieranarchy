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
	
	private int yOffset;
	
	private PopupState(String title, Container contents, ButtonRunnable okayTask, ButtonRunnable cancelTask) {
		int labelHeight = Component.componentFont.getTextHeight(title);
		
		yOffset = (Main.HEIGHT - (labelHeight + contents.getHeight() + 25)) / 2;
		
		registerComponent(new Label(title, Main.WIDTH / 2 - Component.componentFont.getTextWidth(title) / 2, yOffset));
		
		Container cont = new Container((Main.WIDTH - contents.getWidth()) / 2, labelHeight + yOffset, contents.getWidth(), contents.getHeight());
		
		for (Component comp : contents.getComponents()) {
			cont.addComponent(comp);
		}
		
		registerComponent(cont);
		
		registerComponent(new Button("Okay", Main.WIDTH / 2 - 125, (int) (cont.getHeight() + cont.getLocation().getY()), 100, 25, false, okayTask));
		registerComponent(new Button("Cancel", Main.WIDTH / 2 + 25, (int) (cont.getHeight() + cont.getLocation().getY()), 100, 25, false, cancelTask));
	
		setIsTransparent(true);
	}
	
	public static void popup(String title, Container contents, ButtonRunnable runnable) {
		PopupState popup = new PopupState(title, contents, new ButtonRunnable() {
			private boolean hasRun = false;
			public void run() {
				if (!hasRun) {
					StateManager.popCurrentState();
					hasRun = true;
					runnable.setButton(button);
					runnable.run();
				}
			}
		}, new ButtonRunnable() {
			private boolean hasRun = false;
			public void run() {
				if (!hasRun) {
					StateManager.popCurrentState();
					hasRun = true;
				}
			}
		});
		StateManager.pushCurrentState(popup);
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT), Colour.vary(Colour.LIGHT_LIGHT_GREY, 0, 0, 0, -0.1f));
		Main.getScreen().renderQuad(new Rectangle(0, yOffset - 20, Main.WIDTH, Main.HEIGHT - yOffset * 2 + 40), Colour.vary(Colour.GREY, 0, 0, 0, -0.4f));
		super.render();
	}
}
