package uk.co.newagedev.hieranarchy.ui;

public class Label extends Component {

	private String text;
	
	public void changeText(String text) {
		this.text = text;
	}
	
	public Label(String text, int x, int y) {
		super(x, y);
		changeText(text);
		setDimensions(componentFont.getTextWidth(text), componentFont.getTextHeight(text));
	}

	@Override
	public void render() {
		componentFont.renderText(text, (int) (getDisplayLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getDisplayLocation().getY() + (getDimensions().getHeight() / 2)));
	}

	@Override
	public void update() {}
}
