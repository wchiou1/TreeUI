package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

/**
 * This class stores a UIItem and gives the item to the hand slot
 * in the mouse manager when clicked.
 * @author Wesley
 *
 */
public class InventorySlot extends ItemStorage{
	public InventorySlot(){
		this.x=0;
		this.y=0;
		stored=null;
		width=20;
		height=20;
	}
	public InventorySlot(int x, int y){
		this.rx=x;
		this.ry=y;
		stored=null;
		width=20;
		height=20;
	}

	@Override
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}
	
}