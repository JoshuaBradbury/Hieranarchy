package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.Mouse;

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
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Screen.renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY() + 5, (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 10, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		if (image != "") {
			Sprite sprite = SpriteRegistry.getSprite(image);
			Screen.renderSpriteIgnoringCamera(image, getLocation().clone().subtract(sprite.getWidth() / 2, sprite.getHeight() / 2).add((int) getDimensions().getWidth() / 2, (int) getDimensions().getHeight() / 2));
		} else {
			Screen.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
		}
		if (toolTipDisplay && toolTip) {
			int toolTipX = Mouse.getMouseX(), toolTipY = (int) (getParent().getLocation().getY() + getParent().getDimensions().getHeight() + 10);
			Screen.renderQuad(toolTipX, toolTipY - Screen.getTextHeight(text) + 14, Screen.getTextWidth(text) + 14, Screen.getTextHeight(text) + 14, Component.DARK);
			Screen.renderQuad(toolTipX + 2, toolTipY + 16 - Screen.getTextHeight(text), Screen.getTextWidth(text) + 10, Screen.getTextHeight(text) + 10, Component.LIGHT);
			Screen.renderText(text, toolTipX + Screen.getTextWidth(text) / 2 + 7, toolTipY - Screen.getTextHeight(text) / 2 + 21);
		}
	}

	public void update() {
		hover = false;
		if (Mouse.getMouseX() > getLocation().getX() && Mouse.getMouseX() < getLocation().getX() + getDimensions().getWidth()) {
			if (Mouse.getMouseY() > getLocation().getY() && Mouse.getMouseY() < getLocation().getY() + getDimensions().getHeight()) {
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
