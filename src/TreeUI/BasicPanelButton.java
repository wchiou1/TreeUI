package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import FocusObject.OriginObject;
import TreeUI.UIItem;

/**
 * This class is a simple implementation of an origin object
 * @author Wesley Chiou
 *
 */
public class BasicPanelButton extends OriginObject{
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
	public UIItem click(int x, int y,UIItem item) {
		if(view==null){
			System.out.println("ERROR: panel not set in origin object");
			return item;
		}
		view.toggle();
		return item;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if(highlight)
			g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
}