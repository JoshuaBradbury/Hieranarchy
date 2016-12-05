package uk.co.newagedev.hieranarchy.main;

import java.awt.Rectangle;
import java.util.ArrayList;

import uk.co.newagedev.hieranarchy.events.types.screen.ScreenResizeEvent;
import uk.co.newagedev.hieranarchy.graphics.Camera;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.map.Map;
import uk.co.newagedev.hieranarchy.project.ProjectManager;
import uk.co.newagedev.hieranarchy.state.MenuState;
import uk.co.newagedev.hieranarchy.state.PopupState;
import uk.co.newagedev.hieranarchy.state.StateManager;
import uk.co.newagedev.hieranarchy.ui.Button;
import uk.co.newagedev.hieranarchy.ui.ButtonRunnable;
import uk.co.newagedev.hieranarchy.ui.Component;
import uk.co.newagedev.hieranarchy.ui.Container;
import uk.co.newagedev.hieranarchy.ui.Label;
import uk.co.newagedev.hieranarchy.ui.ScrollBar;
import uk.co.newagedev.hieranarchy.ui.ScrollPane;
import uk.co.newagedev.hieranarchy.ui.TextBox;
import uk.co.newagedev.hieranarchy.util.Colour;

public class ProjectManagementState extends MenuState {

	private ScrollPane pane;
	private Container toolbar;
	private Button exitProject, saveProject, addMap, deleteMap;
	private ArrayList<Container> projectMaps = new ArrayList<Container>();

	public ProjectManagementState() {
		ProjectManager.getCurrentProject().loadMaps();
		
		pane = new ScrollPane(0, 90, Main.WIDTH, Main.HEIGHT - 90, ScrollBar.VERTICAL);
		
		for (String mapName : ProjectManager.getCurrentProject().getMaps()) {
			addMap(mapName);
		}
		
		toolbar = new Container(0, 50);
		
		Sprite exit = SpriteRegistry.getSprite("exit");
		exit.setWidth(20);
		exit.setHeight(20);

		Sprite save = SpriteRegistry.getSprite("save");
		save.setWidth(20);
		save.setHeight(20);
		
		Sprite add = SpriteRegistry.getSprite("addMap");
		add.setWidth(20);
		add.setHeight(20);
		
		Sprite delete = SpriteRegistry.getSprite("deleteMap");
		delete.setWidth(20);
		delete.setHeight(20);
		
		addMap = new Button("Add Map", 5, 55, 30, 30, true, new ButtonRunnable() {
			public void run() {
				Container cont = new Container(0, 0);
				Label mapNameLabel = new Label("Map name", 0, 30);
				cont.addComponent(mapNameLabel);
				TextBox nameBox = new TextBox(100, 30, 300, (int) mapNameLabel.getDimensions().getHeight());
				cont.addComponent(nameBox);
				cont.addComponent(new Label("", 0, 60));
				PopupState.popup("Add map", cont, new ButtonRunnable() {
					public void run() {
						ProjectManager.getCurrentProject().loadMap(nameBox.getText());
						addMap(nameBox.getText());
						pane.getPane().addComponent(projectMaps.get(projectMaps.size() - 1));
					}
				});
			}
		});
		
		addMap.setImage("addMap");
		
		deleteMap = new Button("Delete Map", 40, 55, 30, 30, true, new ButtonRunnable() {
			public void run() {
				
			}
		});
		
		deleteMap.setImage("deleteMap");
		
		exitProject = new Button("Exit", Main.WIDTH - 35, 55, 30, 30, true, new ButtonRunnable() {
			public void run() {
				if (!ProjectManager.getCurrentProject().isSaved()) {
					Container cont = new Container(0, 0);
					cont.addComponent(new Label("This project hasn't been saved yet, would you like to exit without saving?", 0, 40));
					cont.addComponent(new Label("", 0, 100));
					PopupState.popup("Project not saved", cont, new ButtonRunnable() {
						public void run() {
							ProjectManager.unloadCurrentProject();
						}
					});
				} else {
					ProjectManager.unloadCurrentProject();
					StateManager.popCurrentState();
				}
			}
		});
		
		exitProject.setImage("exit");
		
		saveProject = new Button("Save", Main.WIDTH - 70, 55, 30, 30, true, new ButtonRunnable() {
			public void run() {
				ProjectManager.getCurrentProject().save();
			}
		});
		
		saveProject.setImage("save");
		
		registerComponent(toolbar);
		registerComponent(pane);
		registerComponent(exitProject);
		registerComponent(saveProject);
		registerComponent(addMap);
		registerComponent(deleteMap);
		for (Container cont : projectMaps) {
			pane.getPane().addComponent(cont);
		}
	}
	
	public void addMap(String mapName) {
		Map map = ProjectManager.getCurrentProject().getMap(mapName);
		Container cont = new Container((projectMaps.size() % 2) * (pane.getWidth() / 2), projectMaps.size() / 2 * pane.getHeight() / 2, pane.getWidth() / 2, pane.getHeight() / 2);
		cont.addComponent(new Button("", 0, 0, (int) cont.getDimensions().getWidth(), (int) cont.getDimensions().getHeight(), false, new ButtonRunnable() {
			public void run() {
				EditorState state = new EditorState(map);
				
				state.registerCamera("play", new Camera(100, 100));
				state.registerCamera("edit", new Camera(0, 0));
				state.switchCamera("edit");

				StateManager.pushCurrentState(state);
			}
		}));
		Label label = new Label(mapName, 0, 0);
		label.setLocation((int) cont.getDimensions().getWidth() / 2 - (int) label.getDimensions().getWidth() / 2, 25);
		cont.addComponent(label);
		cont.addComponent(new Label("Object count: " + map.getObjectCount(), 20, 70));
		cont.addComponent(new Label("Width: " + map.getWidth(), 20, 100));
		cont.addComponent(new Label("Height: " + map.getHeight(), 20, 130));
		projectMaps.add(cont);
	}

	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(0, 0, Main.WIDTH, 50), Colour.GREY);
		Component.componentFont.renderText(ProjectManager.getCurrentProject().getName(), Main.WIDTH / 2, 25);
		Main.getScreen().renderQuad(new Rectangle(0, 50, Main.WIDTH, 40), Colour.LIGHT_GREY);
		super.render();
	}
	
	public void screenResize(ScreenResizeEvent event) {
		pane.setDimensions(event.getWidth(), event.getHeight() - 90);
		exitProject.setLocation(Main.WIDTH - 35, 55);
		saveProject.setLocation(Main.WIDTH - 70, 55);
		for (int i = 0; i < projectMaps.size(); i++) {
			projectMaps.get(i).setLocation(i % 2 * pane.getWidth() / 2, i / 2 * pane.getHeight() / 2);
			projectMaps.get(i).setDimensions(pane.getWidth() / 2, pane.getHeight() / 2);
			projectMaps.get(i).getComponents().get(0).setDimensions((int) projectMaps.get(i).getDimensions().getWidth(), (int) projectMaps.get(i).getDimensions().getHeight());
			Label label = (Label) projectMaps.get(i).getComponents().get(1);
			label.setLocation((int) projectMaps.get(i).getDimensions().getWidth() / 2 - (int) label.getDimensions().getWidth() / 2, 25);
		}
	}
}
