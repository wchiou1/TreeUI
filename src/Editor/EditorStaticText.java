package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Test.Shell;
import focusObject.UIElement;
import uiItem.UIItem;

public class EditorStaticText extends UIElement implements EditorImmune{
	public String text = "";
	public int width,height;
	public EditorStaticText(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}
	public EditorStaticText(int x, int y, int width, int height,String text){
		this.x=0;
		this.y=0;
		this.rx=x;
		this.ry=y;
		this.width=width;
		this.height=height;
		this.text=text;
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
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		return item;
	}
}