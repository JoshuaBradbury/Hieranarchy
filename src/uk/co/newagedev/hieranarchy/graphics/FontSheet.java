package uk.co.newagedev.hieranarchy.graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class FontSheet {
	private STBTTBakedChar.Buffer cdata;
	private String fontName;
	private int fontSize;

	private static final int BITMAP_WIDTH = 512, BITMAP_HEIGHT = 512;

	public FontSheet(String fontName, int fontSize) {
		this.fontName = fontName;
		this.fontSize = fontSize;
		generateFontCharacters();
	}

	public void generateFontCharacters() {
		cdata = STBTTBakedChar.mallocBuffer(96);

		ByteBuffer bitmap = null;

		try {
			ByteBuffer ttf = FileUtil.readToByteBuffer(new FileInputStream(FileUtil.load("assets/fonts/" + fontName + ".ttf")));
			bitmap = BufferUtils.createByteBuffer(BITMAP_WIDTH * BITMAP_HEIGHT);
			STBTruetype.stbtt_BakeFontBitmap(ttf, fontSize, bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, 32, cdata);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (bitmap == null)
			return;

		SpriteRegistry.registerSprite(fontName + fontSize, new Sprite(bitmap, BITMAP_WIDTH, BITMAP_HEIGHT, GL11.GL_ALPHA));
	}

	public int getTextWidth(String text) {
		FloatBuffer x = BufferUtils.createFloatBuffer(1);
		FloatBuffer y = BufferUtils.createFloatBuffer(1);
		STBTTAlignedQuad q = STBTTAlignedQuad.malloc();

		x.put(0, 0);
		y.put(0, 0);

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				y.put(0, y.get(0) + fontSize);
				x.put(0, 0.0f);
				continue;
			} else if (c < 32 || 128 <= c)
				continue;

			STBTruetype.stbtt_GetBakedQuad(cdata, BITMAP_WIDTH, BITMAP_HEIGHT, c - 32, x, y, q, 1);
		}
		
		return (int) x.get(0);
	}

	public int getTextHeight(String text) {
		FloatBuffer x = BufferUtils.createFloatBuffer(1);
		FloatBuffer y = BufferUtils.createFloatBuffer(1);
		STBTTAlignedQuad q = STBTTAlignedQuad.malloc();

		x.put(0, 0);
		y.put(0, 0);

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '\n') {
				y.put(0, y.get(0) + fontSize);
				x.put(0, 0.0f);
				continue;
			} else if (c < 32 || 128 <= c)
				continue;

			STBTruetype.stbtt_GetBakedQuad(cdata, BITMAP_WIDTH, BITMAP_HEIGHT, c - 32, x, y, q, 1);
		}
		
		return (int) (y.get(0) == 0 ? fontSize : y.get(0));
	}

	public void renderText(String text, int xPos, int yPos) {
		FloatBuffer x = BufferUtils.createFloatBuffer(1);
		FloatBuffer y = BufferUtils.createFloatBuffer(1);
		STBTTAlignedQuad q = STBTTAlignedQuad.malloc();
		
		x.put(0, xPos);
		y.put(0, yPos);
		
		int textWidth = getTextWidth(text);

		if (text != null) {
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (c == '\n') {
					y.put(0, y.get(0) + fontSize);
					x.put(0, 0.0f);
					continue;
				} else if (c < 32 || 128 <= c)
					continue;

				STBTruetype.stbtt_GetBakedQuad(cdata, BITMAP_WIDTH, BITMAP_HEIGHT, c - 32, x, y, q, 1);
				
				Main.getScreen().renderSpriteIgnoringCamera(fontName + fontSize, new Vector2f(q.x0() - textWidth / 2, q.y0() + getTextHeight(String.valueOf(c)) / 4), new Vector2f(q.x1() - q.x0(), q.y1() - q.y0()), new float[] { q.s0(), q.s1(), q.t0(), q.t1() }, Colour.BLACK);
			}
		}
	}
}
