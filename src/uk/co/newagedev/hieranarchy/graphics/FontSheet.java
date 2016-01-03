package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.MathUtil;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class FontSheet {

	public static final String POSSIBLE_CHARACTERS = "1234567890!\"£$%^&*()-_+=`¬¦qwertyuiop[]asdfghjkl;'#\\zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:@~|ZXCVBNM<>? éóíúá";
	private HashMap<String, FontCharacter> fontCharacters = new HashMap<String, FontCharacter>();
	private Font font;
	private Color colour = Color.BLACK;

	public FontSheet(Font font) {
		this.font = font;
		generateFontCharacters();
	}

	public FontSheet(Font font, Color colour) {
		this.font = font;
		this.colour = colour;
		generateFontCharacters();
	}
	
	public Font getFont() {
		return font;
	}

	public void generateFontCharacters() {
		for (int i = 0; i < POSSIBLE_CHARACTERS.length(); i++) {
			String text = POSSIBLE_CHARACTERS.substring(i, i + 1);
			String name = font.getFontName() + "-" + font.getSize() + "-" + text;

			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics g = img.getGraphics();
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();
			int width = fm.stringWidth(text);
			int height = fm.getHeight();
			g.dispose();

			int imageWidth = (int) Math.pow(2, (int) Math.ceil(MathUtil.logab(2, width))), imageHeight = (int) Math.pow(2, (int) Math.ceil(MathUtil.logab(2, height)));

			if (imageWidth == 0 || imageHeight == 0)
				continue;

			BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) image.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g2d.setFont(font);
			fm = g2d.getFontMetrics();
			g2d.setColor(colour);
			g2d.drawString(text, 0, fm.getAscent());
			g2d.dispose();

			SpriteRegistry.registerImage(name, image);
			
			fontCharacters.put(text, new FontCharacter(text, name, width, height));
		}
	}
	
	public int getTextWidth(String text) {
		int width = 0;
		for (int i = 0; i < text.length(); i++) {
			FontCharacter fontCharacter = getFontCharacter(text.substring(i, i + 1));
			width += fontCharacter.getWidth() + 1;
		}
		return width;
	}
	
	public int getTextHeight(String text) {
		int maxHeight = 0;
		for (int i = 0; i < text.length(); i++) {
			FontCharacter fontCharacter = getFontCharacter(text.substring(i, i + 1));
			if (fontCharacter.getHeight() > maxHeight) {
				maxHeight = fontCharacter.getHeight();
			}
		}
		return maxHeight;
	}
	
	public void renderText(String text, int x, int y) {
		if (text != null) {
			int width = getTextWidth(text), height = getTextHeight(text);
			
			int progressiveX = x - width / 2;

			for (int i = 0; i < text.length(); i++) {
				String character = text.substring(i, i + 1);
				FontCharacter fontCharacter = getFontCharacter(character);
				Main.getScreen().renderSpriteIgnoringCamera(fontCharacter.getSprite(), new Vector2f(progressiveX, y - height / 2));
				progressiveX += fontCharacter.getWidth() + 1;
			}
		}
	}
	
	public FontCharacter getFontCharacter(String character) {
		if (POSSIBLE_CHARACTERS.indexOf(character) > -1) {
			return fontCharacters.get(character);
		}
		return null;
	}
}
