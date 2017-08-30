package focusObject;

import org.newdawn.slick.Graphics;

import TreeUI.InventoryPanel;
import uiItem.UIItem;

/**
 * Handles the inventory of the player
 * Has hotbar functionality and tracks active item
 * @author Wesley
 */
public class InventoryManager{
	private InventoryPanel inventory;
	int activeSlot;
	public InventoryManager(InventoryPanel inv){
		activeSlot=0;
		inventory=inv;
		inventory.setHighlight(activeSlot);
	}
	public void overwriteActive(UIItem item){
		inventory.forcePush(activeSlot, item);
	}
	public InventoryPanel getPanel(){
		return inventory;
	}
	/**
	 * Adds an item in the first hotbar slot possible
	 * @param item
	 * @return if there is room in the hotbar
	 */
	public boolean addItem(UIItem item){
		System.out.println("Adding "+item);
		//First let's set the query slot to 0
		for(int i=0;i<inventory.size();i++){
			if(inventory.getItem(i)==null){
				inventory.swap(i, item);
				return true;
			}
		}
		return false;
	}
	public UIItem switchActiveUp(){
		activeSlot++;
		if(activeSlot>=inventory.size())
			activeSlot=0;
		inventory.setHighlight(activeSlot);
		return getActiveItem();
	}
	public UIItem switchActiveDown(){
		activeSlot--;
		if(activeSlot<0)
			activeSlot=inventory.size()-1;
		inventory.setHighlight(activeSlot);
		return getActiveItem();
	}
	/**
	 * Gets the current active
	 * @return
	 */
	public UIItem getActiveItem(){
		return inventory.getItem(activeSlot);
	}
	
	/**
	 * Removes the currently active item
	 */
	public void removeActiveItem(){
		inventory.forcePush(activeSlot, null);
	}
}