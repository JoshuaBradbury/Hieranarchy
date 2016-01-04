package uk.co.newagedev.hieranarchy.main;

import java.util.Stack;

import org.lwjgl.input.Keyboard;

import com.google.gson.Gson;

import uk.co.newagedev.hieranarchy.graphics.OpenGLScreen;
import uk.co.newagedev.hieranarchy.graphics.Screen;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.input.KeyBinding;
import uk.co.newagedev.hieranarchy.input.Mouse;
import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.state.PopupState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Main {

	public static final String TITLE = "Hieranarchy";

	public static final Gson GSON = new Gson();

	public static Project project = new Project("testing");

	public static final int WIDTH = 1024, HEIGHT = 576;

	public static final int SPRITE_WIDTH = 64, SPRITE_HEIGHT = 64;

	public static float SCALE = 1;

	public static OpenGLScreen screen;

	private Thread thread;

	private boolean running;

	private static String currentState;

	private static Stack<String> popupStack = new Stack<String>();

	public void init() {
		initResources();
		initStates();
		initBindings();
	}

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

	public void initBindings() {
		KeyBinding.bindKey("Left", Keyboard.KEY_LEFT);
		KeyBinding.bindKey("Right", Keyboard.KEY_RIGHT);
		KeyBinding.bindKey("Up", Keyboard.KEY_UP);
		KeyBinding.bindKey("Down", Keyboard.KEY_DOWN);
		KeyBinding.bindKey("editmapplay", Keyboard.KEY_RETURN);
		KeyBinding.bindKey("SelectPrevTile", Keyboard.KEY_Z);
		KeyBinding.bindKey("SelectNextTile", Keyboard.KEY_X);
	}

	public void initStates() {
		StartMenuState state = new StartMenuState();
		currentState = "start menu";
		StateManager.registerState(currentState, state);
	}

	public static void main(String[] args) {
		Main hieranarchy = new Main();
		screen = new OpenGLScreen();
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
			Logger.error(e.getMessage());
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}
		running = false;
	}

	public void update() {
		Mouse.update();
		KeyBinding.update();
		if (StateManager.getState(currentState) != null) {
			StateManager.getState(currentState).update();
		}
	}

	public void render() {
		screen.renderInit();
		if (StateManager.getState(currentState) != null) {
			StateManager.getState(currentState).render();
		}
		screen.postRender();
	}

	public static Screen getScreen() {
		return screen;
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

	public static void popup(String title, Container contents, ButtonRunnable runnable) {
		if (!popupStack.isEmpty()) {
			currentState = ((PopupState) StateManager.getState(popupStack.lastElement())).getState();
		}
		PopupState popup = new PopupState(title, contents, currentState, new ButtonRunnable() {
			public void run() {
				if (!popupStack.isEmpty())
					setCurrentState(((PopupState) StateManager.getState(popupStack.lastElement())).getState());
				StateManager.removeState(popupStack.pop());

				runnable.setButton(button);
				runnable.run();
			}
		}, new ButtonRunnable() {
			public void run() {
				if (!popupStack.isEmpty())
					setCurrentState(((PopupState) StateManager.getState(popupStack.lastElement())).getState());
				StateManager.removeState(popupStack.pop());
			}
		});
		StateManager.registerState(title, popup);
		setCurrentState(title);
		popupStack.push(title);

	}

	public void cleanup() {
		project.cleanup();
		SpriteRegistry.clear();
		KeyBinding.cleanup();
		screen.cleanup();
	}

	public static void setCurrentState(String state) {
		currentState = state;
	}

	public static String getCurrentState() {
		return currentState;
	}
}
