package uk.co.newagedev.hieranarchy.project;

import java.util.ArrayList;
import java.util.List;

public class ProjectData {
	
	private String name;
	private List<String> mapNames = new ArrayList<String>();
	
	public void addMap(String map) {
		mapNames.add(map);
	}
	
	public void removeMap(String map) {
		mapNames.remove(map);
	}
	
	public List<String> getMapNames() {
		return mapNames;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
