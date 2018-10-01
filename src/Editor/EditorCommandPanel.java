package Editor;

import Editor.Item.EditorToolsPanel;
import Editor.Item.NodeConnector;
import Editor.Item.ObjectGrabber;
import Editor.Item.Selector;
import Editor.Save.SaveManagementPanel;
import Editor.Variables.VariablePanel;
import Test.SuperGlobal;
import focusObject.Incubator;
import focusObject.InteractableObject;
import focusObject.Panel;
import focusObject.TreeUIManager;

/**
 * This panel will have small buttons to close and open the save and variable panels
 * This panel is auto-generated in the lower right corner of the screen
 * @author Wesley Chiou
 *
 */
public class EditorCommandPanel extends Panel implements EditorImmune{
	private TreeUIManager tuim;
	private InteractableObject subject;
	private VariablePanel vp;
	private SaveManagementPanel smp;
	private EditorToolsPanel etp;
	public EditorCommandPanel(TreeUIManager tuim){
		this.tuim=tuim;
		this.active=true;
		this.width=200;
		this.height=20;
		this.x = 0;
		this.y = SuperGlobal.getLength()-height;
		
		int buttonSpace = width - 20;
		int buttonCount = 3;
		int buttonWidth = buttonSpace/buttonCount;
		//Create the buttons and panels related to the editor
		EditorButton vpb = new EditorButton("VP",0,0,buttonWidth,20);
		EditorButton smb = new EditorButton("SM",buttonWidth,0,buttonWidth,20);
		EditorButton etb = new EditorButton("ET",buttonWidth*2,0,buttonWidth,20);
		
		//Add the buttons to the command panel
		addObject(vpb);
		addObject(smb);
		addObject(etb);
		
		//Create the panel which will show the variables
		vp = new VariablePanel(tuim.getIncubator());
		tuim.addObject(vp);
		
		//Create the panel which will manage saving and loading
		smp = new SaveManagementPanel(tuim.getIncubator());
		tuim.addObject(smp);
		
		//Create the panel which will manage saving and loading
		etp = new EditorToolsPanel(new Selector(this),new NodeConnector(tuim.getIncubator()),new ObjectGrabber());
		tuim.addObject(etp);
		
		//Connect the panels to their buttons
		vp.setOrigin(vpb,SuperGlobal.getWidth()-vp.width,0);
		smp.setOrigin(smb,0,SuperGlobal.getLength()-height-smp.height);
		etp.setOrigin(etb, 0, SuperGlobal.getLength()-height-etp.height);
		
		//Add all the objects required for editing into the inventory
		//tuim.addItemtoInv(new Selector(this));//Selector needs access to the variable panel inorder to change variables displayed
		//tuim.addItemtoInv(new NodeConnector(tuim.getIncubator()));//NodeConnector uses the Incubator to add network connections
		//tuim.addItemtoInv(new ObjectGrabber());
		
	}
	
	public void openVPanel(){
		vp.open();
	}
	void openSPanel(){
		smp.open();
	}
	
	public void setObject(InteractableObject io){
		subject = io;
		//Let's make sure out panels know that a new element has been selected
		vp.setObject(io);
		smp.setObject(io);
	}
}