package uk.co.newagedev.hieranarchy.ui;

import java.awt.Point;
import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;

public class Button extends Component {
	
	private String text;
	
	private boolean hover = false, toolTip = false, toolTipDisplay = false;
	
	private ButtonRunnable task;
	
	private String image = "";
	
	public Button(String text, int x, int y, int width, int height, boolean toolTip, ButtonRunnable task) {
		super(x, y, width, height);
		this.text = text;
		this.task = task;
		this.task.setButton(this);
		this.toolTip = toolTip;
	}
	
	public void changeText(String text) {
		this.text = text;
	}
	
	public void setImage(String sprite) {
		this.image = sprite;
	}

	public void render() {
		Rectangle buttonDimensions = new Rectangle(new Point((int) getDisplayLocation().getX(), (int) getDisplayLocation().getY()), getDimensions());
		Main.getScreen().renderQuad(buttonDimensions, Component.DARK);
		buttonDimensions.grow(-1, -1);
		Main.getScreen().renderQuad(buttonDimensions, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		if (image != "") {
			Sprite sprite = SpriteRegistry.getSprite(image);
			Main.getScreen().renderSpriteIgnoringCamera(image, getDisplayLocation().clone().subtract(sprite.getWidth() / 2, sprite.getHeight() / 2).add((int) getDimensions().getWidth() / 2, (int) getDimensions().getHeight() / 2));
		} else {
			componentFont.renderText(text, (int) (getDisplayLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getDisplayLocation().getY() + (getDimensions().getHeight() / 2)));
		}
		if (toolTipDisplay && toolTip) {
			int toolTipX = Mouse.getMouseX(), toolTipY = (int) (getParent().getDisplayLocation().getY() + getParent().getDimensions().getHeight() + 10);
			Main.getScreen().renderQuad(toolTipX, toolTipY - componentFont.getTextHeight(text) + 14, componentFont.getTextWidth(text) + 14, componentFont.getTextHeight(text) + 14, Component.DARK);
			Main.getScreen().renderQuad(toolTipX + 2, toolTipY + 16 - componentFont.getTextHeight(text), componentFont.getTextWidth(text) + 10, componentFont.getTextHeight(text) + 10, Component.LIGHT);
			componentFont.renderText(text, toolTipX + componentFont.getTextWidth(text) / 2 + 7, toolTipY - componentFont.getTextHeight(text) / 2 + 21);
		}
	}

	public void update() {
		hover = false;
		if (Mouse.getMouseX() > getDisplayLocation().getX() && Mouse.getMouseX() < getDisplayLocation().getX() + getDimensions().getWidth()) {
			if (Mouse.getMouseY() > getDisplayLocation().getY() && Mouse.getMouseY() < getDisplayLocation().getY() + getDimensions().getHeight()) {
				hover = true;
			}
		}
		if (hover) {
			if (Mouse.getMillisSinceLastMovement() > 30 && !toolTipDisplay) {
				toolTipDisplay = true;
			}
		} else {
			if (toolTipDisplay) {
				toolTipDisplay = false;
			}
		}
		if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON)) {
			if (hover) {
				task.run();
			}
		}
	}
}
