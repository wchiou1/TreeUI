package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import uiItem.UIItem;

public class Slider extends UIElement{
	private int length;
	public int width=50,height=10,range=100,current=0;
	public boolean vertical=true;
	public String key = "";
	/*public Slider(int x, int y, int length, int range,String key){
		this.x=x;
		this.y=y;
		this.width=length;
		this.length=length;
		this.range=range;
		height=10;
		current=length/2;
		this.key=key;
		vertical=false;
	}
	
	public Slider(int x, int y, int length, int range, boolean vertical, String key){
		this.x=x;
		this.y=y;
		this.length=length;
		this.vertical=vertical;
		if(vertical){
			this.height=length;
			this.width=10;
		}
		else{
			this.width=length;
			this.height=10;
		}
		this.range=range;
		current=length/2;
		this.key=key;
	}*/

	
	@Override
	public void update(int mouseX, int mouseY) {
		//super.update(mouseX, mouseY);TODO
		
		//Update the location of the slider using mouse info
		if(!locked)
			return;
		//If length is null, set the length using the vertical variable
		if(vertical)
			length = height;
		else
			length = width;
		changeSlider(mouseX,mouseY);
		dataNode.changeData(key, (int)(1.0*current/length*range));
		
	}
	private void changeSlider(int mouseX,int mouseY){
		int temp;
		if(vertical)
			temp=mouseY-y;
		else
			temp=mouseX-x;
		
		if(temp>0&&temp<length){
			current=temp;
			return;
		}
		if(temp<0)
			current=0;
		if(temp>length)
			current=length;
	}
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		if(vertical){
			//Draw top end bar
			g.setColor(Color.black);
			g.drawLine(x, y, x+width, y);
			
			//Draw bottom end bar
			g.drawLine(x, y+height, x+width, y+height);
			
			//Draw middle track bar
			g.drawLine(x+width/2, y, x+width/2, y+height);
			
			//Draw slider
			g.setColor(Color.lightGray);
			g.fillRect(x, y+current-2, width, 4);
		}
		else{
			//Draw left end bar
			g.setColor(Color.black);
			g.drawLine(x, y, x, y+height);
			
			//Draw right end bar
			g.drawLine(x+width, y, x+width, y+height);
			
			//Draw middle track bar
			g.drawLine(x, y+height/2, x+width, y+height/2);
			
			//Draw slider
			g.setColor(Color.lightGray);
			g.fillRect(x+current-2, y, 4, height);
		}
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		//Does nothing on click
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
		// TODO Auto-generated method stub
		
	}
	
}