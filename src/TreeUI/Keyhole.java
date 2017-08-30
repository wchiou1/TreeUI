package TreeUI;

import org.newdawn.slick.Graphics;

import uiItem.Key;
import uiItem.UIItem;

public class Keyhole extends UIElement{
	public int pattern = -1;
	public String key = "";
	private boolean open;
	/*
	public Keyhole(int x, int y,int pattern,String key){
		this.x=x;
		this.y=y;
		this.pattern=pattern;
		this.key=key;
		open=false;
	}
	*/
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.fillRect(x, y, 3, 6);
		
	}
	
	private void toggle(){
		if(open){
			open=false;
			dataNode.changeData(key, 0);
		}
		else{
			open=true;
			dataNode.changeData(key, 1);
		}
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		if(item!=null&&((Key)item).getPattern()==pattern)//Check if the key fits
			toggle();
		return item;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//Doesn't do anything when you hold the button down
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+3)
			if(y>=this.y&&y<=this.y+6)
				return true;
		return false;
	}
	@Override
	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}
	
}