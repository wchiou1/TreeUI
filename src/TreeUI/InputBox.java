package TreeUI;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import GameLogic.RequiresTyping;
import Test.Shell;

public class InputBox extends TextButton implements RequiresTyping{
	protected boolean keyLocked = false;
	public int width,height;
	public InputBox(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}
	@Override
	public void update(int x, int y){
		
	}
	@Override
	public UIItem click(int x, int y,UIItem item) {
		if(!fleetingLock)
			text="";
		return item;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setClip(x+2, y+2, width-4, height-4);
		g.setFont(Shell.SMALL_FONT);
		if(fleetingLock)
			g.drawString(text+"|", x+2, y+2);
		else
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
	public void processUniversalKeyPress(int key, char c) {
		System.out.println("UNIVERSAL "+key+","+c);
		
	}
	
}