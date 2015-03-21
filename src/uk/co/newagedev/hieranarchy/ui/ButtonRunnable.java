package uk.co.newagedev.hieranarchy.ui;

public abstract class ButtonRunnable implements Runnable {
	public Button button;
	
	public void setButton(Button button) {
		this.button = button;
	}
	
	public abstract void run();
}
