package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

public class Indicator extends UIElement{
	public int width=6,height=8;
	public String key = "";
	/*
	public Indicator(int x, int y,String key){
		this.x=x;
		this.y=y;
		width=6;
		height=8;
		this.key=key;
	}
	*/

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		int temp=dataNode.getData(key);
		if(temp==Integer.MIN_VALUE){
			g.setColor(Color.gray);
			g.fillRect(x, y, width, height);
			return;
		}
		switch(temp){
		case 0:{
			g.setColor(Color.red); break;
		}
		case 1:{
			g.setColor(Color.blue); break;
		}
		case 2:{
			g.setColor(Color.green); break;
		}
		default:{
			g.setColor(Color.gray);
		}
		}
		g.fillRect(x, y, width, height);
	}

	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//Do nothing
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
		// TODO Auto-generated method stub
		
	}
}