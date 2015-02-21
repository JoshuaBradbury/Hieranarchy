package uk.co.newagedev.hieranarchy.ui;

import java.util.ArrayList;
import java.util.List;

public class Container extends Component {
	private List<Component> components = new ArrayList<Component>();
	
	public Container(int x, int y, int width, int height) {
		super(x, y, width, height);
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
}
