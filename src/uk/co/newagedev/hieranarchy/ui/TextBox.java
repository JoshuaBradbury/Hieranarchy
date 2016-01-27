package uk.co.newagedev.hieranarchy.ui;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.events.types.input.CursorClickEvent;
import uk.co.newagedev.hieranarchy.events.types.input.CursorMoveEvent;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class TextBox extends Component {

	private boolean hover = false, selected = false;
	private String text;
	private int selectedTimer = 0, cursorPlacement = 0;
	private int[] keyTimer = new int[KeyBinding.KEYBOARD_SIZE];

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
		Rectangle rect = new Rectangle((int) getDisplayLocation().getX(), (int) getDisplayLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight());
		Main.getScreen().renderQuad(rect, Colour.DARK_GREY);
		rect.grow(-1, -1);
		Main.getScreen().renderQuad(rect, (hover ? Colour.LIGHT_GREY : Colour.GREY));
		componentFont.renderText(text, (int) (getDisplayLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getDisplayLocation().getY() + (getDimensions().getHeight() / 2)));
		if (selected) {
			selectedTimer += 1;
			if (selectedTimer % 60 < 30) {
				int lineHeight = text.length() > 0 ? componentFont.getTextHeight(text) : 20;
				int cursorX = (int) (getDimensions().getWidth() / 2) - (componentFont.getTextWidth(text) / 2) + componentFont.getTextWidth(text.substring(0, cursorPlacement));
				Main.getScreen().renderLine(new Vector2f((int) getDisplayLocation().getX() + cursorX, (int) getDimensions().getHeight() / 2 + getDisplayLocation().getY() - lineHeight / 2), new Vector2f((int) getDisplayLocation().getX() + cursorX, (int) getDimensions().getHeight() / 2 + getDisplayLocation().getY() + lineHeight / 2), 1, Colour.BLACK);
			}
		} else {
			selectedTimer = 0;
		}
	}

	public void update() {
		if (selected) {
			for (int key : KeyBinding.getKeysDown()) {
				keyTimer[key] += 1;
				if (keyTimer[key] == 1 || keyTimer[key] > 30) {
					if (KeyBinding.getKeyName(key).trim().equalsIgnoreCase("backspace")) {
						removeFromText();
					} else if (KeyBinding.getKeyName(key).trim().equalsIgnoreCase("delete")) {
						if (cursorPlacement < text.length()) {
							cursorPlacement++;
							removeFromText();
						}
					} else if (KeyBinding.getKeyName(key).trim().equalsIgnoreCase("left")) {
						cursorPlacement--;
						if (cursorPlacement < 0) cursorPlacement++;
					} else if (KeyBinding.getKeyName(key).trim().equalsIgnoreCase("right")) {
						cursorPlacement++;
						if (cursorPlacement > text.length()) cursorPlacement--;
					} else {
						String kt = String.valueOf(KeyBinding.getKeyChar(key));
						char c = kt.toCharArray()[0];
						if (c >= 32 && c < 128) {
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
	
	public void cursorMove(CursorMoveEvent event) {
		hover = false;
		if (event.getX() > getDisplayLocation().getX() && event.getX() < getDisplayLocation().getX() + getDimensions().getWidth()) {
			if (event.getY() > getDisplayLocation().getY() && event.getY() < getDisplayLocation().getY() + getDimensions().getHeight()) {
				hover = true;
			}
		}
	}	
	
	public void cursorClick(CursorClickEvent event) {
		if (event.isButtonReleasing(Mouse.BUTTON_LEFT)) {
			selected = hover;
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
