package uk.co.newagedev.hieranarchy.ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Container extends Component {
	private List<Component> components = new ArrayList<Component>();

	public Container(int x, int y) {
		super(x, y);
	}

	public Container(int x, int y, int width, int height) {
		super(x, y);
		setDimensions(width, height);
	}

	public void addComponent(Component component) {
		components.add(component);
		component.setParent(this);
		update();
	}

	public void removeComponent(Component component) {
		components.remove(component);
		component.setParent(null);
	}

	public List<Component> getComponents() {
		return components;
	}

	@Override
	public Dimension getDimensions() {
		return new Dimension(getWidth(), getHeight());
	}

	public int getHeight() {
		int height = 0;
		for (Component component : components) {
			int temp = (int) (component.getLocation().getY() + component.getDimensions().getHeight());
			if (temp > height) {
				height = temp;
			}
		}
		return height;
	}

	public int getWidth() {
		int width = 0;
		for (Component component : components) {
			int temp = (int) (component.getLocation().getX() + component.getDimensions().getWidth());
			if (temp > width) {
				width = temp;
			}
		}
		return width;
	}

	@Override
	public void update() {
		try {
			for (Component component : components) {
				component.update();
				component.setOffset((int) (getLocation().getX() + getOffset().getX()), (int) (getLocation().getY() + getOffset().getY()));
			}
		} catch (Exception e) {
		}
	}
	
	@Override
	public void render() {
		for (Component component : components) {
			component.render();
		}
	}
	
	@Override
	public void hide() {
		for (Component component : components) {
			component.hide();
		}
		super.hide();
	}
	
	@Override
	public void show() {
		for (Component component : components) {
			component.show();
		}
		super.show();
	}
}
