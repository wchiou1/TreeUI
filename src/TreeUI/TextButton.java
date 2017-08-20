package TreeUI;


import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;


public class TextButton extends StaticText{
	
	static TrueTypeFont SMALL_FONT = new TrueTypeFont(new Font("Verdana", Font.PLAIN, 10), true); 
	public int width,height;
	public TextButton(){
		this.x=0;
		this.y=0;
		this.width=80;
		this.height=20;
	}
	@Override
	public UIItem click(int x, int y,UIItem item) {
		//Does nothing on click
		return item;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		g.setClip(x+2, y+2, width-4, height-4);
		g.setFont(SMALL_FONT);
		g.drawString(text, x+2, y+2);
		
	}
	
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
}