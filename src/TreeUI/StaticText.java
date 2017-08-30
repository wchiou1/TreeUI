package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Test.Shell;
import uiItem.UIItem;

public class StaticText extends UIElement{
	public String text = "";
	public int width,height;
	public StaticText(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setClip(x+2, y+2, width-4, height-4);
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
	public void keyPress(int mouseX, int mouseY, int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		return item;
	}
}