package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;

public class Button extends Component {

	private String text;
	
	public Button(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.text = text;
	}

	public void render() {
		Screen.renderQuad((int) getLocation().getX(), (int) getLocation().getY(), (int) getDimensions().getWidth(), (int) getDimensions().getHeight(), new float[] {0.3f, 0.3f, 0.3f});
		Screen.renderQuad((int) getLocation().getX() + 5, (int) getLocation().getY() + 5, (int) getDimensions().getWidth() - 10, (int) getDimensions().getHeight() - 10, new float[] {0.6f, 0.6f, 0.6f});
		Screen.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
	}
	
	public void update() {
		
	}
}
