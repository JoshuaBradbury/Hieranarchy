package uk.co.newagedev.hieranarchy.graphics;

public class FontCharacter {

	private int width, height;
	private String fontCharacter, sprite;
	
	public FontCharacter(String fontCharacter, String sprite, int width, int height) {
		this.width = width;
		this.height = height;
		this.fontCharacter = fontCharacter;
		this.sprite = sprite;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getFontCharacter() {
		return fontCharacter;
	}

	public String getSprite() {
		return sprite;
	}
}
