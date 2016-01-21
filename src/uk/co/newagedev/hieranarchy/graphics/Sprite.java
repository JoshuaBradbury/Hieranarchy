package uk.co.newagedev.hieranarchy.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

public class Sprite {
	private ByteBuffer image;
	private int textureID, width, height;

	public Sprite(ByteBuffer image, int width, int height, int glFormat) {
		this.image = image;
		this.width = width;
		this.height = height;
		
		textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glFormat, width, height, 0, glFormat, GL11.GL_UNSIGNED_BYTE, image);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getImageData() {
		return image;
	}

	public void release() {
		GL11.glDeleteTextures(textureID);
	}
}
