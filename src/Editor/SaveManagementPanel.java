package Editor;

import Test.SuperGlobal;
import TreeUI.InputBox;
import TreeUI.StaticText;
import focusObject.InteractableObject;
import focusObject.Panel;

/**
 * This panel will display the variables of the object it has stored
 * @author Wesley Chiou
 *
 */
public class SaveManagementPanel extends Panel implements EditorImmune{
	private Incubator inc;
	private InteractableObject subject;
	private SavePanelButton spb;
	private LoadPanelButton lpb;
	private EditorStaticText on;
	private EditorInputBox fn;
	public SaveManagementPanel(Incubator inc){
		this.inc=inc;
		this.active=false;
		this.width=200;
		this.height=60;
		this.x = SuperGlobal.getWidth()-width;
		this.y = 0;
		
		//Create the buttons to load, save and enter filenames
		on = new EditorStaticText(0,0,this.width-20,20,"");
		on.text="NULL";
		fn = new EditorInputBox(0,20,this.width-20,20);
		spb = new SavePanelButton(0,40,inc,null,fn);//The subject starts null for these buttons
		lpb = new LoadPanelButton(90,40,inc,null,fn);
		
		addObject(spb);
		addObject(lpb);
		addObject(on);
		addObject(fn);
		
	}
	
	public void setObject(InteractableObject io){
		subject = io;
		//Remember to set the object for the save and load buttons
		spb.setObject(io);
		lpb.setObject(io);
		on.text=""+io.getId()+" "+io.getClass().getSimpleName();
		
	}
}