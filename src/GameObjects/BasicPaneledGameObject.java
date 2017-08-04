package GameObjects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.UIItem;

public class BasicPaneledGameObject extends PaneledGameObject{
	
	public BasicPaneledGameObject(){
		this.height=5;
		this.width=5;
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