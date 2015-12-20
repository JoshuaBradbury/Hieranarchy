package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.graphics.FontSheet;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class TextBox extends Component {

	private boolean hover = false, selected = false;
	private String text;
	private int selectedTimer = 0, cursorPlacement = 0;
	private int[] keyTimer = new int[Keyboard.KEYBOARD_SIZE];

	public TextBox(int x, int y, int width, int height) {
		super(x, y, width, height);
		changeText("");
	}

	public void changeText(String text) {
		this.text = text;
	}

	public TextBox(String defaultText, int x, int y, int width, int height) {
		this(x, y, width, height);
		changeText(defaultText);
	}

	public void render() {
		Rectangle rect = new Rectangle((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight());
		Main.getScreen().renderQuad(rect, Component.DARK);
		rect.grow(-1, -1);
		Main.getScreen().renderQuad(rect, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		componentFont.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
		if (selected) {
			selectedTimer += 1;
			if (selectedTimer % 60 < 30) {
				int lineHeight = text.length() > 0 ? componentFont.getTextHeight(text) : 20;
				int cursorX = (int) (getDimensions().getWidth() / 2) - (componentFont.getTextWidth(text) / 2) + componentFont.getTextWidth(text.substring(0, cursorPlacement));
				Main.getScreen().renderLine(new Vector2f((int) getLocation().getX() + cursorX, (int) getDimensions().getHeight() / 2 + getLocation().getY() - lineHeight / 2), new Vector2f((int) getLocation().getX() + cursorX, (int) getDimensions().getHeight() / 2 + getLocation().getY() + lineHeight / 2), 1, Colour.BLACK);
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
						removeFromText();
					} else if (Keyboard.getKeyName(key).trim().equalsIgnoreCase("delete")) {
						if (cursorPlacement < text.length()) {
							cursorPlacement++;
							removeFromText();
						}
					} else if (Keyboard.getKeyName(key).trim().equalsIgnoreCase("left")) {
						cursorPlacement--;
						if (cursorPlacement < 0) cursorPlacement++;
					} else if (Keyboard.getKeyName(key).trim().equalsIgnoreCase("right")) {
						cursorPlacement++;
						if (cursorPlacement > text.length()) cursorPlacement--;
					} else {
						String kt = String.valueOf(KeyBinding.getKeyChar(key));
						if (FontSheet.POSSIBLE_CHARACTERS.contains(kt)) {
							addToText(kt);
						}
					}
				}
			}
			for (int key : KeyBinding.getKeysReleasing()) {
				keyTimer[key] = 0;
			}
		}
	}

	private void removeFromText() {
		if (cursorPlacement > 0) {
			text = text.substring(0, cursorPlacement - 1) + text.substring(cursorPlacement, text.length());
			cursorPlacement--;
		}
	}

	private void addToText(String add) {
		text = text.substring(0, cursorPlacement) + add + text.substring(cursorPlacement, text.length());
		cursorPlacement++;
	}

	public String getText() {
		return text;
	}
}
