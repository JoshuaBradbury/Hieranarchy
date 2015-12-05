package uk.co.newagedev.hieranarchy.ui;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.graphics.TextObject;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.FontUtil;

public class TextBox extends Component {

	private boolean hover = false, selected = false;
	private TextObject text;
	private int characterWidth, selectedTimer = 0;
	private int[] keyTimer = new int[Keyboard.KEYBOARD_SIZE];

	public TextBox(int x, int y, int width, int height) {
		super(x, y, width, height);
		changeText("");
	}
	
	public void changeText(String text) {
		if (this.text != null) {
			this.text.destroy();
		}
		this.text = FontUtil.getStringFromFont(componentFont, text);
	}
	
	public TextBox(String defaultText, int x, int y, int width, int height) {
		this(x, y, width, height);
		changeText(defaultText);
	}

	public void render() {
		Main.getScreen().renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), Component.DARK);
		Main.getScreen().renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY() + 5, (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 10, (hover ? Component.VERY_LIGHT : Component.LIGHT));
		FontUtil.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
		if (selected) {
			selectedTimer += 1;
			if (selectedTimer % 60 < 30) {
				Main.getScreen().renderLine(new int[] { (int) getLocation().getX() + 5 + (int) (getDimensions().getWidth() / 2) + (text.getWidth() / 2), (int) getLocation().getY() + 6 }, new int[] { (int) getLocation().getX() + 5 + (int) (getDimensions().getWidth() / 2) + (text.getWidth() / 2), (int) getDimensions().getHeight() + (int) getLocation().getY() - 6 }, 4, Component.VERY_LIGHT);
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
						removeLastFromText();
					} else if (Keyboard.getKeyName(key).trim().equalsIgnoreCase("space")) {
						addToText(" ");
					} else {
						if (text.getText().length() < characterWidth) {
							String kt = Keyboard.getKeyName(key);
							if ("abcdefghijklmnopqrstuvwxyz".contains(kt.toLowerCase())) {
								addToText(kt);
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
	
	private void removeLastFromText() {
		String text = getText();
		text = text.substring(0, text.length() - 1);
		changeText(text);
	}
	
	private void addToText(String add) {
		String text = getText();
		text += add;
		changeText(text);
	}

	public String getText() {
		return text.getText();
	}
}
