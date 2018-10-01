package Editor.Item;

import Editor.EditorImmune;
import Test.SuperGlobal;
import TreeUI.InputBox;
import TreeUI.InventoryPanelSlot;
import TreeUI.StaticText;
import focusObject.Incubator;
import focusObject.InteractableObject;
import focusObject.Panel;

/**
 * This panel will display the variables of the object it has stored
 * @author Wesley Chiou
 *
 */
public class EditorToolsPanel extends Panel implements EditorImmune{
	public EditorToolsPanel(Selector s,NodeConnector nc,ObjectGrabber og){
		this.active=false;
		this.width=200;
		this.height=60;
		this.x = SuperGlobal.getWidth()-width;
		this.y = 0;
		
		//Create the Inventory Slots and fill them with items
		InventoryPanelSlot selector = new InventoryPanelSlot(5,5);
		InventoryPanelSlot nodeconnector = new InventoryPanelSlot(30,5);
		InventoryPanelSlot objectgrabber = new InventoryPanelSlot(55,5);
		
		addObject(selector);
		addObject(nodeconnector);
		addObject(objectgrabber);
		
		selector.forcePush(s);
		nodeconnector.forcePush(nc);
		objectgrabber.forcePush(og);
		
		//addObject(spb);
		
	}
}