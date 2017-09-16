package Editor;

import java.lang.reflect.InvocationTargetException;

import Test.SuperGlobal;
import TreeUI.InventoryPanel;
import focusObject.Incubator;
import focusObject.InventoryManager;
import focusObject.Panel;
import focusObject.TreeUIManager;

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
		inc.enableEditor();
		
		//Create the edge panels so panels will snap to the edges of the screen
		inc.getManager().addObject(new Panel(-2,0,2,SuperGlobal.getLength()));//Left
		inc.getManager().addObject(new Panel(SuperGlobal.getWidth(),0,2,SuperGlobal.getLength()));//Right
		inc.getManager().addObject(new Panel(0,-2,SuperGlobal.getWidth(),2));//Top
		inc.getManager().addObject(new Panel(0,SuperGlobal.getLength(),SuperGlobal.getWidth(),2));//Bottom
		
		//Create the editor command panel, the command panel auto-generates the variable panel and save management panel
		EditorCommandPanel ecp = new EditorCommandPanel(inc);
		inc.getManager().addObject(ecp);
		
	}
	
	/**
	 * Get the save string
	 */
	public void getCompleteSaveString(String fileName){
		
	}
	/**
	 * Load a save string
	 */
	public void loadSaveString(){
		
	}
}