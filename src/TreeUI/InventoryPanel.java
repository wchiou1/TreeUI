package TreeUI;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Editor.EditorImmune;
import Test.SuperGlobal;
import focusObject.Panel;
import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

/**
 * This creates an inventory panel horizontally with the size and length auto generated
 * @author Wesley
 *
 */
public class InventoryPanel extends Panel implements EditorImmune{
	private int slotNum;
	private ArrayList<InventoryPanelSlot> slots = new ArrayList<InventoryPanelSlot>();
	public InventoryPanel() {
		super();
		this.slotNum=5;
		this.width=22*slotNum+2;
		this.x=SuperGlobal.getWidth()/2-(this.width)/2;
		this.y=SuperGlobal.getLength()-26;
		this.height=24;
		this.active=true;
		addSlots();
	}
	public void clearHighlight(){
		for(InventoryPanelSlot s:slots){
			s.highlight=false;
		}
	}
	public void setHighlight(int slot){
		clearHighlight();
		slots.get(slot).highlight=true;
	}
	public int size(){
		return slotNum;
	}
	public SmallGameObject swap(int slot, SmallGameObject item){
		SmallGameObject temp = getItem(slot);
		slots.get(slot).forcePush(item);
		return temp;
	}
	public SmallGameObject getItem(int slot){
		return slots.get(slot).getStored();
	}
	private void addSlots(){
		for(int i=0;i<slotNum;i++){
			slots.add(new InventoryPanelSlot(2+i*22,2));
			addObject(slots.get(i));
		}
	}
	public void forcePush(int slot, SmallGameObject item){
		slots.get(slot).forcePush(item);
	}
	@Override
	public boolean isMoveable() {
		return false;
	}
	public void printStorage(){
		for(InventoryPanelSlot slot:slots){
			System.out.print(slot.getStored());
		}
		System.out.println();
	}
	@Override
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