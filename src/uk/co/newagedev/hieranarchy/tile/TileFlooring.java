package uk.co.newagedev.hieranarchy.tile;

import uk.co.newagedev.hieranarchy.util.Location;

public class TileFlooring extends Tile {

	public TileFlooring(Location loc) {
		super(loc);
		setProperty("sprite", "flooring");
	}
}
