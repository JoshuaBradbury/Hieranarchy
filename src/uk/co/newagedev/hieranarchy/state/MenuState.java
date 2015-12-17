package uk.co.newagedev.hieranarchy.state;

import java.util.ArrayList;
import java.util.List;

import uk.co.newagedev.hieranarchy.ui.Component;

public class MenuState extends State {

	public List<Component> components = new ArrayList<Component>();
	
	@Override
	public void render() {
		for (Component component : components) {
			component.render();
		}
	}
	
	@Override
	public void onLoad() {
		
	}

	@Override
	public void update() {
		for (Component component : components) {
			component.update();
		}
	}

	public void registerComponent(Component component) {
		components.add(component);
	}
	
	public void removeComponent(Component component) {
		components.remove(component);
	}
}
