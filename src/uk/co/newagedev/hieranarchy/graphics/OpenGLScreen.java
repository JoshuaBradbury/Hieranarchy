package uk.co.newagedev.hieranarchy.graphics;

import java.awt.Font;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.Vector2f;

public class OpenGLScreen implements Screen {

	private boolean close = false;
	public static final boolean DEBUG = false;
	private Font screenFont;
	private long windowID;

	@SuppressWarnings("unused")
	private GLFWFramebufferSizeCallback framebufferSizeCallback;
	@SuppressWarnings("unused")
	private GLFWErrorCallback errorCallback;

	public OpenGLScreen() {
		if (GLFW.glfwInit() != GLFW.GLFW_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

		GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		windowID = GLFW.glfwCreateWindow(Main.WIDTH, Main.HEIGHT, Main.TITLE, 0, 0);

		if (windowID == 0) {
			throw new RuntimeException("Failed to create GLFW window");
		}

		GLFW.glfwSetFramebufferSizeCallback(windowID, (framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				Main.WIDTH = width;
				Main.HEIGHT = height;
				Logger.info(width, height);
			}
		}));

		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

		GLFW.glfwSetWindowPos(windowID, (vidmode.width() - Main.WIDTH) / 2, (vidmode.height() - Main.HEIGHT) / 2);

		GLFW.glfwMakeContextCurrent(windowID);
		GL.createCapabilities();

		GLFW.glfwShowWindow(windowID);
	}

	public void cleanup() {
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
	}

	public long getWindowID() {
		return windowID;
	}

	private long variableYieldTime, lastTime;

	private void sync(int fps) {
		if (fps <= 0)
			return;

		long sleepTime = 1000000000 / fps;
		long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
		long overSleep = 0;

		try {
			while (true) {
				long t = System.nanoTime() - lastTime;

				if (t < sleepTime - yieldTime) {
					Thread.sleep(1);
				} else if (t < sleepTime) {
					Thread.yield();
				} else {
					overSleep = t - sleepTime;
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

			if (overSleep > variableYieldTime) {
				variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime);
			} else if (overSleep < variableYieldTime - 200 * 1000) {
				variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0);
			}
		}
	}

	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowID) == GL11.GL_TRUE || close;
	}

	public Sprite loadImageFromFile(String path) {
		ByteBuffer image = null, imageBuffer;

		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer components = BufferUtils.createIntBuffer(1);

		try {
			imageBuffer = FileUtil.readToByteBuffer(new FileInputStream(path));

			image = STBImage.stbi_load_from_memory(imageBuffer, width, height, components, 0);

			if (image == null)
				throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}

		return new Sprite(image, width.get(0), height.get(0), components.get(0) == 3 ? GL11.GL_RGB : GL11.GL_RGBA);
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
				renderSprite(spriteName, tloc, width, height, texCoords, colour, new float[] { 0.0f, 0.0f, 0.0f });
			}
		}
	}

	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			Sprite sprite = SpriteRegistry.getSprite(spriteName);
			renderSpriteIgnoringCamera(spriteName, location, new Vector2f(sprite.getWidth(), sprite.getHeight()), new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
		}
	}

	public void renderSpriteIgnoringCamera(String spriteName, Vector2f location, Vector2f size, float[] texCoords, float[] colour) {
		renderSprite(spriteName, location, size.getX(), size.getY(), texCoords, colour, new float[] { 0.0f, 0.0f, 0.0f });
	}

	public void renderSprite(String spriteName, Vector2f location, float width, float height, float[] rotation) {
		renderSprite(spriteName, location, width, height, new float[] { 0.0f, 1.0f, 0.0f, 1.0f }, new float[] { 1.0f, 1.0f, 1.0f, 1.0f }, rotation);
	}

	public void renderSprite(String spriteName, Vector2f location, float width, float height, float[] texCoords, float[] colour, float[] rotation) {
		if (SpriteRegistry.doesSpriteExist(spriteName)) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			SpriteRegistry.getSprite(spriteName).bind();
			GL11.glColor4f(colour[0], colour[1], colour[2], colour[3]);
			GL11.glPushMatrix();
			GL11.glTranslatef(location.getX(), location.getY(), 0.0f);
			GL11.glRotatef(rotation[0], 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(rotation[1], 0.0f, 1.0f, 0.0f);
			GL11.glRotatef(rotation[2], 0.0f, 0.0f, 1.0f);
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glTexCoord2f(texCoords[1], texCoords[3]);
				GL11.glVertex2f(width, height);
				GL11.glTexCoord2f(texCoords[1], texCoords[2]);
				GL11.glVertex2f(width, 0.0f);
				GL11.glTexCoord2f(texCoords[0], texCoords[2]);
				GL11.glVertex2f(0.0f, 0.0f);
				GL11.glTexCoord2f(texCoords[0], texCoords[3]);
				GL11.glVertex2f(0.0f, height);
			}
			GL11.glEnd();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowID, title);
	}

	public void renderInit() {
		GL11.glViewport(0, 0, Main.WIDTH, Main.HEIGHT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Main.WIDTH, Main.HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();
	}

	public void postRender() {
		if (DEBUG) {
			if (screenFont == null) {
				screenFont = new Font("Tahoma", Font.PLAIN, 10);
			}
			String text = "(" + String.valueOf(Mouse.getMouseX()) + "," + String.valueOf(Mouse.getMouseY()) + ")";
		}

		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(windowID);
		sync(60);
	}

	public void renderQuad(Rectangle rect, float[] colour) {
		renderQuad(new Vector2f((int) rect.getX(), (int) rect.getY()), (int) rect.getWidth(), (int) rect.getHeight(), colour);
	}

	public void renderQuad(Rectangle rect, float[][] colours) {
		renderQuad(new Vector2f((int) rect.getX(), (int) rect.getY()), (int) rect.getWidth(), (int) rect.getHeight(), colours);
	}

	public void renderQuad(Vector2f loc, int width, int height, float[] colour) {
		renderQuad(loc, width, height, new float[][] { colour, colour, colour, colour });
	}

	public void renderQuad(Vector2f loc, int width, int height, float[][] colours) {
		if (colours[0].length == 4) {
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glColor4f(colours[0][0], colours[0][1], colours[0][2], colours[0][3]);
				GL11.glVertex2f(loc.getX() + width, loc.getY() + height);
				GL11.glColor4f(colours[1][0], colours[1][1], colours[1][2], colours[1][3]);
				GL11.glVertex2f(loc.getX() + width, loc.getY());
				GL11.glColor4f(colours[2][0], colours[2][1], colours[2][2], colours[1][3]);
				GL11.glVertex2f(loc.getX(), loc.getY());
				GL11.glColor4f(colours[3][0], colours[3][1], colours[3][2], colours[1][3]);
				GL11.glVertex2f(loc.getX(), loc.getY() + height);
			}
			GL11.glEnd();
		} else {
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glColor3f(colours[0][0], colours[0][1], colours[0][2]);
				GL11.glVertex2f(loc.getX() + width, loc.getY() + height);
				GL11.glColor3f(colours[1][0], colours[1][1], colours[1][2]);
				GL11.glVertex2f(loc.getX() + width, loc.getY());
				GL11.glColor3f(colours[2][0], colours[2][1], colours[2][2]);
				GL11.glVertex2f(loc.getX(), loc.getY());
				GL11.glColor3f(colours[3][0], colours[3][1], colours[3][2]);
				GL11.glVertex2f(loc.getX(), loc.getY() + height);
			}
			GL11.glEnd();
		}
	}

	public void renderLine(Vector2f point1, Vector2f point2, float thickness, float[] colour) {
		GL11.glColor3f(colour[0], colour[1], colour[2]);
		float t = thickness / 2;
		int x = (int) point1.getX(), x2 = (int) point2.getX(), y = (int) point1.getY(), y2 = (int) point2.getY();
		float hyp = (float) Math.sqrt(((x2 - x) * (x2 - x)) + ((y2 - y) * (y2 - y)));
		float angle = (float) Math.acos(((x2 - x) / hyp) % 1);
		float[][] points = new float[4][2];
		points[0] = new float[] { (float) (x + (Math.cos(angle + (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle + (Math.PI / 2)) * t)) };
		points[1] = new float[] { (float) (x + (Math.cos(angle - (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle - (Math.PI / 2)) * t)) };
		points[2] = new float[] { (float) (x + (Math.cos(angle) * hyp) + (Math.cos(angle + (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle) * hyp) + (Math.sin(angle + (Math.PI / 2)) * t)) };
		points[3] = new float[] { (float) (x + (Math.cos(angle) * hyp) + (Math.cos(angle - (Math.PI / 2)) * t)), (float) (y + (Math.sin(angle) * hyp) + (Math.sin(angle - (Math.PI / 2)) * t)) };
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2f(points[2][0], points[2][1]);
			GL11.glVertex2f(points[0][0], points[0][1]);
			GL11.glVertex2f(points[1][0], points[1][1]);
			GL11.glVertex2f(points[3][0], points[3][1]);
		}
		GL11.glEnd();
	}

	public void close() {
		close = true;
	}

	public void startScissor(Vector2f loc, int width, int height) {
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int) loc.getX(), (int) loc.getY(), width, height);
	}

	public void stopScissor() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}