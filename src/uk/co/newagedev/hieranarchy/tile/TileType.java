package uk.co.newagedev.hieranarchy.tile;

import java.awt.Color;

public enum TileType {

	ICE(new Color(0, 0x88, 0x88), TileIce.class),
	FLOORING(new Color(0xaa, 0x44, 0), TileFlooring.class);
	
	private Color colour;
	private Class<? extends Tile> tileClass;
	
	TileType(Color colour, Class<? extends Tile> tileClass) {
		this.colour = colour;
		this.tileClass = tileClass;
	}
	
	public static TileType getTileTypeByColour(Color colour) {
		for (TileType type : values()) {
			if (type.colour.getRGB() == colour.getRGB()) {
				return type;
			}
		}
		return null;
	}
	
	public Class<? extends Tile> getTileClass() {
		return tileClass;
	}
}
