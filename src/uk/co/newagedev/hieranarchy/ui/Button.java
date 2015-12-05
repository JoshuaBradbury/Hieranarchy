package uk.co.newagedev.hieranarchy.ui;

import java.awt.Point;
import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class Button extends Component {

	public void changeText(String text) {
		/*BufferedImage image = FontUtil.getImageFromFont(componentFont, text);
		Logger.info(image == null);
		if (image != null) {
			ImageUtil.saveImageToFile(image, "Projects/testing/Assets/Textures/" + text + ".png");
		}
		*/
		String name = componentFont.getFontName() + "-" + componentFont.getSize() + "-" + text;
		this.image = name;
		
		SpriteRegistry.registerSprite(name, "Projects/testing/Assets/Textures/" + text + ".png");
	}

	private boolean hover = false, toolTip = false, toolTipDisplay = false;

	private ButtonRunnable task;

	private String image = "";

	public Button(String text, int x, int y, int width, int height, boolean toolTip, ButtonRunnable task) {
		super(x, y, width, height);
		changeText(text);
		this.task = task;
		this.task.setButton(this);
		this.toolTip = toolTip;
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
			Vector2f loc = getDisplayLocation().clone().subtract(sprite.getWidth() / 2, sprite.getHeight() / 2).add((int) getDimensions().getWidth() / 2, (int) getDimensions().getHeight() / 2);
			Main.getScreen().renderSpriteIgnoringCamera(image, loc);
			Main.getScreen().renderQuad((int) loc.getX(), (int) loc.getY(), sprite.getWidth(), sprite.getHeight(), Component.DARK_ALPHA);
		}
//		if (toolTipDisplay && toolTip) {
//			int toolTipX = Mouse.getMouseX(), toolTipY = (int) (getParent().getDisplayLocation().getY() + getParent().getDimensions().getHeight() + 10);
//			Main.getScreen().renderQuad(toolTipX, toolTipY - text.getHeight() + 14, text.getWidth() + 14, text.getHeight() + 14, Component.DARK);
//			Main.getScreen().renderQuad(toolTipX + 2, toolTipY + 16 - text.getHeight(), text.getWidth() + 10, text.getHeight() + 10, Component.LIGHT);
//			FontUtil.renderText(text, toolTipX + text.getWidth() / 2 + 7, toolTipY - text.getHeight() / 2 + 21);
//		}
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
