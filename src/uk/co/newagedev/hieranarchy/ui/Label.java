package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.Screen;

public class Label extends Component {

	private String text;
	
	public Label(String text, int x, int y) {
		super(x, y, Screen.getTextWidth(text), Screen.getTextHeight(text));
		this.text = text;
	}

	@Override
	public void render() {
		Screen.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
	}

	@Override
	public void update() {}
}
