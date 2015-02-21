package uk.co.newagedev.hieranarchy.state;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

	private static Map<String, State> states = new HashMap<String, State>();
	
	public static void addState(String name, State state) {
		states.put(name, state);
	}
	
	public static void removeState(String name) {
		states.remove(name);
	}
}
