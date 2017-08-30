package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.GameMath;
import uiItem.UIItem;

public class Switch extends UIElement{
	public int width=10,height=14;
	public String key = "";
	public boolean up=true;
	/*public Button(int x, int y,String key){
		this.x=x;
		this.y=y;
		this.key=key;
		radius=6;
	}
	public Button(int x, int y, int radius, String key){
		this.x=x;
		this.y=y;
		this.radius=radius;
		this.key=key;
	}*/
	
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		g.fillRect(x+1, y+1, width-2, height-2);
		g.setColor(Color.black);
		if(up)
			g.fillRect(x+3, y+3, width-6, height/2-3);
		else
			g.fillRect(x+3, y+3+height/2-3, width-6, height/2-3);
		
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		if(up)
			up=false;
		else
			up=true;
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
	public void keyPress(int mouseX, int mouseY, int key) {
		
	}
	@Override
	public void update(int mouseX, int mouseY) {
		if(up)
			dataNode.changeData(key, 0);
		else
			dataNode.changeData(key, 1);
		
	}
	
}