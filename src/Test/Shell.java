package Test;

import java.awt.Font;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import GameObjects.GameObject;
import GameObjects.NonPaneledGameObject;
import GameObjects.PaneledGameObject;
import Imported.ClassFinder;
import TreeUI.UIElement;
import focusObject.Panel;

public class Shell extends StateBasedGame{

	public static int menu = 0;
	private static ArrayList<Class<?>> gameObjectTypes = new ArrayList<Class<?>>();
	private static ArrayList<Class<?>> uiElementTypes = new ArrayList<Class<?>>();
	public static TrueTypeFont SMALL_FONT;
	public Shell(String name) {
		super(name);
		SuperGlobal.setShell(this);
		SuperGlobal.setGameState("MainMenu", new MainMenu(this));
		SuperGlobal.setGameState("Demo", new Demo(this));
		SuperGlobal.setGameState("Editor", new EditorState(this));
		
		//int shipID=Integer.parseInt(JOptionPane.showInputDialog("Enter your shipID:"));
		//this.addState(new SBLobby());
		//this.addState(new SBBuild(shipID));
		this.addState(SuperGlobal.getGameState("MainMenu"));
		this.addState(SuperGlobal.getGameState("Demo"));
		this.addState(SuperGlobal.getGameState("Editor"));
		
		List<Class<?>> cls=ClassFinder.find(Shell.class.getPackage().getName());
		System.out.println(cls);
		scanGOTypes();
		scanUITypes();
	}
	private void scanUITypes(){
		List<Class<?>> cls=ClassFinder.find(UIElement.class.getPackage().getName());
		for(Class<?> type:cls){
			//Filter out abstract objects
			if(Modifier.isAbstract(type.getModifiers()))
				continue;
			if(UIElement.class.isAssignableFrom(type))
				uiElementTypes.add(type);
		}
	}
	private void scanGOTypes(){
		List<Class<?>> cls=ClassFinder.find(GameObject.class.getPackage().getName());
		for(Class<?> type:cls){
			//Filter out abstract objects
			if(Modifier.isAbstract(type.getModifiers()))
				continue;
			//if(GameObject.class.isAssignableFrom(type))
			//Pointless if statement, there is no core gameobject class(The required parent class OriginObject makes extending another class impossible)
			gameObjectTypes.add(type);
		}
	}
	
	public static ArrayList<Class<?>> getUITypes(){
		return uiElementTypes;
	}
	
	public static ArrayList<Class<?>> getGOTypes(){
		return gameObjectTypes;
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		container.setShowFPS(false);
		SMALL_FONT = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 10), true);
		this.enterState(0);
	}
	
	public static void main(String Args[])
	{
		try{
			AppGameContainer app = new AppGameContainer(new Shell("Zoom and Drag Testing"));
			app.setIcon("res/icon1.png");
			SuperGlobal.setWindowSize(800, 600);
			app.setDisplayMode(800, 600, false);
			app.start();
		}catch(SlickException e)
		{
			//System.out.println("Slick Exception fired: Shell Main");
		}
	}
}
