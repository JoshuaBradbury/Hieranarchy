package uk.co.newagedev.hieranarchy.state;

import uk.co.newagedev.hieranarchy.map.Map;

public class GameState implements State {
	private Map currentMap;
	
	public void render() {
		currentMap.render();
	}
	
	public void update() {
		currentMap.update();
	}
}
