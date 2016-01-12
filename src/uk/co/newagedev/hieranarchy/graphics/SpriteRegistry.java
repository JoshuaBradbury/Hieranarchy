package uk.co.newagedev.hieranarchy.graphics;

import java.util.HashMap;
import java.util.Map;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.Logger;

public class SpriteRegistry {

	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public static void registerSprite(String name, String path) {
		Sprite sprite = Main.getScreen().loadImageFromFile(path);
		if (sprite != null) {
			sprites.put(name, sprite);
			Logger.info("\"" + path + "\" loaded as \"" + name + "\"");
		} else {
			Logger.error("The path \"" + path + "\" couldn't be loaded.");
		}
	}
	
	public static void registerSprite(String name, Sprite sprite) {
		sprites.put(name, sprite);
	}
	
	public static Sprite getSprite(String name) {
		return sprites.get(name);
	}
	
	public static boolean doesSpriteExist(String name) {
		return sprites.containsKey(name);
	}
	
	public static void removeSprite(String name) {
		if (doesSpriteExist(name)) {
			Sprite sprite = getSprite(name);
			sprite.release();
			sprites.remove(name);
		}
	}
	
	public static void clear() {
		while (sprites.size() > 0) {
			removeSprite(sprites.keySet().iterator().next());
		}
	}
}