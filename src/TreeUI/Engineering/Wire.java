package TreeUI.Engineering;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import focusObject.UIElement;
import smallGameObjects.*;

public class Wire extends UIElement{
	public String key = "";
	public boolean cut = false;
	public boolean vertical=false;
	public boolean pulse = false;
	public int length = 60;
	public int thickness = 4;
	public int wireRed = (int) Math.round(Math.random()*255);
	public int wireGreen = (int) Math.round(Math.random()*255);
	public int wireBlue = (int) Math.round(Math.random()*255);
	@Override
	public boolean isMoveable() {
		return false;
	}

	@Override
	public void draw(Graphics g,int x,int y) {
		g.setColor(new Color(wireRed,wireGreen,wireBlue));
		if(vertical){
			if(cut){
				g.fillRect(x, y, thickness, length/2-2);
				g.fillRect(x, y+length/2+2, thickness, length/2-2);
			}
			else
				g.fillRect(x, y, thickness, length);
		}
		else{
			if(cut){
				g.fillRect(x, y, length/2-2, thickness);
				g.fillRect(x+length/2+2, y, length/2-2, thickness);
			}
			else
				g.fillRect(x, y, length, thickness);
		}
	}
	private void cutMend(){
		cut = !cut;
	}
	@Override
	public SmallGameObject click(int x, int y, SmallGameObject item) {
		if(item instanceof Multitool&&!cut)
			pulse = true;
		if(item instanceof Wirecutters)
			cutMend();
		return item;
	}

	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x-1&&x<=this.x+(vertical?thickness:length)+1)
			if(y>=this.y-1&&y<=this.y+(vertical?length:thickness)+1)
				return true;
		return false;
	}
	@Override
	public void update(int mouseX, int mouseY,int delta) {
		if(pulse&&!locked)
			pulse = false;
	}

	@Override
	protected int getWidth() {
		return 0;
	}

	@Override
	protected int getHeight() {
		return 0;
	}
	
}