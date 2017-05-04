package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class StaticText extends UIElement{
	private String text;
	public StaticText(int x, int y,String text){
		this.x=x;
		this.y=y;
		this.text=text;
	}
	StaticText(int x, int y){
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
		g.drawString(text, x, y);
		
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