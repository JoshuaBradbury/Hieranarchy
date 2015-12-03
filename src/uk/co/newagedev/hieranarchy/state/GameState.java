package uk.co.newagedev.hieranarchy.state;

import uk.co.newagedev.hieranarchy.map.Map;

public class GameState extends State {
	private Map currentMap;
	
	public GameState(Map map) {
		currentMap = map;
	}
	
	public void render() {
		currentMap.render(getCurrentCamera());
	}
	
	public void update() {
		currentMap.update();
	}
}
