package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import GameLogic.GameMath;
import focusObject.UIElement;
import uiItem.UIItem;

public class Button extends UIElement{
	public int radius = 6;
	public String key = "";
	public int range=2;
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
		g.fillOval(x, y, radius, radius);
		
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		int temp=dataNode.getData(key);
		if(temp==Integer.MIN_VALUE){
			dataNode.changeData(key, 0);
			temp=-1;
		}
		if(temp>=range)
			dataNode.changeData(key, 0);
		else
			dataNode.changeData(key, temp+1);
		System.out.println("Key:"+key+" = "+temp);
		return item;
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+radius/2, this.y+radius/2, x, y)<=radius/2)
			return true;
		return false;
	}
	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//Buttons don't do anything when a key is pressed
		
	}
	@Override
	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
	
}