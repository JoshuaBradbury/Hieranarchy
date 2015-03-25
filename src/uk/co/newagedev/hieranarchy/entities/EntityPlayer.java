package uk.co.newagedev.hieranarchy.entities;

import uk.co.newagedev.hieranarchy.util.Location;

public class EntityPlayer extends Entity {

	/**
	 * The constructor of EntityPlayer.
	 * @param sprite - the sprite of the player.
	 * @param loc - the starting location of the player.
	 */
	public EntityPlayer(String sprite, Location loc) {
		super(sprite, loc);
	}

}
