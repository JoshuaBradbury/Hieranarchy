package uk.co.newagedev.hieranarchy.events.types;

public class Event {

	private boolean disabled = false;
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public boolean isDisabled() {
		return disabled;
	}
	
}
