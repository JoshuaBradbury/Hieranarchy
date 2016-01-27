package uk.co.newagedev.hieranarchy.events.types.input;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class MouseMoveEvent extends Event {

	private double mx, my, mdx, mdy;
	
	public MouseMoveEvent(double mx, double my, double mdx, double mdy) {
		this.mx = mx;
		this.my = my;
		this.mdx = mdx;
		this.mdy = mdy;
	}

	public double getX() {
		return mx;
	}

	public double getY() {
		return my;
	}

	public double getDX() {
		return mdx;
	}

	public double getDY() {
		return mdy;
	}
}
