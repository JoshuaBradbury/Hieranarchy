package uk.co.newagedev.hieranarchy.testing;

import org.lwjgl.input.Keyboard;

import uk.co.newagedev.hieranarchy.graphics.OpenGLScreen;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.util.Logger;

import com.google.gson.Gson;

public class Main {
	
	/**
	 * 	The title of the application.
	 */
	public static final String TITLE = "Hieranarchy";
	
	/**
	 * The instance of Gson used in multiple locations throughout the engine.
	 */
	public static final Gson GSON = new Gson();
	
	/**
	 * The current project in use/
	 */
	public static Project project = new Project("testing");
	
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
	public static OpenGLScreen screen;
	
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
		SpriteRegistry.registerSprite("bg", "Projects/testing/Assets/Textures/background.png");
		SpriteRegistry.registerSprite("play", "Projects/testing/Assets/Textures/gui/play.png");
		SpriteRegistry.registerSprite("reset", "Projects/testing/Assets/Textures/gui/reset.png");
		SpriteRegistry.registerSprite("pause", "Projects/testing/Assets/Textures/gui/pause.png");
		SpriteRegistry.registerSprite("edit", "Projects/testing/Assets/Textures/gui/edit.png");
		SpriteRegistry.registerSprite("new tile", "Projects/testing/Assets/Textures/gui/newtile.png");
		SpriteRegistry.registerSprite("arrow", "Projects/testing/Assets/Textures/gui/arrow.png");
		SpriteRegistry.registerSprite("arrow hover", "Projects/testing/Assets/Textures/gui/arrow hover.png");
	}
	
	/**
	 * Binds the initial keys for the engine.
	 */
	public void initBindings() {
		KeyBinding.bindKey("Left", Keyboard.KEY_LEFT);
		KeyBinding.bindKey("Right", Keyboard.KEY_RIGHT);
		KeyBinding.bindKey("Up", Keyboard.KEY_UP);
		KeyBinding.bindKey("Down", Keyboard.KEY_DOWN);
		KeyBinding.bindKey("editmapplay", Keyboard.KEY_RETURN);
		KeyBinding.bindKey("SelectPrevTile", Keyboard.KEY_Z);
		KeyBinding.bindKey("SelectNextTile", Keyboard.KEY_X);
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
		screen = new OpenGLScreen();
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
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}
		running = false;
	}
	
	/**
	 * Updates the current state.
	 */
	public void update() {
		Mouse.update();
		KeyBinding.update();
		if (StateManager.getState(currentState) != null) {
			StateManager.getState(currentState).update();
		}
	}
	
	/**
	 * Prepares the screen for rendering and then renders the current state.
	 */
	public void render() {
		screen.renderInit();
		if (StateManager.getState(currentState) != null) {
			StateManager.getState(currentState).render();
		}
		screen.postRender();
	}

	/**
	 * Gets the Screen.
	 * @return screen - instance of Screen.
	 */
	public static Screen getScreen() {
		return screen;
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
		project.cleanup();
		SpriteRegistry.clear();
		KeyBinding.cleanup();
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
