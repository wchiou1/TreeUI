package TreeUI;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import FocusObject.Panel;
import Test.SuperGlobal;

/**
 * This creates an inventory panel horizontally with the size and length auto generated
 * @author Wesley
 *
 */
public class InventoryPanel extends Panel{
	private int slotNum;
	public InventoryPanel() {
		super(SuperGlobal.getWidth()/2-(22*slotNum+2)/2,SuperGlobal.getLength()-26);
		this.slotNum=slotNum;
		this.height=24;
		this.width=22*slotNum+2;
		this.active=true;
		addSlots();
	}
	private void addSlots(){
		for(int i=0;i<slotNum;i++){
			addObject(new InventoryPanelSlot(2+i*22,2));
		}
	}
	@Override
	public boolean isMoveable() {
		return false;
	}
	public void draw(Graphics g){
		//TODO Draw a panel and then draw the inventory slots
		g.setColor(Color.gray);
		g.fillRoundRect(x, y, width, height, 2);
		g.setColor(Color.white);
		g.fillRoundRect(x+2, y+2, width-4, height-4, 2);
		
		g.setClip(x+2, y+2, width-4, height-4);

		for(UIElement io:objectList)
			io.UDraw(g);
		g.clearClip();
	}
	
}