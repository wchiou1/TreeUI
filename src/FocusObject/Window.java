package FocusObject;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.Snappable;
import TreeUI.UIItem;

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
	public boolean isMouseOver(int x, int y){
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	@Override
	public void draw(Graphics g) {
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
	public UIItem click(int x, int y,UIItem item) {
		//Do nothing
		return null;
	}
	@Override
	public void update(int x, int y) {
		//No logic needed
		
	}
	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//Doesn't do anything
		
	}
	
}