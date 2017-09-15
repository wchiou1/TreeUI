package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Test.Shell;
import focusObject.OriginObject;

/**
 * This button will open the panel to the save manager
 * @author Wesley Chiou
 *
 */
public class SaveManagementButton extends OriginObject implements EditorImmune{
	private int width;
	private int height;
	private String text;
	public SaveManagementButton(int x, int y, int width, int height){
		this.x=0;
		this.y=0;
		this.rx=x;
		this.ry=y;
		this.width=width;
		this.height=height;
		this.text="Save Manager";
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
	
}