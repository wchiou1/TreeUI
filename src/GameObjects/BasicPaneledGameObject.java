package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.UIItem;

public class BasicPaneledGameObject extends PaneledGameObject{
	
	public BasicPaneledGameObject(int x, int y){
		//This is relative to the panel they are on
		this.x=x;
		this.y=y;
		this.height=5;
		this.width=5;
	}
	public BasicPaneledGameObject(int x, int y, int height, int width){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if(highlight)
			g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
	
}