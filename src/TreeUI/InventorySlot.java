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
public class InventorySlot extends UIElement{
	public int width,height;
	protected SmallGameObject stored;
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
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x ,int y) {
		//Draw a box and then draw the item inside
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		if(stored!=null)
			stored.draw(g,x,y);
	}

	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		//Apply the currently held item to what is in the slot
		if(stored!=null)
			stored.click(x,y,item);
		
		
		//Switches the item in the stored slot, returns the item in the stored slot
		SmallGameObject temp=stored;
		stored=item;
		return temp;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	@Override
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}
	
}