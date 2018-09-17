package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

public class ScrollBar extends UIElement{
	public boolean vertical=true;
	public int width=9,height=100,range=100;

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		
	}

	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		
		return item;
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
		
	}
}