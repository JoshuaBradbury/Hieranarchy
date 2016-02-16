package uk.co.newagedev.hieranarchy.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Project {

	public static final String DIRECTORY = "Projects/", PROJECT_FILE = "project.json", MAPS_DIRECTORY = "Maps/", TEXTURES_DIRECTORY = "Assets/Textures/", SOUNDS_DIRECTORY = "Assets/Sounds/";
	
	private String projectFolder;
	private ProjectData projectData;
	private HashMap<String, Map> maps = new HashMap<String, Map>();
	private String name;
	private boolean saved = true;
	
	public Project(String projectName) {
		name = projectName;
		projectFolder = DIRECTORY + name + "/";
		if (!FileUtil.doesFileExist(projectFolder)) {
			FileUtil.create(projectFolder);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FileUtil.create(projectFolder + PROJECT_FILE)));
			
			projectData = Main.GSON.fromJson(reader, ProjectData.class);
			
			reader.close();
		} catch (IOException e) {
			Logger.error(e.getMessage());
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}
		
		if (projectData == null) {
			projectData = new ProjectData();
		}
	}
	
	public void cleanup() {
		save();
		for (String map : maps.keySet()) {
			maps.get(map).save();
		}
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public String getProjectFolder() {
		return projectFolder;
	}

	public ProjectData getData() {
		return projectData;
	}
	
	public String getName() {
		return name;
	}
	
	public void loadMap(String mapName) {
		saved = false;
		maps.put(mapName, new Map(mapName, this));
	}
	
	public Map getMap(String mapName) {
		return maps.get(mapName);
	}
	
	public void saveMap(String mapName) {
		Map map = maps.get(mapName);
		map.save();
	}
	
	public void removeMap(String mapName) {
		saveMap(mapName);
		maps.remove(mapName);
		saved = false;
	}
	
	public void save() {
		saved = true;
		for (String mapName : maps.keySet()) {
			saveMap(mapName);
		}
		try {
			FileWriter writer = new FileWriter(FileUtil.create(projectFolder + PROJECT_FILE));
			String json = Main.GSON.toJson(projectData);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			Logger.error(e.getMessage());
			for (Object obj : e.getStackTrace()) {
				Logger.error(obj);
			}
		}
	}
	
	public void addObjectToMap(String map, HashMap<String, Object> objectProperties) {
		maps.get(map).getMapStore().writeObject((String) objectProperties.get("name"), objectProperties);
		saved = false;
	}
}
