package uk.co.newagedev.hieranarchy.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Project {

	public static final String DIRECTORY = "Projects/", PROJECT_FILE = "project.json";
	
	private String projectFolder;
	private File folder;
	private JsonObject jsonBase;
	private String name;
	
	public Project(String projectName) {
		name = projectName;
		projectFolder = Project.DIRECTORY + name + "/";
		folder = FileUtil.create(projectFolder);
		FileUtil.create(projectFolder + Project.PROJECT_FILE);
		JsonParser parser = new JsonParser();
		try {
			JsonElement jsonElement;
			jsonElement = parser.parse(new FileReader(projectFolder + PROJECT_FILE));
	        jsonBase = jsonElement.getAsJsonObject();
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			Logger.error(e.getMessage());
			jsonBase = new JsonObject();
		}
	}
	
	public void addTileToProject(Map<String, Object> tileProperties) {
		JsonArray tiles = jsonBase.getAsJsonArray("tiles");
		JsonObject tileJson = new JsonObject();
		tileJson.addProperty("name", (String) tileProperties.get("name"));
		for (String property : tileProperties.keySet()) {
			Object obj = tileProperties.get(property);
			if (obj instanceof String) {
				tileJson.addProperty(property, (String) obj);
			} else if (obj instanceof Boolean) {
				tileJson.addProperty(property, (Boolean) obj);
			} else if (obj instanceof Integer) {
				tileJson.addProperty(property, (Integer) obj);
			} else if (obj instanceof Character) {
				tileJson.addProperty(property, (Character) obj);
			}
		}
	}
}
