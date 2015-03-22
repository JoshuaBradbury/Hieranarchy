package uk.co.newagedev.hieranarchy.tile;

import uk.co.newagedev.hieranarchy.util.Location;

public class TileIce extends Tile {

	public TileIce(Location loc) {
		super(loc);
		setProperty("sprite", "icetile");
		setProperty("connected-textures", true);
	}

}
