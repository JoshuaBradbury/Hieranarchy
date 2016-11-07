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

	public static Project getProjectFromName(String name) {
		for (Project project : loadedProjects) {
			if (project.getName().equalsIgnoreCase(name)) {
				return project;
			}
		}
		return null;
	}
	
	public static void setCurrentProject(String name) {
		Project proj = getProjectFromName(name);
		if (proj != null)
			currentProject = proj;
	}
	
	public static void unloadCurrentProject() {
		unloadProject(currentProject.getName());
	}

	public static Project getCurrentProject() {
		return currentProject;
	}

	public static void unloadProject(String name) {
		Project proj = getProjectFromName(name);
		loadedProjects.remove(proj);
		if (currentProject == proj)
			currentProject = null;
	}
}
