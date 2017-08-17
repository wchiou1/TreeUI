package Test;

import java.util.Hashtable;

import org.newdawn.slick.state.BasicGameState;

public class SuperGlobal{
	private static int width,length;
	private static boolean set=false;
	private static Hashtable<String,BasicGameState> gameStates=new Hashtable<String,BasicGameState>();
	private static Shell shell;
	public static void setGameState(String name,BasicGameState state){
		gameStates.put(name, state);
	}
	public static BasicGameState getGameState(String string){
		if(!gameStates.containsKey(string)){
			System.out.println("Error, does not contain gamestate of "+string);
		}
		return gameStates.get(string);
	}
	public static void setShell(Shell runner){
		shell=runner;
	}
	public static Shell getShell(){
		return shell;
	}
	public static void setWindowSize(int w,int l){
		if(set){
			System.out.println("WARNING: Attempted to setWindowSize in SuperGlobal after size has been set.");
			return;
		}
		set=true;
		width=w;
		length=l;
	}
	public static int getWidth(){
		return width;
	}
	public static int getLength(){
		return length;
	}
}