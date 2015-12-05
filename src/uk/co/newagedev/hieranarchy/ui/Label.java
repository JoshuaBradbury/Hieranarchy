package uk.co.newagedev.hieranarchy.ui;

import uk.co.newagedev.hieranarchy.graphics.TextObject;
import uk.co.newagedev.hieranarchy.util.FontUtil;

public class Label extends Component {

	private TextObject text;
	
	public void changeText(String text) {
		this.text = FontUtil.getStringFromFont(componentFont, text);
	}
	
	public Label(String text, int x, int y) {
		super(x, y);
		changeText(text);
		setDimensions(this.text.getWidth(), this.text.getHeight());
	}

	@Override
	public void render() {
		FontUtil.renderText(text, (int) (getLocation().getX() + (getDimensions().getWidth() / 2)), (int) (getLocation().getY() + (getDimensions().getHeight() / 2)));
	}

	@Override
	public void update() {}
}
