package uk.co.newagedev.hieranarchy.state;

import uk.co.newagedev.hieranarchy.map.Map;

public class GameState extends State {
	private Map currentMap;
	
	public GameState(Map map) {
		currentMap = map;
	}

	@Override
	public void onLoad() {
		
	}
	
	@Override
	public void onDestroy() {
		currentMap.save();
	}
	
	@Override
	public void render() {
		currentMap.render(getCurrentCamera());
	}
	
	@Override
	public void update() {
		currentMap.update();
	}
}
