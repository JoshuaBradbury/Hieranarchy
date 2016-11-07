package uk.co.newagedev.hieranarchy.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.newagedev.hieranarchy.events.types.Event;

public class EventHub {

	private static List<Listener> eventListeners = new ArrayList<Listener>();
	private static List<Listener> ignoreList = new ArrayList<Listener>();
	private static Map<Listener, String> listenersEvents = new HashMap<Listener, String>();

	public static void registerListener(Listener listener) {
		eventListeners.add(listener);
		if (listenersEvents.size() > 0) {
			listenersEvents.clear();
			init();
		}
	}
	
	public static void ignoreListener(Listener listener) {
		ignoreList.add(listener);
	}
	
	public static void listenTo(Listener listener) {
		ignoreList.remove(listener);
	}

	public static void init() {
		for (Listener listener : eventListeners) {
			String listenerEvents = "";
			for (Method method : listener.getClass().getMethods()) {
				if (method.getParameterTypes().length > 0) {
					Type type = method.getGenericParameterTypes()[0];
					if (type.getTypeName().contains(EventHub.class.getPackage().getName() + ".types")) {
						String[] nameParts = method.getGenericParameterTypes()[0].getTypeName().split("\\.");
						listenerEvents += nameParts[nameParts.length - 1] + ",";
					}
				}
			}
			if (listenerEvents.length() > 0) {
				listenerEvents = listenerEvents.substring(0, listenerEvents.length() - 1);
			}
			listenersEvents.put(listener, listenerEvents);
		}
	}

	public static void pushEvent(Event event) {
		String[] eventNameParts = event.getClass().getTypeName().split("\\.");
		String eventName = eventNameParts[eventNameParts.length - 1];
		
		List<Listener> listeners = new ArrayList<Listener>();

		for (Listener listener : listenersEvents.keySet()) {
			if (listenersEvents.get(listener).contains(eventName) && !ignoreList.contains(listener))
				listeners.add(listener);
		}
		
		for (Listener listener : listeners) {
			for (Method method : listener.getClass().getMethods()) {
				if (method.getParameterTypes().length > 0) {
					Type type = method.getGenericParameterTypes()[0];
					if (type.getTypeName().contains(EventHub.class.getPackage().getName() + ".types")) {
						String[] nameParts = method.getGenericParameterTypes()[0].getTypeName().split("\\.");
						if (nameParts[nameParts.length - 1].equalsIgnoreCase(eventName)) {
							try {
								method.invoke(listener, event);
								if (event.isDisabled()) {
									return;
								}
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
