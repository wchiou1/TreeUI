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
public abstract class ItemStorage extends UIElement{
	public int width,height;
	public SmallGameObject stored;
	@Override
	public boolean isMoveable(){
		return false;
	}
	public SmallGameObject getStored(){
		return stored;
	}
	@Override
	public void draw(Graphics g,int x ,int y) {
		//Draw a box and then draw the item inside
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		if(stored!=null){
			int centerX = stored.getCenterX();
			int centerY = stored.getCenterY();
			stored.draw(g,x+width/2-centerX,y+height/2-centerY);
		}
	}
	public boolean testMouseOnStored(int mx, int my){
		if(stored==null)
			return false;
		//First move the stored object
		int tempX = stored.x;
		int tempY = stored.y;
		int centerX = stored.getCenterX();
		int centerY = stored.getCenterY();
		
		stored.move(this.x+(width/2)-centerX, this.y+(height/2)-centerY);
		
		boolean result = stored.isMouseOver(mx, my);
		
		stored.move(tempX, tempY);
		return result;
	}

	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		return switchStored(item);
	}
	protected SmallGameObject switchStored(SmallGameObject item){
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