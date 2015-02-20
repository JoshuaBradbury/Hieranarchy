package uk.co.newagedev.hieranarchy.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

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

import uk.co.newagedev.hieranarchy.Main;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Location;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Screen {

	public Screen() {
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
		return Display.isCloseRequested();
	}

	public static Texture loadTexture(String path) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(FileUtil.getExtension(path).toUpperCase(), new FileInputStream(new File(path)));
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
		return texture;
	}

	public static void renderSprite(String spriteName, Location location, Camera camera) {
		renderSprite(spriteName, location, camera, new float[] { 0.0f, 1.0f, 0.0f, 1.0f });
	}

	public static void renderSprite(String spriteName, Location location, Camera camera, float[] texCoords) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			int width = Main.SPRITE_WIDTH;
			int height = Main.SPRITE_HEIGHT;
			Location tloc = location.clone().multiply(new Location(width * camera.getZoom(), height * camera.getZoom())).add(new Location(camera.getX(), camera.getY()));
			renderSprite(spriteName, tloc.getX(), tloc.getY(), width, height, texCoords);
		}
	}

	public static void renderSpriteIgnoringCamera(String spriteName, Location location) {
		renderSpriteIgnoringCamera(spriteName, location, new float[] { 0.0f, 1.0f, 0.0f, 1.0f });
	}

	public static void renderSpriteIgnoringCamera(String spriteName, Location location, float[] texCoords) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			Sprite sprite = SpriteRegistry.getSprite(spriteName);
			renderSprite(spriteName, location.getX(), location.getY(), sprite.getWidth(), sprite.getHeight(), texCoords);
		}
	}

	public static void renderSprite(String texture, float x, float y, float width, float height, float[] texCoords) {
		if (SpriteRegistry.doesSpriteExist(texture)) {
			SpriteRegistry.getSprite(texture).bind();
			glBegin(GL_QUADS);
			{
				glTexCoord2f(texCoords[1], texCoords[3]);
				glVertex2f(x + width, y + height);
				glTexCoord2f(texCoords[1], texCoords[2]);
				glVertex2f(x + width, y);
				glTexCoord2f(texCoords[0], texCoords[2]);
				glVertex2f(x, y);
				glTexCoord2f(texCoords[0], texCoords[3]);
				glVertex2f(x, y + height);
			}
			glEnd();
		}
	}

	public void setTitle(String title) {
		Display.setTitle(title);
	}

	public void renderInit() {
		glEnable(GL_TEXTURE_2D);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Main.WIDTH, Main.HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
	}

	public void postRender() {
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

}