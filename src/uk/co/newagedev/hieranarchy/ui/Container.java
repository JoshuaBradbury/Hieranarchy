package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.hieranarchy.util.Logger;

public class Container extends Component {
	private List<Component> components = new ArrayList<Component>();
	
	public Container(int x, int y) {
		super(x, y);
	}
	
	public void addComponent(Component component) {
		components.add(component);
	}
	
	public void removeComponent(Component component) {
		components.remove(component);
	}

	public List<Component> getComponents() {
		return components;
	}
	
	public Dimension getDimensions() {
		return new Dimension(getWidth(), getHeight());
	}
	
	public int getHeight() {
		int height = 0;
		for (Component component : components) {
			if (component.getLocation().getY() + component.getDimensions().getHeight() > height) {
				height = (int) (component.getLocation().getY() + component.getDimensions().getHeight());
			}
		}
		return height;
	}
	
	public int getWidth() {
		int width = 0;
		for (Component component : components) {
			if (component.getLocation().getX() + component.getDimensions().getWidth() > width) {
				width = (int) (component.getLocation().getX() + component.getDimensions().getWidth());
			}
		}
		return width;
	}
	
	public void update() {
		for (Component component : components) {
			component.update();
			component.setOffset((int) getLocation().getX(), (int) getLocation().getY());
		}
	}
	
	public void render(Rectangle view) {
		for (Component component : components) {
			if (view.intersects(component.getAsRectangle())) {
				Logger.info(component.getOffset());
				component.render(view);
			}
		}
	}
}
