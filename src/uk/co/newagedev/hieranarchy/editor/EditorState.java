package uk.co.newagedev.hieranarchy.editor;

import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.state.State;

public class EditorState extends State {
	private Map currentMap;
	private boolean playing = false;
	
	@Override
	public void render() {
		currentMap.render();
	}
	
	public void restartMap() {
		currentMap.reload();
		playing = true;
	}
	
	public void continueMap() {
		playing = true;
	}

	@Override
	public void update() {
		if (playing) {
			currentMap.update();
		}
	}
}
