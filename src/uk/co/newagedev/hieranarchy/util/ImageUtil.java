package uk.co.newagedev.hieranarchy.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;

import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;

public class ImageUtil {

	public static Texture getSectionOfTexture(Texture full, int x, int y, int width, int height) {
		int tx = 0, ty = 0, tw = full.getImageWidth(), th = full.getImageHeight();

		if (x > 0 && x + width < full.getImageWidth()) {
			tx = x;
			tw = width;
		}
		if (y > 0 && y + height < full.getImageHeight()) {
			ty = y;
			th = height;
		}

		InputStream in = new ByteArrayInputStream(full.getTextureData());
		BufferedImage img = null;

		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (img == null)
			return null;

		String imgName = "image util temp " + full.getTextureData().length;
		
		SpriteRegistry.registerImage(imgName, img.getSubimage(tx, ty, tw, th));
		
		Texture result = SpriteRegistry.getSprite(imgName).getTexture();
		
		return result;
	}

	public static void saveImageToFile(BufferedImage image, String fileName) {
		try {
			File file = new File(fileName);
			ImageIO.write(image, "png", file);
			Logger.info(fileName, "saved at", file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
