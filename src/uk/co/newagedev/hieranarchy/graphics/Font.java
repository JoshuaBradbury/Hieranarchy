package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Color;

import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.StringUtil;

public class Font {

	/** The chars that are in the font image in order. */
	private static String chars = "abcdefghijklmnopqrstuvwxyz(),1234567890 ";
	
	/** The size and spacing of the letters. */
	private int size, spacing;
	
	/** The name of the font's sprite. */
	private String fontImg;
	
	/** The colour of the font. */
	private Color colour;

	/**
	 * Instantiates a new font.
	 * @param fontImg - the sprite of the font.
	 * @param size - the size of the font letters.
	 * @param spacing - the spacing of the font letters.
	 */
	public Font(String fontImg, int size, int spacing) {
		this.fontImg = FileUtil.getFileNameWithoutExtension(fontImg);
		String[] letters = new String[chars.length()];
		for (int i = 0; i < chars.length(); i++) {
			letters[i] = this.fontImg + " - " + chars.charAt(i);
		}
		Main.getScreen().loadSpritesFromImage(fontImg, 32, 4, letters);
		this.size = size;
		this.spacing = spacing;
		this.colour = new Color(0x00, 0x00, 0x00);
	}

	/**
	 * Render text.
	 * @param text - the text to render.
	 * @param x - the x value of where to render the font.
	 * @param y - the y value of where to render the font.
	 */
	public void renderText(String text, int x, int y) {
		text = text.toLowerCase();
		for (int i = 0; i < text.length(); i++) {
			Sprite s = SpriteRegistry.getSprite(fontImg + " - " + text.charAt(i));
			int prevSize = s.getWidth();
			s.setWidth(size);
			s.setHeight(size);
			Main.getScreen().renderSpriteIgnoringCamera(fontImg + " - " + text.charAt(i), new Location(x + (i * (size + spacing)) - ((text.length() * (size + spacing)) / 2), y - (size / 2)), new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, new float[] { colour.getRed() / 255.0f, colour.getGreen() / 255.0f, colour.getBlue() / 255.0f, colour.getAlpha() / 255.0f });
			s.setWidth(prevSize);
			s.setHeight(prevSize);
		}
	}

	/**
	 * Sets the colour.
	 * @param colour - the new colour.
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * Calculates a string's width if rendered using a font.
	 * @param text - the text to measure.
	 * @return the width of the text.
	 */
	public int getTextWidth(String text) {
		return text.length() * (size + spacing);
	}

	/**
	 * Calculates a string's height if rendered using a font.
	 * @param text - the text to measure.
	 * @return the height of the text.
	 */
	public int getTextHeight(String text) {
		return (StringUtil.countOccurences(text, "\n") + 1) * (size + spacing) - spacing;
	}
}
