package uk.co.newagedev.hieranarchy.util;

public class Colour {

	private Colour() {
	}

	public static final float[] BLACK            = new float[] {  0.0f,  0.0f,  0.0f, 1.0f };
	public static final float[] WHITE            = new float[] {  1.0f,  1.0f,  1.0f, 1.0f };
	public static final float[] RED              = new float[] {  1.0f,  0.0f,  0.0f, 1.0f };
	public static final float[] GREEN            = new float[] {  0.0f,  1.0f,  0.0f, 1.0f };
	public static final float[] BLUE             = new float[] {  0.0f,  0.0f,  1.0f, 1.0f };
	public static final float[] MAGENTA          = new float[] {  1.0f,  0.0f,  1.0f, 1.0f };
	public static final float[] YELLOW           = new float[] {  1.0f,  1.0f,  0.0f, 1.0f };
	public static final float[] TURQUOISE        = new float[] {  0.0f,  1.0f,  1.0f, 1.0f };
	public static final float[] ORANGE           = new float[] {  1.0f,  0.5f,  0.0f, 1.0f };
	public static final float[] GREY             = new float[] {  0.5f,  0.5f,  0.5f, 1.0f };
	public static final float[] DARK_GREY        = new float[] { 0.25f, 0.25f, 0.25f, 1.0f };
	public static final float[] DARK_DARK_GREY   = new float[] { 0.15f, 0.15f, 0.15f, 1.0f };
	public static final float[] LIGHT_GREY       = new float[] { 0.75f, 0.75f, 0.75f, 1.0f };
	public static final float[] LIGHT_LIGHT_GREY = new float[] { 0.85f, 0.85f, 0.85f, 1.0f };
	
	public static float[] vary(float[] colour, float r, float g, float b, float a) {
		return new float[] {colour[0] + r, colour[1] + g, colour[2] + b, colour[3] + a};
	}
}
