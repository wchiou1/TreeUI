package Test;

import java.awt.Font;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import Imported.ClassFinder;
import TreeUI.BasicPanelButton;
import focusObject.Panel;
import focusObject.UIElement;
import gameObjects.GameObject;
import smallGameObjects.SmallGameObject;

public class Shell extends StateBasedGame{

	public static int menu = 0;
	private static ArrayList<Class<?>> gameObjectTypes = new ArrayList<Class<?>>();
	private static ArrayList<Class<?>> uiElementTypes = new ArrayList<Class<?>>();
	private static ArrayList<Class<?>> smallGameObjectTypes = new ArrayList<Class<?>>();
	public static TrueTypeFont SMALL_FONT;
	public Shell(String name) {
		super(name);
		SuperGlobal.setShell(this);
		SuperGlobal.setGameState("MainMenu", new MainMenu(this));
		SuperGlobal.setGameState("Demo", new Demo(this));
		SuperGlobal.setGameState("Editor Server", new EditorState(this,true));
		SuperGlobal.setGameState("Editor Client", new EditorState(this,false));
		
		//int shipID=Integer.parseInt(JOptionPane.showInputDialog("Enter your shipID:"));
		//this.addState(new SBLobby());
		//this.addState(new SBBuild(shipID));
		this.addState(SuperGlobal.getGameState("MainMenu"));
		this.addState(SuperGlobal.getGameState("Demo"));
		this.addState(SuperGlobal.getGameState("Editor Server"));
		this.addState(SuperGlobal.getGameState("Editor Client"));
		
		List<Class<?>> cls=ClassFinder.find(Shell.class.getPackage().getName());
		System.out.println(cls);
		scanGOTypes();
		scanUITypes();
		scanSGOTypes();
	}
	private void scanUITypes(){
		List<Class<?>> cls=ClassFinder.find(BasicPanelButton.class.getPackage().getName());
		Hashtable<String,Class<?>> classes = new Hashtable<String,Class<?>>();
		ArrayList<String> classNames = new ArrayList<String>();
		for(Class<?> type:cls){
			//Filter out abstract objects
			if(Modifier.isAbstract(type.getModifiers()))
				continue;
			if(UIElement.class.isAssignableFrom(type)){
				classes.put(type.getSimpleName(), type);
				classNames.add(type.getSimpleName());
			}
		}
		Collections.sort(classNames);
		System.out.println(classNames);
		for(String cn:classNames){
			Class<?> type = classes.get(cn);
			uiElementTypes.add(type);
		}
	}
	private void scanGOTypes(){
		List<Class<?>> cls=ClassFinder.find(GameObject.class.getPackage().getName());
		Hashtable<String,Class<?>> classes = new Hashtable<String,Class<?>>();
		ArrayList<String> classNames = new ArrayList<String>();
		for(Class<?> type:cls){
			//Filter out abstract objects
			if(Modifier.isAbstract(type.getModifiers()))
				continue;
			if(GameObject.class.isAssignableFrom(type)){
				classes.put(type.getSimpleName(), type);
				classNames.add(type.getSimpleName());
			}
		}
		Collections.sort(classNames);
		System.out.println(classNames);
		for(String cn:classNames){
			Class<?> type = classes.get(cn);
			gameObjectTypes.add(type);
		}
	}
	private void scanSGOTypes(){
		List<Class<?>> cls=ClassFinder.find(SmallGameObject.class.getPackage().getName());
		for(Class<?> type:cls){
			//Filter out abstract objects
			if(Modifier.isAbstract(type.getModifiers()))
				continue;
			if(SmallGameObject.class.isAssignableFrom(type))
				smallGameObjectTypes.add(type);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Class<?>> getUITypes(){
		return (ArrayList<Class<?>>) uiElementTypes.clone();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Class<?>> getGOTypes(){
		return (ArrayList<Class<?>>) gameObjectTypes.clone();
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<Class<?>> getSGOTypes(){
		return (ArrayList<Class<?>>) smallGameObjectTypes.clone();
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
