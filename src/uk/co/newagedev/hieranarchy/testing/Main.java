package uk.co.newagedev.hieranarchy.testing;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.util.KeyBinding;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.Mouse;

public class Main {
	
	/**
	 * 	The title of the application.
	 */
	public static final String TITLE = "Hieranarchy";
	
	/**
	 * The width and height of the window.
	 */
	public static final int WIDTH = 720, HEIGHT = 480;
	
	/**
	 * The default width and height of sprites.
	 */
	public static final int SPRITE_WIDTH = 64, SPRITE_HEIGHT = 64;
	
	/**
	 * The default scale of the application.
	 */
	public static float SCALE = 1;
	
	/**
	 * The screen variable of the application.
	 */
	public static Screen screen;
	
	/**
	 * The thread of the application.
	 */
	private Thread thread;
	
	/**
	 * Whether the application is running or not.
	 */
	private boolean running;
	
	/**
	 * The current state being displayed.
	 */
	public static String currentState;
	
	/**
	 * Bundle of init methods.
	 */
	public void init() {
		initResources();
		initStates();
		initBindings();
	}
	
	/**
	 * Initialises the resources for the game.
	 */
	public void initResources() {
		SpriteRegistry.registerSprite("bg", "assets/textures/background.png");
		SpriteRegistry.registerSprite("flooring", "assets/textures/crate.png");
		SpriteRegistry.registerSprite("icetile", "assets/textures/icesheet.png");
		SpriteRegistry.registerSprite("play", "assets/textures/play.png");
		SpriteRegistry.registerSprite("reset", "assets/textures/reset.png");
		SpriteRegistry.registerSprite("pause", "assets/textures/pause.png");
		Screen.loadFont("assets/textures/font.png");
	}
	
	/**
	 * Binds the initial keys for the engine.
	 */
	public void initBindings() {
		KeyBinding.bindKey("Left", Keyboard.KEY_LEFT);
		KeyBinding.bindKey("Right", Keyboard.KEY_RIGHT);
		KeyBinding.bindKey("editmapplay", Keyboard.KEY_RETURN);
	}
	
	/**
	 * Initialises the states.
	 */
	public void initStates() {
		StartMenuState state = new StartMenuState();
		currentState = "start menu";
		StateManager.registerState(currentState, state);
	}
	
	/**
	 * Main method for the program.
	 * @param args - Command line arguments.
	 */
	public static void main(String[] args) {
		Main hieranarchy = new Main();
		screen = new Screen();
		hieranarchy.init();
		hieranarchy.start();
	}
	
	/**
	 * Starts the thread and the engine.
	 */
	public synchronized void start() {
		thread = new Thread("Hieranarchy");
		running = true;
		run();
	}
	
	/**
	 * Stops the engine and calls for the resources to be cleaned up.
	 */
	public synchronized void stop() {
		cleanup();
		try {
			thread.join();
		} catch (InterruptedException e) {
			Logger.error(e.getMessage());
		}
		running = false;
	}
	
	/**
	 * Updates the current state.
	 */
	public void update() {
		Mouse.update();
		StateManager.getState(currentState).update();
	}
	
	/**
	 * Prepares the screen for rendering and then renders the current state.
	 */
	public void render() {
		screen.renderInit();
		StateManager.getState(currentState).render();
		screen.postRender();
	}
	
	/**
	 * Main loop for the program.
	 */
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
	
	/**
	 * Clears the sprite registry and destroys the display.
	 */
	public void cleanup() {
		SpriteRegistry.clear();
		screen.cleanup();
	}
	
	/**
	 * Sets the current state. 
	 * @param state - The state to change to.
	 */
	public static void setCurrentState(String state) {
		currentState = state;
	}
}
