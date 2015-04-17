package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Color;

import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.StringUtil;

public class Font {

	private static String chars = "abcdefghijklmnopqrstuvwxyz(),1234567890 ";
	private int size, spacing;
	private String fontImg;
	private Color colour;

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

	public void setColour(Color colour) {
		this.colour = colour;
	}

	public int getTextWidth(String text) {
		return text.length() * (size + spacing);
	}

	public int getTextHeight(String text) {
		return (StringUtil.countOccurences(text, "\n") + 1) * (size + spacing) - spacing;
	}
}
