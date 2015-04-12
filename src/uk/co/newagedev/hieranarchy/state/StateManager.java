package uk.co.newagedev.hieranarchy.state;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

	private static Map<String, State> states = new HashMap<String, State>();
	
	public static void registerState(String name, State state) {
		state.setName(name);
		states.put(name, state);
	}
	
	public static void removeState(String name) {
		states.remove(name);
	}
	
	public static State getState(String name) {
		return states.get(name);
	}
}
