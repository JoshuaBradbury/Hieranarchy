package uk.co.newagedev.hieranarchy.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

import uk.co.newagedev.hieranarchy.util.Logger;

public class SpriteRegistry {

	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public static void registerSprite(String name, String path) {
		Texture texture = Screen.loadTexture(path);
		if (texture != null) {
			sprites.put(name, new Sprite(texture));
			Logger.info("\"" + path + "\" loaded as \"" + name + "\"");
		} else {
			Logger.error("The path \"" + path + "\" couldn't be loaded.");
		}
	}
	
	public static void registerImage(String name, BufferedImage image) {
		Texture texture = Screen.getTextureFromImage(image);
		if (texture != null) {
			sprites.put(name, new Sprite(texture));
		}
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