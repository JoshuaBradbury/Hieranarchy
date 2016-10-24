package uk.co.newagedev.hieranarchy.main;

import java.awt.Rectangle;
import java.util.ArrayList;

import uk.co.newagedev.hieranarchy.project.Project;
import uk.co.newagedev.hieranarchy.project.ProjectManager;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.ScrollBar;
import uk.co.newagedev.hieranarchy.ui.ScrollPane;
import uk.co.newagedev.hieranarchy.util.Colour;
import uk.co.newagedev.hieranarchy.util.FileUtil;
import uk.co.newagedev.hieranarchy.util.Logger;
import uk.co.newagedev.hieranarchy.util.StringUtil;

public class ProjectLoaderState extends MenuState {

	private Container container;
	private ScrollPane pane;
	
	public ProjectLoaderState() {
		pane = new ScrollPane(50, 50, Main.WIDTH - 100, Main.HEIGHT - 100, ScrollBar.VERTICAL | ScrollBar.HORIZONTAL);
		container = pane.getPane();
		String[] projects = getLoadableProjects();
		for (int i = 0; i < projects.length; i++) {
			addProjectToLoad(projects[i]);
		}
		registerComponent(pane);
	}
	
	public void addProjectToLoad(String projectName) {
		ProjectManager.loadProject(projectName);
		ProjectManager.setCurrentProject(projectName);
		Container cont = new Container(0, container.getHeight());
		cont.addComponent(new Label(projectName, 25, 35));
		cont.addComponent(new Button("Load Project", pane.getWidth() - 225, 20, 200, 50, false, new ButtonRunnable() {
			public void run() {
				Logger.info("Loading project " + StringUtil.surroundWith(projectName, "\""));
				ProjectManagementState state = new ProjectManagementState();
				StateManager.registerState(projectName + " management", state);
				Main.setCurrentState(projectName + " management");
			}
		}));
		cont.update();
		container.addComponent(cont);
	}
	
	public String[] getLoadableProjects() {
		String[] projectFolders = FileUtil.getAllFilesInFolder(Project.DIRECTORY);
		ArrayList<String> loadableProjects = new ArrayList<String>();
		for (int i = 0; i < projectFolders.length; i++) {
			String[] projectFolderFileList = FileUtil.getAllFilesInFolder(Project.DIRECTORY + projectFolders[i]);
			if (StringUtil.doesArrayContainString(projectFolderFileList, Project.PROJECT_FILE)) {
				loadableProjects.add(projectFolders[i]);
			}
		}
		return loadableProjects.toArray(new String[] {});
	}
	
	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT), Colour.DARK_DARK_GREY);
		super.render();
	}
}