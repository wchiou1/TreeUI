package Editor;

import java.lang.reflect.InvocationTargetException;

import GameLogic.Incubator;
import Test.SuperGlobal;
import TreeUI.InventoryPanel;
import focusObject.InventoryManager;
import focusObject.Panel;

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
		inc.getManager().addObject(new Panel(100,0,2,SuperGlobal.getLength()));//Left
		inc.getManager().addObject(new Panel(SuperGlobal.getWidth(),0,2,SuperGlobal.getLength()));//Right
		inc.getManager().addObject(new Panel(0,-2,SuperGlobal.getWidth(),2));//Top
		inc.getManager().addObject(new Panel(0,SuperGlobal.getLength(),SuperGlobal.getWidth(),2));//Bottom
		VariablePanel vp = new VariablePanel(inc);
		inc.getManager().addObject(vp);
		InventoryPanel iPanel = new InventoryPanel();
		iPanel.forcePush(0, new Selector(vp));
		inc.getManager().addObject(iPanel);
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