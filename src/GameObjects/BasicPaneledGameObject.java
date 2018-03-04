package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import uiItem.UIItem;

public class BasicPaneledGameObject extends GameObject{
	public int width,height;
	public BasicPaneledGameObject(){
		this.height=5;
		this.width=5;
		System.out.println();
	}
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if(highlight)
			g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
	@Override
	public UIItem rightClick(int x, int y, UIItem item) {
		return item;
	}
	@Override
	public int getCenterX() {
		// TODO Auto-generated method stub
		return x+width/2;
	}
	@Override
	public int getCenterY() {
		// TODO Auto-generated method stub
		return y+height/2;
	}
	@Override
	public void objectKeyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}
	
}