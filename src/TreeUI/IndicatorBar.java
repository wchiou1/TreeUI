package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import uiItem.UIItem;

public class IndicatorBar extends UIElement{
	public boolean vertical=true;
	public int width=9,height=100,range=100;
	public String key="";
	/*public IndicatorBar(int x ,int y ,int length,int range, String key){
		this(x,y,length,range,true,key);
	}
	public IndicatorBar(int x, int y, int length, int range, boolean vertical, String key){
		this.x=x;
		this.y=y;
		if(vertical){
			this.height=length;
			this.width=9;
		}
		else{
			this.width=length;
			this.height=9;
		}
		this.range=range;
		this.key=key;
		this.vertical=vertical;
	}*/

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		//Calculate how far the bar needs to be
		int data=dataNode.getData(key);
		if(data>range)
			data=range;
		if(data<0)
			data=0;
		int disLength;
		
		g.setColor(Color.red);
		if(vertical){
			disLength=(int)(1.0*data/range*(height-4));
			g.fillRect(x+2, y+2+(height-4-disLength), width-4, disLength);
		}
		else{
			disLength=(int)(1.0*data/range*(width-4));
			g.fillRect(x+2+(width-4-disLength), y+2, disLength, height-4);
		}
		
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		//IndicatorBars don't do anything on click.
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
		//IndicatorBars don't do anything on keypress
		
	}
	@Override
	public void update(int mouseX, int mouseY) {
		
	}
	
}