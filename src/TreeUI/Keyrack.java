package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.Key;
import smallGameObjects.SmallGameObject;


/**
 * This UIElement holds a single key which will be put into the inventory
 * when the UIElement is clicked. The rack only holds a single key.
 * @author Wesley
 *
 */
public class Keyrack extends UIElement{
	public Key key;
	/*public Keyrack(int x, int y,Key key){
		this.x=x;
		this.y=y;
		this.key=key;
	}*/
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.orange);
		g.fillRect(x, y, 10, 10);
		if(key!=null){
			key.draw(g, x, y);
		}
		
	}

	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		//Give a key if item is null(hand is empty)
		if(item==null){
			Key temp=key;
			key=null;
			return temp;
		}
		//If there is no key and the hand is holding a key, put it on the rack
		if(item instanceof Key&&key==null){
			key=(Key)item;
			return null;
		}
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+10)
			if(y>=this.y&&y<=this.y+10)
				return true;
		return false;
	}
	@Override
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}
	
}