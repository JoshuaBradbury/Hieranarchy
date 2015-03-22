package uk.co.newagedev.hieranarchy.tile;

import java.awt.Color;

public enum TileType {

	ICE(new Color(0, 0x88, 0x88)),
	FLOORING(new Color(0xaa, 0x44, 0));
	
	private Color colour;
	
	TileType(Color colour) {
		this.colour = colour;
	}
	
	public static TileType getTileTypeByColour(Color colour) {
		for (TileType type : values()) {
			if (type.colour.getRGB() == colour.getRGB()) {
				return type;
			}
		}
		return null;
	}
}
