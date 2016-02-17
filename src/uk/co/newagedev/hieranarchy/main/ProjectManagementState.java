package uk.co.newagedev.hieranarchy.main;

import java.awt.Rectangle;

import uk.co.newagedev.hieranarchy.events.types.screen.ScreenResizeEvent;
import uk.co.newagedev.hieranarchy.graphics.Sprite;
import uk.co.newagedev.hieranarchy.graphics.SpriteRegistry;
import uk.co.newagedev.hieranarchy.state.MenuState;
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

	public ProjectManagementState() {
		pane = new ScrollPane(0, 90, Main.WIDTH, Main.HEIGHT - 90, ScrollBar.VERTICAL);
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
				Main.popup("Add map", cont, new ButtonRunnable() {
					public void run() {
						Main.project.loadMap(nameBox.getText());
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
				if (!Main.project.isSaved()) {
					Container cont = new Container(0, 0);
					cont.addComponent(new Label("This project hasn't been saved yet, would you like to exit without saving?", 0, 40));
					cont.addComponent(new Label("", 0, 100));
					Main.popup("Project not saved", cont, new ButtonRunnable() {
						public void run() {
							Main.setCurrentState("start menu");
						}
					});
				} else {
					Main.setCurrentState("start menu");
				}
			}
		});
		
		exitProject.setImage("exit");
		
		saveProject = new Button("Save", Main.WIDTH - 70, 55, 30, 30, true, new ButtonRunnable() {
			public void run() {
				if (!Main.project.isSaved()) {
					Main.project.save();
				}
			}
		});
		
		saveProject.setImage("save");
		
		registerComponent(toolbar);
		registerComponent(pane);
		registerComponent(exitProject);
		registerComponent(saveProject);
		registerComponent(addMap);
		registerComponent(deleteMap);
	}

	@Override
	public void render() {
		Main.getScreen().renderQuad(new Rectangle(0, 0, Main.WIDTH, 50), Colour.GREY);
		Component.componentFont.renderText(Main.project.getName(), Main.WIDTH / 2, 25);
		Main.getScreen().renderQuad(new Rectangle(0, 50, Main.WIDTH, 40), Colour.LIGHT_GREY);
		super.render();
	}
	
	public void screenResize(ScreenResizeEvent event) {
		pane.setDimensions(event.getWidth(), event.getHeight() - 90);
		exitProject.setLocation(Main.WIDTH - 35, 55);
		saveProject.setLocation(Main.WIDTH - 70, 55);
	}
}
