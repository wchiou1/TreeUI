package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import TreeUI.InputBox;
import focusObject.OriginObject;

public class FileScannerButton extends OriginObject{
	private InputBox fileNameBox;
	private int width;
	private int height;
	public FileScannerButton(int x, int y,InputBox fileNameBox){
		this.rx=x;
		this.ry=y;
		this.fileNameBox=fileNameBox;
		this.width=20;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		//Draw 3 lines in hamburger format
		g.drawLine(x+2, y+height/4, x+width-2, y+height/4);
		g.drawLine(x+2, y+height/2, x+width-2, y+height/2);
		g.drawLine(x+2, y+height/4*3, x+width-2, y+height/4*3);
		
	}
	@Override
	public boolean isMouseOver(int x, int y) {
		if(x>=this.x&&x<=this.x+width)
			if(y>=this.y&&y<=this.y+height)
				return true;
		return false;
	}
}