package Editor;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import FocusObject.OriginObject;
import FocusObject.Panel;
import TreeUI.UIElement;

public class BudPanel extends Panel{
	private OriginObject origin;
	public BudPanel(OriginObject origin){
		this.active=true;
		this.origin=origin;
	}
	
	@Override
	public void draw(Graphics g) {
		if(!active)
			return;
		g.setColor(Color.black);
		g.drawLine(x+width/2, y+height/2, origin.getX()+2, origin.getY()+2);
		g.setColor(Color.gray);
		g.fillRoundRect(x, y, width, height, 2);
		g.setColor(Color.white);
		g.fillRoundRect(x+2, y+2, width-4, height-4, 2);
		
		g.setClip(x+2, y+2, width-4, height-4);
		for(UIElement io:objectList)
			io.UDraw(g);
		g.clearClip();
	}
}