package uk.co.newagedev.hieranarchy;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.graphics.Background;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.KeyBinding;

public class Main {
	public static final String TITLE = "Hieranarchy";
	public static final int WIDTH = 720, HEIGHT = 480, SPRITE_WIDTH = 64, SPRITE_HEIGHT = 64;
	public static float SCALE = 1;
	public static Screen screen;
	private Thread thread;
	private boolean running;
	public static Map map;
	
	public void init() {
		initResources();
		initWorld();
		initBindings();
	}
	
	public void initResources() {
		SpriteRegistry.registerSprite("bg", "assets/textures/background.png");
		SpriteRegistry.registerSprite("flooring", "assets/textures/flooring.png");
		SpriteRegistry.registerSprite("icetile", "assets/textures/icesheet.png");
	}
	
	public void initBindings() {
		KeyBinding.bindKey("Left", Keyboard.KEY_LEFT);
		KeyBinding.bindKey("Right", Keyboard.KEY_RIGHT);
	}
	
	public void initWorld() {
		map = new Map("assets/maps/test.png");
		map.setBackground(new Background("bg", 0, 0, 2));
	}
	
	public static void main(String[] args) {
		Main hieranarchy = new Main();
		screen = new Screen();
		hieranarchy.init();
		hieranarchy.start();
	}
	
	public synchronized void start() {
		thread = new Thread("Hieranarchy");
		running = true;
		run();
	}
	
	public synchronized void stop() {
		cleanup();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}
	
	public void update() {
		map.update();
	}
	
	public void render() {
		screen.renderInit();
		map.render();
		screen.postRender();
	}
	
	public void run() {
		long secondTime = System.currentTimeMillis();
		int fps = 0;
		while (running) {
			update();
			render();
			fps++;
			if (System.currentTimeMillis() - secondTime >= 1000) {
				screen.setTitle(TITLE + "     FPS: " + fps);
				secondTime += 1000;
				fps = 0;
			}
			if (screen.shouldClose()) {
				stop();
			}
		}
	}
	
	public void cleanup() {
		SpriteRegistry.clear();
		screen.cleanup();
	}
}
