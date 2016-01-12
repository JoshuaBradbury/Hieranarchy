package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.util.Vector2f;

public interface Screen {
	
	public void cleanup();
	
	public boolean shouldClose();
	public void close();
	
	public void setTitle(String title);
	public long getWindowID();
	
	public Sprite loadImageFromFile(String path);

	public void renderInit();
	public void postRender();

	public void renderSprite(String spriteName, Vector2f location, Camera camera);
	public void renderSprite(String spriteName, Vector2f location, Camera camera, float[] texCoords, float[] colour);
	public void renderSprite(String spriteName, Vector2f location, float width, float height, float[] rotation);
	public void renderSprite(String spriteName, Vector2f location, float width, float height, float[] texCoords, float[] colour, float[] rotation);

	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location);
	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location, Vector2f size, float[] texCoords, float[] colour);

	public void renderQuad(Vector2f loc, int width, int height, float[] colour);
	public void renderQuad(Vector2f loc, int width, int height, float[][] colours);
	public void renderQuad(Rectangle rect, float[] colour);
	public void renderQuad(Rectangle rect, float[][] colours);
	
	public void renderLine(Vector2f point1, Vector2f point2, float thickness, float[] colour);
	
	public void startScissor(Vector2f loc, int width, int height);
	public void stopScissor();
}
