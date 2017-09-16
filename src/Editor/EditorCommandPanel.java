package Editor;

import Test.SuperGlobal;
import focusObject.Incubator;
import focusObject.InteractableObject;
import focusObject.Panel;

/**
 * This panel will have small buttons to close and open the save and variable panels
 * This panel is auto-generated in the lower right corner of the screen
 * @author Wesley Chiou
 *
 */
public class EditorCommandPanel extends Panel implements EditorImmune{
	private Incubator inc;
	private InteractableObject subject;
	private VariablePanel vp;
	private SaveManagementPanel smp;
	public EditorCommandPanel(Incubator inc){
		this.inc=inc;
		this.active=true;
		this.width=200;
		this.height=20;
		this.x = 0;
		this.y = SuperGlobal.getLength()-height;
		//Create the buttons and panels related to the editor
		VariablePanelButton vpb = new VariablePanelButton(0,0,90,20);
		SaveManagementButton smb = new SaveManagementButton(90,0,90,20);
		
		//Add the buttons to the command panel
		addObject(vpb);
		addObject(smb);
		
		//Create the panel which will show the variables
		vp = new VariablePanel(inc);
		inc.getManager().addObject(vp);
		
		//Create the panel which will manage saving and loading
		smp = new SaveManagementPanel(inc);
		inc.getManager().addObject(smp);
		
		//Connect the panels to their buttons
		vp.setOrigin(vpb,SuperGlobal.getWidth()-vp.width,0);
		smp.setOrigin(smb,0,SuperGlobal.getLength()-height-smp.height);
		
		//Add all the objects required for editing into the inventory
		inc.getManager().addItemtoInv(new Selector(this));//Selector needs access to the variable panel inorder to change variables displayed
		inc.getManager().addItemtoInv(new NodeConnector(inc));//NodeCOnnector uses the Incubator to add network connections
		
	}
	
	void openVPanel(){
		vp.open();
	}
	void openSPanel(){
		smp.open();
	}
	
	void setObject(InteractableObject io){
		subject = io;
		//Let's make sure out panels know that a new element has been selected
		vp.setObject(io);
		smp.setObject(io);
	}
}