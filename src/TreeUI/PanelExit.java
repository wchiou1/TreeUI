package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.SmallGameObject;

public class PanelExit extends UIElement{
	private int height, width;
	public PanelExit(){
		height=10;
		width=10;
	}

	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.lightGray);
		g.fillRect(x+1, y+1, width-2, height-2);
		g.setColor(Color.black);
		g.drawLine(x+1, y+1, width-2+x, height-2+y);
		g.drawLine(x+1, y+height-2, width-2+x, y+1);
	}

	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		screen.close();
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
	public void update(int mouseX, int mouseY,int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getWidth() {
		return width;
	}

	@Override
	protected int getHeight() {
		return height;
	}
	
}