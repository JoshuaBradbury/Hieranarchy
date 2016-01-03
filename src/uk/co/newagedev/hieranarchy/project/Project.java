package uk.co.newagedev.hieranarchy.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import uk.co.newagedev.hieranarchy.main.Main;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Logger;

public class Project {

	public static final String DIRECTORY = "Projects/", PROJECT_FILE = "project.json", MAPS_DIRECTORY = "Maps/", TEXTURES_DIRECTORY = "Assets/Textures/", SOUNDS_DIRECTORY = "Assets/Sounds/";
	
	private String projectFolder;
	private ProjectData projectData;
	private Map<String, uk.co.newagedev.hieranarchy.map.Map> maps = new HashMap<String, uk.co.newagedev.hieranarchy.map.Map>();
	private String name;
	
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
	
	public String getProjectFolder() {
		return projectFolder;
	}

	public ProjectData getData() {
		return projectData;
	}
	
	public void loadMap(String mapName) {
		maps.put(mapName, new uk.co.newagedev.hieranarchy.map.Map(mapName, this));
	}
	
	public uk.co.newagedev.hieranarchy.map.Map getMap(String mapName) {
		return maps.get(mapName);
	}
	
	public void saveMap(String mapName) {
		uk.co.newagedev.hieranarchy.map.Map map = maps.get(mapName);
		map.save();
	}
	
	public void removeMap(String mapName) {
		saveMap(mapName);
		maps.remove(mapName);
	}
	
	public void save() {
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
	
	public void addObjectToMap(String map, Map<String, Object> objectProperties) {
		maps.get(map).getMapStore().writeObject((String) objectProperties.get("name"), objectProperties);
	}
}
