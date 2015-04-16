package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Container extends Component {
	private List<Component> components = new ArrayList<Component>();
	private int width, height;
	
	public Container(int x, int y) {
		super(x, y);
		width = 0;
		height = 0;
	}

	public Container(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	public void addComponent(Component component) {
		components.add(component);
		component.setParent(this);
	}

	public void removeComponent(Component component) {
		components.remove(component);
		component.setParent(null);
	}

	public List<Component> getComponents() {
		return components;
	}

	public Dimension getDimensions() {
		return new Dimension(getWidth(), getHeight());
	}

	public int getHeight() {
		if (height == 0) {
			for (Component component : components) {
				if (component.getLocation().getY() + component.getDimensions().getHeight() > height) {
					height = (int) (component.getLocation().getY() + component.getDimensions().getHeight());
				}
			}
		}
		return height;
	}

	public int getWidth() {
		if (width == 0) {
			for (Component component : components) {
				if (component.getLocation().getX() + component.getDimensions().getWidth() > width) {
					width = (int) (component.getLocation().getX() + component.getDimensions().getWidth());
				}
			}
		}
		return width;
	}

	public void update() {
		try {
			for (Component component : components) {
				component.update();
				component.setOffset((int) (getLocation().getX() + getOffset().getX()), (int) (getLocation().getY() + getOffset().getY()));
			}
		} catch (Exception e) {
		}
	}

	public void render() {
		for (Component component : components) {
			component.render();
		}
	}
}
