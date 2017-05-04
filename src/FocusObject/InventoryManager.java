package FocusObject;

import org.newdawn.slick.Graphics;

import TreeUI.InventoryPanel;
import TreeUI.UIItem;

/**
 * Handles the inventory of the player
 * Has hotbar functionality and tracks active item
 * @author Wesley
 */
public class InventoryManager{
	private InventoryPanel inventory;
	UIItem activeItem;
	int activeSlot;//-1 means there is no active slot
	UIItem[] hotbar;
	public InventoryManager(InventoryPanel inv,int size){
		activeSlot=-1;
		hotbar=new UIItem[size];
		inventory=inv;
	}
	/**
	 * Adds an item in the first hotbar slot possible
	 * @param item
	 * @return if there is room in the hotbar
	 */
	public boolean addItem(UIItem item){
		int i;
		for(i=0;i<hotbar.length;i++){
			if(hotbar[i]==null){
				hotbar[i]=item;
				return true;
			}
		}
		return false;
	}
	/**
	 * Switch active item
	 */
	public void switchActive(int slot){
		if(slot>=hotbar.length||slot<0)
			return;
		activeItem=hotbar[slot];
	}
	/**
	 * Gets the current active
	 * @return
	 */
	public UIItem getActiveItem(){
		return activeItem;
	}
	
	/**
	 * Removes the item at the slot
	 */
	public void removeItem(int slot){
		if(slot>=hotbar.length||slot<0)
			return;
		hotbar[slot]=null;
		if(slot==activeSlot)
			activeSlot=-1;
		activeItem=null;
	}
	/**
	 * Removes the currently active item
	 */
	public void removeActiveItem(){
		hotbar[activeSlot]=null;
		activeItem=null;
		activeSlot=-1;
	}
	public void draw(Graphics g){
		
	}
}