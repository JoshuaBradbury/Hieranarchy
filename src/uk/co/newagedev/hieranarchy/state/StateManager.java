package uk.co.newagedev.hieranarchy.state;

import java.util.ArrayList;
import java.util.Stack;

import uk.co.newagedev.hieranarchy.util.ErrorUtil;

public final class StateManager {

	private static Stack<State> currentStates = new Stack<State>();
	
	private StateManager() {}
	
	public static State getCurrentState() {
		return currentStates.peek();
	}
	
	public static Stack<State> getCurrentStates() {
		return currentStates;
	}
	
	public static void pushCurrentState(State state) {
		if (state == null) {
			ErrorUtil.throwError("Invalid state, please supply a non-null state");
			return;
		}
		if (!state.isTransparent())
			for (State st : currentStates) {
				if (!st.isShown()) break;
				st.hide();
			}
		currentStates.push(state);
	}
	
	public static void popCurrentState() {
		State state = getCurrentState();
		if (!state.isTransparent()) {
			for (State st : currentStates) {
				if (st != state) {
					if (st.isShown())
						break;
					st.show();
					if (!st.isTransparent())
						break;
				}
			}
		}
		currentStates.pop();
	}
	
	public static void updateStates() {
		ArrayList<State> statesVisible = new ArrayList<State>();
		
		for (State state : currentStates) {
			if (!state.isTransparent())
				statesVisible.clear();
			statesVisible.add(state);
		}
		
		for (State state : statesVisible) {
			state.update();
		}
	}
	
	public static void renderStates() {
		ArrayList<State> statesVisible = new ArrayList<State>();
		
		for (State state : currentStates) {
			if (!state.isTransparent())
				statesVisible.clear();
			statesVisible.add(state);
		}
		
		for (State state : statesVisible) {
			state.render();
		}
	}
}
