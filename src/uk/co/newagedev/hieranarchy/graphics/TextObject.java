package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Font;

public class TextObject {

	private String sprite, text;
	private int width, height;
	private Font font;
	
	public TextObject(String sprite, String text, Font font, int width, int height) {
		this.sprite = sprite;
		this.font = font;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public String getSprite() {
		return sprite;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Font getFont() {
		return font;
	}
	
	public void destroy() {
		SpriteRegistry.removeSprite(sprite);
	}
}
