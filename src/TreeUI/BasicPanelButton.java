package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.OriginObject;
import uiItem.UIItem;

/**
 * This class is a simple implementation of an origin object
 * @author Wesley Chiou
 *
 */
public class BasicPanelButton extends OriginObject{
	public int width,height;
	public BasicPanelButton(){
		this.width=10;
		this.height=10;
	}
	/*public BasicPanelButton(int x, int y){
		//This is relative to the panel they are on
		this.x=x;
		this.y=y;
		this.height=5;
		this.width=5;
	}
	public BasicPanelButton(int x, int y, int height, int width){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
	}*/
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
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
}