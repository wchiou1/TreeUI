package focusObject;

import TreeUI.InventoryPanel;
import smallGameObjects.SmallGameObject;

/**
 * Handles the inventory of the player
 * Has hotbar functionality and tracks active item
 * @author Wesley
 */
public class InventoryManager{
	public static InventoryManager master;
	private InventoryPanel inventory;
	int activeSlot;
	public InventoryManager(InventoryPanel inv){
		activeSlot=0;
		inventory=inv;
		inventory.setHighlight(activeSlot);
		master = this;
	}
	public void overwriteActive(SmallGameObject item){
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
	public boolean addItem(SmallGameObject item){
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
	public SmallGameObject switchActiveUp(){
		activeSlot++;
		if(activeSlot>=inventory.size())
			activeSlot=0;
		inventory.setHighlight(activeSlot);
		return getActiveItem();
	}
	public SmallGameObject switchActiveDown(){
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
	public SmallGameObject getActiveItem(){
		return inventory.getItem(activeSlot);
	}
	
	/**
	 * Removes the currently active item
	 */
	public void removeActiveItem(){
		inventory.forcePush(activeSlot, null);
	}
}