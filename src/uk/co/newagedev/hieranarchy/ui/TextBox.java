package uk.co.newagedev.hieranarchy.ui;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;

public class TextBox extends Component {

	private boolean hover = false, selected = false;
	private String text = "";
	private int characterWidth, selectedTimer = 0;
	private int[] keyTimer = new int[Keyboard.KEYBOARD_SIZE];

	public TextBox(int x, int y, int characterWidth) {
		super(x, y, componentFont.getTextWidth("a") * characterWidth, componentFont.getTextHeight("a") + 10);
		this.characterWidth = characterWidth;
	}

	public void render() {
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Screen.renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY() + 5, (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 10, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		componentFont.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
		if (selected) {
			selectedTimer += 1;
			if (selectedTimer % 60 < 30) {
				Screen.renderLine(new int[] { (int) getLocation().getX() + 5 + (int) (getDimensions().getWidth() / 2) + (componentFont.getTextWidth(text) / 2), (int) getLocation().getY() + 5 }, new int[] { (int) getLocation().getX() + 5 + (int) (getDimensions().getWidth() / 2) + (componentFont.getTextWidth(text) / 2), (int) getDimensions().getHeight() + (int) getLocation().getY() - 5 }, 4, Component.DARK);
			}
		} else {
			selectedTimer = 0;
		}
	}

	public void update() {
		hover = false;
		if (Mouse.getMouseX() > getLocation().getX() && Mouse.getMouseX() < getLocation().getX() + getDimensions().getWidth()) {
			if (Mouse.getMouseY() > getLocation().getY() && Mouse.getMouseY() < getLocation().getY() + getDimensions().getHeight()) {
				hover = true;
			}
		}
		if (Mouse.isButtonReleasing(Mouse.LEFT_BUTTON)) {
			selected = hover;
		}

		if (selected) {
			for (int key : KeyBinding.getKeysDown()) {
				keyTimer[key] += 1;
				if (keyTimer[key] == 1 || keyTimer[key] > 30) {
					if (Keyboard.getKeyName(key).trim().equalsIgnoreCase("back")) {
						text = text.substring(0, text.length() - 1);
					} else {
						if (text.length() < characterWidth) {
							String kt = Keyboard.getKeyName(key);
							if ("abcdefghijklmnopqrstuvwxyz".contains(kt.toLowerCase())) {
								text += kt;
							}
						}
					}
				}
			}
			for (int key : KeyBinding.getKeysReleasing()) {
				keyTimer[key] = 0;
			}
		}
	}

}
