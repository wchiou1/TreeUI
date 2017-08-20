package Editor;

import java.lang.reflect.InvocationTargetException;

import GameLogic.Incubator;

/**
 * This is the editor class.
 * It will handle the creation of specialized editor panels and overencompassing functions
 * such as saving, loading and wrapper handling.
 * @author Wesley Chiou
 *
 */
public class Editor{
	private Incubator inc;
	public Editor(Incubator inc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		this.inc=inc;
		//Create and add the EditorBasePanel
		inc.getManager().addGameObject(new EditorBasePanel(inc));
	}
	
	/**
	 * Get the save string with wrappers filtered out and save it in a file
	 */
	public void getCompleteSaveString(String fileName){
		//We must remove all wrappers!
		//because of how the wrappers work, we need to reconnect any originobjects to their wrapped panels
	}
	/**
	 * Load a save string and automatically add in wrappers to support editing
	 */
	public void loadSaveString(){
		
	}
}