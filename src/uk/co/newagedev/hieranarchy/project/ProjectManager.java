package uk.co.newagedev.hieranarchy.project;

import java.util.ArrayList;

public class ProjectManager {

	private static ArrayList<Project> loadedProjects = new ArrayList<Project>();

	private static Project currentProject;

	public static Project loadProject(String name) {
		Project project = new Project(name);
		loadedProjects.add(project);
		return project;
	}

	public static void setCurrentProject(String name) {
		for (Project project : loadedProjects) {
			if (project.getName().equalsIgnoreCase(name)) {
				currentProject = project;
				break;
			}
		}
	}
	
	public static void unloadCurrentProject() {
		unloadProject(currentProject.getName());
	}

	public static Project getCurrentProject() {
		return currentProject;
	}

	public static void unloadProject(String name) {
		for (Project project : loadedProjects) {
			if (project.getName().equalsIgnoreCase(name)) {
				project.save();
				loadedProjects.remove(project);
				if (currentProject == project)
					currentProject = null;
			}
		}
	}
}
