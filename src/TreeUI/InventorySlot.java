package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * This class stores a UIItem and gives the item to the hand slot
 * in the mouse manager when clicked.
 * @author Wesley
 *
 */
public class InventorySlot extends UIElement{
	private int width,height;
	protected UIItem stored;
	public InventorySlot(int x, int y){
		this.x=x;
		this.y=y;
		stored=null;
		width=20;
		height=20;
	}
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//Draw a box and then draw the item inside
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		if(stored!=null)
			stored.draw(g,x,y);
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		//Switches the item in the stored slot, returns the item in the stored slot
		UIItem temp=stored;
		stored=item;
		return temp;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		// Does nothing on keypress
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	@Override
	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
	
}