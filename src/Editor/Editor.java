package Editor;

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
	public Editor(Incubator inc){
		this.inc=inc;
		//Create and add the EditorBasePanel
	}
	
	/**
	 * Get the save string with wrappers filtered out and save it in a file
	 */
	public void getCompleteSaveString(String fileName){
		
	}
	/**
	 * Load a save string and automatically add in wrappers to support editing
	 */
	public void loadSaveString(){
		
	}
}