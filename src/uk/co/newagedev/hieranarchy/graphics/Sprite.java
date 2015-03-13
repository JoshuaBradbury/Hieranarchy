package uk.co.newagedev.hieranarchy.graphics;

import org.newdawn.slick.opengl.Texture;

public class Sprite {
	private Texture texture;
	private int width, height;
	
	public Sprite(Texture texture) {
		this.texture = texture;
		width = texture.getImageWidth();
		height = texture.getImageHeight();
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void bind() {
		texture.bind();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void release() {
		texture.release();
	}
}
