package focusObject;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.Snappable;
import smallGameObjects.SmallGameObject;

public class Window extends Snappable{

	private int lastMouseX,lastMouseY;
	private int mouseX,mouseY;
	private Color color;
	public Window(int x, int y, int width, int height,Color color){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.color=color;
	}
	@Override
	public boolean isMouseOver(int x, int y){
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(color);
		g.fillRoundRect(x, y, width, height, 1);
		g.drawString(""+lastMouseX, x, y);
		g.drawString(""+lastMouseY, x+width, y);
		g.drawString(""+mouseX, x, y+height);
		g.drawString(""+mouseY, x+width, y+height);
	}
	@Override
	public boolean isMoveable() {
		return true;
	}
	@Override
	public SmallGameObject click(int x, int y,SmallGameObject item) {
		//Do nothing
		return null;
	}
	@Override
	public void update(int x, int y,int delta) {
		//No logic needed
		
	}
	@Override
	public SmallGameObject objectKeyPress(int x, int y, int key, SmallGameObject held) {
		// TODO Auto-generated method stub
		return null;
	}
	
}