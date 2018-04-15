package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.Key;
import smallGameObjects.SmallGameObject;

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
	public void draw(Graphics g, int x, int y) {
		if(dataNode.getData(key)==0)
			g.setColor(Color.red);
		else
			g.setColor(Color.blue);
			
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
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		if(item!=null&&((Key)item).getPattern()==pattern)//Check if the key fits
			toggle();
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+3)
			if(y>=this.y&&y<=this.y+6)
				return true;
		return false;
	}
	@Override
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}
	
}