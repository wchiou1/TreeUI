package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TextBox extends UIElement{
	private String key;
	public TextBox(int x, int y,String key){
		this.x=x;
		this.y=y;
		this.key=key;
	}
	TextBox(int x, int y){
		this.x=x;
		this.y=y;
	}

	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		int data=dataNode.getData(key);
		if(data==Integer.MIN_VALUE)
			g.drawString("NO SIGNAL", x, y);
		else
			g.drawString(""+data, x, y);
		
		
	}

	@Override
	public UIItem click(int x, int y,UIItem item) {
		//Does nothing on click
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
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
}