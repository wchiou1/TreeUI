package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Editor.EditorImmune;
import uiItem.UIItem;

public class InventoryPanelSlot extends InventorySlot implements EditorImmune{
	public boolean highlight;
	public InventoryPanelSlot(int x, int y) {
		super(x, y);
		highlight=false;
	}
	public UIItem getStored(){
		return stored;
	}
	public void forcePush(UIItem item){
		stored=item;
	}
	
	@Override
	public void draw(Graphics g) {
		//Draw a box and then draw the item inside
		if(highlight){
			g.setColor(Color.black);
			g.fillRect(x, y, width, height);
		}
		g.setColor(Color.lightGray);
		g.fillRect(x+1, y+1, width-2, height-2);
		if(stored!=null)
			stored.draw(g,x,y);
	}
	
}