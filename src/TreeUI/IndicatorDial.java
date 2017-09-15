package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import GameLogic.GameMath;
import focusObject.UIElement;
import uiItem.UIItem;

public class IndicatorDial extends UIElement{
	public int radius, range, angle;
	public String key;
	public IndicatorDial(){
		this.x=0;
		this.y=0;
		radius=20;
		this.range=360;
		this.key="";
		angle=0;
	}
	@Override
	public void update(int x, int y) {
		//super.update(x, y);TODO
		//update the angle
		int temp=dataNode.getData(key);
		if(temp==Integer.MIN_VALUE)
			temp=0;
		angle=(int)(1.0*temp/range*360);
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		int tempAngle=angle+270;
		g.setColor(Color.black);
		g.fillOval(x, y, radius*2, radius*2);
		g.setColor(Color.white);
		g.fillOval(x+2,y+2,radius*2-4,radius*2-4);
		g.setColor(Color.lightGray);
		g.fillOval(x+radius-radius/8,y+radius-radius/8,radius/4,radius/4);
		
		g.setColor(Color.red);
		int x1=(int)((radius/8)*Math.cos(1.0*tempAngle/180*Math.PI));
		int y1=(int)((radius/8)*Math.sin(1.0*tempAngle/180*Math.PI));
		int x2=(int)((radius-2)*Math.cos(1.0*tempAngle/180*Math.PI));
		int y2=(int)((radius-2)*Math.sin(1.0*tempAngle/180*Math.PI));
		
		g.drawLine(x+radius+x1, y+radius+y1, x2+x+radius, y2+y+radius);
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		//Do nothing
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(GameMath.dis(this.x+radius, this.y+radius, x, y)<=radius)
			return true;
		return false;
	}
	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//IndicatorDials don't do anything on keypress
		
	}
	
}