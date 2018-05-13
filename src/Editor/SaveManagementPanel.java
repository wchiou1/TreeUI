package Editor;

import Test.SuperGlobal;
import TreeUI.InputBox;
import TreeUI.StaticText;
import focusObject.Incubator;
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
	private SaveEnvironmentButton seb;
	private LoadEnvironmentButton leb;
	private FileScannerButton fsb;
	private FileScannerPanel fsp;
	public SaveManagementPanel(Incubator inc){
		this.inc=inc;
		this.active=false;
		this.width=200;
		this.height=80;
		this.x = SuperGlobal.getWidth()-width;
		this.y = 0;
		
		//Create the buttons to load, save and enter filenames
		on = new EditorStaticText(0,0,this.width-20,20,"");
		on.text="NULL";
		fn = new EditorInputBox(0,20,this.width-20,20);//The subject starts null for these buttons
		spb = new SavePanelButton(0,40,inc,null,fn);//The save panel needs to update the FileScannerPanel
		lpb = new LoadPanelButton(80,40,inc,null,fn);
		seb = new SaveEnvironmentButton(0,60,inc,fn);
		leb = new LoadEnvironmentButton(80,60,inc,fn);
		//fsp = new FileScannerPanel();
		
		addObject(spb);
		addObject(lpb);
		addObject(seb);
		addObject(leb);
		addObject(on);
		addObject(fn);
		addObject(new FileScannerButton(160,40,fn));
		
	}
	
	public void setObject(InteractableObject io){
		subject = io;
		//Remember to set the object for the save and load buttons
		spb.setObject(io);
		lpb.setObject(io);
		on.text=""+io.getId()+" "+io.getClass().getSimpleName();
		
	}
}