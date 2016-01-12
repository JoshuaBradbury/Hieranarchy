package uk.co.newagedev.hieranarchy.util;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class ImageUtil {

	public static ByteBuffer getSectionOfImage(ByteBuffer buffer, int x, int y, int width, int height, int originalWidth) {
		ByteBuffer result = BufferUtils.createByteBuffer(width * height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				result.put(i + j * width, buffer.get((y + j) * originalWidth + x + i));
			}
		}
		return result;
	}
}
