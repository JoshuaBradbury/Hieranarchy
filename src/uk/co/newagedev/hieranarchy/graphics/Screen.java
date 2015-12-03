package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.newdawn.slick.opengl.Texture;

import uk.co.newagedev.hieranarchy.util.Vector2f;

public interface Screen {
	
	public void cleanup();
	
	public boolean shouldClose();
	public void close();
	
	public void setTitle(String title);
	
	public Texture loadTexture(String path);
	public BufferedImage loadImage(String path);
	public void loadSpritesFromImage(String imagePath, int width, int height);
	public void loadSpritesFromImage(String imagePath, int width, int height, String[] names);
	public Texture getTextureFromImage(BufferedImage image);
	
	public void renderInit();
	public void postRender();

	public void renderSprite(String spriteName, Vector2f location, Camera camera);
	public void renderSprite(String spriteName, Vector2f location, Camera camera, float[] texCoords, float[] colour);
	public void renderSprite(String spriteName, float x, float y, float width, float height, float[] rotation);
	public void renderSprite(String spriteName, float x, float y, float width, float height, float[] texCoords, float[] colour, float[] rotation);

	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location);
	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location, float[] texCoords, float[] colour);

	public void renderQuad(int x, int y, int width, int height, float[] colour);
	public void renderQuad(int x, int y, int width, int height, float[][] colours);
	public void renderQuad(Rectangle rect, float[] colour);
	public void renderQuad(Rectangle rect, float[][] colours);
	
	public void renderLine(int[] point1, int[] point2, float thickness, float[] colour);
	
	public void startScissor(int x, int y, int width, int height);
	public void stopScissor();
}
