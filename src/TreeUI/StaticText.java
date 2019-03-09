package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Test.Shell;
import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

public class StaticText extends UIElement{
	public String text = "";
	public int width,height;
	public StaticText(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}
	public StaticText(int x, int y, int width, int height,String text){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.text=text;
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x,int y) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setFont(Shell.SMALL_FONT);
		g.drawString(text, x+2, y+2);
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}

	@Override
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		return item;
	}
}