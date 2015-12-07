package uk.co.newagedev.hieranarchy.graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.BufferedImageUtil;

import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.testing.Main;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class OpenGLScreen implements Screen {

	private boolean close = false;
	public static final boolean DEBUG = false;
	private Font screenFont;

	public OpenGLScreen() {
		try {
			PixelFormat pixelFormat = new PixelFormat();

			Display.setDisplayMode(new DisplayMode(Main.WIDTH, Main.HEIGHT));
			Display.setTitle(Main.TITLE);
			Display.create(pixelFormat);
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}
	}

	public void cleanup() {
		Display.destroy();
	}

	public boolean shouldClose() {
		return Display.isCloseRequested() || close;
	}

	public Texture loadTexture(String path) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(FileUtil.getExtension(path).toUpperCase(), new FileInputStream(new File(path)));
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
		return texture;
	}

	public void renderSprite(String spriteName, Vector2f location, Camera camera) {
		renderSprite(spriteName, location, camera, new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
	}

	public void renderSprite(String spriteName, Vector2f location, Camera camera, float[] texCoords, float[] colour) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			if (camera != null) {
				int width = Main.SPRITE_WIDTH;
				int height = Main.SPRITE_HEIGHT;
				Vector2f tloc = location.clone().multiply(new Vector2f(width * camera.getZoom(), height * camera.getZoom())).add(new Vector2f(-camera.getX(), camera.getY()));
				renderSprite(spriteName, tloc.getX(), tloc.getY(), width, height, texCoords, colour, new float[] { 0.0f, 0.0f, 0.0f });
			}
		}
	}

	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location) {
		renderSpriteIgnoringCamera(spriteName, location, new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
	}

	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location, float[] texCoords, float[] colour) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			Sprite sprite = SpriteRegistry.getSprite(spriteName);
			renderSprite(spriteName, location.getX(), location.getY(), sprite.getWidth(), sprite.getHeight(), texCoords, colour, new float[] { 0.0f, 0.0f, 0.0f });
		}
	}
	
	public void renderSprite(String spriteName, float x, float y, float width, float height, float[] rotation) {
		renderSprite(spriteName, x, y, width, height, new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, new float[] { 1.0f, 1.0f, 1.0f, 1.0f }, rotation);
	}
	
	public void renderSprite(String spriteName, float x, float y, float width, float height, float[] texCoords, float[] colour, float[] rotation) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			glEnable(GL_TEXTURE_2D);
			SpriteRegistry.getSprite(spriteName).bind();
			glColor4f(colour[0], colour[1], colour[2], colour[3]);
			glPushMatrix();
			glTranslatef(x, y, 0.0f);
			glRotatef(rotation[0], 1.0f, 0.0f, 0.0f);
			glRotatef(rotation[1], 0.0f, 1.0f, 0.0f);
			glRotatef(rotation[2], 0.0f, 0.0f, 1.0f);
			glBegin(GL_QUADS);
			{
				glTexCoord2f(texCoords[1], texCoords[3]);
				glVertex2f(width, height);
				glTexCoord2f(texCoords[1], texCoords[2]);
				glVertex2f(width, 0.0f);
				glTexCoord2f(texCoords[0], texCoords[2]);
				glVertex2f(0.0f, 0.0f);
				glTexCoord2f(texCoords[0], texCoords[3]);
				glVertex2f(0.0f, height);
			}
			glEnd();
			glPopMatrix();
			glDisable(GL_TEXTURE_2D);
		}
	}

	public void setTitle(String title) {
		Display.setTitle(title);
	}

	public void renderInit() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Main.WIDTH, Main.HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}

	public void postRender() {
		if (DEBUG) {
			if (screenFont == null) {
				screenFont = new Font("Tahoma", Font.PLAIN, 10);
			}
			String text = "(" + String.valueOf(Mouse.getMouseX()) + "," + String.valueOf(Mouse.getMouseY()) + ")";
		}
		Display.update();
		Display.sync(60);
	}

	public BufferedImage loadImage(String path) {
		File imageFile = new File(path);
		if (imageFile.exists()) {
			try {
				return ImageIO.read(imageFile);
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
		return null;
	}

	public void loadSpritesFromImage(String imagePath, int width, int height) {
		String[] names = new String[width * height];
		for (int i = 0; i < (width * height); i++) {
			names[i] = FileUtil.getFileNameWithoutExtension(imagePath) + Math.floor(i / width) + (i % width);
		}
		loadSpritesFromImage(imagePath, width, height, names);
	}

	public void loadSpritesFromImage(String imagePath, int width, int height, String[] names) {
		int max = names.length;
		if (FileUtil.doesFileExist(imagePath)) {
			BufferedImage image;
			try {
				image = ImageIO.read(FileUtil.load(imagePath));
				int tileWidth = (image.getWidth() / width);
				int tileHeight = (image.getHeight() / height);
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						if (y * width + x < max) {
							SpriteRegistry.registerImage(names[x + (y * width)], image.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
						}
					}
				}
				Logger.info("\"" + imagePath + "\"", "loaded sheet", "width:", width, "height:", height, "tile width:", tileWidth, "tile height:", tileHeight);
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
	}

	public Texture getTextureFromImage(BufferedImage image) {
		try {
			return BufferedImageUtil.getTexture("", image);
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
		return null;
	}
	
	public void renderQuad(Rectangle rect, float[] colour) {
		renderQuad((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), colour);
	}

	public void renderQuad(Rectangle rect, float[][] colours) {
		renderQuad((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), colours);
	}

	public void renderQuad(int x, int y, int width, int height, float[] colour) {
		renderQuad(x, y, width, height, new float[][] { colour, colour, colour, colour });
	}

	public void renderQuad(int x, int y, int width, int height, float[][] colours) {
		if (colours[0].length == 4) {
			glBegin(GL_QUADS);
			{
				glColor4f(colours[0][0], colours[0][1], colours[0][2], colours[0][3]);
				glVertex2f(x + width, y + height);
				glColor4f(colours[1][0], colours[1][1], colours[1][2], colours[1][3]);
				glVertex2f(x + width, y);
				glColor4f(colours[2][0], colours[2][1], colours[2][2], colours[1][3]);
				glVertex2f(x, y);
				glColor4f(colours[3][0], colours[3][1], colours[3][2], colours[1][3]);
				glVertex2f(x, y + height);
			}
			glEnd();
		} else {
			glBegin(GL_QUADS);
			{
				glColor3f(colours[0][0], colours[0][1], colours[0][2]);
				glVertex2f(x + width, y + height);
				glColor3f(colours[1][0], colours[1][1], colours[1][2]);
				glVertex2f(x + width, y);
				glColor3f(colours[2][0], colours[2][1], colours[2][2]);
				glVertex2f(x, y);
				glColor3f(colours[3][0], colours[3][1], colours[3][2]);
				glVertex2f(x, y + height);
			}
			glEnd();
		}
	}

	public void renderLine(int[] point1, int[] point2, float thickness, float[] colour) {
		glColor3f(colour[0], colour[1], colour[2]);
		float t = thickness / 2;
		int x = point1[0], x2 = point2[0], y = point1[1], y2 = point2[1];
		float hyp = (float) Math.sqrt(((x2 - x) * (x2 - x)) + ((y2 - y) * (y2 - y)));
		float angle = (float) Math.acos(((x2 - x) / hyp) % 1);
		float[][] points = new float[4][2];
		points[0] = new float[] { (float) (x + (Math.cos(angle + (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle + (Math.PI / 2)) * t)) };
		points[1] = new float[] { (float) (x + (Math.cos(angle - (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle - (Math.PI / 2)) * t)) };
		points[2] = new float[] { (float) (x + (Math.cos(angle) * hyp) + (Math.cos(angle + (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle) * hyp) + (Math.sin(angle + (Math.PI / 2)) * t)) };
		points[3] = new float[] { (float) (x + (Math.cos(angle) * hyp) + (Math.cos(angle - (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle) * hyp) + (Math.sin(angle - (Math.PI / 2)) * t)) };
		glBegin(GL_QUADS);
		{
			glVertex2f(points[2][0], points[2][1]);
			glVertex2f(points[0][0], points[0][1]);
			glVertex2f(points[1][0], points[1][1]);
			glVertex2f(points[3][0], points[3][1]);
		}
		glEnd();
	}

	public void close() {
		close = true;
	}

	public void startScissor(int x, int y, int width, int height) {
		glEnable(GL_SCISSOR_TEST);
		glScissor(x, y, width, height);
	}
	
	public void stopScissor() {
		glDisable(GL_SCISSOR_TEST);
	}
}