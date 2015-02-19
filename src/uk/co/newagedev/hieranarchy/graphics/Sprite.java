package uk.co.newagedev.hieranarchy.graphics;

import org.newdawn.slick.opengl.Texture;

public class Sprite {
	private Texture texture;
	
	public Sprite(Texture texture) {
		this.texture = texture;
	}
	
	public void bind() {
		texture.bind();
	}
	
	public int getWidth() {
		return texture.getImageWidth();
	}
	
	public int getHeight() {
		return texture.getImageHeight();
	}
	
	public void release() {
		texture.release();
	}
}
