package uiItem;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Crowbar extends UIItem{
	@Override
	public void draw(Graphics g,int x, int y) {
		g.setColor(Color.red);
		g.drawLine(x, y, x+5, y+5);
		g.drawLine(x, y+5, x+5, y);
	}
}