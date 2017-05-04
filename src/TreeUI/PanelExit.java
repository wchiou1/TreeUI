package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import FocusObject.OriginObject;

public class PanelExit extends UIElement{
	private int height, width;
	private OriginObject oo;
	public PanelExit(int x, int y, OriginObject oo){
		this.x=x;
		this.y=y;
		height=10;
		width=10;
		this.oo=oo;
	}
	@Override
	public void update(int mouseX, int mouseY) {
		if(hover){
			oo.highlight();
		}
		else{
			oo.unhighlight();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.lightGray);
		g.fillRect(x+1, y+1, width-2, height-2);
		g.setColor(Color.black);
		g.drawLine(x+1, y+1, width-2+x, height-2+y);
		g.drawLine(x+1, y+height-2, width-2+x, y+1);
	}

	@Override
	public UIItem click(int x, int y, UIItem item) {
		oo.getView().close();
		return item;
	}

	@Override
	public void keyPress(int mouseX, int mouseY, int key) {
		//Does nothing
		
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
	
}