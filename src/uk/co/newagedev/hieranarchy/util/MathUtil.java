package uk.co.newagedev.hieranarchy.util;

import java.util.ArrayList;
import java.util.Stack;

public class MathUtil {

	public static float logab(float base, float value) {
		return (float) (Math.log(value) / Math.log(base));
	}
	
	@SuppressWarnings("unchecked")
	public static <E> Stack<E> flipStack(Stack<E> stack) {
		Stack<E> flipped = new Stack<E>();
		Stack<E> copy = (Stack<E>) stack.clone();
		
		while (!copy.isEmpty())
			flipped.push(copy.pop());
		
		return flipped;
	}
	
	public static <E> ArrayList<E> flipArrayList(ArrayList<E> array) {
		ArrayList<E> flipped = new ArrayList<E>();
		
		for (int i = array.size() - 1; i >= 0; i--) {
			flipped.add(array.get(i));
		}
		
		return flipped;
	}
	
}
