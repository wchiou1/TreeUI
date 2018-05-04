package focusObject;

import java.lang.reflect.InvocationTargetException;

import Editor.EditorCommandPanel;
import Test.SuperGlobal;
import smallGameObjects.*;

/**
 * This is the editor class.
 * It will handle the creation of specialized editor panels and overencompassing functions
 * such as saving, loading and wrapper handling.
 * @author Wesley Chiou
 *
 */
public class Editor{
	private Incubator inc;
	public Editor(TreeUIManager tuim) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		this.inc=tuim.getIncubator();
		tuim.enableEditor();
		
		//Create the edge panels so panels will snap to the edges of the screen
		/*tuim.addObject(new Panel(-2,0,2,SuperGlobal.getLength()));//Left
		tuim.addObject(new Panel(SuperGlobal.getWidth(),0,2,SuperGlobal.getLength()));//Right
		tuim.addObject(new Panel(0,-2,SuperGlobal.getWidth(),2));//Top
		tuim.addObject(new Panel(0,SuperGlobal.getLength(),SuperGlobal.getWidth(),2));//Bottom
		*/
		//Create the editor command panel, the command panel auto-generates the variable panel and save management panel
		EditorCommandPanel ecp = new EditorCommandPanel(tuim);
		tuim.addObject(ecp);
		tuim.addItemtoInv(new Wirecutters());
		tuim.addItemtoInv(new Multitool());
		
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