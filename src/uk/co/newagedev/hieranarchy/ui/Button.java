package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.util.Mouse;

public class Button extends Component {

	private String text;
	private boolean hover = false, toolTip = false, toolTipDisplay = false;
	private Runnable task;
	private String image = "";

	public Button(String text, int x, int y, int width, int height, boolean toolTip, Runnable task) {
		super(x, y, width, height);
		this.text = text;
		this.task = task;
		this.toolTip = toolTip;
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
			Screen.renderQuad(Mouse.getMouseX(), Mouse.getMouseY(), Screen.getTextWidth(text) + 14, Screen.getTextHeight(text) + 14, Component.DARK);
			Screen.renderQuad(Mouse.getMouseX() + 2, Mouse.getMouseY() + 2, Screen.getTextWidth(text) + 10, Screen.getTextHeight(text) + 10, Component.LIGHT);
			Screen.renderText(text, Mouse.getMouseX() + Screen.getTextWidth(text) / 2 + 7, Mouse.getMouseY() + Screen.getTextHeight(text) / 2 + 7);
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
			if (Mouse.getMillisSinceLastMovement() > 60 && !toolTipDisplay) {
				toolTipDisplay = true;
			}
		} else {
			if (toolTipDisplay) {
				toolTipDisplay = false;
			}
		}
		if (Mouse.isMouseReleasing(Mouse.LEFT_BUTTON)) {
			if (hover) {
				task.run();
			}
		}
	}
}
